package com.aboutfuture.moodchart.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutfuture.moodchart.R;
import com.aboutfuture.moodchart.utils.SpecialUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyMoodAdapter extends RecyclerView.Adapter<DailyMoodAdapter.ViewHolder> {
    private final Context mContext;
    private List<DailyMood> mDailyMoods;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onItemClickListener(int dailyMoodId, int position);
    }

    public DailyMoodAdapter(Context context, ListItemClickListener clickListener) {
        mContext = context;
        mOnClickListener = clickListener;
    }

    @NonNull
    @Override
    public DailyMoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_view, parent, false);
        view.setFocusable(false);
        return new DailyMoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMoodAdapter.ViewHolder holder, int position) {

        if (position == 0) {
            holder.cellView.setVisibility(View.GONE);
            holder.monthTextView.setVisibility(View.VISIBLE);
            holder.monthTextView.setText("");
        } else if (position > 0 && position <= 12) {
            holder.cellView.setVisibility(View.GONE);
            holder.monthTextView.setVisibility(View.VISIBLE);
            holder.monthTextView.setText(SpecialUtils.getMonthInitial(mContext, position));
        } else if (position % 13 == 0) {
            holder.cellView.setVisibility(View.GONE);
            holder.monthTextView.setVisibility(View.VISIBLE);
            holder.monthTextView.setText(String.valueOf(position / 13));
        } else {
            holder.cellView.setVisibility(View.VISIBLE);
            holder.monthTextView.setVisibility(View.GONE);

            DailyMood currentDay = mDailyMoods.get(position);

            if (currentDay != null) {
                holder.cellView.setBackgroundColor(currentDay.getFirstColor());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDailyMoods != null ? mDailyMoods.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.up_view)
        View cellView;
        @BindView(R.id.month_text_view)
        TextView monthTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position > 12 && position % 13 != 0) {
                if (mDailyMoods.get(position) != null) {
                    mOnClickListener.onItemClickListener(mDailyMoods.get(position).getId(), position);
                } else {
                    mOnClickListener.onItemClickListener(-1, position);
                }
            }
        }
    }

    public void setDailyMoods(List<DailyMood> dailyMoods) {
        mDailyMoods = dailyMoods;
    }
}
