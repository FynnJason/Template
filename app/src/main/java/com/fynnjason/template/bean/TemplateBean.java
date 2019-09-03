package com.fynnjason.template.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：FynnJason
 * 时间：2019-09-03
 * 备注：
 */

@Entity
public class TemplateBean {

    @Id
    private long id;

    @Generated(hash = 841063013)
    public TemplateBean(long id) {
        this.id = id;
    }

    @Generated(hash = 741639705)
    public TemplateBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
