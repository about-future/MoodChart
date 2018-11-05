package com.aboutfuture.moodchart.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutfuture.moodchart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> {
    private final Context mContext;
    private int[] mNumbers;

    public NumbersAdapter(Context context) {
        mContext = context;
        //mNumbers = numbers;
    }

    @NonNull
    @Override
    public NumbersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_numbers_view, parent, false);
        view.setFocusable(false);
        return new NumbersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumbersAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            holder.numberTextView.setText("");
        } else {
            holder.numberTextView.setText(String.valueOf(mNumbers[position]));
        }
    }

    @Override
    public int getItemCount() {
        return mNumbers != null ? mNumbers.length : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number_text_view)
        TextView numberTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setNumbers(int[] numbers) {
        mNumbers = numbers;
        Log.v("SIZE", String.valueOf(numbers.length));
    }
}
