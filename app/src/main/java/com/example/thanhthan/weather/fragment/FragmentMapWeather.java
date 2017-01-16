package com.example.thanhthan.weather.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanhthan.weather.R;
import com.example.thanhthan.weather.model.OpenWeatherJSon;
import com.example.thanhthan.weather.utils.TrackGPS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thanh Than on 14/01/2017.
 */

public class FragmentMapWeather extends Fragment implements OnMapReadyCallback {
    final int maxResult = 5;
    double longitude;
    double latitude;
    String addressList[] = new String[maxResult];
    String temp;
    private OpenWeatherJSon results;
    private TrackGPS gps;
    private GoogleMap map;
    private Marker marker;
    private String url;
    private ArrayAdapter<String> adapter;
    private String mProvince;
    private LatLng mLatLng;
    NumberFormat format = new DecimalFormat("#0.0");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_weather_map, container, false);
        SupportMapFragment mSupportMapFragment = SupportMapFragment.newInstance(null);
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.map1, mSupportMapFragment).commit();
        mSupportMapFragment.getMapAsync(this);
        gps = new TrackGPS(getActivity());
        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();
            Log.d("tag11", longitude + " 11  " + latitude);
        } else {
            gps.showSettingsAlert();
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.animateCamera(CameraUpdateFactory.zoomIn());
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else {
            drawDirection();
        }
    }

    public void drawDirection() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else {
            if (isNetWork()) {
                mLatLng = new LatLng(latitude, longitude);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 13));
                if (marker != null) {
                    marker.remove();
                }
                getAddress();
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.message_turn_on_network), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public boolean isNetWork() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void getAddress() {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                String strReturnedAddress = "Address";
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress += returnedAddress.getAddressLine(i);
                    strReturnedAddress += ";";
                }
                String[] nameLocation = strReturnedAddress.split("[;]");
                mProvince = nameLocation[nameLocation.length - 1];
                String[] nameLocation1 = mProvince.split("\\s+");
                url = "http://api.openweathermap.org/data/2.5/weather?q=";
                for (int i = 0; i < nameLocation1.length; i++) {
                    url += nameLocation1[i] + "%20";
                }
                url += "&appid=483cd66e9b77c29fc09f9903508f51b3";
                Log.d("tag1134", url);
                new DataFromService().execute(url);

            } else {
                Log.d("tag", "no address");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("tag", "not find address");
        }

    }

    private Bitmap getMarkerBitmapFromView(String temperature) {
        View customMarkerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        TextView tvTemperature = (TextView) customMarkerView.findViewById(R.id.tvTemperature);
        tvTemperature.setText(temperature);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
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
            Log.d("temp ", " 11");
            super.onPostExecute(s);
            results = new Gson().fromJson(s, OpenWeatherJSon.class);

            temp = format.format(results.getMain().getTemp()-273.15) + "°C";
            CameraPosition position = new CameraPosition.Builder()
                    .target(mLatLng).zoom(15).tilt(30).build();
            marker = map
                    .addMarker(new MarkerOptions()
                            .position(mLatLng)
                            .title(getResources().getString(R.string.message_you_are_here))
                            .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(temp))));
            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position));
        }
    }
}
