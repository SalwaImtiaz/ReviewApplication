package com.intellisense.review.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.intellisense.review.db_classes.Server;

import java.util.List;

/**
 * Created by user on 12/6/2018.
 */

@Dao
public interface ServerDao {

    @Query("SELECT * FROM server WHERE server_id = :id")
    Server loadSingleServer(int id);

    @Insert
    void insertServer(Server server);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateServer(Server server);

    @Delete
    void deleteServer(Server server);

    @Query("SELECT * FROM server")
    List<Server> findServer();

    @Query ( "Select server_name from server" )
    List<String> findServernames();
}
