/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: TestServiceImpl
 * Author:   zengyufei
 * Date:     2017/12/21
 * Description: TODO
 */
package com.zyf.dc.service.impl.test;

import com.zyf.dc.base.Group;
import com.zyf.dc.base.Msg;
import com.zyf.dc.entity.test.Test;
import com.zyf.dc.service.test.TestService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class TestServiceImpl implements TestService {

  /*  @Autowired
    private TestMapper testMapper;*/

    @Override
    public Msg insert(@Validated(Group.S.Insert.class) Test test) {
        // TODO
        test.setName("ssss");
        return Msg.ok(test);
    }

    @Override
    public Msg findAll() {
        /*List<Test> all = testMapper.selectAll();
        return Msg.ok(all);*/
        return Msg.error("");
    }
}
