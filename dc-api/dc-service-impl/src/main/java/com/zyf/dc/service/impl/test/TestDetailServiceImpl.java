package com.zyf.dc.service.impl.test;

import com.zyf.dc.base.Msg;
import com.zyf.dc.service.test.TestDetailService;
import org.springframework.stereotype.Service;

@Service
public class TestDetailServiceImpl implements TestDetailService {

/*    @Autowired
    private TestDetailMapper testDetailMapper;*/

    @Override
    public Msg findAll() {
        /*return Msg.ok(testDetailMapper.selectAll());*/
        return Msg.error("");
    }
}
