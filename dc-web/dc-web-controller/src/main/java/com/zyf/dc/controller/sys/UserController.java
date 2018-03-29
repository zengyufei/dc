package com.zyf.dc.controller.sys;

import com.zyf.dc.base.Msg;
import com.zyf.dc.entity.sys.UserAccountInfo;
import com.zyf.dc.service.sys.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("index")
    public Msg index() {
        return userService.list(new UserAccountInfo());
    }

}
