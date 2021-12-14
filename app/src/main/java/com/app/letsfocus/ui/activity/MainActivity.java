package com.app.letsfocus.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.app.letsfocus.R;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.core.RemiderBroadcast;
import com.app.letsfocus.databinding.ActivityMainBinding;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.fragment.home.HomeFragment;
import com.app.letsfocus.ui.fragment.report1.Report1Fragment;
import com.app.letsfocus.ui.fragment.setting.SettingFragment;
import com.app.letsfocus.ui.fragment.timetable.TimeTableListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    List<Model> listTime = new ArrayList<>();
    PendingIntent pendingIntent;
    Intent intent;
    AlarmManager alarmManager;
    SharedPreferences sharedPreferences;
    Boolean b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Helper.loadToolbar(this, R.id.my_toolbar);


        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.navigation_home) {
                loadFragment(new HomeFragment());
            } else if(id == R.id.navigation_timetable) {
                loadFragment(new TimeTableListFragment());
            } else if(id == R.id.navigation_report) {
                loadFragment(new Report1Fragment());
            } else if(id==R.id.navigation_setting) {
                loadFragment(new SettingFragment());
            }
            return true;
        });

//        sharedPreferences = getSharedPreferences("switch_state",MODE_PRIVATE);
//        b = sharedPreferences.getBoolean("state",true);
//        if(b){
//
//        }
        configAlert();
    }

    private void configAlert(){
        listTime = new ToDo(this).all();
        String todoTime = "";
        for(int i= 0; i<listTime.size(); i++){
            int hour, minute;
            todoTime = String.valueOf(listTime.get(i).get("time"));
            Log.e("check", todoTime);
            String[] str = todoTime.split(":");
            hour = Integer.parseInt(str[0]);
            minute = Integer.parseInt(str[1]);
            startAlert(hour,minute);
            todoTime = "";
        }
    }
    private void startAlert(int h , int m) {
        intent = new Intent(MainActivity.this, RemiderBroadcast.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND,0);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if(alarmManager!= null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void loadFragment(Fragment fragment) {
        Helper.loadFragment(R.id.nav_host_fragment_activity_main, fragment, getSupportFragmentManager());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
    }
}