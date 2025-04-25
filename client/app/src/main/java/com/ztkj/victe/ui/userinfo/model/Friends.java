package com.ztkj.victe.ui.userinfo.model;

import java.io.Serializable;


public class Friends  implements Serializable {

private Integer friendsid;
private Integer senduserid;
private Integer touserid;
private Integer status;
private String remark1;
private User user=new User();
    private User user2=new User();

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getFriendsid() {
return this.friendsid;
}
public void setFriendsid(Integer friendsid) {
this.friendsid = friendsid;
}public Integer getSenduserid() {
return this.senduserid;
}
public void setSenduserid(Integer senduserid) {
this.senduserid = senduserid;
}public Integer getTouserid() {
return this.touserid;
}
public void setTouserid(Integer touserid) {
this.touserid = touserid;
}public Integer getStatus() {
return this.status;
}
public void setStatus(Integer status) {
this.status = status;
}public String getRemark1() {
return this.remark1;
}
public void setRemark1(String remark1) {
this.remark1 = remark1;
}
}