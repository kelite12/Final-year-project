package com.ztkj.victe.ui.yin.model;
import java.io.Serializable;
public class Yin implements Serializable {
     private Integer yinid;
     private String title;
     private String pics;
     private String re;
     private String dan;
     private String tang;
     private String gai;
     private String wei;
     private String qian;
     private String sendtime;
    public Integer getYinid() {
        return yinid;
    }
    public void setYinid(Integer yinid) {
        this.yinid = yinid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPics() {
        return pics;
    }
    public void setPics(String pics) {
        this.pics = pics;
    }
    public String getRe() {
        return re;
    }
    public void setRe(String re) {
        this.re = re;
    }
    public String getDan() {
        return dan;
    }
    public void setDan(String dan) {
        this.dan = dan;
    }
    public String getTang() {
        return tang;
    }
    public void setTang(String tang) {
        this.tang = tang;
    }
    public String getGai() {
        return gai;
    }
    public void setGai(String gai) {
        this.gai = gai;
    }
    public String getWei() {
        return wei;
    }
    public void setWei(String wei) {
        this.wei = wei;
    }
    public String getQian() {
        return qian;
    }
    public void setQian(String qian) {
        this.qian = qian;
    }
    public String getSendtime() {
        return sendtime;
    }
    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

}