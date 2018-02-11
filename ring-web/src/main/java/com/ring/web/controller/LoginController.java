package com.ring.web.controller;

import com.ring.common.util.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        throw new BusinessException(100,"success");
    }
}
