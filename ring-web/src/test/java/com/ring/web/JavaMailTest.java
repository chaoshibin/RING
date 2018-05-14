package com.ring.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/5/14
 * @author CHAO 修改日期：2018/5/14
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaMailTest {
    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("****@qq.com");
        message.setTo("****@qq.com");
        message.setSubject("主题");
        message.setText("测试内容");
        javaMailSender.send(message);
    }
}
