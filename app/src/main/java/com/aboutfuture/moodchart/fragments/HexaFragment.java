package com.aboutfuture.moodchart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutfuture.moodchart.MainActivity;
import com.aboutfuture.moodchart.R;
import com.aboutfuture.moodchart.views.HexaView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HexaFragment extends Fragment {
    @BindView(R.id.first_hexagon)
    HexaView mHexagonView;

    public HexaFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_hexagons, container, false);
        ButterKnife.bind(this, rootView);

        mHexagonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHexagonView.setHexaColor(ContextCompat.getColor(getActivityCast(), R.color.mood_color_6b));
            }
        });

        return rootView;
    }

    private MainActivity getActivityCast() {
        return (MainActivity) getActivity();
    }
}
