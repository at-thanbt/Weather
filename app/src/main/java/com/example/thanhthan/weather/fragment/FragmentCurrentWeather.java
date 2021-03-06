package com.example.thanhthan.weather.fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanhthan.weather.R;
import com.example.thanhthan.weather.model.OpenWeatherJSon;
import com.example.thanhthan.weather.utils.DataFromService;
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
 * Created by HCD-Fresher041 on 1/12/2017.
 */

public class FragmentCurrentWeather extends Fragment {
    Bitmap myBitmap=null;
    NumberFormat format = new DecimalFormat("#0.0");
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
    private OpenWeatherJSon results;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_weather_current_location, container, false);

        txtCurrentAddressName = (TextView) view.findViewById(R.id.txtCurrentAddressName);
        txtTemp = (TextView) view.findViewById(R.id.txtTemp);
        imgSky = (ImageView) view.findViewById(R.id.imgSky);
        txtMaxTemp = (TextView) view.findViewById(R.id.txtMaxTemp);
        txtMinTemp = (TextView) view.findViewById(R.id.txtMinTemp);
        txtWind = (TextView) view.findViewById(R.id.txtWind);
        txtCloudiness = (TextView) view.findViewById(R.id.txtCloudiness);
        txtPressure = (TextView) view.findViewById(R.id.txtPressure);
        txtHumidity = (TextView) view.findViewById(R.id.txtHumidity);
        txtSunrise = (TextView) view.findViewById(R.id.txtSunrise);
        txtSunset = (TextView) view.findViewById(R.id.txtSunset);

                new DataFromService().execute("http://api.openweathermap.org/data/2.5/weather?q=hồ%20chí%20minh&appid=483cd66e9b77c29fc09f9903508f51b3");

        if(results!=null){
            Log.d("tag",results.toString()+" 11");
        }
        else{
            Log.d("tag","11");
        }
        return view;
    }

    class DataFromService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            BufferedReader inputStream = null;
            Log.d("tag11", url[0]);
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
            results = new Gson().fromJson(s,OpenWeatherJSon.class);
            Log.d("tag",results.toString()+" 11");
            txtCurrentAddressName.setText(results.getName());
            txtTemp.setText((format.format(results.getMain().getTemp()-273.15))+"");
            txtMaxTemp.setText((format.format(results.getMain().getTemp_max()-273.15))+" °C");
            txtMinTemp.setText((format.format(results.getMain().getTemp_min()-273.15))+" °C");
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
            Picasso.with(getContext()).load(urlImage+results.getWeather().get(0).getIcon()+".png").into(imgSky);
            imgSky.setImageBitmap(myBitmap);

            super.onPostExecute(s);
        }
    }



}
