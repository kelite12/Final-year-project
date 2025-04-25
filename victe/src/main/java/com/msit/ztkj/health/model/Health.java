package com.msit.ztkj.health.model;

import java.io.Serializable;

import com.msit.ztkj.userinfo.model.User;
import com.msit.ztkj.utils.PageParam;


public class Health extends PageParam implements Serializable {

private Integer healthid;
private Integer age;
private String sex;
private Double xuey;
private Double xuet;
private Double xuez;
private Double xueg;
private Integer userid;
private String remark1;
private User user = new User();
private String sendtime;

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
return this.healthid;
}
public void setHealthid(Integer healthid) {
this.healthid = healthid;
}public Integer getAge() {
return this.age;
}
public void setAge(Integer age) {
this.age = age;
}public String getSex() {
return this.sex;
}
public void setSex(String sex) {
this.sex = sex;
}public Double getXuey() {
return this.xuey;
}
public void setXuey(Double xuey) {
this.xuey = xuey;
}public Double getXuet() {
return this.xuet;
}
public void setXuet(Double xuet) {
this.xuet = xuet;
}public Double getXuez() {
return this.xuez;
}
public void setXuez(Double xuez) {
this.xuez = xuez;
}public Double getXueg() {
return this.xueg;
}
public void setXueg(Double xueg) {
this.xueg = xueg;
}public Integer getUserid() {
return this.userid;
}
public void setUserid(Integer userid) {
this.userid = userid;
}public String getRemark1() {
return this.remark1;
}
public void setRemark1(String remark1) {
this.remark1 = remark1;
}
}