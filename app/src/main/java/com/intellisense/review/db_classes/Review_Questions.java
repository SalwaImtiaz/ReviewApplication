package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by user on 12/6/2018.
 */

@Entity(tableName = "questions")
public class Review_Questions {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int question_id;
    private String questions;

    @Ignore
    public Review_Questions(String questions){
        this.questions = questions;    }

    public Review_Questions(int question_id, String questions){
        this.question_id = question_id;
        this.questions = questions;    }

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
}

