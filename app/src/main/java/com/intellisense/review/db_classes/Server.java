package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by user on 12/6/2018.
 */

@Entity(tableName = "server")
public class Server {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int server_id;
    private String server_name;
    private int company_id;
    private String entry_time;

    @Ignore
    public Server( String server_name,int company_id,String entry_time){
        this.server_name = server_name;
        this.company_id = company_id;
        this.entry_time = entry_time;
    }

    public Server(int server_id,String server_name,int company_id,String entry_time){
        this.server_id = server_id;
        this.server_name = server_name;
        this.company_id = company_id;
        this.entry_time = entry_time;
    }

    @NonNull
    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(@NonNull int server_id) {
        this.server_id = server_id;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
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
