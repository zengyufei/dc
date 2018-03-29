package com.zyf.dc.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串转list的转换器
 * @author zengyufei
 * @since 1.0.0
 */
public class StringToIntegerListConverter implements Converter<String, List<Integer>> {

    @Override
    public List<Integer> convert(String source) {
        if (StringUtils.isBlank(source)) {
            return new ArrayList<>();
        }
        return Arrays
                .stream(source.split(","))
                .filter(StringUtils::isNotBlank)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

}