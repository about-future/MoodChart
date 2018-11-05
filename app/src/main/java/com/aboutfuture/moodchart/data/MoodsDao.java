package com.aboutfuture.moodchart.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MoodsDao {
    @Query("SELECT * FROM moods WHERE year = :year ORDER BY id ASC")
    LiveData<List<DailyMood>> loadAllMoodsOfThisYear(int year);

    @Query("SELECT * FROM moods WHERE year = :year AND month = :month ORDER BY id ASC")
    LiveData<List<DailyMood>> loadAllMoodsOfThisMonth(int year, int month);

    @Query("SELECT * FROM moods WHERE id = :id")
    LiveData<DailyMood> loadMoodDetails(int id);

    @Insert
    void insertDailyMood(DailyMood dailyMood);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDailyMood(DailyMood dailyMood);

    @Delete
    void deleteDailyMood(DailyMood dailyMood);
}
