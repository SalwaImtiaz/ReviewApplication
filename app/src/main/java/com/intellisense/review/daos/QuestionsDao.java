package com.intellisense.review.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.intellisense.review.db_classes.Review_Questions;

import java.util.List;

/**
 * Created by user on 12/6/2018.
 */

@Dao
public interface QuestionsDao {

    @Query("SELECT * FROM questions")
    List<Review_Questions> loadAllQuestions();

    @Insert
    void insertQuestion(Review_Questions review_questions);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateQuestion(Review_Questions review_questions);

    @Delete
    void deleteQuestion(Review_Questions review_questions);

    @Query("SELECT questions FROM questions WHERE question_id == :question_id")
    String findQuestion(int question_id);

    @Query("SELECT COUNT(questions) FROM questions")
    int TotalQuestion( );


}
