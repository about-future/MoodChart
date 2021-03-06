package com.aboutfuture.moodchart.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MoodsDao {
    @Query("SELECT * FROM moods WHERE year = :year ORDER BY id ASC")
    LiveData<List<Mood>> loadAllMoodsOfThisYear(int year);

    //@Query("SELECT * FROM moods WHERE year = :year AND month = :month ORDER BY id ASC")
    //LiveData<List<Mood>> loadAllMoodsOfThisMonth(int year, int month);

    @Query("SELECT * FROM moods WHERE year = :year AND position = :position ORDER BY id ASC")
    LiveData<Mood> loadMoodDetails(int position, int year);

    @Query("SELECT COUNT(*) FROM moods WHERE first_color = :moodId AND year = :year")
    int countFirstColorInYear(int moodId, int year);

    @Query("SELECT COUNT(*) FROM moods WHERE second_color = :moodId AND year = :year")
    int countSecondColorInYear(int moodId, int year);

    @Query("SELECT COUNT(*) FROM moods WHERE first_color = :moodId AND year = :year AND month = :month")
    int countFirstColorInMonth(int moodId, int month, int year);

    @Query("SELECT COUNT(*) FROM moods WHERE second_color = :moodId AND year = :year AND month = :month")
    int countSecondColorInMonth(int moodId, int month, int year);

    @Query("SELECT COUNT(*) FROM moods WHERE year = :year")
    int countMoodsInYear(int year);

    /*
    @Query("SELECT COUNT(*) FROM moods WHERE year = :year")
    int countMoods(int year); */

    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    void insertEntireYearMoods(List<Mood> entireYearMoods);

    @Insert
    void insertMood(Mood mood);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMood(Mood mood);

//    @Delete
//    void deleteDailyMood(Mood dailyMood);
}
