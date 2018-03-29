package com.zyf.dc.converter;

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
    private static final String DATE_FORMAT2 = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
    private static final String FORMAT_SYMBOL_DATE = "-";
    private static final String FORMAT_TIME_SYMBOL = ":";
    private static final String FORMAT_TIME_SYMBOL21 = "T";
    private static final String FORMAT_TIME_SYMBOL22 = "Z";
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
                    if (source.contains(FORMAT_TIME_SYMBOL21) && source.contains(FORMAT_TIME_SYMBOL22)) {
                        formatter = new SimpleDateFormat(DATE_FORMAT2);
                    } else {
                        formatter = new SimpleDateFormat(DATE_FORMAT);
                    }
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