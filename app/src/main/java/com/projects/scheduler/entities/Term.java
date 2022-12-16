package com.projects.scheduler.entities;

import android.text.Editable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String termName;
    private String startDate;
    private String endDate;

    public Term(int termId, String termName, String startDate, String endDate) {
        this.id = termId;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Term(){}

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
