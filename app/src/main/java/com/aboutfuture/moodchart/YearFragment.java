package com.aboutfuture.moodchart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.AppExecutors;
import com.aboutfuture.moodchart.data.Mood;
import com.aboutfuture.moodchart.data.MoodsAdapter;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.utils.SpecialUtils;
import com.aboutfuture.moodchart.viewmodel.YearViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YearFragment extends Fragment {
    public static final String SELECTED_DAY_POSITION_KEY = "day_position_key";

    @BindView(R.id.moods_list)
    GridView mYearGridView;

    private MoodsAdapter mAdapter;
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

        mAdapter = new MoodsAdapter(
                getContext(),
                SpecialUtils.getScreenDensity(getContext()),
                SpecialUtils.isPortraitMode(getContext()));
        mYearGridView.setAdapter(mAdapter);
        mYearGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // For all the positions that are not listed bellow or if position is 379 and
                // it's a leap year, we set an intent
                if (position > 12 && position % 13 != 0 && position != 392 && position != 405 &&
                        position != 407 && position != 409 && position != 412 && position != 414) {
                    if (position == 379) {
                        if (SpecialUtils.isLeapYear(Preferences.getSelectedYear(getContext()))) {
                            createIntent(position);
                        }
                    } else {
                        createIntent(position);
                    }
                }
            }
        });

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

    private void createIntent(int position) {
        Intent moodDetailsIntent = new Intent(getContext(), TodayActivity.class);
        moodDetailsIntent.putExtra(SELECTED_DAY_POSITION_KEY, position);
        startActivity(moodDetailsIntent);
    }

    private void insertEmptyYear() {
        final ArrayList<Mood> mEntireYearMoodsList = new ArrayList<>();
        for (int i = 0; i < 416; i++) {
            mEntireYearMoodsList.add(new Mood(
                    Preferences.getSelectedYear(getContext()),
                    i % 13,
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
        yearViewModel.getAllYearMoods().observe(this, new Observer<List<Mood>>() {
            @Override
            public void onChanged(@Nullable List<Mood> yearMoodsList) {
                if (yearMoodsList != null) {
                    mAdapter.setMoods(yearMoodsList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
