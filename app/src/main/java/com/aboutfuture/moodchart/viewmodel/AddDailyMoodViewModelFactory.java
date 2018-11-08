package com.aboutfuture.moodchart.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aboutfuture.moodchart.data.AppDatabase;

public class AddDailyMoodViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final int mPosition;
    private final int mYear;

    public AddDailyMoodViewModelFactory(AppDatabase database, int position, int year) {
        mDb = database;
        mPosition = position;
        mYear = year;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddDailyMoodViewModel(mDb, mPosition, mYear);
    }
}
