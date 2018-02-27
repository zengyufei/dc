package com.zyf.dc.controller.test;

import com.zyf.dc.base.Group;
import com.zyf.dc.base.Msg;
import com.zyf.dc.entity.test.Test;
import com.zyf.dc.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("insert")
    public Msg insert(@Validated(Group.C.Insert.class) Test test) {
        return testService.insert(test);
    }

    @GetMapping("index")
    public Msg index() {
        return testService.findAll();
    }

}
