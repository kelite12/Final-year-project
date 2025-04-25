package com.ztkj.victe.ui.course.model;

import java.io.Serializable;

public class Examcontent implements Serializable {
    private int examcontentid;
    private int examid;
    private int questionid;
    private double score;
    //private Exam exam = new Exam();
    private Question question = new Question();
    private String userAnswer="";

    public String getUserAnswer() {
        if(userAnswer==null){
            userAnswer="";
        }
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getExamcontentid() {
        return examcontentid;
    }

    public void setExamcontentid(int examcontentid) {
        this.examcontentid = examcontentid;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

//    public Exam getExam() {
//        return exam;
//    }
//
//    public void setExam(Exam exam) {
//        this.exam = exam;
//    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
