package com.zyf.dc.service.impl;

import com.zyf.dc.entity.sys.LoginDetail;
import com.zyf.dc.entity.sys.TokenDetail;
import com.zyf.dc.mapper.UserMapper;
import com.zyf.dc.service.LoginService;
import com.zyf.dc.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version V1.0.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;
    private final TokenUtils tokenUtils;

    @Autowired
    public LoginServiceImpl(UserMapper userMapper, TokenUtils tokenUtils) {
        this.userMapper = userMapper;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public LoginDetail getLoginDetail(String username) {
        return userMapper.getUserFromDatabase(username);
    }

    @Override
    public String generateToken(TokenDetail tokenDetail) {
        return tokenUtils.generateToken(tokenDetail);
    }
}
