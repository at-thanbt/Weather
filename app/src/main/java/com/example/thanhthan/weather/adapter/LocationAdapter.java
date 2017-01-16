package com.example.thanhthan.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thanhthan.weather.R;

import java.util.List;

/**
 * Created by Thanh Than on 14/01/2017.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyVietHolder> {
    private List<String> mLoactions;
    private Context mContext;
    private onItemClickListener mListener;

    public LocationAdapter(Context mContext, List<String> mLoactions,onItemClickListener listener) {
        this.mContext = mContext;
        this.mLoactions = mLoactions;
        mListener = listener;
    }

    @Override
    public MyVietHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new MyVietHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVietHolder holder, final int position) {
        String location = mLoactions.get(position);
        holder.mTvLocation.setText(location);
        holder.mLinearLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mLoactions == null) ? 0 : mLoactions.size();
    }

    class MyVietHolder extends RecyclerView.ViewHolder {

        private TextView mTvLocation;
        private LinearLayout mLinearLayoutItem;
        public MyVietHolder(View itemView) {
            super(itemView);
            mTvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            mLinearLayoutItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    public interface onItemClickListener{
        void itemClick(int position);
    }
}
