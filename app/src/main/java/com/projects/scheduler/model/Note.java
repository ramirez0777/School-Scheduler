package com.projects.scheduler.model;

public class Note {

    public Note(){

    }

    private int id;
    private String note;

    public int getId(){
        return this.id;
    }

    public void setNote(String note){
        this.note = note;
    }

    public String getNote(){
        return this.note;
    }
}
