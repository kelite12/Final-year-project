package com.msit.ztkj.yin.model;

import java.io.Serializable;
import com.msit.ztkj.utils.PageParam;



public class Yin extends PageParam implements Serializable {

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
return this.yinid;
}
public void setYinid(Integer yinid) {
this.yinid = yinid;
}
public String getTitle() {
return this.title;
}
public void setTitle(String title) {
this.title = title;
}
public String getPics() {
return this.pics;
}
public void setPics(String pics) {
this.pics = pics;
}
public String getRe() {
return this.re;
}
public void setRe(String re) {
this.re = re;
}
public String getDan() {
return this.dan;
}
public void setDan(String dan) {
this.dan = dan;
}
public String getTang() {
return this.tang;
}
public void setTang(String tang) {
this.tang = tang;
}
public String getGai() {
return this.gai;
}
public void setGai(String gai) {
this.gai = gai;
}
public String getWei() {
return this.wei;
}
public void setWei(String wei) {
this.wei = wei;
}
public String getQian() {
return this.qian;
}
public void setQian(String qian) {
this.qian = qian;
}
public String getSendtime() {
return this.sendtime;
}
public void setSendtime(String sendtime) {
this.sendtime = sendtime;
}

}