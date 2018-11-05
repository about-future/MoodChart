package com.aboutfuture.moodchart.utils;

import android.content.Context;

import com.aboutfuture.moodchart.R;

public class SpecialUtils {
    public static String getMonthInitial(Context context, int month) {
        switch (month) {
            case 1:
                return context.getString(R.string.month_initial_january);
            case 2:
                return context.getString(R.string.month_initial_february);
            case 3:
                return context.getString(R.string.month_initial_march);
            case 4:
                return context.getString(R.string.month_initial_april);
            case 5:
                return context.getString(R.string.month_initial_may);
            case 6:
                return context.getString(R.string.month_initial_june);
            case 7:
                return context.getString(R.string.month_initial_july);
            case 8:
                return context.getString(R.string.month_initial_august);
            case 9:
                return context.getString(R.string.month_initial_september);
            case 10:
                return context.getString(R.string.month_initial_october);
            case 11:
                return context.getString(R.string.month_initial_november);
            default:
                return context.getString(R.string.month_initial_december);
        }
    }
}
