package com.aboutfuture.moodchart.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.aboutfuture.moodchart.R;
import com.aboutfuture.moodchart.data.DailyMood;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static String getMonthName(Context context, int month) {
        switch (month) {
            case 1:
                return context.getString(R.string.january);
            case 2:
                return context.getString(R.string.february);
            case 3:
                return context.getString(R.string.march);
            case 4:
                return context.getString(R.string.april);
            case 5:
                return context.getString(R.string.may);
            case 6:
                return context.getString(R.string.june);
            case 7:
                return context.getString(R.string.july);
            case 8:
                return context.getString(R.string.august);
            case 9:
                return context.getString(R.string.september);
            case 10:
                return context.getString(R.string.october);
            case 11:
                return context.getString(R.string.november);
            default:
                return context.getString(R.string.december);
        }
    }

    public static boolean isLeapYear(int year) {
        // Calculate if it's a leap year
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        } else if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static int getCurrentYear() {
        Date time = new Date(new Date().getTime());
        SimpleDateFormat simpleYearFormat = new SimpleDateFormat("yyyy", Locale.US);
        return Integer.parseInt(simpleYearFormat.format(time));
    }

    public static int getColor(Context context, int moodIndex) {
        switch (moodIndex) {
            case 1:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_1_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_1));
            case 2:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_2_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_2));
            case 3:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_3_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_3));
            case 4:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_4_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_4));
            case 5:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_5_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_5));
            case 6:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_6_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_6));
            case 7:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_7_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_7));
            case 8:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_8_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_8));
            case 9:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_9_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_9));
            case 10:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_10_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_10));
            case 11:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_11_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_11));
            case 12:
                return Preferences.getMoodColor(
                        context,
                        context.getString(R.string.pref_mood_12_color_key),
                        ContextCompat.getColor(context, R.color.mood_color_12));
            default:
                return 0xFFFFFFFF;
        }
    }
}
