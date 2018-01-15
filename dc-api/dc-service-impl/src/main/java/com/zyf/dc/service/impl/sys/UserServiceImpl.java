package com.zyf.dc.service.impl.sys;

import com.zyf.dc.base.Msg;
import com.zyf.dc.mapper.sys.UserVoMapper;
import com.zyf.dc.service.sys.UserService;
import com.zyf.dc.vo.sys.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserVoMapper userVoMapper;

    @Override
    public Msg get(long id) {
        UserVo userVo = userVoMapper.get(id);
        return Msg.ok(userVo);
    }

    @Override
    public Msg list(UserVo userVo) {
        List<UserVo> list = null;
        //List<UserVo> list = userVoMapper.list(userVo);
        return Msg.ok(list);
    }

}
