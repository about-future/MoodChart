package com.aboutfuture.moodchart.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "moods")
public class DailyMood {
    @PrimaryKey(autoGenerate = true)
    private int id;
    // Year of entry
    private int year;
    // Position in the year matrix of 13 cols x 32 rows
    private int position;
    @ColumnInfo(name = "first_color")
    private int firstColor;
    @ColumnInfo(name = "second_color")
    private int secondColor;

    @Ignore
    public DailyMood(int firstColor) {
        this.firstColor = firstColor;
    }

    public DailyMood(int id, int year, int position, int firstColor, int secondColor) {
        this.id = id;
        this.year = year;
        this.position = position;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    @Ignore
    public DailyMood(int year, int position, int firstColor, int secondColor) {
        this.year = year;
        this.position = position;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    public int getId() { return id; }
    public int getYear() { return year; }
    public int getPosition() { return position; }
    public int getFirstColor() {
        return firstColor;
    }
    public int getSecondColor() {
        return secondColor;
    }

    public void setId(int id) { this.id = id; }
    public void setPosition(int position) { this.position = position; }
    public void setYear(int year) { this.year = year; }

    public int getCount() {
        int count = 0;
        if (firstColor != 0) {
            count += 1;
            if (secondColor != 0) {
                count += 1;
            }
        }
        return count;
    }
}
