package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Administrator on 3/11/2019.
 */

@Entity(tableName = "company")
public class Company {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int company_id;

    private String company_name;

    private Date entry_time;

    public Company(String company_name,Date entry_time) {
        this.company_name = company_name;
        this.entry_time = entry_time;
    }

    @Ignore
    public Company(int company_id, String company_name, Date entry_time) {
        this.company_id = company_id;
        this.company_name = company_name;
        this.entry_time = entry_time;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Date getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(Date entry_time) {
        this.entry_time = entry_time;
    }

}
