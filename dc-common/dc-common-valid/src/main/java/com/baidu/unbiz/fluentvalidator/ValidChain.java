package com.baidu.unbiz.fluentvalidator;

import java.util.Arrays;
import java.util.List;

/**
 * 多个{@link Validator}组成的调用链
 * @author zhangxu
 */
public class ValidChain {

    /**
     * 验证器list
     */
    private List<Validator> validators;

    /**
     * get validators
     * @return validators
     */
    public List<Validator> getValidators() {
        return validators;
    }

    /**
     * set validators
     * @param validators
     */
    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public String toString() {
        return "ValidChain{" +
                "validators=" + validators +
                '}';
    }

    public static ValidChain create(Validator... validators) {
        ValidChain validatorChain = new ValidChain();
        List<Validator> list = Arrays.asList(validators);
        validatorChain.setValidators(list);
        return validatorChain;
    }
}
