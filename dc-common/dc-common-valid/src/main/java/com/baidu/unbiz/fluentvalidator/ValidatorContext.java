package com.baidu.unbiz.fluentvalidator;

import com.baidu.unbiz.fluentvalidator.util.CollectionUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证器执行调用中的上下文
 * <p/>
 * 在验证过程中{@link Validator# (ValidatorContext, Object)}以及{@link Validator#accept(ValidatorContext,
 * Object)}使用，主要用途在于:
 * <ul>
 * <li>1. 调用发起点可以放入一些变量或者对象，所有验证器均可以共享使用。</li>
 * <li>2. 调用发起点可以做闭包，将一些验证过程中可以复用的结果对象缓存住（一般是比较耗时才可以获取到的），在发起点可以获取。</li>
 * <li>3. 代理添加错误信息</li>
 * </ul>
 * @author zhangxu
 * @see Closure
 * @see Result
 */
public class ValidatorContext {

    /**
     * 验证器均可以共享使用的属性键值对
     */
    private Map<String, Object> attributes;

    /**
     * 调用发起点注入的闭包
     */
    private Map<String, Closure> closures;

    /**
     * 调用结果对象
     */
    public ValidationResult result;

    /**
     * 传值的 key
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 添加错误信息
     * @param msg 错误信息
     */
    public void addErrorMsg(String msg) {
        result.addError(ValidError.create(msg));
    }

    /**
     * 添加错误信息
     * @param validError 验证错误
     */
    public void addError(ValidError validError) {
        String errorMsg = validError.getErrorMsg();
        Pattern compile = Pattern.compile("%s");
        Matcher matcher = compile.matcher(errorMsg);
        int count = 0;
        while ((matcher.find())) {
            ++count;
        }
        if (count == 1) {
            if (StringUtils.isNotBlank(errorMsg)) {
                if (StringUtils.isBlank(this.getAliasNameFirst()) && StringUtils.isBlank(this.getAliasNameTwo())) {
                    validError.setErrorMsg(errorMsg, "参数");
                    validError.setErrorLogMsg(errorMsg, "参数");
                }
                if (StringUtils.isNotBlank(this.getAliasNameTwo())) {
                    validError.setErrorMsg(errorMsg, this.getAliasNameTwo());
                }
                if (StringUtils.isNotBlank(this.getAliasNameFirst())) {
                    validError.setErrorLogMsg(errorMsg, this.getAliasNameFirst());
                }
            }
        }
        result.addError(validError);
    }

    /**
     * 添加错误信息
     * @param validError 验证错误
     */
    public void setError(ValidError validError) {
        result.setError(validError);
    }

    /**
     * 获取属性
     * @param key 键
     * @return 值
     */
    public Object getAttribute(String key) {
        if (attributes != null && !attributes.isEmpty()) {
            return attributes.get(key);
        }
        return null;
    }

    /**
     * 根据类型<t>T</t>直接获取属性值
     * @param key 键
     * @param clazz 值类型
     * @return 值
     */
    public <T> T getAttribute(String key, Class<T> clazz) {
        return (T) getAttribute(key);
    }

    public void setAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = CollectionUtil.createHashMap(Const.INITIAL_CAPACITY);
        }
        if (key != null) {
            attributes.put(key, value);
        }
    }

    public void setAttribute(Object value) {
        if (attributes == null) {
            attributes = CollectionUtil.createHashMap(Const.INITIAL_CAPACITY);
        }
        attributes.put(getKey(), value);
    }

    /**
     * 获取闭包
     * @param key 闭包名称
     * @return 闭包
     */
    public Closure getClosure(String key) {
        if (closures != null && !closures.isEmpty()) {
            return closures.get(key);
        }
        return null;
    }

    /**
     * 注入闭包
     * @param key 闭包名称
     * @param closure 闭包
     */
    public void setClosure(String key, Closure closure) {
        if (closures == null) {
            closures = CollectionUtil.createHashMap(Const.INITIAL_CAPACITY);
        }
        closures.put(key, closure);
    }

    /**
     * 设置验证结果
     * @param result 验证结果
     */
    public void setResult(ValidationResult result) {
        this.result = result;
    }

    public String getAliasNameFirst() {
        return targetNameFirst;
    }

    public void setTargetNameFirst(String targetNameFirst) {
        this.targetNameFirst = targetNameFirst;
    }

    public String getAliasNameTwo() {
        return targetNameTwo;
    }

    public void setTargetNameTwo(String targetNameTwo) {
        this.targetNameTwo = targetNameTwo;
    }
}
