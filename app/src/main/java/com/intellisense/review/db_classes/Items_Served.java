package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by user on 12/6/2018.
 */

@Entity(tableName = "items")
public class Items_Served {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int item_id;
    private String item_name;
    @Ignore
    public Items_Served( String item_name){
        this.item_id = item_id;
        this.item_name = item_name;    }

    public Items_Served(int item_id,String item_name){
        this.item_id = item_id;
        this.item_name = item_name;    }

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
}
