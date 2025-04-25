package com.ztkj.victe.ui.course.model;


import com.ztkj.victe.ui.userinfo.model.User;

import java.io.Serializable;

public class Course implements Serializable {
    private int courseid;
    private String coursename;
    private String content;
    private double score;
    private int sendid;
    private String pic;
    private int status; // 0为未审核，1为审核通过，2为审核未通过
    private String sendtime;
    private int section;
    private int sectionspan;
    private int week;
    private String classroom;
    private int courseflag; // 1为红色，2为绿色，3为蓝色，4为紫色，5为橙色，6为粉色，7为棕色，8为黄色，9为灰色
    private int teacherid;
    private String remark1;
    private String remark2;
    private User sender = new User();
    private User teacher = new User();

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSendid() {
        return sendid;
    }

    public void setSendid(int sendid) {
        this.sendid = sendid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSectionspan() {
        return sectionspan;
    }

    public void setSectionspan(int sectionspan) {
        this.sectionspan = sectionspan;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getCourseflag() {
        return courseflag;
    }

    public void setCourseflag(int courseflag) {
        this.courseflag = courseflag;
    }

    public int getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(int teacherid) {
        this.teacherid = teacherid;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }
}
