package com.msit.ztkj.news.model;

import com.msit.ztkj.userinfo.model.User;
import com.msit.ztkj.utils.PageParam;

import java.io.Serializable;

public class News extends PageParam implements Serializable {

private Integer newsid;
private String title;
private String content;
private String sendtime;
private Integer userid;
private Integer status;
private Integer readcount;
private Integer follow;
private User user=new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNewsid() {
return this.newsid;
}
public void setNewsid(Integer newsid) {
this.newsid = newsid;
}public String getTitle() {
return this.title;
}
public void setTitle(String title) {
this.title = title;
}public String getContent() {
return this.content;
}
public void setContent(String content) {
this.content = content;
}public String getSendtime() {
return this.sendtime;
}
public void setSendtime(String sendtime) {
this.sendtime = sendtime;
}public Integer getUserid() {
return this.userid;
}
public void setUserid(Integer userid) {
this.userid = userid;
}public Integer getStatus() {
return this.status;
}
public void setStatus(Integer status) {
this.status = status;
}public Integer getReadcount() {
return this.readcount;
}
public void setReadcount(Integer readcount) {
this.readcount = readcount;
}public Integer getFollow() {
return this.follow;
}
public void setFollow(Integer follow) {
this.follow = follow;
}
}