package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.ValidError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;
import org.apache.commons.lang3.EnumUtils;

/**
 * 枚举通用非空白验证
 * @author zengyufei
 * @since 1.0.0
 */
public class EnumValid extends ValidatorHandler<Enum> implements Validator<Enum> {

    private Class enumClass;

    public EnumValid(Class enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, Enum t) {
        ValidError validationError = new ValidError();
        if (!this.enumClass.isEnum()) {
            context.addError(validationError.setErrorMsg("传入的 class 并不是枚举类型。")
                    .setErrorCode(500));
            return false;
        }
        String name = t.name();
        Enum anEnum = EnumUtils.getEnum(this.enumClass, name);
        if (anEnum == null) {
            context.addError(validationError.setErrorMsg("参数枚举类型不符合要求。")
                    .setErrorCode(500));
            return false;
        } else {
            return true;
        }

    }

/*    public static void main(String[] args) {
        Msg result = ValidUtils.checkAll()
                .on(TodoStatus.COMPLETED, new EnumValid(TodoStatus.class)).alias("todoStatus", "待办状态")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result);
        Msg result2 = ValidUtils.checkAll()
                .on(TodoType.PROBLEM, new EnumValid(TodoStatus.class)).alias("todoStatus", "待办状态")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result2);
    }*/
}