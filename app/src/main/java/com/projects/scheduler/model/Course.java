package com.projects.scheduler.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Course {

    public Course(){

    }

    private int id;
    private String name;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<Note> notes;
    private ArrayList<Assessment> assesments;

    public int getCourseId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public LocalDate getStartDate(){
        return this.startDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public LocalDate getEndDate(){
        return this.endDate;
    }

    public void addNote(Note note){
        notes.add(note);
    }

    public ArrayList<Note> getNotes(){
        return this.notes;
    }

    public void deleteNote(Note note){
        notes.remove(note);
    }

    public void addAssesment(Assessment assesment){
        assesments.add(assesment);
    }

    public ArrayList<Assessment> getAssesments(){
        return this.assesments;
    }

    public void deleteAssesment(Assessment assesment){
        assesments.remove(assesment);
    }
}