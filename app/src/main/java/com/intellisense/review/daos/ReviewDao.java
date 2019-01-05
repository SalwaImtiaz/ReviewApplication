package com.intellisense.review.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.intellisense.review.db_classes.Admin;
import com.intellisense.review.db_classes.Review;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 12/1/2018.
 */

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM reviews ORDER BY created_time DESC")
    List<Review> loadAllReviews();

    @Query("SELECT * FROM reviews WHERE review_id = :id")
    Review loadSingleReview(int id);

    @Insert
    void insertReview(Review review);


    @Query("Select MAX(review_id)+1 from reviews")
    int getID();

    @Delete
    void deleteReview(Review review);

}
