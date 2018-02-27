package com.zyf.dc.vo.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.zyf.dc.base.BaseEntity;

@TableName(value = "user")
public class UserVo extends BaseEntity {

    @TableField("username")
    private String name;

    private boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
