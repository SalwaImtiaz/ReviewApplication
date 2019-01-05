package com.intellisense.review.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.intellisense.review.db_classes.Items_Served;
import com.intellisense.review.db_classes.Review;

import java.util.List;


/**
 * Created by user on 12/6/2018.
 */

@Dao
public interface ItemsDao {

    @Query("SELECT * FROM items WHERE item_id = :id")
    Items_Served loadSingleItem(int id);

    @Insert
    void insertItem(Items_Served items_served);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(Items_Served items_served);

    @Delete
    void deleteItem(Items_Served items_served);

    @Query("SELECT * FROM items")
    List<Items_Served> findItems();

    @Query ( "Select item_name from items" )
    List<String> findItemnames();
}
