package com.baidu.unbiz.fluentvalidator.validator.element;

import com.baidu.unbiz.fluentvalidator.ValidUtils;
import com.baidu.unbiz.fluentvalidator.able.ListAble;
import com.baidu.unbiz.fluentvalidator.util.CollectionUtil;

import java.util.List;

/**
 * 在{@link ValidUtils}内部调用使用的验证器链包装类，用于以下两种场景：
 * <ul>
 * <li>当启用注解验证时候，多个验证器共同作用于一个对象</li>
 * <li>一个列表或者数组在一个验证器上</li>
 * </ul>
 * @author zhangxu
 */
public abstract class ValidatorElementComposite implements ListAble<ValidatorElement> {

    /**
     * 待验证对象机器验证器列表
     */
    protected List<ValidatorElement> validatorElements;

    /**
     * Create
     * @param validatorElements 待验证对象机器验证器列表
     */
    public ValidatorElementComposite(List<ValidatorElement> validatorElements) {
        this.validatorElements = validatorElements;
    }

    /**
     * 列表长度
     * @return 长度
     */
    public int size() {
        if (CollectionUtil.isEmpty(validatorElements)) {
            return 0;
        }
        return validatorElements.size();
    }

    @Override
    public abstract String toString();

    @Override
    public List<ValidatorElement> getAsList() {
        return validatorElements;
    }

}
