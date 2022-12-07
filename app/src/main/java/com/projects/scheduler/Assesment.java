package com.projects.scheduler;

import java.time.LocalDate;
import java.util.ArrayList;

public class Assesment {

    public Assesment(){

    }

    private int id;
    private String title;
    private LocalDate endDate;


    public int getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate(){
        return this.endDate;
    }
}
