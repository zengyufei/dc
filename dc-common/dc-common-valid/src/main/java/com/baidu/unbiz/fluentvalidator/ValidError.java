package com.baidu.unbiz.fluentvalidator;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 内部使用的验证结果包含的错误
 * @author zhangxu
 */
public class ValidError {

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 错误日志消息
     */
    private String errorLogMsg;

    /**
     * 错误码
     */
    private int errorCode;

    private String field;

    /**
     * 对象
     */
    private Object errorData;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    /**
     * 静态构造方法
     * @param errorMsg 错误信息，其他信息可以省略，但是一个错误认为错误消息决不可少
     * @return ValidError
     */
    public static ValidError create(String errorMsg) {
        return new ValidError().setErrorMsg(errorMsg);
    }

    /**
     * 静态构造方法
     * @return ValidError
     */
    public static ValidError create() {
        return new ValidError();
    }

    /**
     * 静态构造方法
     * @param errorMsg 错误信息，其他信息可以省略，但是一个错误认为错误消息决不可少
     * @return ValidError
     */
    public static ValidError create(String errorMsg, String... values) {
        return new ValidError().setErrorMsg(errorMsg, values);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public ValidError setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ValidError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ValidError setErrorMsg(String errorMsg, String... values) {
        String[] strings = Arrays.stream(values).filter(StringUtils::isNotBlank).toArray(String[]::new);
        if (strings.length > 0) {
            this.errorMsg = String.format(errorMsg, strings);
        } else {
            this.errorMsg = errorMsg;
        }
        return this;
    }

    public String getErrorLogMsg() {
        return errorLogMsg;
    }

    public ValidError setErrorLogMsg(String errorLogMsg) {
        this.errorLogMsg = errorLogMsg;
        return this;
    }

    public ValidError setErrorLogMsg(String errorLogMsg, String... values) {
        String[] strings = Arrays.stream(values).filter(StringUtils::isNotBlank).toArray(String[]::new);
        if (strings.length > 0) {
            this.errorLogMsg = String.format(errorLogMsg, strings);
        } else {
            this.errorLogMsg = errorLogMsg;
        }
        return this;
    }

    public Object getErrorData() {
        return errorData;
    }

    public ValidError setErrorData(Object errorData) {
        this.errorData = errorData;
        return this;
    }

    public String getField() {
        return field;
    }

    public ValidError setField(String field) {
        this.field = field;
        return this;
    }
}
