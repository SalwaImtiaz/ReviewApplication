package com.intellisense.review.adapters;

/**
 * Created by Administrator on 3/30/2019.
 */

public class SyncUp {
    private String user_name;

    private String email;

    private String password;

    private int type;

    private int company_id;

    private int active;

    private boolean status;

    private String message;

    public SyncUp(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public SyncUp(String user_name, String email, String password, int type, int company_id, int active) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.company_id = company_id;
        this.active = active;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
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

    public int getActive() {
        return active;
    }
}
