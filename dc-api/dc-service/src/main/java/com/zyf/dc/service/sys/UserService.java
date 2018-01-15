package com.zyf.dc.service.sys;

import com.zyf.dc.base.Msg;
import com.zyf.dc.vo.sys.UserVo;

public interface UserService {

    Msg get(long id);

    Msg list(UserVo userVo);


}
