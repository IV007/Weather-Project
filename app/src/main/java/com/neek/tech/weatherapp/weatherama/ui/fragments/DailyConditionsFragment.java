package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.DailyWeather;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.activities.HomeActivity;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

/**
 * DailyConditions fragment for displaying weather conditions for
 * 1-week from current day.
 */
public class DailyConditionsFragment extends BaseFragment implements HomeActivityListener {

    private static final String TAG = DailyConditionsFragment.class.getSimpleName();

    public static DailyConditionsFragment newInstance(){
        Bundle b = new Bundle();
        DailyConditionsFragment frag = new DailyConditionsFragment();
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
        Log.e(TAG, "onDetach() called");
        if (getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).removeListener(this);
        }
    }

    @Override
    protected int onCreateViewId() {
        return R.layout.fragment_conditions_daily;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onWeatherRetrieved(Weather weather) {
        if (weather != null && weather.getDailyWeather() != null){
            displayDailyWeather(weather.getDailyWeather());
        }
    }

    private void displayDailyWeather(final DailyWeather dailyWeather){
        //TODO - show daily weather.
        Log.i(TAG, "DailyWeather " + dailyWeather.toString());
    }
}
