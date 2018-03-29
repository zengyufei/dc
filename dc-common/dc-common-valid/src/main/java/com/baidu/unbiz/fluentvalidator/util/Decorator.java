package com.baidu.unbiz.fluentvalidator.util;

import com.baidu.unbiz.fluentvalidator.ValidUtils;

/**
 * Fluent validator decorator, same as decorator design pattern
 * @author zhangxu
 */
public interface Decorator {

    /**
     * Decorate one ValidUtils and return the new instance.
     * @param fv ValidUtils
     * @return Decorated ValidUtils
     */
    ValidUtils decorate(ValidUtils fv);

}
