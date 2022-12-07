package com.projects.scheduler;

import java.time.LocalDate;
import java.util.ArrayList;

public class Term {
    public Term()
    {

    }

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<Course> courses;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
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

    public void addCourse(Course newCourse){
        courses.add(newCourse);
    }

    public ArrayList<Course> getCourses(){
        return this.courses;
    }

    public Course getCourse(int courseId){
        return courses.get(courseId);
    }

    public void deleteCourse(Course course) {
        courses.remove(course);
    }
}

