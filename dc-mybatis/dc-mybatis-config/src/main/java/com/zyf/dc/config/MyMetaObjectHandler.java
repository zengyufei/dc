package com.zyf.dc.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自定义填充公共 name 字段
 */
public class MyMetaObjectHandler extends MetaObjectHandler {

    /**
     * 字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 当前用户注入
        Object createId = getFieldValByName("createId", metaObject);//mybatis-plus版本2.0.9+
        if (createId != null && Integer.parseInt(createId.toString()) == 0) {
            setFieldValByName("createId", 1, metaObject);//mybatis-plus版本2.0.9+
            setFieldValByName("modifyId", 1, metaObject);//mybatis-plus版本2.0.9+
        }
        // 创建时间注入
        Object createTime = getFieldValByName("createTime", metaObject);//mybatis-plus版本2.0.9+
        if (createTime == null) {
            setFieldValByName("createTime", new Date(), metaObject);//mybatis-plus版本2.0.9+
        }
        // 修改时间注入
        Object updateTime = getFieldValByName("updateTime", metaObject);//mybatis-plus版本2.0.9+
        if (updateTime == null) {
            setFieldValByName("updateTime", new Date(), metaObject);//mybatis-plus版本2.0.9+
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 当前用户注入
        setFieldValByName("modifyId", 1, metaObject);//mybatis-plus版本2.0.9+
    }

}