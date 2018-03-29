package com.zyf.dc;

import com.zyf.dc.converter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * date 类型参数绑定注册
 * @author zengyufei
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
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) this.handlerAdapter
                .getWebBindingInitializer();
        if (initializer != null && null != initializer.getConversionService()) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer
                    .getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());
            genericConversionService.addConverter(new StringToCalendarConverter());
            genericConversionService.addConverter(new StringToIntegerListConverter());
            genericConversionService.addConverter(new StringToListConverter());
            genericConversionService.addConverter(new StringToBigDecimalConverter());

            genericConversionService.addConverter(new StringArrayToIntegerListConverter());
            genericConversionService.addConverter(new StringArrayToStringListConverter());
        }
    }

}