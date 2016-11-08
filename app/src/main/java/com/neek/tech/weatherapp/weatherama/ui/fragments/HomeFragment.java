package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.controllers.WeatherController;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

/**
 * Created by ivanutsalo on 11/7/16.
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

    }

    @Override
    protected int onCreateViewId() {
        return 0;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }
}
