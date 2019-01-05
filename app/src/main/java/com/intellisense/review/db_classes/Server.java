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

    @Ignore
    public Server( String server_name){
        this.server_name = server_name;    }

    public Server(int server_id,String server_name){
        this.server_id = server_id;
        this.server_name = server_name;    }

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
}
