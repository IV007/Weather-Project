package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.MinuteWeather;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.activities.HomeActivity;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

/**
 * Created by ivanutsalo on 11/7/16.
 */
public class MinutelyConditionsFragment extends BaseFragment implements HomeActivityListener {

    private static final String TAG = MinutelyConditionsFragment.class.getSimpleName();

    public static MinutelyConditionsFragment newInstance(MinuteWeather weather) {
        Bundle b = new Bundle();
        b.putSerializable(TAG, weather);
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

        if (getArguments() != null && getArguments().containsKey(TAG)) {
            displayMinutelyWeather((MinuteWeather) getArguments().getSerializable(TAG));
        }

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
    }
}
