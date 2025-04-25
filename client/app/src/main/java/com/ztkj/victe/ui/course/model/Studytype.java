package com.ztkj.victe.ui.course.model;

import java.io.Serializable;

public class Studytype implements Serializable {
    private int studytypeid;
    private String name;
    private int status; // 0为未启用，1为已启用

    public int getStudytypeid() {
        return studytypeid;
    }

    public void setStudytypeid(int studytypeid) {
        this.studytypeid = studytypeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
