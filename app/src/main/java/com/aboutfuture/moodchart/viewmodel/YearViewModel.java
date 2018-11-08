package com.aboutfuture.moodchart.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.DailyMood;

import java.util.List;

public class YearViewModel extends ViewModel {
    private final LiveData<List<DailyMood>> yearMoods;

    public YearViewModel(AppDatabase appDatabase, int year) {
        yearMoods = appDatabase.moodsDao().loadAllMoodsOfThisYear(year);
    }

    public LiveData<List<DailyMood>> getYearMoods() {
        return yearMoods;
    }
}
