package com.zyf.dc.controller.login;

import com.zyf.dc.base.Msg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRegController {

    @RequestMapping("/login_page")
    public Msg loginPage() {
        return Msg.error("error", "尚未登录，请登录!");
    }
}