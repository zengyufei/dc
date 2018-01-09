/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: 枚举父类
 * Author:   zengyufei
 * Date:     2017/11/20
 * Description: 用于被枚举类型继承
 */
package com.zyf.dc.base;

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
