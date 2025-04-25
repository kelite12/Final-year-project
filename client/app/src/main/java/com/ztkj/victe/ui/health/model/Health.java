package com.ztkj.victe.ui.health.model;
import com.ztkj.victe.ui.userinfo.model.User;

import java.io.Serializable;

public class Health implements Serializable {
 
    private Integer healthid;
 
    private Integer age;
 
    private String sex;
 
    private Double xuey;
 
    private Double xuet;
 
    private Double xuez;
 
    private Double xueg;
 
    private Integer userid;
 
    private String remark1;
    private String sendtime;
    private User user = new User();

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getHealthid() {
        return healthid;
    }

    public void setHealthid(Integer healthid) {
        this.healthid = healthid;
    }
 
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
 
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
 
    public Double getXuey() {
        return xuey;
    }

    public void setXuey(Double xuey) {
        this.xuey = xuey;
    }
 
    public Double getXuet() {
        return xuet;
    }

    public void setXuet(Double xuet) {
        this.xuet = xuet;
    }
 
    public Double getXuez() {
        return xuez;
    }

    public void setXuez(Double xuez) {
        this.xuez = xuez;
    }
 
    public Double getXueg() {
        return xueg;
    }

    public void setXueg(Double xueg) {
        this.xueg = xueg;
    }
 
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
 
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }
 

}