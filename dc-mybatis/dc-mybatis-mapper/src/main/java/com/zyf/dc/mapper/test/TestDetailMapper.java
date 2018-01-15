package com.zyf.dc.mapper.test;

import com.zyf.dc.entity.test.TestDetail;
import java.util.List;

public interface TestDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestDetail record);

    TestDetail selectByPrimaryKey(Integer id);

    List<TestDetail> selectAll();

    int updateByPrimaryKey(TestDetail record);
}