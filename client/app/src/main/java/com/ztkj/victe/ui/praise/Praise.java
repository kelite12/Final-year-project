package com.ztkj.victe.ui.praise;

import java.io.Serializable;

public class Praise implements Serializable {
    private int praiseid;
    private int userid;
    private int targetid;
    private String time;


    public int getPraiseid() {
        return praiseid;
    }

    public void setPraiseid(int praiseid) {
        this.praiseid = praiseid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getTargetid() {
        return targetid;
    }

    public void setTargetid(int targetid) {
        this.targetid = targetid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
