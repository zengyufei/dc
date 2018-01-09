/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: JsonConfiguration
 * Author:   zengyufei
 * Date:     2017/11/23
 * Description: json 配置类
 */
package com.zyf.dc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * json 配置类
 * @author zengyufei
 * @create 2017/11/23
 * @since 1.0.0
 */
@Configuration
public class JsonConfiguration {

    /**
     * 关闭 FAIL_ON_EMPTY_BEANS
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Bean
    public HttpMessageConverters mappingJackson2HttpMessageConverter() {
        // 使用 fastJson 才能格式化 LocalDateTime
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat, // 打印漂亮的 json
                SerializerFeature.DisableCircularReferenceDetect, // 关闭循环引用检测
                SerializerFeature.QuoteFieldNames,  // 输出 key 时是否使用双引号, 默认为 true
                SerializerFeature.WriteMapNullValue,  // 是否输出值为 null 的字段, 默认为 false
                SerializerFeature.WriteNullNumberAsZero,  // 数值字段如果为 null, 输出为 0, 而非 null
                SerializerFeature.WriteNullListAsEmpty,  // List 字段如果为 null, 输出为 [], 而非 null
                SerializerFeature.WriteNullStringAsEmpty,  // 字符类型字段如果为 null, 输出为”“, 而非 null
                SerializerFeature.WriteNullBooleanAsFalse  // Boolean 字段如果为 null, 输出为 false, 而非 null
        );
        converter.setFastJsonConfig(fastJsonConfig);
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(fastMediaTypes);
        return new HttpMessageConverters((HttpMessageConverter<?>) converter);
    }


}
