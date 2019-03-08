package com.intellisense.review.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.intellisense.review.db_classes.Company;
import com.intellisense.review.db_classes.Items_Served;

import java.util.List;

/**
 * Created by Administrator on 3/11/2019.
 */
@Dao
public interface CompanyDao {
    @Query("SELECT * FROM company WHERE company_id = :id")
    Company loadSingleItem(int id);

    @Insert
    void insertCompany(Company company);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCompany(Company company);

    @Delete
    void deleteCompany(Company company);

    @Query("SELECT * FROM company")
    List<Company> findCompany();

}
