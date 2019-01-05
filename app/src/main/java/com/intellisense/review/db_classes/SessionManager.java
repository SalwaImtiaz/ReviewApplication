package com.intellisense.review.db_classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.intellisense.review.activities.Review_one;

import java.util.HashMap;

/**
 * Created by Administrator on 12/22/2018.
 */


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String review_id = "id";
    private static final String PREF_NAME = "UserDemo";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String customer_name = "name";
    public static final String customer_email = "email";
    public static final String customer_contact_no = "contact";
    public static final String customer_birthday = "birthday";
    public static final String customer_anniversary = "anniversary";
    public static final String server_id = "servers_id";
    public static final String table_no = "tables_no";
    public static final String item_served = "items_served";
    public static final String ratings = "rating";
    public static final String question_id = "questions_id";
    public static final String suggestions = "suggestion";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences (PREF_NAME, PRIVATE_MODE);
        editor = pref.edit ();
    }

    public void createLoginSession(String rating,String name, String contact,String email,String birth,String anniversary,String items_served,String servers_id,String tables_no,String suggestion){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref

        editor.putString( customer_name, name);
        editor.putString( customer_email, email);
        editor.putString ( ratings, rating);
        editor.putString( customer_birthday, birth);
        editor.putString( customer_contact_no, contact);
        editor.putString( customer_anniversary, anniversary);
        editor.putString (server_id, servers_id);
        editor.putString (table_no, tables_no);
        editor.putString (suggestions, suggestion);
        editor.putString (item_served, items_served);

        //Total 11
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String> ();
        user.put( review_id, pref.getString ( review_id ,null) );
        // user name
        user.put( customer_name, pref.getString( customer_name, null));
        user.put( customer_email, pref.getString( customer_email, null));
        user.put( customer_birthday, pref.getString( customer_birthday, null));
        user.put( customer_contact_no, pref.getString( customer_contact_no, null));
        user.put( customer_anniversary, pref.getString( customer_anniversary, null));
        user.put( ratings, pref.getString( ratings, null));
        user.put( suggestions, pref.getString( suggestions, null));
        user.put( question_id, pref.getString( question_id, null));
        user.put( item_served, pref.getString( item_served, null));
        user.put(table_no, pref.getString (table_no, null));
        user.put(server_id, pref.getString(server_id, null));
        return user;
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent (_context, Review_one.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent (_context, Review_one.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}