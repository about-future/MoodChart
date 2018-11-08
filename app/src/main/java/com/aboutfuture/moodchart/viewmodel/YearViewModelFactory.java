package com.aboutfuture.moodchart.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aboutfuture.moodchart.data.AppDatabase;

public class YearViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final int mYear;

    public YearViewModelFactory(AppDatabase database, int year) {
        mDb = database;
        mYear = year;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new YearViewModel(mDb, mYear);
    }
}
