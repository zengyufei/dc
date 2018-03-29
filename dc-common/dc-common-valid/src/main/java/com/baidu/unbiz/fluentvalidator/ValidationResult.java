package com.baidu.unbiz.fluentvalidator;

import com.baidu.unbiz.fluentvalidator.annotation.NotThreadSafe;
import com.baidu.unbiz.fluentvalidator.util.CollectionUtil;

import java.util.List;

/**
 * 内部用验证结果
 * @author zhangxu
 * @see ValidError
 */
@NotThreadSafe
public class ValidationResult {

    /**
     * 是否成功，一旦发生错误，即置为false，默认为{@value}
     */
    private boolean isSuccess = true;

    /**
     * 验证的值
     */
    private Object invalidValue;

    /**
     * 验证错误
     */
    private List<ValidError> errors;

    /**
     * key的 value
     */
    private Object keyValue;

    /**
     * 验证总体耗时，指通过<code>ValidUtils.doValidate(..)</code>真正“及时求值”过程中的耗时
     */
    private int timeElapsed;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * 添加错误
     * @param error 错误
     */
    public void addError(ValidError error) {
        if (CollectionUtil.isEmpty(errors)) {
            errors = CollectionUtil.createArrayList(Const.INITIAL_CAPACITY);
        }
        errors.add(error);
    }

    /**
     * 设置错误，保持一个
     * @param error 错误
     */
    public void setError(ValidError error) {
        if (CollectionUtil.isEmpty(errors)) {
            errors = CollectionUtil.createArrayList(Const.INITIAL_CAPACITY);
        }
        errors.clear();
        errors.add(error);
    }

    public List<ValidError> getErrors() {
        return errors;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public Object getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Object keyValue) {
        this.keyValue = keyValue;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(Object invalidValue) {
        this.invalidValue = invalidValue;
    }
}
