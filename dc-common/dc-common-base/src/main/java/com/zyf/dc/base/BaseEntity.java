package com.zyf.dc.base;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.FieldFill;

import java.util.Date;

public class BaseEntity {

    protected long id;

    @TableField(fill = FieldFill.INSERT)
    protected long createId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected long modifyId;

    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    protected boolean deleteFlag;

    @Version
    private Integer version;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateId() {
        return this.createId;
    }

    public void setCreateId(long createId) {
        this.createId = createId;
    }

    public long getModifyId() {
        return this.modifyId;
    }

    public void setModifyId(long modifyId) {
        this.modifyId = modifyId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
