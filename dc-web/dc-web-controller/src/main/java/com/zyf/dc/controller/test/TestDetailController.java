/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: TestController
 * Author:   zengyufei
 * Date:     2017/12/21
 * Description: TODO
 */
package com.zyf.dc.controller.test;

import com.zyf.dc.base.Msg;
import com.zyf.dc.service.test.TestDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("testDetail")
public class TestDetailController {

    @Autowired
    private TestDetailService testDetailService;

    @GetMapping("index")
    public Msg index() {
        return testDetailService.findAll();
    }

}
