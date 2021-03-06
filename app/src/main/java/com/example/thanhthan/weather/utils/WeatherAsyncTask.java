package com.example.thanhthan.weather.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.thanhthan.weather.model.OpenWeatherJSon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Thanh Than on 11/01/2017.
 */

public class WeatherAsyncTask extends AsyncTask<String, Void,String>{
    Activity activity;
    String q ;
    TypePrediction typePrediction;
    double latitude;
    double longitude;
    Bitmap myBitmap = null;
    public WeatherAsyncTask(String url){
        q = url;
    }
    @Override
    protected String doInBackground(String... url) {
        BufferedReader inputStream = null;
        Log.d("tag11",url[0]);
        URL jsonUrl = null;
        try {
            jsonUrl = new URL(url[0]);
            URLConnection dc = jsonUrl.openConnection();

            dc.setConnectTimeout(5000);
            dc.setReadTimeout(5000);

            inputStream = new BufferedReader(new InputStreamReader(
                    dc.getInputStream()));

            // read the JSON results into a string
            String jsonResult = inputStream.readLine();
            return jsonResult;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    /**
     * Constructor dùng để lấy thời tiết theo địa chỉ bất kỳ
     * @param activity
     * @param q
     */
}
