package com.baidu.unbiz.fluentvalidator;

/**
 * 枚举父类
 * @author zengyufei
 * @since 1.0.0
 */
public interface BaseEnum {

    /**
     * 枚举序列值
     * @return 返回枚举下标
     */
    int getIndex();

    /**
     * 枚举描述
     * @return 返回枚举描述
     */
    String getMark();

}
