package com.aboutfuture.moodchart.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.DailyMood;

public class AddDailyMoodViewModel extends ViewModel {
    private LiveData<DailyMood> dailyMood;

    public AddDailyMoodViewModel(AppDatabase database, int dailyMoodId) {
        dailyMood = database.moodsDao().loadMoodDetails(dailyMoodId);
    }

    public LiveData<DailyMood> getDailyMoodDetails() {
        return dailyMood;
    }
}
