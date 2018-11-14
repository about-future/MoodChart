package com.aboutfuture.moodchart.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutfuture.moodchart.R;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.utils.SpecialUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyMoodAdapter extends RecyclerView.Adapter<DailyMoodAdapter.ViewHolder> {
    private final Context mContext;
    private List<DailyMood> mDailyMoods;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onItemClickListener(int position);
    }

    public DailyMoodAdapter(Context context, ListItemClickListener clickListener) {
        mContext = context;
        mOnClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set text
        if (position == 0) {
            holder.dayMoodTextView.setText("");
        } else if (position > 0 && position <= 12) {
            holder.dayMoodTextView.setText(SpecialUtils.getMonthInitial(mContext, position));
        } else if (position % 13 == 0) {
            holder.dayMoodTextView.setText(String.valueOf(position / 13));
        } else {
            holder.dayMoodTextView.setText("");
        }

        // Set background color
        if ((position >= 0 && position <= 12) ||
                position % 13 == 0 ||
                position == 392 || position == 405 || position == 407 ||
                position == 409 || position == 412 || position == 414) {
            holder.dayMoodTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        } else if (position == 379) {
            if (SpecialUtils.isLeapYear(Preferences.getSelectedYear(mContext))) {
                //TODO: code is repeating here
                if (mDailyMoods.get(position) != null && mDailyMoods.get(position).getFirstColor() != 0) {
                    holder.dayMoodTextView.setBackgroundColor(SpecialUtils.getColor(
                            mContext,
                            mDailyMoods.get(position).getFirstColor()));
                } else {
                    holder.dayMoodTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_selector));
                }
            } else {
                holder.dayMoodTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            }
        } else {
            //TODO: code is repeating here
            if (mDailyMoods.get(position) != null && mDailyMoods.get(position).getFirstColor() != 0) {
                holder.dayMoodTextView.setBackgroundColor(SpecialUtils.getColor(
                        mContext,
                        mDailyMoods.get(position).getFirstColor()));
            } else {
                holder.dayMoodTextView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_selector));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDailyMoods != null ? mDailyMoods.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.day_mood_text_view)
        TextView dayMoodTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            // For all the positions that are not listed bellow or if position is 379 and
            // it's a leap year, we set a listener
            if (position > 12 && position % 13 != 0 && position != 392 && position != 405 &&
                    position != 407 && position != 409 && position != 412 && position != 414) {
                if (position == 379) {
                    if (SpecialUtils.isLeapYear(Preferences.getSelectedYear(mContext))) {
                        mOnClickListener.onItemClickListener(position);
                    }
                } else {
                    mOnClickListener.onItemClickListener(position);
                }
            }
        }
    }

    public void setMoods(List<DailyMood> dailyMoods) {
        mDailyMoods = dailyMoods;
    }
}
