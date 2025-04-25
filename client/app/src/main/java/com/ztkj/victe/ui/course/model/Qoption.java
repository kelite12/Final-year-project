package com.ztkj.victe.ui.course.model;

import java.io.Serializable;

public class Qoption implements Serializable {
    private int qoptionid;
    private String name;
    private String content;
    private int questionid;


    public int getQoptionid() {
        return qoptionid;
    }

    public void setQoptionid(int qoptionid) {
        this.qoptionid = qoptionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }


}
