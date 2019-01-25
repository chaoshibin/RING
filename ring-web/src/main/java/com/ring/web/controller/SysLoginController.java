package com.ring.web.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ring.api.model.sys.SysUser;
import com.ring.core.annotion.Lockable;
import com.ring.core.annotion.Lockable2;
import com.ring.core.util.ShiroUtil;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class SysLoginController extends AbstractController {
    @Autowired
    private Producer producer;

    @GetMapping(value = "/login")
    @Lockable(prefix = "key", unique = "#sysUser.id")
    public   String login(String a, SysUser sysUser) {
        return "login";
    }

    /**
     * 验证码
     */
    @RequestMapping("captcha.jpg")
    @Lockable2
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtil.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody SysUser sysUser, RedirectAttributes redirectAttributes) throws IOException {
        //本项目已实现，前后端完全分离，但页面还是跟项目放在一起了，所以还是会依赖session
        //如果想把页面单独放到nginx里，实现前后端完全分离，则需要把验证码注释掉(因为不再依赖session了)
//		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//		if(!captcha.equalsIgnoreCase(kaptcha)){
//			return R.error("验证码不正确");
//		}

        /* 获取当前的Subject,如果已经登录，则跳转到管理首页 */
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:index";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword(), false,
                getRequest().getRemoteAddr());
        try {
            /* 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
             每个Realm都能在必要时对提交的AuthenticationTokens作出反应
             所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法*/
            subject.login(token);
            if (LOG.isDebugEnabled()) {
                LOG.debug("用户[" + sysUser.getUsername() + "]进行登录验证..验证通过");
            }
        } catch (UnknownAccountException uae) {
            if (LOG.isInfoEnabled()) {
                LOG.info("用户[" + sysUser.getUsername() + "]进行登录验证..验证未通过,未知账户");
            }
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            if (LOG.isInfoEnabled()) {
                LOG.info("用户[" + sysUser.getUsername() + "]进行登录验证..验证未通过,错误的凭证");
            }
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            if (LOG.isInfoEnabled()) {
                LOG.info("用户[" + sysUser.getUsername() + "]进行登录验证..验证未通过,账户已锁定");
            }
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            if (LOG.isInfoEnabled()) {
                LOG.info("用户[" + sysUser.getUsername() + "]进行登录验证..验证未通过,错误次数过多");
            }
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            if (LOG.isInfoEnabled()) {
                LOG.info("用户[" + sysUser.getUsername() + "]进行登录验证..验证未通过,堆栈轨迹如下");
            }
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }

        /* 验证是否登录成功 */
        if (!subject.isAuthenticated()) {
            token.clear();
            return "redirect:login";
        }
        return "redirect:index";
    }


    /**
     * 退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @Lockable2
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login";
    }

}
