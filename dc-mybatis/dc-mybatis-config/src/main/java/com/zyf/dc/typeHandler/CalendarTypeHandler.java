/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: CalendarTypeHandler.java
 * Author:   zengyufei
 * Date:     2017-11-6 19:11
 * Description: mybatis 进行数据读写时自动类型转换
 */
package com.zyf.dc.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.Calendar;

/**
 * mybatis calendar 转 string 数据类型转换器
 * @author zengyufei
 * @since 1.0.0
 */
@MappedJdbcTypes({JdbcType.TIMESTAMP})
public class CalendarTypeHandler extends BaseTypeHandler<Calendar> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Calendar parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setTimestamp(i, new Timestamp(parameter.getTimeInMillis()));
        } else {
            ps.setTimestamp(i, null);
        }
    }

    @Override
    public Calendar getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp time = rs.getTimestamp(columnName);
        if (rs.wasNull()) {
            return null;
        }
        if (time != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            return calendar;
        }
        return null;
    }

    @Override
    public Calendar getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp time = rs.getTimestamp(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        if (time != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            return calendar;
        }
        return null;
    }

    @Override
    public Calendar getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp time = cs.getTimestamp(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        if (time != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            return calendar;
        }
        return null;
    }

}