package com.wangxiaohan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Band {

    @Id
    @Column(name = "bname")
    private String bname;

    @Column(name = "year")
    private String year;

    @Column(name = "bleader")
    private String bleader;

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBleader() {
        return bleader;
    }

    public void setBleader(String bleader) {
        this.bleader = bleader;
    }
}
