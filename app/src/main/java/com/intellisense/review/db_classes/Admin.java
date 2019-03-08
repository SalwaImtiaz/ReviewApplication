package com.intellisense.review.db_classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.v4.media.MediaBrowserCompat;
import android.widget.AdapterView;

import java.util.Date;

/**
 * Created by user on 11/30/2018.
 */

@Entity(tableName = "admin")
public class Admin {

    @Ignore
    public static final int ADMIN_TYPE_UNKNOWN = 0;

    @Ignore
    public static final int ADMIN_TYPE_NORMAL = 1;

    @Ignore
    public static final int ADMIN_TYPE_SUPER = 2;

    @PrimaryKey(autoGenerate = true)
    private int admin_id;

    private String user_name;

    private String email;

    private String password;

    private int company_id;

    private int active;

    private Date entry_time;


    // Integer to represent whether the admin is normal or super
    private int type;

    public Admin(int admin_id, String user_name, String email, String password, int type,int company_id,int active,Date entry_time)
    {
        this.admin_id = admin_id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.company_id = company_id;
        this.active = active;
        this.entry_time = entry_time;
    }

    @Ignore
    public Admin(String user_name, String email, String password, int type, int company_id, int active, Date entry_time)
    {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.company_id = company_id;
        this.active = active;
        this.entry_time = entry_time;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Date getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(Date entry_time) {
        this.entry_time = entry_time;
    }

    public void setType(int type) {
        this.type = type;
    }
}
