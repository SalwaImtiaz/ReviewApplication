package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by user on 12/1/2018.
 */

@Entity(tableName = "reviews")
public class Review {

    @PrimaryKey(autoGenerate = true)
    private int review_id;

    private String customer_name;

    private String customer_email;

    private String customer_contact_no;

    private String customer_birthday;

    private String customer_anniversary;

    private int server_id;

    private String service_offered_ids;

    private float rating;

    private String suggestion;

    private String suggestion_for_server;

    private Date created_time;

    @Ignore
    public Review(String customer_name, String customer_email, String customer_contact_no, String customer_birthday, String customer_anniversary, int server_id, String service_offered_ids, float rating, String suggestion, String suggestion_for_server, Date created_time) {
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_contact_no = customer_contact_no;
        this.customer_birthday = customer_birthday;
        this.customer_anniversary = customer_anniversary;
        this.server_id = server_id;
        this.service_offered_ids = service_offered_ids;
        this.rating = rating;
        this.suggestion = suggestion;
        this.suggestion_for_server = suggestion_for_server;
        this.created_time = created_time;
    }

    public Review(int review_id, String customer_name, String customer_email, String customer_contact_no, String customer_birthday, String customer_anniversary, int server_id, String service_offered_ids, float rating, String suggestion, String suggestion_for_server, Date created_time) {
        this.review_id = review_id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_contact_no = customer_contact_no;
        this.customer_birthday = customer_birthday;
        this.customer_anniversary = customer_anniversary;
        this.server_id = server_id;
        this.service_offered_ids = service_offered_ids;
        this.rating = rating;
        this.suggestion = suggestion;
        this.suggestion_for_server = suggestion_for_server;
        this.created_time = created_time;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_contact_no() {
        return customer_contact_no;
    }

    public void setCustomer_contact_no(String customer_contact_no) {
        this.customer_contact_no = customer_contact_no;
    }

    public String getCustomer_birthday() {
        return customer_birthday;
    }

    public void setCustomer_birthday(String customer_birthday) {
        this.customer_birthday = customer_birthday;
    }

    public String getCustomer_anniversary() {
        return customer_anniversary;
    }

    public void setCustomer_anniversary(String customer_anniversary) {
        this.customer_anniversary = customer_anniversary;
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public String getService_offered_ids() {
        return service_offered_ids;
    }

    public void setService_offered_ids(String service_offered_ids) {
        this.service_offered_ids = service_offered_ids;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion_for_server() {
        return suggestion_for_server;
    }

    public void setSuggestion_for_server(String suggestion_for_server) {
        this.suggestion_for_server = suggestion_for_server;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }
}
