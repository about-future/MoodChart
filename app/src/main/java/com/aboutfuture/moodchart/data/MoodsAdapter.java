package com.aboutfuture.moodchart.data;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboutfuture.moodchart.R;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.utils.SpecialUtils;
import com.aboutfuture.moodchart.views.CellView;

import java.util.List;


public class MoodsAdapter extends BaseAdapter {
    private final Context mContext;
    private List<Mood> mMoods;
    private float mDensity;
    private boolean mIsPortraitMode;

    public MoodsAdapter(Context context, float density, boolean isPortraitMode) {
        mContext = context;
        mDensity = density;
        mIsPortraitMode = isPortraitMode;
    }

    @Override
    public int getCount() {
        return mMoods != null ? mMoods.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mMoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mood mood = mMoods.get(position);
        //TextView moodTextView;
        CellView moodCellView;

        if(convertView == null){
            //moodTextView = new TextView(mContext);
            moodCellView = new CellView(mContext);
            if(mIsPortraitMode) {
                //moodTextView.setHeight((int) (28 * mDensity));
                moodCellView.setHeight((int) (28 * mDensity));
            } else {
                //moodTextView.setHeight((int) (50 * mDensity));
                moodCellView.setHeight((int) (50 * mDensity));
            }
            //moodTextView.setGravity(Gravity.CENTER);
            moodCellView.setGravity(Gravity.CENTER);
        } else {
            //moodTextView = (TextView) convertView;
            moodCellView = (CellView) convertView;
        }

        // Set text
        if (position == 0) {
            //moodTextView.setText("");
            moodCellView.setText("");
        } else if (position <= 12) {
            //moodTextView.setText(SpecialUtils.getMonthInitial(mContext, position));
            moodCellView.setText(SpecialUtils.getMonthInitial(mContext, position));
        } else if (position % 13 == 0) {
            //moodTextView.setText(String.valueOf(position / 13));
            moodCellView.setText(String.valueOf(position / 13));
        } else {
            //moodTextView.setText("");
            moodCellView.setText("");
        }

        // Set background color
        if (position <= 12 || position % 13 == 0 ||
                position == 392 || position == 405 || position == 407 ||
                position == 409 || position == 412 || position == 414) {
            //moodTextView.setBackgroundColor(0xFFFFFFFF);
            moodCellView.setBackgroundColor(0xFFFFFFFF);
            //moodCellView.setSecondColor(Color.TRANSPARENT);
        } else if (position == 379) {
            if (SpecialUtils.isLeapYear(Preferences.getSelectedYear(mContext))) {
                //TODO: code is repeating here
                if (mood.getFirstColor() != 0) {
                    //moodTextView.setBackgroundColor(SpecialUtils.getColor(mContext, mood.getFirstColor()));
                    moodCellView.setBackgroundColor(SpecialUtils.getColor(mContext, mood.getFirstColor()));
                    // TODO: add if block
                } else {
                    //moodTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_selector));
                    moodCellView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_selector));
                    //moodCellView.setSecondColor(Color.TRANSPARENT);
                }
            } else {
                //moodTextView.setBackgroundColor(0xFFFFFFFF);
                moodCellView.setBackgroundColor(0xFFFFFFFF);
                //moodCellView.setSecondColor(Color.TRANSPARENT);
            }
        } else {
            //TODO: code is repeating here
            moodCellView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_selector));
            if (mood.getFirstColor() != 0) {
                //moodTextView.setBackgroundColor(SpecialUtils.getColor(mContext, mood.getFirstColor()));
                //moodCellView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_selector));
                moodCellView.setBackgroundColor(SpecialUtils.getColor(mContext, mood.getFirstColor()));
//                if (mood.getSecondColor() != 0) {
//                    moodCellView.setSecondColor(SpecialUtils.getColor(mContext, mood.getSecondColor()));
//                } else {
//                    moodCellView.setSecondColor(0x00FFFFFF);
//                }
            } else {
                //moodTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_selector));
                moodCellView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_selector));
                //moodCellView.setSecondColor(Color.TRANSPARENT);
            }
        }

        //return moodTextView;
        return moodCellView;
    }

    public void setMoods(List<Mood> moods) {
        mMoods = moods;
    }
}
