package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;
import org.apache.commons.lang3.EnumUtils;

/**
 * 枚举通用非空白验证
 * @author zengyufei
 * @since 1.0.0
 */
public class EnumNotEqValid extends ValidatorHandler<BaseEnum> implements Validator<BaseEnum> {

    private BaseEnum expectValue;
    private Class enumClass;

    public EnumNotEqValid(Class enumClass, BaseEnum expectValue) {
        this.expectValue = expectValue;
        this.enumClass = enumClass;
    }

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, BaseEnum t) {
        if (!this.enumClass.isEnum()) {
            context.addError(ValidError.create("传入的 class 并不是枚举类型。")
                    .setErrorCode(500));
            return false;
        }
        String name = t.toString();
        Enum targetEnum = EnumUtils.getEnum(this.enumClass, name);
        if (targetEnum == null) {
            context.addError(ValidError.create("参数枚举类型不符合要求。")
                    .setErrorCode(500));
            return false;
        } else if (this.expectValue == targetEnum) {
            String errorMsg = "期望 %s 枚举不等于 %s，但实际值为 %s";
            context.addError(ValidError.create(errorMsg, element.getTargetNameTwo(), this.expectValue.getMark(), t.getMark())
                    .setErrorCode(500)
                    .setErrorLogMsg(errorMsg, element.getTargetNameFirst(), this.expectValue.toString(), name));
            return false;
        } else {
            return true;
        }

    }

    /*public static void main(String[] args) {
        Msg result = ValidUtils.checkAll()
                .on(TodoStatus.NOT_STARTED, new EnumNotEqValid(TodoStatus.class, TodoStatus.COMPLETED)).alias("todoStatus", "待办状态")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result);
        Msg result2 = ValidUtils.checkAll()
                .on(TodoStatus.COMPLETED, new EnumNotEqValid(TodoStatus.class, TodoStatus.COMPLETED)).alias("todoStatus", "待办状态")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result2);
    }*/
}