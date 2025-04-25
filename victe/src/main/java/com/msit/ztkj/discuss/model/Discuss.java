package com.msit.ztkj.discuss.model;

import com.msit.ztkj.userinfo.model.User;

public class Discuss {
    private int discussid;
    private String content;
    private int userid;
    private String dtime;
    private int status;//0 显示 1 不显示
    private String remark1;
    private int targetid;

    public int getTargetid() {
        return targetid;
    }

    public void setTargetid(int targetid) {
        this.targetid = targetid;
    }

    private User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDiscussid() {
        return discussid;
    }

    public void setDiscussid(int discussid) {
        this.discussid = discussid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }
}
