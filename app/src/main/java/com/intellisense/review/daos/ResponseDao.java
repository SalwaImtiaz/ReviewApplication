package com.intellisense.review.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.intellisense.review.db_classes.Response;

import java.util.List;


/**
 * Created by user on 12/6/2018.
 */

@Dao
public interface ResponseDao {

    @Query("SELECT * FROM response WHERE review_id = :review_id AND question_id = :question_id")
    Response loadSingleResponse(int review_id, int question_id);

    @Insert
    void insertResponse(Response response);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateResponse(Response response);

    @Query("SELECT * FROM response")
    List<Response> loadAllResponse();

    @Query("Select response_id,review_id,response,question_id,company_id,entry_time from response")
    List<Response> getResponse();

    @Delete
    void deleteResponse(Response response);

}
