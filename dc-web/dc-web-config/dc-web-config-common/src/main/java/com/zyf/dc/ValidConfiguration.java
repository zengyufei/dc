/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: ValidConfiguration
 * Author:   zengyufei
 * Date:     2017/11/23
 * Description: jsr 303 配置类
 */


package com.zyf.dc;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * jsr 303 配置类
 * @author zengyufei
 * @create 2017/11/23
 * @since 1.0.0
 */
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

}
