package com.baidu.unbiz.fluentvalidator.support;

import com.baidu.unbiz.fluentvalidator.ValidUtils;
import com.baidu.unbiz.fluentvalidator.util.Preconditions;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

/**
 * 将拦截器拦截的方法名称注入<code>ValidUtils</code>上下文
 * @author zhangxu
 */
public class MethodNameFluentValidatorPostProcessor implements FluentValidatorPostProcessor {

    /**
     * 拦截的方法名
     */
    public static final String KEY_METHOD_NAME = "_method_name";

    /**
     * 拦截的类Class
     */
    public static final String KEY_TARGET_CLASS_SIMPLE_NAME = "_target_class_simple_name";

    @Override
    public ValidUtils postProcessBeforeDoValidate(ValidUtils fluentValidator, MethodInvocation
            methodInvocation) {
        Preconditions.checkNotNull(methodInvocation, "MethodInvocation should not be NULL");
        String methodName = methodInvocation.getMethod().getName();
        Class<?> targetClass = AopUtils.getTargetClass(methodInvocation.getThis());
        fluentValidator.putAttribute2Context(KEY_METHOD_NAME, methodName);
        fluentValidator.putAttribute2Context(KEY_TARGET_CLASS_SIMPLE_NAME, targetClass);
        return fluentValidator;
    }

}
