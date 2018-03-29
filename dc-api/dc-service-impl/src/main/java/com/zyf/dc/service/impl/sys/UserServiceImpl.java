package com.zyf.dc.service.impl.sys;

import com.zyf.dc.base.Msg;
import com.zyf.dc.entity.sys.UserAccountInfo;
import com.zyf.dc.service.sys.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Msg list(UserAccountInfo user) {
        return Msg.ok("hhahaha");
    }
}
