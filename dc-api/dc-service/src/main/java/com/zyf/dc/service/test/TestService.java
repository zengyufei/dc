package com.zyf.dc.service.test;

import com.zyf.dc.base.Msg;
import com.zyf.dc.entity.test.Test;

public interface TestService {

    Msg insert(Test test);

    Msg findAll();

}
