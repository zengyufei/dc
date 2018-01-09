/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: GenericEnumTypeHandler.java
 * Author:   zengyufei
 * Date:     2017-11-6 19:11
 * Description: mybatis 进行数据读写时自动类型转换
 */
package com.zyf.dc.typeHandler;

import com.zyf.dc.base.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis enum 转 string 枚举类型转换器
 * @author zengyufei
 * @since 1.0.0
 */
public class GenericEnumTypeHandler<E extends BaseEnum> extends BaseTypeHandler<E> {

    private Class<E> type;

    private E[] enums;

    public GenericEnumTypeHandler() {
    }

    public GenericEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getName() + " does not represent an enum type.");
        }
    }

    private E loadEnum(int index) {
        for (E e : enums) {
            if (e.getIndex() == index) {
                return e;
            }
        }
        throw new IllegalArgumentException(type.getName() + "  unknown enumerated type  index:" + index);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIndex());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getObject(columnName) == null) {
            return null;
        }
        int index = rs.getInt(columnName);
        return loadEnum(index);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getObject(columnIndex) == null) {
            return null;
        }
        int index = rs.getInt(columnIndex);
        return loadEnum(index);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getObject(columnIndex) == null) {
            return null;
        }
        int index = cs.getInt(columnIndex);
        return loadEnum(index);
    }

}