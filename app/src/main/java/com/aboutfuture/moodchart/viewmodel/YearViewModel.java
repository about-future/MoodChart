package com.aboutfuture.moodchart.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.Mood;
import com.aboutfuture.moodchart.utils.Preferences;

import java.util.List;

public class YearViewModel extends AndroidViewModel {

    private final LiveData<List<Mood>> allYearMoods;

    public YearViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        allYearMoods = appDatabase.moodsDao().loadAllMoodsOfThisYear(Preferences.getSelectedYear(getApplication()));
    }

    public LiveData<List<Mood>> getAllYearMoods() {
        return allYearMoods;
    }
}
