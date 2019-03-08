package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Date;

/**
 * Created by user on 12/6/2018.
 */

@Entity(tableName = "questions")
public class Review_Questions {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int question_id;
    private String questions;
    private int company_id;
    private String entry_time;

    @Ignore
    public Review_Questions(String questions,int company_id_fk){
        this.questions = questions;
    this.company_id = company_id_fk;}

    public Review_Questions(int question_id, String questions,int company_id){
        this.question_id = question_id;
        this.questions = questions;
        this.company_id = company_id;
    }

    @NonNull
    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(@NonNull int question_id) {
        this.question_id = question_id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }
}

