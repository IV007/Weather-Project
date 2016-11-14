package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.content.Context;
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
import android.widget.TextView;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.MinuteWeather;
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
public class MinutelyConditionsFragment extends BaseFragment implements HomeActivityListener {

    private static final String TAG = MinutelyConditionsFragment.class.getSimpleName();

    @Nullable
    @BindView(R.id.minuteListView)
    protected MinuteListView mMinuteListView;

    private MinutelyAdapter mMinutelyAdapter;

    public static MinutelyConditionsFragment newInstance() {
        Bundle b = new Bundle();
        MinutelyConditionsFragment frag = new MinutelyConditionsFragment();
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
    public void onDetach() {
        super.onDetach();

        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).removeListener(this);
        }
    }

    @Override
    protected int onCreateViewId() {
        return R.layout.fragment_conditions_minutely;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onWeatherRetrieved(Weather weather) {
        if (weather != null && weather.getMinuteWeather() != null) {
            displayMinutelyWeather(weather.getMinuteWeather());
        }
    }

    private void displayMinutelyWeather(MinuteWeather minuteWeather) {
        //TODO - Show minutely weather.
        Log.i(TAG, "Minute weather " + minuteWeather.toString());
        if (mMinutelyAdapter == null) {
            mMinutelyAdapter = new MinutelyAdapter(minuteWeather.getData(), getActivity());
        }

        if (mMinuteListView != null){
            mMinuteListView.setAdapter(mMinutelyAdapter);
            if (!TextUtils.isEmpty(minuteWeather.getIcon()))    {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mMinuteListView.setBackground(WeatherUtils.setLayoutBackground(getActivity(), minuteWeather.getIcon()));
                }   else {
                    mMinuteListView.setBackgroundResource(WeatherUtils.setLayoutBackground(minuteWeather.getIcon()));
                }
            }
        }
    }

    public class MinutelyAdapter extends BaseAdapter  {


        private ArrayList<MinutelyAdapter.MinuteData> mMinuteDatas;
        private Context mContext;

        public MinutelyAdapter(ArrayList<MinuteWeather> minuteDatas, Context context)   {
            minuteDatas = minuteDatas;
            mContext = context;
        }

        @Override
        public int getCount()   {
            if (mMinuteDatas != null)   {
                return mMinuteDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mMinuteDatas != null)   {
                return mMinuteDatas.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {return 0;}

        @Override
        public View getView(int position, View convertView, ViewGroup parent)   {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            ItemViewHolder viewHolder;

            if (convertView == null){
                convertView = inflater.inflate(R.layout.item_weather, parent, false);
                viewHolder = new ItemViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder) convertView.getTag();
            }

            if (mMinuteDatas != null) {
                MinuteWeather.MinuteData minuteData = mMinuteDatas.get(position);
                if (minuteData != null) {
                    if (!TextUtils.isEmpty(minuteData.getIcon()) && viewHolder.iconImageView != null) {
                        viewHolder.iconImageView.setImageDrawable(WeatherUtils.getIconId(mContext, minuteData.getIcon()));
                    }
                    if (!TextUtils.isEmpty(minuteData.getSummary()) && viewHolder.summaryLabel != null) {
                        viewHolder.summaryLabel.setText(minuteData.getSummary());
                    }
                    if (minuteData.getTemperature() != null && viewHolder.tempLabel != null) {
                        viewHolder.tempLabel.setText(WeatherUtils.getTemperature(minuteData.getTemperature()));
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
