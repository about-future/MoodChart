package com.aboutfuture.moodchart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.AppExecutors;
import com.github.mikephil.charting.charts.BarChart;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphsFragment extends Fragment {

    @BindView(R.id.chart)
    BarChart mBarChart;
    //private BarChartAdapter mAdapter;
    private AppDatabase mDb;

    public GraphsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
        // Bind the views
        ButterKnife.bind(this, rootView);

        mDb = AppDatabase.getInstance(getContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int mood1 = mDb.moodsDao().countFirstColor(1);
                int mood2 = mDb.moodsDao().countFirstColor(2);
                int mood3 = mDb.moodsDao().countFirstColor(3);
                int mood4 = mDb.moodsDao().countFirstColor(4);
                int mood5 = mDb.moodsDao().countFirstColor(5);
                int mood6 = mDb.moodsDao().countFirstColor(6);

                Log.v("Mood 1", String.valueOf(mood1));
                Log.v("Mood 2", String.valueOf(mood2));
                Log.v("Mood 3", String.valueOf(mood3));
                Log.v("Mood 4", String.valueOf(mood4));
                Log.v("Mood 5", String.valueOf(mood5));
                Log.v("Mood 6", String.valueOf(mood6));
            }
        });

        return rootView;
    }
}
