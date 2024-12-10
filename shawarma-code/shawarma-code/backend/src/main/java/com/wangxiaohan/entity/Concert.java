package com.wangxiaohan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Concert {

    @Id
    @Column(name = "CID")
    private String CID;

    @Column(name = "ctime")
    private String ctime;

    @Column(name = "cplace")
    private String cplace;

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCplace() {
        return cplace;
    }

    public void setCplace(String cplace) {
        this.cplace = cplace;
    }
}
