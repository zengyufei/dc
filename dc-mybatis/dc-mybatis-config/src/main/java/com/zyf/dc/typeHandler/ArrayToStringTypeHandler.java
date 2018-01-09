/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: ArrayToStringTypeHandler.java
 * Author:   zengyufei
 * Date:     2017-11-6 19:11
 * Description: mybatis 进行数据读写时自动类型转换
 */
package com.zyf.dc.typeHandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * mybatis list 转 string 数据类型转换器
 * @author zengyufei
 * @since 1.0.0
 */
@MappedTypes(List.class)
@MappedJdbcTypes({JdbcType.VARCHAR})
public class ArrayToStringTypeHandler extends BaseTypeHandler<String[]> {

    private static final String ARRAY_SIGN = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, StringUtils.join(parameter, ARRAY_SIGN));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        if (rs.getObject(columnName) == null) {
            return null;
        }
        return StringUtils.split(rs.getString(columnName), ARRAY_SIGN);
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        if (rs.getObject(columnIndex) == null) {
            return null;
        }
        return StringUtils.split(rs.getString(columnIndex), ARRAY_SIGN);
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) {
            return null;
        }
        if (cs.getObject(columnIndex) == null) {
            return null;
        }
        return StringUtils.split(cs.getString(columnIndex), ARRAY_SIGN);
    }

}