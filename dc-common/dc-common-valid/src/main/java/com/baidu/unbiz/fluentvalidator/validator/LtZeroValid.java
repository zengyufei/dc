package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;

/**
 * 通用判断小于0 验证
 * @author zengyufei
 * @since 1.0.0
 */
public class LtZeroValid extends ValidatorHandler<Number> {

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, Number t) {
        if (t == null) {
            String errorMsg = "%s 为 null，无法验证。";
            context.addError(
                    ValidError.create(errorMsg).setErrorCode(500)
            );
            return false;
        }
        if (t.intValue() >= 0) {
            String errorMsg = "期望 %s 小于 0，但实际值大于等于 0。";
            context.addError(
                    ValidError.create(errorMsg).setErrorCode(500)
            );
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Msg result = ValidUtils.checkAll()
                .on(0, new LtZeroValid()).alias("privateId", "待办外键")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result);
    }
}