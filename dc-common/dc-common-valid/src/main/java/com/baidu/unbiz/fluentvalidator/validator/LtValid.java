package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;

/**
 * 通用判断小于 验证
 * @author zengyufei
 * @since 1.0.0
 */
public class LtValid extends ValidatorHandler<Number> {

    private Number expectValue;

    public LtValid(Number expectValue) {
        this.expectValue = expectValue;
    }

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, Number t) {
        if (t == null) {
            String errorMsg = "%s 为 null，无法验证。";
            context.addError(
                    ValidError.create(errorMsg).setErrorCode(500)
            );
            return false;
        }
        if (t.doubleValue() > this.expectValue.doubleValue()) {
            String errorMsg = "期望 %s 小于 %s，但实际值为 %s。";
            context.addError(
                    ValidError.create(errorMsg, element.getTargetNameTwo(), String.valueOf(this.expectValue), String.valueOf(t))
                            .setErrorLogMsg(errorMsg, element.getTargetNameFirst(), String.valueOf(this.expectValue), String.valueOf(t))
                            .setErrorCode(500)
            );
            context.setAttribute(element.getKey(), t);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Msg result = ValidUtils.checkAll().key("privateId")
                .on(2, new LtValid(3)).alias("privateId", "待办外键")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result);
    }
}