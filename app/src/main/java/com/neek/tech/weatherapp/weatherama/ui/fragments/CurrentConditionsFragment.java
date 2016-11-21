package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.CurrentWeather;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.activities.HomeActivity;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatherUtils;

import butterknife.BindView;

/**
 * Created by ivanutsalo on 11/7/16.
 */
public class CurrentConditionsFragment extends BaseFragment implements HomeActivityListener {

    private static final String TAG = CurrentConditionsFragment.class.getSimpleName();

    @Nullable
    @BindView(R.id.timeLabel)
    TextView mTimeLabel;

    @Nullable
    @BindView(R.id.locationLabel)
    TextView mLocationiLabel;

    @Nullable
    @BindView(R.id.temperatureLabel)
    TextView mTemperatureLabel;

    @Nullable
    @BindView(R.id.humidityValue)
    TextView mHumidityValue;

    @Nullable
    @BindView(R.id.precipValue)
    TextView mPrecipValue;

    @Nullable
    @BindView(R.id.summaryLabel)
    TextView mSummaryLabel;

    @Nullable
    @BindView(R.id.iconImageView)
    ImageView mIconImageView;

    @Nullable
    @BindView(R.id.currentWearherRootLayout)
    RelativeLayout mCurrentWearherRootLayout;


    public static CurrentConditionsFragment newInstance(){
        Bundle b = new Bundle();
        CurrentConditionsFragment frag = new CurrentConditionsFragment();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).addListener(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

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
        return R.layout.fragment_conditions_current;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onWeatherRetrieved(Weather weather) {
        if (weather != null && weather.getCurrentWeather() != null){
            displayCurrentWeather(weather.getCurrentWeather(), weather.getTimezone());
        }
    }

    private void displayCurrentWeather(CurrentWeather currentWeather, String timeZone){
        //TODO - Show current weather.
        Log.i(TAG, "Current weather " + currentWeather.toString());




        if (currentWeather != null) {
            if (mTemperatureLabel != null) {
                mTemperatureLabel.setText(WeatherUtils.getTemperature(currentWeather.getTemperature()));
            }
            if (mLocationiLabel != null) {
                mLocationiLabel.setText(timeZone);
            }

            if (mTimeLabel != null) {
                mTimeLabel.setText("At " + WeatherUtils.getFormattedTime(timeZone, currentWeather.getTime()) + " it will be");
            }

            if (mHumidityValue != null) {
                mHumidityValue.setText(String.format("%s", currentWeather.getHumidity()));
            }

            if (mPrecipValue != null) {
                mPrecipValue.setText(String.format("%s %s", WeatherUtils.getPrecipProbability(currentWeather.getPrecipProbability()), "%"));
            }

            if (mSummaryLabel != null) {
                mSummaryLabel.setText(currentWeather.getSummary());
            }


            if (mIconImageView != null) {
                mIconImageView.setImageDrawable(WeatherUtils.getIconId(getActivity(), currentWeather.getIcon()));
            }

            if (mCurrentWearherRootLayout != null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mCurrentWearherRootLayout.setBackground(WeatherUtils.setLayoutBackground(getActivity(), currentWeather.getIcon()));
                } else {
                    mCurrentWearherRootLayout.setBackgroundResource(WeatherUtils.setLayoutBackgroundResource(currentWeather.getIcon()));
                }
            }
        }
    }


}
