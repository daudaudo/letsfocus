package com.app.letsfocus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.app.letsfocus.databinding.ActivityMainBinding;
import com.app.letsfocus.ui.home.HomeFragment;
import com.app.letsfocus.ui.profile.ProfileFragment;
import com.app.letsfocus.ui.report1.Report1Fragment;
import com.app.letsfocus.ui.timetable.TimeTableListFragment;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.app.letsfocus.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.navigation_home) {
                    loadFragment(new HomeFragment());
                } else if(id == R.id.navigation_timetable) {
                    loadFragment(new TimeTableListFragment());
                } else if(id == R.id.navigation_report) {
                    loadFragment(new Report1Fragment());
                } else if(id==R.id.navigation_profile) {
                    loadFragment(new ProfileFragment());
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.detach(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main)));
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        transaction.commitNow();
    }
}