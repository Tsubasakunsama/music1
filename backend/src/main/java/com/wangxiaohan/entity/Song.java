package com.wangxiaohan.entity;

import javax.persistence.*;

@Entity
public class Song {

    @Id
    @Column(name = "sname")
    private String sname;

    @Column(name = "swriter")
    private String swriter;

    @Column(name = "salbum")
    private String salbum;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSwriter() {
        return swriter;
    }

    public void setSwriter(String swriter) {
        this.swriter = swriter;
    }

    public String getSalbum() {
        return salbum;
    }

    public void setSalbum(String salbum) {
        this.salbum = salbum;
    }
}
