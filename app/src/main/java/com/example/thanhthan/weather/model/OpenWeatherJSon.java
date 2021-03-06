package com.example.thanhthan.weather.model;

import java.util.List;

/**
 * Created by Thanh Than on 11/01/2017.
 */

public class OpenWeatherJSon {
    private Coord coord;
    private  Sys sys;
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private long id;
    private String name;
    private  int cod;
    private List<WeatherItem> weather;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "OpenWeatherJSon{" +
                "base='" + base + '\'' +
                ", coord=" + coord +
                ", sys=" + sys +
                ", main=" + main +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                ", weather=" + weather +
                '}';
    }
}
