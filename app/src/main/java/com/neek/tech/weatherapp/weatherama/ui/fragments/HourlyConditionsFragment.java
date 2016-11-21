package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.HourlyWeather;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.activities.HomeActivity;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatherUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ivanutsalo on 11/7/16.
 */

public class HourlyConditionsFragment extends BaseFragment implements HomeActivityListener {

    private static final String TAG = HourlyConditionsFragment.class.getSimpleName();

    @Nullable
    @BindView(R.id.listView)
    protected ListView mListView;

    private HourlyAdapter mHourlyAdapter;


    public static HourlyConditionsFragment newInstance() {
        Bundle b = new Bundle();
        HourlyConditionsFragment frag = new HourlyConditionsFragment();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).addListener(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).removeListener(this);
        }
    }

    @Override
    protected int onCreateViewId() {
        return R.layout.fragment_conditions_hourly;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onWeatherRetrieved(Weather weather) {
        if (weather != null && weather.getHourlyWeather() != null) {
            displayHourlyWeather(weather.getHourlyWeather());
        }
    }

    private void displayHourlyWeather(final HourlyWeather hourlyWeather) {
        Log.i(TAG, "Hourly weather " + hourlyWeather.toString());

        if (mHourlyAdapter == null) {
            mHourlyAdapter = new HourlyAdapter(hourlyWeather.getData(), getActivity());
        } else {
            if (hourlyWeather.getData() != null && hourlyWeather.getData().size() > 0)
                mHourlyAdapter.setNewHourlyData(hourlyWeather.getData());
        }

        if (mListView != null) {
            mListView.setAdapter(mHourlyAdapter);
            if (!TextUtils.isEmpty(hourlyWeather.getIcon())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    final Drawable drawable = WeatherUtils.setLayoutBackground(getActivity(), hourlyWeather.getIcon());
                    if (drawable != null) {
                        mListView.setBackground(drawable);
                    }
                } else {
                    final int drawableId = WeatherUtils.setLayoutBackgroundResource(hourlyWeather.getIcon());
                    if (drawableId != 0) {
                        mListView.setBackgroundResource(drawableId);
                    }
                }
            }
        }
    }

    public class HourlyAdapter extends BaseAdapter {

        private ArrayList<HourlyWeather.HourlyData> mHourlyDatas;
        private Context mContext;

        public HourlyAdapter(ArrayList<HourlyWeather.HourlyData> hourlyDatas, Context context) {
            mHourlyDatas = hourlyDatas;
            mContext = context;
        }

        public void setNewHourlyData(ArrayList<HourlyWeather.HourlyData> newHourlyData){
            if (newHourlyData != this.mHourlyDatas){
                this.mHourlyDatas = newHourlyData;
            }
        }

        @Override
        public int getCount() {
            return (mHourlyDatas != null) ? mHourlyDatas.size() : 0;
        }


        @Override
        public Object getItem(int position) {
            if (mHourlyDatas != null) {
                return mHourlyDatas.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ItemViewHolder viewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_weather, parent, false);
                viewHolder = new ItemViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder) convertView.getTag();
            }

            if (mHourlyDatas != null) {
                HourlyWeather.HourlyData hourlyData = mHourlyDatas.get(position);
                if (hourlyData != null) {

                    if (!TextUtils.isEmpty(hourlyData.getIcon()) && viewHolder.iconImageView != null) {
                        viewHolder.iconImageView.setImageDrawable(WeatherUtils.getIconId(mContext, hourlyData.getIcon()));
                    }
                    if (!TextUtils.isEmpty(hourlyData.getSummary()) && viewHolder.summaryLabel != null) {
                        viewHolder.summaryLabel.setText(hourlyData.getSummary());
                    }
                    if (hourlyData.getTemperature() != null && viewHolder.tempLabel != null) {
                        viewHolder.tempLabel.setText(WeatherUtils.getTemperature(hourlyData.getTemperature()));
                    }


                }
            }
            return convertView;
        }


        class ItemViewHolder {
            @Nullable
            @BindView(R.id.iconImageView)
            ImageView iconImageView;

            @Nullable
            @BindView(R.id.tempLabel)
            TextView tempLabel;

            @Nullable
            @BindView(R.id.summaryLabel)
            TextView summaryLabel;

            public ItemViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
