package com.ring.web.controller;

import com.ring.common.util.Assert;
import com.ring.common.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author chaoshibin
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        Assert.isNull(null,"loginName");
        throw new BusinessException(100, "success");
    }
}
