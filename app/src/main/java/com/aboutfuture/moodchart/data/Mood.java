package com.aboutfuture.moodchart.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "moods")
public class Mood {
    @PrimaryKey(autoGenerate = true)
    private int id;
    // Year of entry
    private int year;
    // Year of entry
    private int month;
    // Position in the year matrix of 13 cols x 32 rows
    private int position;
    @ColumnInfo(name = "first_color")
    private int firstColor;
    @ColumnInfo(name = "second_color")
    private int secondColor;

    @Ignore
    public Mood(int firstColor) {
        this.firstColor = firstColor;
    }

    public Mood(int id, int year, int month, int position, int firstColor, int secondColor) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.position = position;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    @Ignore
    public Mood(int year, int month, int position, int firstColor, int secondColor) {
        this.year = year;
        this.month = month;
        this.position = position;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    public int getId() { return id; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
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