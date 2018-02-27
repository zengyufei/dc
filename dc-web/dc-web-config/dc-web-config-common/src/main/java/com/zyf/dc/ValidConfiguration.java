/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: ValidConfiguration
 * Author:   zengyufei
 * Date:     2017/11/23
 * Description: jsr 303 配置类
 */


package com.zyf.dc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * jsr 303 配置类
 * @author zengyufei
 * @since 1.0.0
 */
@ControllerAdvice
@Configuration
public class ValidConfiguration extends WebMvcConfigurerAdapter {

    @Value("${spring.messages.basename}")
    private String basename;
    @Value("${spring.messages.encoding}")
    private String encoding;

    /**
     * 方法级别的单个参数验证
     * @return MethodValidationPostProcessor 方法前置注册
     * 使用需要在类上面注解 @Validated
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    @Primary
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(false);
        resourceBundleMessageSource.setDefaultEncoding(encoding);
        resourceBundleMessageSource.setBasenames(basename.split(","));
        return resourceBundleMessageSource;
    }

    @Bean
    @Primary
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setProviderClass(HibernateValidator.class);
        validatorFactoryBean.setValidationMessageSource(messageSource());
        return validatorFactoryBean;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectMapper objectMapper = builder.build();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);// 忽略 transient 修饰的属性
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        super.configureMessageConverters(converters);
    }

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
