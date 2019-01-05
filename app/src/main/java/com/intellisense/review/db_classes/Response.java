package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by user on 12/6/2018.
 */

@Entity(tableName = "response")
public class Response {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int response_id;
    private int review_id;
    private int response;
    private int question_id;

    @Ignore
    public Response(int review_id, int question_id, int response){
        this.review_id = review_id;
        this.question_id = question_id;
        this.response = response;
    }

    public Response(int response_id,int review_id,int question_id,int response){
        this.response_id = response_id;
        this.review_id = review_id;
        this.question_id = question_id;
        this.response = response;
    }

    @NonNull
    public int getResponse_id() {
        return response_id;
    }

    public void setResponse_id(@NonNull int response_id) {
        this.response_id = response_id;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }
}
