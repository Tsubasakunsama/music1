package com.wangxiaohan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @Column(name = "mname")
    private String mname;

    @Column(name = "msex")
    private String msex;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "mwork")
    private String mwork;

    @Column(name = "bname")
    private String bname;


    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMsex() {
        return msex;
    }

    public void setMsex(String msex) {
        this.msex = msex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getMwork() {
        return mwork;
    }

    public void setMwork(String mwork) {
        this.mwork = mwork;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
