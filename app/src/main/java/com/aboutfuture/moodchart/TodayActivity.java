package com.aboutfuture.moodchart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.AppExecutors;
import com.aboutfuture.moodchart.data.Mood;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.utils.SpecialUtils;
import com.aboutfuture.moodchart.viewmodel.AddDailyMoodViewModel;
import com.aboutfuture.moodchart.viewmodel.AddDailyMoodViewModelFactory;
import com.aboutfuture.moodchart.views.CellView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aboutfuture.moodchart.MainActivity.SELECTED_DAY_POSITION_KEY;

public class TodayActivity extends AppCompatActivity {
    private static final String IS_CLEAR_BUTTON_VISIBLE_KEY = "clear_button_key";
    private static final String SELECTED_FIRST_COLOR_KEY = "first_color_key";
    private static final String SELECTED_SECOND_COLOR_KEY = "second_color_key";
    private static final String MOOD_ID_KEY = "mood_id_key";
    private static final int DEFAULT_VALUE = -1;
    private int mMoodId = DEFAULT_VALUE;
    private int mPosition = DEFAULT_VALUE;

    @BindView(R.id.selected_mood_color_view)
    CellView mSelectedColorView;
    @BindView(R.id.date_text_view)
    TextView mDateTextView;
    @BindView(R.id.mood_1_label)
    TextView mMood1LabelTextView;
    @BindView(R.id.mood_2_label)
    TextView mMood2LabelTextView;
    @BindView(R.id.mood_3_label)
    TextView mMood3LabelTextView;
    @BindView(R.id.mood_4_label)
    TextView mMood4LabelTextView;
    @BindView(R.id.mood_5_label)
    TextView mMood5LabelTextView;
    @BindView(R.id.mood_6_label)
    TextView mMood6LabelTextView;
    @BindView(R.id.mood_7_label)
    TextView mMood7LabelTextView;
    @BindView(R.id.mood_8_label)
    TextView mMood8LabelTextView;
    @BindView(R.id.mood_9_label)
    TextView mMood9LabelTextView;
    @BindView(R.id.mood_10_label)
    TextView mMood10LabelTextView;
    @BindView(R.id.mood_11_label)
    TextView mMood11LabelTextView;
    @BindView(R.id.mood_12_label)
    TextView mMood12LabelTextView;
    @BindView(R.id.reset_selected_color)
    ImageView mResetColorImageView;

