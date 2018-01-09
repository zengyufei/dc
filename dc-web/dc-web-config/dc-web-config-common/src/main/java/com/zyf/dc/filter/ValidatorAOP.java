package com.zyf.dc.filter;
/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: ValidatorAOP.java
 * Author:   zengyufei
 * Date:     2017-11-6 19:11
 * Description: service 参数验证拦截器，基于 JSR303
 */

import com.zyf.dc.utils.ValidUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * service 参数验证拦截器，基于 JSR303
 * @author zengyufei
 * @since 1.0.0
 */
@Aspect
@Component
public class ValidatorAOP {

    @Autowired
    private ValidUtil validUtil;

    /**
     *  定义拦截规则：拦截  com.evergrande.itmc.servic  包下面的所有类中，有 @Service 注解的方法。
     */
    @Pointcut("execution(* com.zyf.dc.service..*(..)) " +
            "and @annotation(org.springframework.stereotype.Service) " +
            "and @annotation(org.springframework.validation.annotation.Validated)")
    public void controllerMethodPointcut() {
    }

    /**
     *  拦截器具体实现
     */
    @Around("controllerMethodPointcut()") // 指定拦截器规则；也可以直接把 “execution(* com.xjj.........)” 写进这里
    public Object Interceptor(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] argAnnotations = method.getParameterAnnotations();
        Object[] args = pjp.getArgs();

        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : argAnnotations[i]) {
                if (Validated.class.isInstance(annotation)) {
                    Validated validated = (Validated) annotation;
                    Class<?>[] groups = validated.value();
                    validUtil.validAndReturnFirstErrorTips(args[i], groups);
                }
            }
        }
        try {
            return pjp.proceed(args);
        } catch (Throwable throwable) {
        }
        return true;
    }

}
