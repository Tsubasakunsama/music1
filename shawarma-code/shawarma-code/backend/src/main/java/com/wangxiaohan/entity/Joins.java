package com.wangxiaohan.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class Joins {
    @Id
    private String CID;   // Concert ID
    @Column(name = "bname")
    private String bname;
    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
