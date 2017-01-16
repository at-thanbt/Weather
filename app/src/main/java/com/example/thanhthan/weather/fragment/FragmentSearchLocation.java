package com.example.thanhthan.weather.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thanhthan.weather.R;
import com.example.thanhthan.weather.adapter.LocationAdapter;
import com.example.thanhthan.weather.weatherprediction.SearchActivity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by Thanh Than on 14/01/2017.
 */

public class FragmentSearchLocation extends Fragment implements LocationAdapter.onItemClickListener {
    TextView txtAddressCheck;
    Button btnSearch;
    private RecyclerView mRecyclerViewLocation;
    private List<String> mLocations;
    private LocationAdapter mAdapter;
    private EditText mEdtSearch;

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choose_address, container, false);
        mRecyclerViewLocation = (RecyclerView) view.findViewById(R.id.rcvCity);
        mEdtSearch = (EditText) view.findViewById(R.id.txtAddressCheck);
        initData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewLocation.setLayoutManager(layoutManager);
        mAdapter = new LocationAdapter(getActivity(), mLocations, this);
        mRecyclerViewLocation.setAdapter(mAdapter);
        addTextListener();
        return view;
    }

    public void initData() {
        mLocations = new ArrayList<>();
        mLocations.add("An Giang");
        mLocations.add("Bà Rịa-Vũng Tàu");
        mLocations.add("Quảng Nam");
        mLocations.add("Bạc Liêu");
        mLocations.add("Bắc Ninh");
        mLocations.add("Bến Tre");
        mLocations.add("Bình Định");
        mLocations.add("Bình Dương");
        mLocations.add("Hà Nội");
        mLocations.add("Đà Nẵng");
        mLocations.add("Huế");
        mLocations.add("Hồ Chí Minh");
        mLocations.add("Hải Phòng");
        mLocations.add("Quảng Bình");
        mLocations.add("Quảng Trị");
        mLocations.add("Hải Phòng");
        mLocations.add("Thanh Hóa");
        mLocations.add("Gia Lai");
        mLocations.add("Quảng Bình");
        mLocations.add("Thanh Hóa");
        mLocations.add("Vĩnh Long");
        mLocations.add("Đồng Nai");

    }

    @Override
    public void itemClick(int position) {
        //chen fragment
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Bundle bundle = new Bundle();
        String location = mLocations.get(position);
        bundle.putString("location", location);
        intent.putExtra("data", bundle);
        startActivity(intent);

    }

    public void addTextListener() {

        mEdtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                query = removeAccent(query.toString());

                final List<String> filteredList = new ArrayList<>();

                for (int i = 0; i < mLocations.size(); i++) {

                    final String text = mLocations.get(i).toLowerCase();
                    if (removeAccent(text).contains(query)) {
                        filteredList.add(mLocations.get(i));
                    }
                }

                mRecyclerViewLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new LocationAdapter(getActivity(), filteredList, (new LocationAdapter.onItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        //kiem tra du lieu o day
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        Bundle bundle = new Bundle();
                        String location = filteredList.get(position);
                        bundle.putString("location", location);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
                    }
                }));
                mRecyclerViewLocation.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }
}
