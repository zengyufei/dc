package com.zyf.dc.service;

import com.zyf.dc.entity.sys.LoginDetail;
import com.zyf.dc.entity.sys.TokenDetail;

/**
 * @version V1.0.0
 */
public interface LoginService {

    LoginDetail getLoginDetail(String username);

    String generateToken(TokenDetail tokenDetail);

}
