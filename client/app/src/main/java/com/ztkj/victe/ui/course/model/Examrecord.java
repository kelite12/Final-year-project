package com.ztkj.victe.ui.course.model;


import com.ztkj.victe.ui.userinfo.model.User;

import java.io.Serializable;

public class Examrecord implements Serializable {
    private int examrecordid;
    private int examid;
    private int questionid;
    private int userid;
    private String uanswer;
    private double score;
    private int status; // 0为未批改，1为已批改
    private String remark1;
    private Exam exam = new Exam();
    private Question question = new Question();
    private User user = new User();
    private int count;//用于记录答题的总记录数

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getExamrecordid() {
        return examrecordid;
    }

    public void setExamrecordid(int examrecordid) {
        this.examrecordid = examrecordid;
    }

    public int getExamid() {
        return examid;
    }

    public void setExamid(int examid) {
        this.examid = examid;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUanswer() {
        return uanswer;
    }

    public void setUanswer(String uanswer) {
        this.uanswer = uanswer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
