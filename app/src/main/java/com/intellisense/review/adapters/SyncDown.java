package com.intellisense.review.adapters;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 3/30/2019.
 */

public class SyncDown {
    private int admin_id;
    private String user_name;
    private String email;
    private String password;
    private int type;
    private int active;
    private int company_id;
    private String last_sync_time;

    @SerializedName("body")
    private String text;

//    public SyncDown(int admin_id,String user_name, String email, String password, int type, int active, int company_id, String last_sync_time) {
//        this.user_name = user_name;
//        this.email = email;
//        this.password = password;
//        this.type = type;
//        this.active = active;
//        this.company_id = company_id;
//        this.last_sync_time = last_sync_time;}
//


    public SyncDown(String last_sync_time) {
        this.last_sync_time = last_sync_time;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getText() {
        return text;
    }

    public int getActive() {
        return active;
    }

    public String getLast_sync_time() {
        return last_sync_time;
    }
}
