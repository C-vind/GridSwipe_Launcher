package com.github.gridswipe_launcher;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // The ViewPager that will host the section contents.
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Create the adapter that will return a fragment for each sections of the app.
        PagerAdapter pagerAdapter = new PagerAdapter(getApplicationContext(), getSupportFragmentManager());

        // For each of the sections in the app, add a tab.
        for (int i = 1; i <= pagerAdapter.getCount(); i++)
            tabLayout.addTab(tabLayout.newTab());

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(pagerAdapter);

        // Set up the TabLayout with the ViewPager.
        tabLayout.setupWithViewPager(viewPager);
    }

    // Disable back button
    @Override
    public void onBackPressed(){

    }
}
