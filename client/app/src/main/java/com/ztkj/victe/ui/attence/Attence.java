package com.ztkj.victe.ui.attence;


import com.ztkj.victe.ui.userinfo.model.User;

import java.io.Serializable;

public class Attence implements Serializable {
    private int attenceid;
    private int userid;
    private String time;
    private String address;
    private String remark1;
    private String remark2;
    private User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAttenceid() {
        return attenceid;
    }

    public void setAttenceid(int attenceid) {
        this.attenceid = attenceid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }
}
