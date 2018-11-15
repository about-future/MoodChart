package com.aboutfuture.moodchart;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.AppExecutors;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.utils.SpecialUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphsFragment extends Fragment {

    @BindView(R.id.year_bar_chart)
    BarChart mBarChart;
    @BindView(R.id.year_conclusion_text_view)
    TextView mYearConclusionTextView;
    private AppDatabase mDb;
    private int[] mMoods;

    public GraphsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
        // Bind the views
        ButterKnife.bind(this, rootView);

        mDb = AppDatabase.getInstance(getContext());
        countYearMoods(new MoodsListener() {
            @Override
            public void onCountMoods(int[] moods) {
                setBarChart(moods);
            }
        });

//        countMoodsInMonth(new MoodsListener() {
//            @Override
//            public void onCountMoods(int[] moods) {
//                setBarChart(moods);
//            }
//        }, 4, Preferences.getSelectedYear(getContext()));


        return rootView;
    }

    private interface MoodsListener {
        void onCountMoods(int[] moods);
    }

    private void countYearMoods(final MoodsListener listener) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int[] moods = new int[12];
                int year = Preferences.getSelectedYear(getContext());
                for (int i = 0; i < 12; i++) {
                    moods[i] = mDb.moodsDao().countFirstColorInYear(i + 1, year);
                }
                listener.onCountMoods(moods);

                //setBarChart(moods);
                try {
                    mYearConclusionTextView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Norican-Regular.ttf"));
                    mYearConclusionTextView.setText(getYearConclusion(moods));
                } catch (IllegalStateException e) {
                    //
                }
            }
        });
    }

//    private void countMoodsInMonth(final MoodsListener listener, final int month, final int year) {
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                int[] monthlyMoods = new int[12];
//                for (int i = 0; i < 12; i++) {
//                    monthlyMoods[i] = mDb.moodsDao().countFirstColorInMonth(i + 1, month, year);
//                }
//                listener.onCountMoods(monthlyMoods);
//            }
//        });
//    }

    private void setBarChart(int[] moods) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < moods.length; i++) {
            entries.add(new BarEntry(i, moods[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Year Moods");
        int[] colors;
        try {
            colors = SpecialUtils.getColors(getContext());
            dataSet.setColors(colors);
        } catch (NullPointerException e) {
            //
        }

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        BarData barData = new BarData(dataSet);
        mBarChart.setData(barData);

        mBarChart.getDescription().setEnabled(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setFitBars(true);
        ///mBarChart.invalidate(); // refresh
    }

    private String getYearConclusion(int[] moods) {
        int maxValue = 0;
        int moodId = 0;
        for (int i = 0; i < moods.length; i++) {
            if (moods[i] > maxValue) {
                maxValue = moods[i];
                moodId = i+1;
            }
        }

        try {
            int year = Preferences.getSelectedYear(getContext());
            String mood = SpecialUtils.getMoodLabel(getContext(), moodId);
            int totalDays = 365;
            if (SpecialUtils.isLeapYear(year)) {
                totalDays = 366;
            }
            int percents = maxValue * 100 / totalDays;
             return String.format(getString(R.string.format_year_conclusion), year, mood, percents);
        } catch (NullPointerException e) {
            //
        }

        return "";
    }

    private void setTypefaceFont(TextView view) {
        view.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Norican-Regular.ttf"));
    }
}
