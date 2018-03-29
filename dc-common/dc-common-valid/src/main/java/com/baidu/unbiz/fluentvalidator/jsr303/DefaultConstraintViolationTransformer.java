package com.baidu.unbiz.fluentvalidator.jsr303;

import com.baidu.unbiz.fluentvalidator.ValidError;

import javax.validation.ConstraintViolation;

/**
 * 默认的{@link ConstraintViolation}到{@link ValidError}的转换器
 * @author zhangxu
 */
public class DefaultConstraintViolationTransformer implements ConstraintViolationTransformer {

    @Override
    public ValidError toValidationError(ConstraintViolation constraintViolation) {
        return ValidError.create(constraintViolation.getMessage())
                .setField(constraintViolation.getPropertyPath().toString())
                .setErrorData(constraintViolation.getInvalidValue());
    }

}
