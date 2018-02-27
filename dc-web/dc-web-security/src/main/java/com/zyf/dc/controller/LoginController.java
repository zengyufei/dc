package com.zyf.dc.controller;

import com.zyf.dc.base.Msg;
import com.zyf.dc.entity.sys.LoginDetail;
import com.zyf.dc.entity.sys.TokenDetail;
import com.zyf.dc.entity.vo.Data;
import com.zyf.dc.entity.vo.RequestLoginUser;
import com.zyf.dc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @version V1.0.0
 */
@RestController
public class LoginController {

    private final LoginService loginService;

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(@Valid RequestLoginUser requestLoginUser, BindingResult bindingResult) {
        // 检查有没有输入用户名密码和格式对不对
        if (bindingResult.hasErrors()) {
            return Msg.error(400, "", "缺少参数或者参数格式不对");
        }

        LoginDetail loginDetail = loginService.getLoginDetail(requestLoginUser.getUsername());
        Msg ifLoginFail = checkAccount(requestLoginUser, loginDetail);
        if (ifLoginFail != null) {
            return ifLoginFail;
        }
        return Msg.ok(200, "", new Data().addObj(tokenHeader, loginService.generateToken((TokenDetail) loginDetail)));
    }

    private Msg checkAccount(RequestLoginUser requestLoginUser, LoginDetail loginDetail) {
        if (loginDetail == null) {
            return Msg.error(434, "", "缺少参数或者参数格式不对");
        } else {
            if (loginDetail.enable() == false) {
                return Msg.error(452, "", "账号在黑名单中");
            }
            if (!loginDetail.getPassword().equals(requestLoginUser.getPassword())) {
                return Msg.error(438, "", "密码错误！");
            }
        }
        return null;
    }

}
