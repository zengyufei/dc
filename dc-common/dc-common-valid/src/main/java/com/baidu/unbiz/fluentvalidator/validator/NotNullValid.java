package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;

/**
 * 通用非 null 验证
 * @author zengyufei
 * @since 1.0.0
 */
public class NotNullValid extends ValidatorHandler<Object> implements Validator<Object> {

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, Object t) {
        if (t != null) {
            return true;
        }
        String errorMsg = "期望 %s 不为空，但实际为 null。";
        context.addError(
                ValidError.create(errorMsg).setErrorCode(500)
        );
        return false;
    }

    public static void main(String[] args) {
        Object t = null;
        Msg result = ValidUtils.checkAll()
                .on(t, new NotNullValid()).alias("privateId", "待办外键")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result);
    }
}