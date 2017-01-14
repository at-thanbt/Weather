package com.example.thanhthan.weather.weatherprediction;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.example.thanhthan.weather.R;
import com.example.thanhthan.weather.adapter.ViewPagerAdapter;
import com.example.thanhthan.weather.fragment.FragmentCurrentWeather;
import com.example.thanhthan.weather.fragment.FragmentSearchLocation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        fragments.add(new FragmentCurrentWeather());
        fragments.add(new FragmentSearchLocation());
        fragments.add(new FragmentCurrentWeather());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        tabLayout.setupWithViewPager(viewPager);


    }


}
