package com.ztkj.victe.ui.xiu.model;
import java.io.Serializable;

public class Xiu implements Serializable {
 
    private Integer xiuid;
 
    private String title;
 
    private String stime;
 
    private String etime;
 
    private Integer userid;
 
    private String remark1;
 

    public Integer getXiuid() {
        return xiuid;
    }

    public void setXiuid(Integer xiuid) {
        this.xiuid = xiuid;
    }
 
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }
 
    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
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