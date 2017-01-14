package com.example.thanhthan.weather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thanhthan.weather.R;

/**
 * Created by HCD-Fresher041 on 1/12/2017.
 */

public class FragmentCurrentWeather extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_weather_current_location,container,false);

        return view;
    }
}
