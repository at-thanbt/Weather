package com.example.thanhthan.weather.utils;

import com.example.thanhthan.weather.model.OpenWeatherJSon;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Thanh Than on 11/01/2017.
 */

public class OpenWeatherMapAPI {
    public static OpenWeatherJSon prediction(String q){
        try {
            String location = URLEncoder.encode(q,"UTF-8");
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+location+"&appid=483cd66e9b77c29fc09f9903508f51b3");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader,OpenWeatherJSon.class);

            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);
            return results;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * http://api.openweathermap.org/data/2.5/weather?lat=10.778182&amp;lon=106.665504
     * @param lat
     * @param lon
     * @return
     */
    public  static OpenWeatherJSon prediction(double lat, double lon){
        URL url = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&amp;lon="+lon+"&appid=483cd66e9b77c29fc09f9903508f51b3");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);
            return results;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * Sửa lại WeatherJSON vì chưa phù hợp trong trường hợp Daily
     * http://api.openweathermap.org/data/2.5/forecast/daily?lat=10.778182&amp;lon=106.66550&amp;cnt=10
     * @param lat
     * @param lon
     * @param cnt
     * @return
     */
    public  static OpenWeatherJSon predictionDaily(double lat, double lon, int cnt){
        try {

            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&amp;lon="+lon+"&amp;cnt="+cnt+"&appid=be8d3e323de722ff78208a7dbb2dcd6f");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);

            return results;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Sửa lại WeatherJSON vì chưa phù hợp trong trường hợp Daily
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=Đà lạt&amp;cnt=10
     * @param q
     * @param cnt
     * @return
     */
    public static OpenWeatherJSon predictionDaily(String q,int cnt)
    {
        try {
            String location= URLEncoder.encode(q, "UTF-8");
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q="+location+"&amp;cnt="+cnt+"&appid=be8d3e323de722ff78208a7dbb2dcd6f");
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);

            return results;

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return null;
    }
}
