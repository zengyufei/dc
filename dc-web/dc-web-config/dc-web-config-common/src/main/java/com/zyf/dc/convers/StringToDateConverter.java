/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: StringToDateConverter
 * Author:   zengyufei
 * Date:     2017-11-6 19:11
 * Description: 字符串转日期的转换器
 */
package com.zyf.dc.convers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串转日期的转换器
 * @author zengyufei
 * @since 1.0.0
 */
public class StringToDateConverter implements Converter<String, Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String FORMAT_SYMBOL_DATE = "-";
    private static final String FORMAT_TIME_SYMBOL = ":";
    private static final String FORMAT_REGEX = "^\\d+$";

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = source.trim();
        try {
            if (source.contains(FORMAT_SYMBOL_DATE)) {
                SimpleDateFormat formatter;
                if (source.contains(FORMAT_TIME_SYMBOL)) {
                    formatter = new SimpleDateFormat(DATE_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
                }
                Date dtDate = formatter.parse(source);
                return dtDate;
            } else if (source.matches(FORMAT_REGEX)) {
                Long lDate = Long.valueOf(source);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", source));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", source));
    }

}