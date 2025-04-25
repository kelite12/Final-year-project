package com.ztkj.victe.ui.course.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    private int questionid;
    private String name;
    private int studytypeid;
    private int courseid;
    private String answer;
    private String status; // 0为不可以，1为可用
    private Studytype studytype = new Studytype();
    private Course course = new Course();
    private List<Qoption> qoptions = new ArrayList<>();

    public List<Qoption> getQoptions() {
        return qoptions;
    }

    public void setQoptions(List<Qoption> qoptions) {
        this.qoptions = qoptions;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudytypeid() {
        return studytypeid;
    }

    public void setStudytypeid(int studytypeid) {
        this.studytypeid = studytypeid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Studytype getStudytype() {
        return studytype;
    }

    public void setStudytype(Studytype studytype) {
        this.studytype = studytype;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
