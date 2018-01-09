package com.zyf.dc.entity.test;

import com.zyf.dc.group.C;
import com.zyf.dc.group.S;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Test {
    private Integer id;

    @NotNull(message = "{test.name.notNull}", groups = {C.Insert.class, S.Insert.class})
    @NotBlank(message = "{test.name.notBlank}", groups = {C.Insert.class, S.Insert.class})
    private String name;

    private Integer createId;

    private Date createTime;

    private Boolean userType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getUserType() {
        return userType;
    }

    public void setUserType(Boolean userType) {
        this.userType = userType;
    }
}