    private AppDatabase mDb;
    private int mFirstColor = 0;
    private int mSecondColor = 0;
    private boolean isClearButtonVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        Toolbar toolbar = findViewById(R.id.today_toolbar);
        TextView mTitle = toolbar.findViewById(R.id.today_toolbar_title);
        mTitle.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Norican-Regular.ttf"));
        setSupportActionBar(toolbar);
        setTitle("");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(IS_CLEAR_BUTTON_VISIBLE_KEY))
                isClearButtonVisible = savedInstanceState.getBoolean(IS_CLEAR_BUTTON_VISIBLE_KEY);
            if (savedInstanceState.containsKey(SELECTED_DAY_POSITION_KEY))
                mPosition = savedInstanceState.getInt(SELECTED_DAY_POSITION_KEY);
            if (savedInstanceState.containsKey(SELECTED_FIRST_COLOR_KEY))
                mFirstColor = savedInstanceState.getInt(SELECTED_FIRST_COLOR_KEY);
            if (savedInstanceState.containsKey(SELECTED_SECOND_COLOR_KEY))
                mSecondColor = savedInstanceState.getInt(SELECTED_SECOND_COLOR_KEY);

            mMoodId = savedInstanceState.getInt(MOOD_ID_KEY);
        }

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(SELECTED_DAY_POSITION_KEY)) {
            mPosition = intent.getIntExtra(SELECTED_DAY_POSITION_KEY, DEFAULT_VALUE);
        }

        if (mPosition != DEFAULT_VALUE && mMoodId == DEFAULT_VALUE) {
            AddDailyMoodViewModelFactory factory = new AddDailyMoodViewModelFactory(
                    mDb,
                    mPosition,
                    SpecialUtils.getCurrentYear());
            final AddDailyMoodViewModel viewModel = ViewModelProviders.of(this, factory).get(AddDailyMoodViewModel.class);
            viewModel.getDailyMoodDetails().observe(this, new Observer<Mood>() {
                @Override
                public void onChanged(@Nullable Mood mood) {
                    if (mood != null) {
                        mMoodId = mood.getId();
                        mFirstColor = mood.getFirstColor();
                        mSecondColor = mood.getSecondColor();
                        if (mFirstColor != 0) {
                            setFirstMoodColor(mSelectedColorView, SpecialUtils.getColor(getApplicationContext(), mFirstColor));
                            mResetColorImageView.setVisibility(View.VISIBLE);
                            isClearButtonVisible = true;
                            if (mSecondColor != 0) {
                                setSecondMoodColor(mSelectedColorView, SpecialUtils.getColor(getApplicationContext(), mSecondColor));
                            } else {
                                setSecondMoodColor(mSelectedColorView, Color.TRANSPARENT);
                            }
                        }
                    }
                }
            });
        }

        populateUI();
        setFirstMoodColor(mSelectedColorView, SpecialUtils.getColor(getApplicationContext(), mFirstColor));
        if (mSecondColor != 0) {
            setSecondMoodColor(mSelectedColorView, SpecialUtils.getColor(getApplicationContext(), mSecondColor));
        } else {
            setSecondMoodColor(mSelectedColorView, Color.TRANSPARENT);
        }

        if (isClearButtonVisible) {
            mResetColorImageView.setVisibility(View.VISIBLE);
        } else {
            mResetColorImageView.setVisibility(View.GONE);
        }

        mResetColorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFirstMoodColor(mSelectedColorView, Color.WHITE);
                setSecondMoodColor(mSelectedColorView, Color.TRANSPARENT);
                mFirstColor = 0;
                mSecondColor = 0;
                mDateTextView.setTextColor(Color.BLACK);
                mResetColorImageView.setColorFilter(Color.BLACK);
                mResetColorImageView.setVisibility(View.GONE);
                isClearButtonVisible = false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_CLEAR_BUTTON_VISIBLE_KEY, isClearButtonVisible);
        outState.putInt(SELECTED_DAY_POSITION_KEY, mPosition);
        outState.putInt(SELECTED_FIRST_COLOR_KEY, mFirstColor);
        outState.putInt(SELECTED_SECOND_COLOR_KEY, mSecondColor);
        outState.putInt(MOOD_ID_KEY, mMoodId);
    }

    // Set default background colors, labels and click listeners for each mood option
    private void populateUI() {
        int day = mPosition / 13;
        int month = mPosition % 13;

        // TODO: see enumerations/plurals in android for st, nd and th
        // TODO: solve this case for other languages
        String dayString = String.valueOf(day);
        switch (day) {
            case 1:
                dayString = dayString.concat("st");
                break;
            case 2:
                dayString = dayString.concat("nd");
                break;
            case 3:
                dayString = dayString.concat("rd");
                break;
            case 21:
                dayString = dayString.concat("st");
                break;
            case 22:
                dayString = dayString.concat("nd");
                break;
            case 23:
                dayString = dayString.concat("rd");
                break;
            case 31:
                dayString = dayString.concat("st");
                break;
            default:
                dayString = dayString.concat("th");
                break;
        }

        mDateTextView.setText(String.format(
                getString(R.string.format_date),
                SpecialUtils.getMonthName(this, month),
                dayString,
                Preferences.getSelectedYear(this)));

        setTypefaceFont(mMood1LabelTextView);
        setTypefaceFont(mMood2LabelTextView);
        setTypefaceFont(mMood3LabelTextView);
        setTypefaceFont(mMood4LabelTextView);
        setTypefaceFont(mMood5LabelTextView);
        setTypefaceFont(mMood6LabelTextView);
        setTypefaceFont(mMood7LabelTextView);
        setTypefaceFont(mMood8LabelTextView);
        setTypefaceFont(mMood9LabelTextView);
        setTypefaceFont(mMood10LabelTextView);
        setTypefaceFont(mMood11LabelTextView);
        setTypefaceFont(mMood12LabelTextView);

        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_1_layout),
                findViewById(R.id.mood_1),
                mMood1LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_1_label_key), getString(R.string.label_mood_1)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_1_color_key), ContextCompat.getColor(this, R.color.mood_color_1)),
                1);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_2_layout),
                findViewById(R.id.mood_2),
                mMood2LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_2_label_key), getString(R.string.label_mood_2)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_2_color_key), ContextCompat.getColor(this, R.color.mood_color_2)),
                2);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_3_layout),
                findViewById(R.id.mood_3),
                mMood3LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_3_label_key), getString(R.string.label_mood_3)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_3_color_key), ContextCompat.getColor(this, R.color.mood_color_3)),
                3);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_4_layout),
                findViewById(R.id.mood_4),
                mMood4LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_4_label_key), getString(R.string.label_mood_4)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_4_color_key), ContextCompat.getColor(this, R.color.mood_color_4)),
                4);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_5_layout),
                findViewById(R.id.mood_5),
                mMood5LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_5_label_key), getString(R.string.label_mood_5)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_5_color_key), ContextCompat.getColor(this, R.color.mood_color_5)),
                5);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_6_layout),
                findViewById(R.id.mood_6),
                mMood6LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_6_label_key), getString(R.string.label_mood_6)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_6_color_key), ContextCompat.getColor(this, R.color.mood_color_6)),
                6);

        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_7_layout),
                findViewById(R.id.mood_7),
                mMood7LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_7_label_key), getString(R.string.label_mood_7)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_7_color_key), ContextCompat.getColor(this, R.color.mood_color_7)),
                7);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_8_layout),
                findViewById(R.id.mood_8),
                mMood8LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_8_label_key), getString(R.string.label_mood_8)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_8_color_key), ContextCompat.getColor(this, R.color.mood_color_8)),
                8);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_9_layout),
                findViewById(R.id.mood_9),
                mMood9LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_9_label_key), getString(R.string.label_mood_9)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_9_color_key), ContextCompat.getColor(this, R.color.mood_color_9)),
                9);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_10_layout),
                findViewById(R.id.mood_10),
                mMood10LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_10_label_key), getString(R.string.label_mood_10)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_10_color_key), ContextCompat.getColor(this, R.color.mood_color_10)),
                10);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_11_layout),
                findViewById(R.id.mood_11),
                mMood11LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_11_label_key), getString(R.string.label_mood_11)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_11_color_key), ContextCompat.getColor(this, R.color.mood_color_11)),
                11);
        setMoodOption(
                (ConstraintLayout) findViewById(R.id.mood_12_layout),
                findViewById(R.id.mood_12),
                mMood12LabelTextView,
                Preferences.getMoodLabel(this, getString(R.string.pref_mood_12_label_key), getString(R.string.label_mood_12)),
                Preferences.getMoodColor(this, getString(R.string.pref_mood_12_color_key), ContextCompat.getColor(this, R.color.mood_color_12)),
                12);
    }

    private void setTypefaceFont(TextView view) {
        view.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Norican-Regular.ttf"));
    }

    private void setMoodOption(ConstraintLayout layout, View view, TextView textView, String label, int color, int moodId) {
        setMoodListeners(layout, color, moodId);
        setMoodColor(view, color);
        setMoodLabel(textView, label);
    }

    private void setMoodListeners(ConstraintLayout layout, final int color, final int moodId) {
        // Listener for the first color
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFirstMoodColor(mSelectedColorView, color);
                mFirstColor = moodId;

                // If color is black or dark gray, set date text and X reset color button as white
                if (color == 0xFF000000 || color == 0xFF111111 || color == 0xFF212121 || color == 0xFF333333) {
                    mDateTextView.setTextColor(Color.WHITE);
                } else {
                    // Otherwise, set it black
                    mDateTextView.setTextColor(Color.BLACK);
                }
                // Set ResetColor image as visible and isClearButtonVisible as true
                mResetColorImageView.setVisibility(View.VISIBLE);
                isClearButtonVisible = true;
            }
        });

        // Listener for the second color
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setSecondMoodColor(mSelectedColorView, color);
                mSecondColor = moodId;

                // If color is black or dark gray, set X reset color button as white
                if (color == 0xFF000000 || color == 0xFF111111 || color == 0xFF212121 || color == 0xFF333333) {
                    mResetColorImageView.setColorFilter(Color.WHITE);
                } else {
                    // Otherwise, set it black
                    mResetColorImageView.setColorFilter(Color.BLACK);
                }

                return true;
            }
        });
    }

    private void setMoodColor(View view, int color) {
        GradientDrawable one = (GradientDrawable) view.getBackground().mutate();
        one.setColor(color);
    }

    private void setFirstMoodColor(CellView view, int color) {
        view.setBackgroundColor(color);
    }

    private void setSecondMoodColor(CellView view, int color) {
        view.setTriangleColor(color);
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
        float moodValue = 0;
        if (mFirstColor != 0) {
            moodValue = 1;
            if (mSecondColor != 0) {
                moodValue = 0.5f;
            }
        } else {
            moodValue = 0;
        }
        //TODO: add moodValue to DB to accurately calculate the real value of the first mood in a day. Necessary for graphs

        final Mood mood = new Mood(
                SpecialUtils.getCurrentYear(),
                mPosition % 13,
                mPosition,
                mFirstColor,
                mSecondColor);
                // moodValue);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mMoodId == DEFAULT_VALUE) {
                    // Insert new contact
                    mDb.moodsDao().insertMood(mood);
                } else {
                    // Update contact
                    mood.setId(mMoodId);
                    mDb.moodsDao().updateMood(mood);
                }

                finish();
            }
        });
    }
}
