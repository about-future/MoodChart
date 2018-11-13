package com.aboutfuture.moodchart;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aboutfuture.moodchart.utils.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.settings_mood_1_label)
    TextView mMood1LabelTextView;
    @BindView(R.id.settings_mood_2_label)
    TextView mMood2LabelTextView;
    @BindView(R.id.settings_mood_3_label)
    TextView mMood3LabelTextView;
    @BindView(R.id.settings_mood_4_label)
    TextView mMood4LabelTextView;
    @BindView(R.id.settings_mood_5_label)
    TextView mMood5LabelTextView;
    @BindView(R.id.settings_mood_6_label)
    TextView mMood6LabelTextView;
    @BindView(R.id.settings_mood_7_label)
    TextView mMood7LabelTextView;
    @BindView(R.id.settings_mood_8_label)
    TextView mMood8LabelTextView;
    @BindView(R.id.settings_mood_9_label)
    TextView mMood9LabelTextView;
    @BindView(R.id.settings_mood_10_label)
    TextView mMood10LabelTextView;
    @BindView(R.id.settings_mood_11_label)
    TextView mMood11LabelTextView;
    @BindView(R.id.settings_mood_12_label)
    TextView mMood12LabelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        TextView mTitle = toolbar.findViewById(R.id.settings_toolbar_title);
        mTitle.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Norican-Regular.ttf"));
        setSupportActionBar(toolbar);
        setTitle("");
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

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
                findViewById(R.id.settings_mood_1),
                getString(R.string.pref_mood_1_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_1_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_1)),
                mMood1LabelTextView,
                getString(R.string.pref_mood_1_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_2),
                getString(R.string.pref_mood_2_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_2_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_2)),
                mMood2LabelTextView,
                getString(R.string.pref_mood_2_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_3),
                getString(R.string.pref_mood_3_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_3_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_3)),
                mMood3LabelTextView,
                getString(R.string.pref_mood_3_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_4),
                getString(R.string.pref_mood_4_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_4_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_4)),
                mMood4LabelTextView,
                getString(R.string.pref_mood_4_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_5),
                getString(R.string.pref_mood_5_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_5_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_5)),
                mMood5LabelTextView,
                getString(R.string.pref_mood_5_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_6),
                getString(R.string.pref_mood_6_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_6_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_6)),
                mMood6LabelTextView,
                getString(R.string.pref_mood_6_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_7),
                getString(R.string.pref_mood_7_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_7_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_7)),
                mMood7LabelTextView,
                getString(R.string.pref_mood_7_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_8),
                getString(R.string.pref_mood_8_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_8_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_8)),
                mMood8LabelTextView,
                getString(R.string.pref_mood_8_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_9),
                getString(R.string.pref_mood_9_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_9_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_9)),
                mMood9LabelTextView,
                getString(R.string.pref_mood_9_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_10),
                getString(R.string.pref_mood_10_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_10_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_10)),
                mMood10LabelTextView,
                getString(R.string.pref_mood_10_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_11),
                getString(R.string.pref_mood_11_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_11_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_11)),
                mMood11LabelTextView,
                getString(R.string.pref_mood_11_label_key));
        setMoodOption(
                findViewById(R.id.settings_mood_12),
                getString(R.string.pref_mood_12_color_key),
                Preferences.getMoodColor(
                        this,
                        getString(R.string.pref_mood_12_color_key),
                        ContextCompat.getColor(this, R.color.mood_color_12)),
                mMood12LabelTextView,
                getString(R.string.pref_mood_12_label_key));
    }

    private void setTypefaceFont(TextView view) {
        view.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Norican-Regular.ttf"));
    }

    private void setMoodOption(View colorView, String colorKey, int defaultColor, TextView labelView, String labelKey) {
//        int defaultColor = ;
        String label = Preferences.getMoodLabel(
                this,
                labelKey,
                getString(R.string.label_mood_1));

        setMoodColorListener(colorView, colorKey, defaultColor);
        setMoodColor(colorView, defaultColor);

        setMoodLabelListener(labelView, labelKey);
        setMoodLabel(labelView, label);
    }

    private void setMoodColorListener(View colorView, final String key, final int color) {
        colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker(view, key, color);
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

    private void setMoodLabelListener(final TextView textView, final String key) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateMoodNameDialog(textView, key);
            }
        });
    }

    // Show a dialog for a section insertion or section update.
    // If title is null, we insert a new section, otherwise we update a section title
    public void showUpdateMoodNameDialog(final TextView textView, final String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View changeMoodNameView = inflater.inflate(R.layout.dialog_change_mood_name, null);
        builder.setIcon(R.drawable.ic_edit);
        builder.setTitle(R.string.change_mood_name);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(changeMoodNameView)
                // Add action buttons
                .setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Find the EditText view so we can read data from it
                        EditText newTitleEditText = changeMoodNameView.findViewById(R.id.new_mood_name_edit_text);
                        // Store the data entered by the user in newTitle
                        String newMoodName = newTitleEditText.getText().toString().toLowerCase();

                        // If the new title is NOT null or an empty string
                        if (!TextUtils.isEmpty(newMoodName)) {
                            // If there is an old title passed to this method, update it with the
                            // new section title
                            Preferences.setMoodLabel(SettingsActivity.this, key, newMoodName);
                            textView.setText(newMoodName);
                        } else {
                            // Otherwise, we display a toast to let the user know about this error.
                            Toast.makeText(SettingsActivity.this,
                                    getString(R.string.invalid_name),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Cancel" button, so dismiss the deletion dialog.
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openColorPicker(final View view, final String key, int defaultColor) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                //
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Preferences.setMoodColor(SettingsActivity.this, key, color);
                setMoodColor(view, color);
            }
        });
        colorPicker.show();
    }
}
