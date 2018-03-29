package com.baidu.unbiz.fluentvalidator.jsr303;

import com.baidu.unbiz.fluentvalidator.ValidError;

import javax.validation.ConstraintViolation;

/**
 * {@link ConstraintViolation}到{@link ValidError}的转换器
 * @author zhangxu
 */
public interface ConstraintViolationTransformer {

    /**
     * {@link ConstraintViolation}到{@link ValidError}的转换
     * @param constraintViolation hibernate的错误
     * @return fluent-validator框架的错误ValidationError
     */
    ValidError toValidationError(ConstraintViolation constraintViolation);

}
