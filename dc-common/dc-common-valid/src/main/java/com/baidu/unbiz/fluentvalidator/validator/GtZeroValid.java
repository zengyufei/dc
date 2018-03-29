package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;

/**
 * 通用判断大于零 验证
 * @author zengyufei
 * @since 1.0.0
 */
public class GtZeroValid extends ValidatorHandler<Number> {

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, Number t) {
        if (t == null) {
            String errorMsg = "%s 为 null，无法验证。";
            context.addError(
                    ValidError.create(errorMsg).setErrorCode(500)
            );
            return false;
        }
        if (t.intValue() <= 0) {
            String errorMsg = "期望 %s 大于 0，但实际值小于等于 0。";
            context.addError(
                    ValidError.create(errorMsg).setErrorCode(500)
            );
            context.setAttribute(element.getKey(), t);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Msg result = ValidUtils.checkAll().key("id")
                .on(-4, ValidChain.create(new GtZeroValid(), new EqValid(-1))).alias("id", "主键")
                .on(-1, new GtZeroValid()).alias("name", "名称")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result.getCode());
        System.out.println(result.getData());
        System.out.println(result.getLogMessage());
        System.out.println(result.getInvalidData());
        System.out.println(result.getMessage());
        System.out.println(result.getInvalidData().toJson());
    }
}