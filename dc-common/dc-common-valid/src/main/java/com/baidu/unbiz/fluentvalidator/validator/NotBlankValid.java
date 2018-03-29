package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;
import org.apache.commons.lang.StringUtils;

/**
 * 字符串通用非空白验证
 * @author zengyufei
 * @since 1.0.0
 */
public class NotBlankValid extends ValidatorHandler<String> implements Validator<String> {

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, String t) {
        if (StringUtils.isNotBlank(t)) {
            return true;
        }
        if (t == null) {
            String errorMsg = "期望 %s 不为 null 或 不为空字符串，实际值为 null。";
            context.addError(ValidError.create(errorMsg)
                    .setErrorCode(500));
            return false;
        }
        String errorMsg = "期望 %s 不为 null 或 不为空字符串，实际值为 %s。";
        context.addError(ValidError.create(errorMsg, element.getTargetNameTwo(), "".equals(t) ? "空字符串" : t)
                .setErrorCode(500)
                .setErrorLogMsg(errorMsg, element.getTargetNameFirst(), "".equals(t) ? "空字符串" : t));
        context.setAttribute(element.getKey(), t);
        return false;
    }

    public static void main(String[] args) {
        Msg result = ValidUtils.checkAll().key("privateId")
                .on("", new NotBlankValid()).alias("privateId", "待办外键")
                .doValidate()
                .result(Msg.toMsg());
        System.out.println(result);
    }
}