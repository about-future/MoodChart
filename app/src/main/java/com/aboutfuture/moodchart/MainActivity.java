package com.aboutfuture.moodchart;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aboutfuture.moodchart.data.AppDatabase;
import com.aboutfuture.moodchart.data.DailyMood;
import com.aboutfuture.moodchart.data.DailyMoodAdapter;
import com.aboutfuture.moodchart.utils.Preferences;
import com.aboutfuture.moodchart.viewmodel.YearViewModel;
import com.aboutfuture.moodchart.viewmodel.YearViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DailyMoodAdapter.ListItemClickListener {

    public static final String DAILY_MOOD_ID_KEY = "mood_id_key";
    public static final String SELECTED_DAY_POSITION_KEY = "day_position_key";
    public static final String SELECTED_YEAR_KEY = "year_key";

    @BindView(R.id.moods_list)
    RecyclerView mMoodsRecyclerView;
    private DailyMoodAdapter mAdapter;
    private AppDatabase mDb;
    private List<DailyMood> mMoodsList;
    private ArrayList<DailyMood> mYearList;
    private int mYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO: Create a + - button to change the value of a shared preference for the selected year
        // First the year is the current year and the app will load data from this year.

        mDb = AppDatabase.getInstance(getApplicationContext());
        mYear = Preferences.getSelectedYear(this);
        setupViewModel();

        mYearList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            mYearList.add(null);
        }

        mYearList.add(new DailyMood(0xFFFF0000));
        mYearList.add(new DailyMood(0xFFFFC0CB));
        mYearList.add(new DailyMood(0xFF00C0CB));
        mYearList.add(new DailyMood(0xFFFF00CB));
        mYearList.add(new DailyMood(0xFFFFC000));
        mYearList.add(new DailyMood(0xFF115588));

        int currentSize = mYearList.size();

        for (int i = 0; i < 416 - currentSize; i++) {
            if (i == 344) {
                mYearList.add(new DailyMood(0xFF11C0CB));
            } else {
                mYearList.add(null);
            }
        }

        mMoodsRecyclerView.setLayoutManager(new GridLayoutManager(this, 13));
        mMoodsRecyclerView.setHasFixedSize(true);
        mAdapter = new DailyMoodAdapter(this, this);
        mMoodsRecyclerView.setAdapter(mAdapter);
        mAdapter.setDailyMoods(mYearList);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClickListener(int dailyMoodId, int position) {
        Intent moodDetailsIntent = new Intent(MainActivity.this, TodayActivity.class);
        if (dailyMoodId != -1) {
            moodDetailsIntent.putExtra(DAILY_MOOD_ID_KEY, dailyMoodId);
        }
        moodDetailsIntent.putExtra(SELECTED_DAY_POSITION_KEY, position);
        moodDetailsIntent.putExtra(SELECTED_YEAR_KEY, mYear);
        startActivity(moodDetailsIntent);
    }

    private void setupViewModel() {
        YearViewModelFactory factory = new YearViewModelFactory(mDb, mYear);
        final YearViewModel viewModel = ViewModelProviders.of(this, factory).get(YearViewModel.class);
        viewModel.getYearMoods().observe(this, new Observer<List<DailyMood>>() {
            @Override
            public void onChanged(@Nullable List<DailyMood> moodsList) {
                mMoodsList = moodsList;
                mAdapter.setDailyMoods(moodsList);
            }
        });
    }

    private void initYear() {
        for (int i = 0; i < mMoodsList.size(); i++) {
            mYearList.set(mMoodsList.get(i).getPosition(), mMoodsList.get(i).getFirstColor());
        }
    }
}
