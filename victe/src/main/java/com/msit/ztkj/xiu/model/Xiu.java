package com.msit.ztkj.xiu.model;

import java.io.Serializable;
import com.msit.ztkj.utils.PageParam;


public class Xiu extends PageParam implements Serializable {

private Integer xiuid;
private String title;
private String stime;
private String etime;
private Integer userid;
private String remark1;


public Integer getXiuid() {
return this.xiuid;
}
public void setXiuid(Integer xiuid) {
this.xiuid = xiuid;
}public String getTitle() {
return this.title;
}
public void setTitle(String title) {
this.title = title;
}public String getStime() {
return this.stime;
}
public void setStime(String stime) {
this.stime = stime;
}public String getEtime() {
return this.etime;
}
public void setEtime(String etime) {
this.etime = etime;
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