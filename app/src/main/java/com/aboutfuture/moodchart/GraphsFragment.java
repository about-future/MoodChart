package com.aboutfuture.moodchart;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.AppExecutors;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.utils.SpecialUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphsFragment extends Fragment {

    @BindView(R.id.year_bar_chart)
    BarChart mBarChart;
    @BindView(R.id.year_conclusion_text_view)
    TextView mYearConclusionTextView;

    @BindView(R.id.mood_1_line_chart)
    BarChart mMood1LineChart;
    @BindView(R.id.mood_2_line_chart)
    BarChart mMood2LineChart;
    @BindView(R.id.mood_3_line_chart)
    BarChart mMood3LineChart;
    @BindView(R.id.mood_4_line_chart)
    BarChart mMood4LineChart;
    @BindView(R.id.mood_5_line_chart)
    BarChart mMood5LineChart;
    @BindView(R.id.mood_6_line_chart)
    BarChart mMood6LineChart;
    @BindView(R.id.mood_7_line_chart)
    BarChart mMood7LineChart;
    @BindView(R.id.mood_8_line_chart)
    BarChart mMood8LineChart;
    @BindView(R.id.mood_9_line_chart)
    BarChart mMood9LineChart;
    @BindView(R.id.mood_10_line_chart)
    BarChart mMood10LineChart;
    @BindView(R.id.mood_11_line_chart)
    BarChart mMood11LineChart;
    @BindView(R.id.mood_12_line_chart)
    BarChart mMood12LineChart;

    @BindView(R.id.mood_1_conclusion_text_view)
    TextView mMood1TextView;
    @BindView(R.id.mood_2_conclusion_text_view)
    TextView mMood2TextView;
    @BindView(R.id.mood_3_conclusion_text_view)
    TextView mMood3TextView;
    @BindView(R.id.mood_4_conclusion_text_view)
    TextView mMood4TextView;
    @BindView(R.id.mood_5_conclusion_text_view)
    TextView mMood5TextView;
    @BindView(R.id.mood_6_conclusion_text_view)
    TextView mMood6TextView;
    @BindView(R.id.mood_7_conclusion_text_view)
    TextView mMood7TextView;
    @BindView(R.id.mood_8_conclusion_text_view)
    TextView mMood8TextView;
    @BindView(R.id.mood_9_conclusion_text_view)
    TextView mMood9TextView;
    @BindView(R.id.mood_10_conclusion_text_view)
    TextView mMood10TextView;
    @BindView(R.id.mood_11_conclusion_text_view)
    TextView mMood11TextView;
    @BindView(R.id.mood_12_conclusion_text_view)
    TextView mMood12TextView;

    private AppDatabase mDb;
    private int[] mColors;
    private String[] mMoodLabels;
    private String[] mMonthsLabels;

    public GraphsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
        ButterKnife.bind(this, rootView);

        mDb = AppDatabase.getInstance(getContext());

        TextView[] views = new TextView[] {
                mMood1TextView, mMood2TextView, mMood3TextView, mMood4TextView,
                mMood5TextView, mMood6TextView, mMood7TextView, mMood8TextView,
                mMood9TextView, mMood10TextView, mMood11TextView, mMood12TextView};

        BarChart[] charts = new BarChart[] {
                mMood1LineChart, mMood2LineChart,mMood3LineChart, mMood4LineChart,
                mMood5LineChart, mMood6LineChart, mMood7LineChart, mMood8LineChart,
                mMood9LineChart, mMood10LineChart, mMood11LineChart, mMood12LineChart};

        //TODO: Make this a pie chart
        mYearConclusionTextView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Norican-Regular.ttf"));

        mColors = SpecialUtils.getColors(getContext());
        mMoodLabels = SpecialUtils.getMoodLabels(getContext());
        int year = Preferences.getSelectedYear(getContext());
        mMonthsLabels = new String[] {
                getString(R.string.january),
                getString(R.string.february),
                getString(R.string.march),
                getString(R.string.april),
                getString(R.string.may),
                getString(R.string.june),
                getString(R.string.july),
                getString(R.string.august),
                getString(R.string.september),
                getString(R.string.october),
                getString(R.string.november),
                getString(R.string.december)};

        countEachMoodForThisYear(year);
        //countEachMoodInThisMonth(4, year);
        for (int i = 0; i < 12; i++) {
            views[i].setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Norican-Regular.ttf"));
            countMoodMonthByMonthForYear(charts[i],i + 1, year, views[i]);
        }

        return rootView;
    }

    private void countEachMoodForThisYear(final int year) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int[] moods = new int[12];
                for (int i = 0; i < 12; i++) {
                    moods[i] = mDb.moodsDao().countFirstColorInYear(i + 1, year);
                }

                if (getActivity() != null) {
                    setBarChart(moods);
                    try {
                        mYearConclusionTextView.setText(getYearConclusion(moods, year));
                    } catch (Exception e) { /**/ }
                }
            }
        });
    }

    //    private void countMoodsInThisMonth(final int month, final int year) {
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                int[] monthlyMoods = new int[12];
//                for (int i = 0; i < 12; i++) {
//                    monthlyMoods[i] = mDb.moodsDao().countFirstColorInMonth(i + 1, month, year);
//                }
//                countMood
//            }
//        });
//    }

    private void countMoodMonthByMonthForYear(final BarChart chart, final int moodId, final int year, final TextView view) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int[] moodByMonth = new int[12];
                int days = 0;
                for (int i = 0; i < 12; i++) {
                    moodByMonth[i] = mDb.moodsDao().countFirstColorInMonth(moodId, i + 1, year);
                    days += moodByMonth[i];
                }

                if (getActivity() != null) {
                    setBarChart(chart, moodId, moodByMonth);
                    try {
                        view.setText(getMoodConclusion(moodId, year, days));
                    } catch (Exception e) { /**/ }
                }
            }
        });
    }

    private void setBarChart(int[] moods) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < moods.length; i++) {
            entries.add(new BarEntry(i, moods[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Year Moods");
        dataSet.setColors(mColors);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMoodLabels[(int) value];
            }
        });
        xAxis.setLabelRotationAngle(45f);
        xAxis.setLabelCount(12);

        YAxis yAxisLeft = mBarChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        YAxis yAxisRight = mBarChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);

        BarData barData = new BarData(dataSet);
        mBarChart.setData(barData);

        mBarChart.getDescription().setEnabled(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setFitBars(true);
    }

    private void setBarChart(BarChart chart, int moodId, int[] moodByMonth) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < moodByMonth.length; i++) {
            entries.add(new BarEntry(i, moodByMonth[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, mMoodLabels[moodId - 1]);
        dataSet.setColor(mColors[moodId - 1]);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mMonthsLabels[(int) value];
            }
        });
        xAxis.setLabelRotationAngle(50f);
        xAxis.setLabelCount(12);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawGridLines(false);

        BarData barData = new BarData(dataSet);
        chart.setData(barData);

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setFitBars(true);
    }


    private void setLineChart(LineChart chart, int moodId, int[] moodByMonth) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < moodByMonth.length; i++) {
            entries.add(new Entry(i, moodByMonth[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, mMoodLabels[moodId - 1]);
        dataSet.setColor(mColors[moodId - 1]);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawGridLines(false);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
    }

    private String getYearConclusion(int[] moods, int year) {
        int maxValue = 0;
        int moodId = 0;
        for (int i = 0; i < moods.length; i++) {
            if (moods[i] > maxValue) {
                maxValue = moods[i];
                moodId = i + 1;
            }
        }

        int totalDays = 365;
        if (SpecialUtils.isLeapYear(year))
            totalDays = 366;
        int percents = maxValue * 100 / totalDays;

        return String.format(getString(R.string.format_year_conclusion), year, mMoodLabels[moodId - 1], percents);
    }

    private String getMoodConclusion(int moodId, int year, int days) {
        if (days == 0) {
            return String.format(getString(R.string.format_zero_mood_conclusion_for_year), year, mMoodLabels[moodId - 1]);
        } else {
            return String.format(getString(R.string.format_mood_conclusion_for_year), year, mMoodLabels[moodId - 1], days);
        }
    }

    private String formatMoodLabel(String label) {
        return label.replaceFirst(
                String.valueOf(label.charAt(0)),
                String.valueOf(Character.toUpperCase(label.charAt(0))));
    }
}
