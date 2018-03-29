package com.baidu.unbiz.fluentvalidator.validator;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElement;

/**
 * 默认的不为空验证器
 * @author zhangxu
 */
public class NotNullValidator extends ValidatorHandler<Object> implements Validator<Object> {

    @Override
    public boolean validate(ValidatorContext context, ValidatorElement element, Object obj) {
        if (obj == null) {
            context.addErrorMsg("Input should not be NULL");
            return false;
        }
        return true;
    }

}

