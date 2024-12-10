package com.wangxiaohan.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class Performs {

    @Id
    @Column(name = "CID")
    private String CID;

    @Column(name = "sname")
    private String sname;

    @Column(name = "performer")
    private String performer;

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }
}
