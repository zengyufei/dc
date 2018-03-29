package com.baidu.unbiz.fluentvalidator.validator.element;

import com.baidu.unbiz.fluentvalidator.ValidUtils;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.able.ListAble;
import com.baidu.unbiz.fluentvalidator.able.ToStringable;

import java.util.Arrays;
import java.util.List;

/**
 * 在{@link ValidUtils}内部调用使用的验证器链包装类
 * @author zhangxu
 */
public class ValidatorElement implements ListAble<ValidatorElement> {

    /**
     * 验证器
     */
    private Validator validator;

    /**
     * 待验证对象
     */
    private Object target;

    /**
     * 互传对象的 key
     */
    private String key;

    /**
     * 待验证对象的别名 1
     */
    private String targetNameFirst;
    /**
     * 待验证对象的别名 2
     */
    private String targetNameTwo;

    /**
     * 自定义的打印信息回调
     */
    private ToStringable customizedToString;

    /**
     * 惰性，是否进行验证
     */
    private boolean check = true;

    /**
     * create
     * @param target 待验证对象
     * @param validator 验证器
     */
    public ValidatorElement(Object target, Validator validator) {
        this.target = target;
        this.validator = validator;
    }

    /**
     * create
     * @param target 待验证对象
     * @param validator 验证器
     * @param customizedToString 自定义的打印信息回调
     */
    public ValidatorElement(Object target, Validator validator,
                            ToStringable customizedToString) {
        this.target = target;
        this.validator = validator;
        this.customizedToString = customizedToString;
    }

    public Object getTarget() {
        return target;
    }

    public Validator getValidator() {
        return validator;
    }

    @Override
    public List<ValidatorElement> getAsList() {
        return Arrays.asList(this);
    }

    @Override
    public String toString() {
        if (customizedToString != null) {
            return customizedToString.toString();
        }
        return String.format("%s@%s", target == null ? "null" : target.getClass().getSimpleName(), validator);
    }

    public String getTargetNameFirst() {
        return targetNameFirst;
    }

    public void setTargetNameFirst(String targetNameFirst) {
        this.targetNameFirst = targetNameFirst;
    }

    public String getTargetNameTwo() {
        return targetNameTwo;
    }

    public void setTargetNameTwo(String targetNameTwo) {
        this.targetNameTwo = targetNameTwo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
