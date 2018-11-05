package com.aboutfuture.moodchart.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.text.TextUtils;

@Entity(tableName = "moods")
public class DailyMood {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private int year;
    private int month;
    @ColumnInfo(name = "first_color")
    private int firstColor;
    @ColumnInfo(name = "second_color")
    private int secondColor;
    @ColumnInfo(name = "third_color")
    private int thirdColor;
    @ColumnInfo(name = "fourth_color")
    private int fourthColor;

    @Ignore
    public DailyMood(int firstColor) {
        this.firstColor = firstColor;
    }

    @Ignore
    public DailyMood(int month, String date) {
        //this.firstColor = firstColor;
        this.month = month;
    }

    @Ignore
    public DailyMood(int firstColor, int secondColor) {
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    @Ignore
    public DailyMood(int firstColor, int secondColor, int thirdColor) {
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.thirdColor = thirdColor;
    }

    public DailyMood(int id, String date, int year, int month, int firstColor,
                     int secondColor, int thirdColor, int fourthColor) {
        this.id = id;
        this.date = date;
        this.year = year;
        this.month = month;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.thirdColor = thirdColor;
        this.fourthColor = fourthColor;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getFirstColor() {
        return firstColor;
    }
    public int getSecondColor() {
        return secondColor;
    }
    public int getThirdColor() {
        return thirdColor;
    }
    public int getFourthColor() {
        return fourthColor;
    }

    public void setMonth(int month) { this.month = month; }
    public void setYear(int year) { this.year = year; }
    public void setDate(String date) { this.date = date; }

    public int getCount() {
        int count = 0;
        if (firstColor != 0) {
            count += 1;
            if (secondColor != 0) {
                count += 1;
                if (thirdColor != 0) {
                    count += 1;
                    if (fourthColor != 0) {
                        count += 1;
                    }
                }
            }

        }
        return count;
    }
}
