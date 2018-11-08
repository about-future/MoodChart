package com.aboutfuture.moodchart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.AppExecutors;
import com.aboutfuture.moodchart.data.DailyMood;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.utils.SpecialUtils;
import com.aboutfuture.moodchart.viewmodel.AddDailyMoodViewModel;
import com.aboutfuture.moodchart.viewmodel.AddDailyMoodViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aboutfuture.moodchart.MainActivity.DAILY_MOOD_ID_KEY;
import static com.aboutfuture.moodchart.MainActivity.SELECTED_DAY_POSITION_KEY;
import static com.aboutfuture.moodchart.MainActivity.SELECTED_YEAR_KEY;

public class TodayActivity extends AppCompatActivity {
    private static final int DEFAULT_VALUE = -1;
    private int mMoodId = DEFAULT_VALUE;
    private int mPosition = DEFAULT_VALUE;
    private int mYear = DEFAULT_VALUE;

    @BindView(R.id.selected_mood_color_view)
    View mSelectedColorView;
    @BindView(R.id.mood_1_layout)
    ConstraintLayout mMood1Layout;
    @BindView(R.id.mood_2_layout)
    ConstraintLayout mMood2Layout;

    @BindView(R.id.position_text_view)
    TextView positionTextView;
    @BindView(R.id.daily_mood_id_text_view)
    TextView dailyMoodIdTextView;
    @BindView(R.id.result_text_view)
    TextView resultTextView;

    private AppDatabase mDb;
    private DailyMood mDailyMood;
    private int mFirstColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(DAILY_MOOD_ID_KEY)) {
            mMoodId = savedInstanceState.getInt(DAILY_MOOD_ID_KEY, DEFAULT_VALUE);
            mPosition = savedInstanceState.getInt(SELECTED_DAY_POSITION_KEY);
            mYear = savedInstanceState.getInt(SELECTED_YEAR_KEY);
        }

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(DAILY_MOOD_ID_KEY)) {
                mMoodId = intent.getIntExtra(DAILY_MOOD_ID_KEY, DEFAULT_VALUE);
            }

            if (intent.hasExtra(SELECTED_DAY_POSITION_KEY)) {
                mPosition = intent.getIntExtra(SELECTED_DAY_POSITION_KEY, DEFAULT_VALUE);
            }
            if (intent.hasExtra(SELECTED_YEAR_KEY)) {
                mYear = intent.getIntExtra(SELECTED_YEAR_KEY, DEFAULT_VALUE);
            }
        }

        if (mPosition != DEFAULT_VALUE) {
            int day = mPosition / 13;
            int month = mPosition % 13;

            positionTextView.setText(String.valueOf(mPosition));
            resultTextView.setText("Month: " + month + " | Day: " + day);
        }

        if (mYear != DEFAULT_VALUE) {
            // Set mYear as the current year
            mYear = SpecialUtils.getCurrentYear();
        }

        if (mMoodId != DEFAULT_VALUE) {
            dailyMoodIdTextView.setText(String.valueOf(mMoodId));
            AddDailyMoodViewModelFactory factory = new AddDailyMoodViewModelFactory(mDb, mMoodId);
            final AddDailyMoodViewModel viewModel = ViewModelProviders.of(this, factory).get(AddDailyMoodViewModel.class);
            viewModel.getDailyMoodDetails().observe(this, new Observer<DailyMood>() {
                @Override
                public void onChanged(@Nullable DailyMood dailyMood) {
                    mDailyMood = dailyMood;
                    populateUI(dailyMood);
                }
            });
        } else {
            populateUI(null);
        }
    }

    // Set default background colors, labels and click listeners for each mood option
    private void populateUI(DailyMood dailyMood) {
        if (dailyMood != null) {
            setMoodColor(mSelectedColorView, dailyMood.getFirstColor());
        }

        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_1_layout),
                findViewById(R.id.mood_1),
                (TextView) findViewById(R.id.mood_1_label),
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_1_label_key), getString(R.string.label_mood_1)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_1_color_key), ContextCompat.getColor(this, R.color.mood_color_1)));
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_2_layout),
                findViewById(R.id.mood_2),
                (TextView) findViewById(R.id.mood_2_label),
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_2_label_key), getString(R.string.label_mood_2)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_2_color_key), ContextCompat.getColor(this, R.color.mood_color_2)));
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_3_layout),
                findViewById(R.id.mood_3),
                (TextView) findViewById(R.id.mood_3_label),
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_3_label_key), getString(R.string.label_mood_3)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_3_color_key), ContextCompat.getColor(this, R.color.mood_color_3)));
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_4_layout),
                findViewById(R.id.mood_4),
                (TextView) findViewById(R.id.mood_4_label),
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_4_label_key), getString(R.string.label_mood_4)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_4_color_key), ContextCompat.getColor(this, R.color.mood_color_4)));
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_5_layout),
                findViewById(R.id.mood_5),
                (TextView) findViewById(R.id.mood_5_label),
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_5_label_key), getString(R.string.label_mood_5)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_5_color_key), ContextCompat.getColor(this, R.color.mood_color_5)));
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_6_layout),
                findViewById(R.id.mood_6),
                (TextView) findViewById(R.id.mood_6_label),
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_6_label_key), getString(R.string.label_mood_6)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_6_color_key), ContextCompat.getColor(this, R.color.mood_color_6)));
    }

    private void setMoodOption(ConstraintLayout layout, View view, TextView textView, String label, int color) {
        setMoodListener(layout, color);
        setMoodColor(view, color);
        setMoodLabel(textView, label);
    }

    private void setMoodListener(ConstraintLayout layout, final int color) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoodColor(mSelectedColorView, color);
                mFirstColor = color;
            }
        });
    }

    private void setMoodColor(View view, int color) {
        GradientDrawable one = (GradientDrawable) view.getBackground().mutate();
        one.setColor(color);
    }

    private void setMoodLabel(TextView textView, String label) {
        textView.setText(label);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar
        getMenuInflater().inflate(R.menu.menu_today_mood, menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        // If this is a new contact, hide the "Delete" menu item
//        if (mContactId == DEFAULT_CONTACT_ID) {
//            MenuItem menuDelete = menu.findItem(R.id.action_delete);
//            menuDelete.setVisible(false);
//        } else {
//            MenuItem menuImport = menu.findItem(R.id.action_import);
//            menuImport.setVisible(false);
//        }
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                onSaveButtonClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSaveButtonClicked() {
        final DailyMood dailyMood = new DailyMood(
                mYear,
                mPosition,
                mFirstColor,
                0);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mMoodId == DEFAULT_VALUE) {
                    // Insert new contact
                    mDb.moodsDao().insertDailyMood(dailyMood);
                } else {
                    // Update contact
                    dailyMood.setId(mMoodId);
                    mDb.moodsDao().updateDailyMood(dailyMood);
                }

                finish();
            }
        });
    }
}
