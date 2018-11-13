package com.aboutfuture.moodchart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.AppExecutors;
import com.aboutfuture.moodchart.data.DailyMood;
import com.aboutfuture.moodchart.data.DailyMoodAdapter;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.viewmodel.YearViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YearFragment extends Fragment implements DailyMoodAdapter.ListItemClickListener {
    public static final String SELECTED_DAY_POSITION_KEY = "day_position_key";

    @BindView(R.id.moods_list)
    RecyclerView mMoodsRecyclerView;
    private DailyMoodAdapter mAdapter;
    private AppDatabase mDb;

    public YearFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Bind the views
        ButterKnife.bind(this, rootView);

        // TODO: Create a + - button to change the value of a shared preference for the selected year
        // First the year is the current year and the app will load data from this year.

        mMoodsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 13));
        mMoodsRecyclerView.setHasFixedSize(true);
        mAdapter = new DailyMoodAdapter(getContext(), this);
        mMoodsRecyclerView.setAdapter(mAdapter);

        mDb = AppDatabase.getInstance(getContext());

        //TODO: determine if next year started of it's the same. If it's not, insert a new year in db and setYear in preferences

        // Check to see if this year was initialized already, if it was setup the viewmodel, otherwise
        // insert empty days for the entire year
        if (Preferences.checkSelectedYearInitializationState(getContext())) {
            setupViewModel();
        } else {
            insertEmptyYear();
        }

        return rootView;
    }

    @Override
    public void onItemClickListener(int position) {
        Intent moodDetailsIntent = new Intent(getContext(), TodayActivity.class);
        moodDetailsIntent.putExtra(SELECTED_DAY_POSITION_KEY, position);
        startActivity(moodDetailsIntent);
    }

    private void insertEmptyYear() {
        final ArrayList<DailyMood> mEntireYearMoodsList = new ArrayList<>();
        for (int i = 0; i < 416; i++) {
            mEntireYearMoodsList.add(new DailyMood(
                    Preferences.getSelectedYear(getContext()),
                    i,
                    0,
                    0));
        }

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Insert empty days for the selected year into the DB
                mDb.moodsDao().insertEntireYearMoods(mEntireYearMoodsList);
                Preferences.setSelectedYearInitializationState(getContext(), true);
            }
        });

        setupViewModel();
    }

    private void setupViewModel() {
        YearViewModel yearViewModel = ViewModelProviders.of(this).get(YearViewModel.class);
        yearViewModel.getAllYearMoods().observe(this, new Observer<List<DailyMood>>() {
            @Override
            public void onChanged(@Nullable List<DailyMood> yearMoodsList) {
                if (yearMoodsList != null) {
                    mAdapter.setMoods(yearMoodsList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
