package com.intellisense.review.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.intellisense.review.db_classes.Admin;

import java.util.List;

/**
 * Created by user on 12/1/2018.
 */

@Dao
public interface AdminDao {

    @Query("SELECT * FROM admin WHERE email = :email AND password = :password")
    Admin loadAdmin(String email, String password);

    @Insert
    void insertAdmin(Admin admin);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAdmin(Admin admin);

    @Delete
    void deleteAdmin(Admin admin);


}
