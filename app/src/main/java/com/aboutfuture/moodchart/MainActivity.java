package com.aboutfuture.moodchart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import com.aboutfuture.moodchart.data.DailyMood;
import com.aboutfuture.moodchart.data.DailyMoodAdapter;
import com.aboutfuture.moodchart.data.NumbersAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DailyMoodAdapter.ListItemClickListener {

    public final String DAILY_MOOD_ID_KEY = "mood_id_key";
    public final String DAY_POSITION_KEY = "day_position_key";

//    @BindView(R.id.numbers_list)
//    RecyclerView mNumbersRecyclerView;
    @BindView(R.id.moods_list)
    RecyclerView mMoodsRecyclerView;

//    private NumbersAdapter mNumbersAdapter;
    private DailyMoodAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Date time = new Date(new Date().getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        Log.v("DATE", simpleDateFormat.format(time));
        SimpleDateFormat simpleYearFormat = new SimpleDateFormat("yyyy", Locale.US);
        int year = Integer.parseInt(simpleYearFormat.format(time));
        Log.v("YEAR", simpleYearFormat.format(time));
        //int year = Integer.getInteger(simpleYearFormat.format(time));
        SimpleDateFormat simpleMonthFormat = new SimpleDateFormat("M", Locale.US);
        Log.v("MONTH", simpleMonthFormat.format(time));

        int numberOfDays = 365;

        // Calculate if it's a leap year
        if (year % 4 == 0 && year % 100 != 0) {
            Log.v("THIS IS", "A LEAP YEAR");
            numberOfDays = 366;
        } else if(year % 100 == 0 && year % 400 == 0) {
            Log.v("THIS IS", "A LEAP YEAR");
            numberOfDays = 366;
        } else {
            Log.v("THIS IS", "NOT A LEAP YEAR");
        }

        ArrayList<DailyMood> moodsList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            moodsList.add(null);
        }

        //janList.add();

        moodsList.add(new DailyMood(0xFFFF0000));
        moodsList.add(new DailyMood(0xFFFFC0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF00C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFFFF00CB));//, null, null));
        moodsList.add(new DailyMood(0xFFFFC000));//, null, null));
        moodsList.add(new DailyMood(0xFFFAA0CB, 0xFF115588));//, null, null));
        moodsList.add(new DailyMood(0xFFFFC0CB, 0xFF4466AA));//, null, null));
        moodsList.add(new DailyMood(0xFF99C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFFCCC0CB, 0xFF5656BB, 0xFFBBEE79));//, null, null));
        moodsList.add(new DailyMood(0xFF22C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF11C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF00C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF55C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFFFF0000));
        moodsList.add(new DailyMood(0xFFFFC0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF00C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFFFF00CB));//, null, null));
        moodsList.add(new DailyMood(0xFFFFC000));//, null, null));
        moodsList.add(new DailyMood(0xFFFAA0CB, 0xFF115588));//, null, null));
        moodsList.add(new DailyMood(0xFFFFC0CB, 0xFF4466AA));//, null, null));
        moodsList.add(new DailyMood(0xFF99C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFFCCC0CB, 0xFF5656BB, 0xFFBBEE79));//, null, null));
        moodsList.add(new DailyMood(0xFF22C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF11C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF00C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF55C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFFFF0000));
        moodsList.add(new DailyMood(0xFFFFC0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF00C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFFFF00CB));//, null, null));
        moodsList.add(new DailyMood(0xFFFFC000));//, null, null));
        moodsList.add(new DailyMood(0xFFFAA0CB, 0xFF115588));//, null, null));
        moodsList.add(new DailyMood(0xFFFFC0CB, 0xFF4466AA));//, null, null));
        moodsList.add(new DailyMood(0xFF99C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFFCCC0CB, 0xFF5656BB, 0xFFBBEE79));//, null, null));
        moodsList.add(new DailyMood(0xFF22C0CB));//, null, null));
        moodsList.add(new DailyMood(0xFF11C0CB));//, null, null));

        int currentSize = moodsList.size();
        for (int i = 0; i < 416 - currentSize; i++) {
            moodsList.add(null);
        }



//        int[] numbers = new int[32];
//        for (int i = 0; i < 32; i++) {
//            numbers[i] = i;
//        }

//        ArrayList<DailyMood> janList = new ArrayList<>();
//        for (int i = 0; i < 32; i++) {
//            janList.add(new DailyMood(0xFFFFFFFF, 1, null));
//        }

//        ArrayList<DailyMood> febList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            febList.add(new DailyMood(2, null));
//        }
//
//        ArrayList<DailyMood> marList = new ArrayList<>();
//        for (int i = 0; i < 32; i++) {
//            marList.add(new DailyMood(3, null));
//        }
//
//        ArrayList<DailyMood> aprList = new ArrayList<>();
//        for (int i = 0; i < 31; i++) {
//            aprList.add(new DailyMood(4, null));
//        }
//
//        ArrayList<DailyMood> mayList = new ArrayList<>();
//        for (int i = 0; i < 32; i++) {
//            mayList.add(new DailyMood(5, null));
//        }
//
//        ArrayList<DailyMood> junList = new ArrayList<>();
//        for (int i = 0; i < 31; i++) {
//            junList.add(new DailyMood(6, null));
//        }
//
//        ArrayList<DailyMood> julList = new ArrayList<>();
//        for (int i = 0; i < 32; i++) {
//            julList.add(new DailyMood(7, null));
//        }
//
//        ArrayList<DailyMood> augList = new ArrayList<>();
//        for (int i = 0; i < 32; i++) {
//            augList.add(new DailyMood(8, null));
//        }
//
//        ArrayList<DailyMood> sepList = new ArrayList<>();
//        for (int i = 0; i < 31; i++) {
//            sepList.add(new DailyMood(9, null));
//        }
//
//        ArrayList<DailyMood> octList = new ArrayList<>();
//        for (int i = 0; i < 32; i++) {
//            octList.add(new DailyMood(10, null));
//        }
//
//        ArrayList<DailyMood> novList = new ArrayList<>();
//        for (int i = 0; i < 31; i++) {
//            novList.add(new DailyMood(11, null));
//        }
//
//        ArrayList<DailyMood> decList = new ArrayList<>();
//        for (int i = 0; i < 32; i++) {
//            decList.add(new DailyMood(12, null));
//        }
//
//
//        mNumbersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mNumbersRecyclerView.setNestedScrollingEnabled(false);
//        mNumbersRecyclerView.addItemDecoration(mDividerItemDecoration);
//        mNumbersRecyclerView.setHasFixedSize(false);
//        mNumbersAdapter = new NumbersAdapter(this);
//        mNumbersAdapter.setNumbers(numbers);
//        mNumbersRecyclerView.setAdapter(mNumbersAdapter);
//
        mMoodsRecyclerView.setLayoutManager(new GridLayoutManager(this, 13));
        mMoodsRecyclerView.setNestedScrollingEnabled(false);
        mMoodsRecyclerView.setHasFixedSize(false);
        mAdapter = new DailyMoodAdapter(this, this);
        mMoodsRecyclerView.setAdapter(mAdapter);
        mAdapter.setDailyMoods(moodsList);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    public boolean onNavigationItemSelected(MenuItem item) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClickListener(int dailyMoodId, int position) {
        if (dailyMoodId != -1) {
            Intent moodDetailsIntent = new Intent(MainActivity.this, TodayActivity.class);
            moodDetailsIntent.putExtra(DAILY_MOOD_ID_KEY, dailyMoodId);
            startActivity(moodDetailsIntent);
        } else {
            Intent moodDetailsIntent = new Intent(MainActivity.this, TodayActivity.class);
            moodDetailsIntent.putExtra(DAY_POSITION_KEY, position);
            startActivity(moodDetailsIntent);
        }
    }
}
