package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;

import java.math.BigDecimal;

/**
 * 通用判断大于零 验证
 * @author zengyufei
 * @since 1.0.0
 */
public class MultipleValid extends ValidatorHandler<Number> {

    private Number expectValue;

    public MultipleValid(Number expectValue) {
        this.expectValue = expectValue;
    }

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, Number t) {
        String errorMsg = "期望 %s 的值是 %s 的倍数，但实际值为 %s。";
        String tryCatchErrorMsg = "期望参数的值是 %s的倍数，但实际值为 %s。";
        if (this.expectValue == null && t == null) {
            return true;
        } else {
            if (this.expectValue == null) {
                ValidError validError;
                try {
                    validError = ValidError.create(errorMsg, element.getTargetNameTwo(), "null", t.toString())
                            .setErrorLogMsg(errorMsg, element.getTargetNameFirst(), "null", t.toString())
                            .setErrorCode(500);
                } catch (Exception e) {
                    validError = ValidError.create(tryCatchErrorMsg, "null", t.toString())
                            .setErrorLogMsg(errorMsg, "null", t.toString())
                            .setErrorCode(500);
                }
                context.addError(validError);
                context.setAttribute(element.getKey(), t);
                return false;
            } else if (t == null) {
                ValidError validError;
                try {
                    validError = ValidError.create(errorMsg, element.getTargetNameTwo(), this.expectValue.toString(), "null")
                            .setErrorLogMsg(errorMsg, element.getTargetNameFirst(), this.expectValue.toString(), "null")
                            .setErrorCode(500);
                } catch (Exception e) {
                    validError = ValidError.create(tryCatchErrorMsg, this.expectValue.toString(), "null")
                            .setErrorLogMsg(errorMsg, this.expectValue.toString(), "null")
                            .setErrorCode(500);
                }
                context.addError(validError);
                context.setAttribute(element.getKey(), t);
                return false;
            } else {
                boolean b = t.doubleValue() % this.expectValue.doubleValue() == 0;
                if (b) {
                    context.setAttribute(element.getKey(), t);
                    return true;
                } else {
                    ValidError validError;
                    try {
                        validError = ValidError.create(errorMsg, element.getTargetNameTwo(), this.expectValue.toString(), t.toString())
                                .setErrorLogMsg(errorMsg, element.getTargetNameFirst(), this.expectValue.toString(), t.toString())
                                .setErrorCode(500);
                    } catch (Exception e) {
                        validError = ValidError.create(tryCatchErrorMsg, this.expectValue.toString(), t.toString())
                                .setErrorLogMsg(errorMsg, element.getTargetNameFirst(), this.expectValue.toString(), t.toString())
                                .setErrorCode(500);
                    }
                    context.addError(validError);
                    context.setAttribute(element.getKey(), t);
                    return false;
                }
            }
        }
    }

    public static void main(String[] args) {
        Msg result = ValidUtils.checkAll().key("age").failOver()
                .on(new BigDecimal(5), new MultipleValid(2.5)).alias("age", "年龄")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result);
    }
}