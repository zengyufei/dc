/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: DateConverConfiguration.java
 * Author:   zengyufei
 * Date:     2017-11-6 19:11
 * Description: 绑定前端 date 类型参数转换器的注册
 */
package com.zyf.dc;

import com.zyf.dc.config.convers.StringToCalendarConverter;
import com.zyf.dc.config.convers.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * date 类型参数绑定注册
 * @author zengyufei
 * @create 2017-11-6
 * @since 1.0.0
 */
@Configuration
public class DateConverConfiguration {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 增加字符串转日期的功能
     */
    @PostConstruct
    public void initEditableValidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
                .getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer
                    .getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());
            genericConversionService.addConverter(new StringToCalendarConverter());
        }
    }

}