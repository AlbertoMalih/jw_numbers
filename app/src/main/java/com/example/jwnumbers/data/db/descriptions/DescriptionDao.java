package com.example.jwnumbers.data.db.descriptions;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import static com.example.jwnumbers.data.db.AppDbHelperKt.DESCRIPTION_TABLE_DB_NAME;

@Dao
public interface DescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDescription(Description number);

    @Query("SELECT * FROM " + DESCRIPTION_TABLE_DB_NAME + " WHERE description !='' AND description IS NOT NULL AND storeId == :storeId")
    List<Description> getAllNumbersWithDescription(String storeId);
}