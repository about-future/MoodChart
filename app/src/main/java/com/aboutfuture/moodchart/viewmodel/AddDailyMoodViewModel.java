package com.aboutfuture.moodchart.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.Mood;

public class AddDailyMoodViewModel extends ViewModel {
    private LiveData<Mood> dailyMood;

    public AddDailyMoodViewModel(AppDatabase database, int position, int year) {
        dailyMood = database.moodsDao().loadMoodDetails(position, year);
    }

    public LiveData<Mood> getDailyMoodDetails() {
        return dailyMood;
    }
}
