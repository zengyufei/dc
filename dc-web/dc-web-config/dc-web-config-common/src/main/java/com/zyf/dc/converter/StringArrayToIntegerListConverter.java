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
public class StringArrayToIntegerListConverter implements Converter<String[], List<Integer>> {

    @Override
    public List<Integer> convert(final String[] source) {
        String[] newSource = source;
        if (source.length == 0) {
            return new ArrayList<>();
        }
        if (source.length == 1) {
            newSource = source[0].split(",");
        }
        return Arrays
                .stream(newSource)
                .filter(StringUtils::isNotBlank) // 过滤 null 和空字符 的每个元素
                .map(Integer::parseInt) // 将 每个元素 转型为 int
                .collect(Collectors.toList());
    }

}