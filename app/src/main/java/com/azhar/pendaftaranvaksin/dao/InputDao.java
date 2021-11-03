package com.azhar.pendaftaranvaksin.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.azhar.pendaftaranvaksin.ui.inputdata.ModelInput;

import java.util.List;

/**
 * Created by Azhar Rivaldi on 17-10-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

@Dao
public interface InputDao {

    @Insert(onConflict = REPLACE)
    void insert(ModelInput modelInput);

    @Update
    void update(ModelInput modelInput);

    @Delete
    void delete(ModelInput modelInput);

    @Query("DELETE FROM vaccine_table")
    void deleteAllData();

    @Query("SELECT * FROM vaccine_table ORDER BY id DESC")
    LiveData<List<ModelInput>> getAllData();

}
