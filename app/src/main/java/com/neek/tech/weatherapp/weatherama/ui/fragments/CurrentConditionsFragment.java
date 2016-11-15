package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.CurrentWeather;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.activities.HomeActivity;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ivanutsalo on 11/7/16.
 */
public class CurrentConditionsFragment extends BaseFragment implements HomeActivityListener {

    private static final String TAG = CurrentConditionsFragment.class.getSimpleName();

    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    public String getIcon() {
        return mIcon;
    }

    public int getIconId() {

        int iconId = R.drawable.clear_day;

        if(mIcon != null) {
            switch (mIcon) {
                case "clear-day":
                    iconId = R.drawable.clear_day;
                    break;
                case "clear-night":
                    iconId = R.drawable.clear_night;
                    break;
                case "rain":
                    iconId = R.drawable.rain;
                    break;
                case "snow":
                    iconId = R.drawable.snow;
                    break;
                case "sleet":
                    iconId = R.drawable.sleet;
                    break;
                case "wind":
                    iconId = R.drawable.wind;
                    break;
                case "fog":
                    iconId = R.drawable.fog;
                    break;
                case "cloudy":
                    iconId = R.drawable.cloudy;
                    break;
                case "partly-cloudy-day":
                    iconId = R.drawable.partly_cloudy;
                    break;
                case "partly-cloudy-night":
                    iconId = R.drawable.cloudy_night;
                    break;
            }
        }
        return iconId;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime(){
        String timeString = "";
        if (mTimeZone != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
            Date dateTime = new Date(getTime() * 1000);
            timeString = formatter.format(dateTime);
        }
        return timeString;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int)Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return (int)Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "mIcon='" + mIcon + '\'' + "\n"+
                ", mTime=" + mTime + "\n"+
                ", mTemperature=" + mTemperature + "\n"+
                ", mHumidity=" + mHumidity + "\n"+
                ", mPrecipChance=" + mPrecipChance + "\n"+
                ", mSummary='" + mSummary + '\'' + "\n"+
                ", mTimeZone='" + mTimeZone + '\'' + "\n"+
                '}';
    }

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
    public void onDetach() {
        super.onDetach();

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
            displayCurrentWeather(weather.getCurrentWeather());
        }
    }

    private void displayCurrentWeather(CurrentWeather currentWeather){
        //TODO - Show current weather.
        Log.i(TAG, "Current weather " + currentWeather.toString());

    }
}
