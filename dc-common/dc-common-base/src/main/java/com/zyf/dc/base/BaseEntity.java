/**
 * Copyright (C): 恒大集团©版权所有 Evergrande Group
 * FileName: BaseEntity
 * Author:   zengyufei
 * Date:     2018/01/12
 * Description: TODO
 */
package com.zyf.dc.base;

import java.util.Date;

public class BaseEntity {

    protected long id;

    protected long createId;

    protected long updateId;

    protected Date createTime;

    protected Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateId() {
        return createId;
    }

    public void setCreateId(long createId) {
        this.createId = createId;
    }

    public long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(long updateId) {
        this.updateId = updateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
