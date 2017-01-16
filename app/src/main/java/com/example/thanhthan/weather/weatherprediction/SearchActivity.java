package com.example.thanhthan.weather.weatherprediction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanhthan.weather.R;
import com.example.thanhthan.weather.model.OpenWeatherJSon;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Created by Thanh Than on 15/01/2017.
 */

public class SearchActivity extends AppCompatActivity {
    Bitmap myBitmap=null;
    NumberFormat format = new DecimalFormat("#0.0");
    private OpenWeatherJSon results;
    TextView txtCurrentAddressName;
    TextView txtTemp;
    ImageView imgSky;
    TextView txtMaxTemp;
    TextView txtMinTemp;
    TextView txtWind;
    TextView txtCloudiness;
    TextView txtPressure;
    TextView txtHumidity;
    TextView txtSunrise;
    TextView txtSunset;
    private String url ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        txtCurrentAddressName = (TextView)findViewById(R.id.txtCurrentAddressName);
        txtTemp = (TextView)findViewById(R.id.txtTemp);
        imgSky = (ImageView)findViewById(R.id.imgSky);
        txtMaxTemp = (TextView)findViewById(R.id.txtMaxTemp);
        txtMinTemp = (TextView)findViewById(R.id.txtMinTemp);
        txtWind = (TextView)findViewById(R.id.txtWind);
        txtCloudiness = (TextView)findViewById(R.id.txtCloudiness);
        txtPressure = (TextView)findViewById(R.id.txtPressure);
        txtHumidity = (TextView)findViewById(R.id.txtHumidity);
        txtSunrise = (TextView)findViewById(R.id.txtSunrise);
        txtSunset = (TextView)findViewById(R.id.txtSunset);
        Intent callerIntent = getIntent();
        Bundle bundle =  callerIntent.getBundleExtra("data");
        String location = bundle.getString("location");
        String[] nameLocation = location.split("\\s+");
        url = "http://api.openweathermap.org/data/2.5/weather?q=";
        for(int i=0;i<nameLocation.length;i++){
            url+=nameLocation[i]+"%20";
        }
        url += "&appid=483cd66e9b77c29fc09f9903508f51b3";
        new DataFromService().execute(url);


    }
    class DataFromService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            BufferedReader inputStream = null;
            URL jsonUrl = null;
            try {
                jsonUrl = new URL(url[0]);
                URLConnection dc = jsonUrl.openConnection();

                dc.setConnectTimeout(10000);
                dc.setReadTimeout(10000);

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
            results = new Gson().fromJson(s,OpenWeatherJSon.class);
            txtCurrentAddressName.setText(results.getName());
            txtTemp.setText((float)(results.getMain().getTemp()-273.15)+"");
            txtMaxTemp.setText((float)(results.getMain().getTemp_max()-273.15)+" °C");
            txtMinTemp.setText((float)(results.getMain().getTemp_min()-273.15)+" °C");
            txtCloudiness .setText(results.getClouds().getAll()+"");
            txtPressure.setText(results.getMain().getPressure()+" hpa");
            txtHumidity.setText(results.getMain().getHumidity()+" %");
            Date timeSunrise = new Date(results.getSys().getSunrise()*1000);
            String Sunrise= timeSunrise.getHours()+":"+timeSunrise.getMinutes()+" AM";
            Date timeSunSet = new Date(results.getSys().getSunset()*1000);
            String sunset= timeSunSet.getHours()+":"+timeSunSet.getMinutes();

            txtSunrise.setText(Sunrise);
            txtSunset.setText(sunset);
            txtWind.setText(results.getWind().getSpeed()+" m/s");
            String urlImage = "http://openweathermap.org/img/w/";
            Picasso.with(SearchActivity.this).load(urlImage+results.getWeather().get(0).getIcon()+".png").into(imgSky);
            imgSky.setImageBitmap(myBitmap);
        }
    }


}
