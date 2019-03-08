package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by user on 12/6/2018.
 */

@Entity(tableName = "items")
public class Items_Served {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int item_id;
    private String item_name;
    private int company_id;
    private Date entry_time;
    @Ignore
    public Items_Served( String item_name,int company_id, Date entry_time){
        this.item_name = item_name;
        this.company_id = company_id;
        this.entry_time = entry_time;
    }

    public Items_Served(int item_id,String item_name,int company_id, Date entry_time){
        this.item_id = item_id;
        this.item_name = item_name;
        this.company_id = company_id;
        this.entry_time = entry_time;
    }

    @NonNull
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(@NonNull int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public Date getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(Date entry_time) {
        this.entry_time = entry_time;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;

    }
}

