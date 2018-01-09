package com.zyf.dc.mapper.test;

import com.zyf.dc.entity.test.Test;

import java.util.List;

public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    Test selectByPrimaryKey(Integer id);

    List<Test> selectAll();

    int updateByPrimaryKey(Test record);
}