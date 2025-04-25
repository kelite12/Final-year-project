package com.ztkj.victe.ui.fornum.model;


import com.ztkj.victe.ui.discuss.Discuss;
import com.ztkj.victe.ui.praise.Praise;
import com.ztkj.victe.ui.userinfo.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fornum implements Serializable {
    private int fornumid;
    private String title;
    private String content;
    private String sendtime;
    private String urls;
    private int userid;
    private int status;
    private String remark1;
    private User user = new User();
    private List<Discuss> discusses = new ArrayList<Discuss>();
    private boolean flag;//判断是否点赞
    private List<Praise> praises = new ArrayList<>();

    public List<Praise> getPraises() {
        return praises;
    }

    public void setPraises(List<Praise> praises) {
        this.praises = praises;
    }
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Discuss> getDiscusses() {
        return discusses;
    }

    public void setDiscusses(List<Discuss> discusses) {
        this.discusses = discusses;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFornumid() {
        return fornumid;
    }

    public void setFornumid(int fornumid) {
        this.fornumid = fornumid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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
