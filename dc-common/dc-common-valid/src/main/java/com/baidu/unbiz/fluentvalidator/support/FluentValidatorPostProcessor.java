package com.baidu.unbiz.fluentvalidator.support;

import com.baidu.unbiz.fluentvalidator.ValidUtils;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 配合{@link com.baidu.unbiz.fluentvalidator.interceptor.FluentValidateInterceptor}使用，
 * 在内部初始化FluentValidator时候，需要额外的一些初始化工作。
 * @author zhangxu
 */
public interface FluentValidatorPostProcessor {

    /**
     * 新建FluentValidator实例后，未做doValidate方法前植入一些调用的hook
     * @param fluentValidator ValidUtils，还未执行doValidate方法
     * @param methodInvocation 拦截器拦截的执行的方法
     * @return ValidUtils
     */
    ValidUtils postProcessBeforeDoValidate(ValidUtils fluentValidator, MethodInvocation methodInvocation);

}
