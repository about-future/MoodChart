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
//            holder.upLeftView.setVisibility(View.GONE);
//            holder.upRightView.setVisibility(View.GONE);
//            holder.downLeftView.setVisibility(View.GONE);
//            holder.downRightView.setVisibility(View.GONE);
            holder.monthTextView.setVisibility(View.VISIBLE);
            holder.monthTextView.setText("");
        } else if (position > 0 && position <= 12) {
            holder.cellView.setVisibility(View.GONE);
//            holder.upLeftView.setVisibility(View.GONE);
//            holder.upRightView.setVisibility(View.GONE);
//            holder.downLeftView.setVisibility(View.GONE);
//            holder.downRightView.setVisibility(View.GONE);
            holder.monthTextView.setVisibility(View.VISIBLE);
            holder.monthTextView.setText(SpecialUtils.getMonthInitial(mContext, position));
        } else if (position % 13 == 0) {
            holder.cellView.setVisibility(View.GONE);
//            holder.upLeftView.setVisibility(View.GONE);
//            holder.upRightView.setVisibility(View.GONE);
//            holder.downLeftView.setVisibility(View.GONE);
//            holder.downRightView.setVisibility(View.GONE);
            holder.monthTextView.setVisibility(View.VISIBLE);
            holder.monthTextView.setText(String.valueOf(position / 13));
        } else {
            holder.cellView.setVisibility(View.VISIBLE);
//            holder.upLeftView.setVisibility(View.VISIBLE);
//            holder.upRightView.setVisibility(View.VISIBLE);
//            holder.downLeftView.setVisibility(View.VISIBLE);
//            holder.downRightView.setVisibility(View.VISIBLE);
            holder.monthTextView.setVisibility(View.GONE);

            DailyMood currentDay = mDailyMoods.get(position);

            if (currentDay != null) {
                int count = currentDay.getCount();

                switch (count) {
                    case 1:
                        holder.cellView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.upLeftView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.upRightView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.downLeftView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.downRightView.setBackgroundColor(currentDay.getFirstColor());
                        break;
                    case 2:
                        holder.cellView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.upLeftView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.upRightView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.downLeftView.setBackgroundColor(currentDay.getSecondColor());
//                        holder.downRightView.setBackgroundColor(currentDay.getSecondColor());
                        break;
                    case 3:
                        holder.cellView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.upLeftView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.upRightView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.downLeftView.setBackgroundColor(currentDay.getSecondColor());
//                        holder.downRightView.setBackgroundColor(currentDay.getThirdColor());
                        break;
                    case 4:
                        holder.cellView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.upLeftView.setBackgroundColor(currentDay.getFirstColor());
//                        holder.upRightView.setBackgroundColor(currentDay.getSecondColor());
//                        holder.downLeftView.setBackgroundColor(currentDay.getThirdColor());
//                        holder.downRightView.setBackgroundColor(currentDay.getFourthColor());
                        break;
                }
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
//        @BindView(R.id.up_left_view)
//        View upLeftView;
//        @BindView(R.id.up_right_view)
//        View upRightView;
//        @BindView(R.id.down_left_view)
//        View downLeftView;
//        @BindView(R.id.down_right_view)
//        View downRightView;
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
