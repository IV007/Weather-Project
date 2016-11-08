package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.os.Bundle;

import com.neek.tech.weatherapp.weatherama.base.BaseFragment;

/**
 * Created by ivanutsalo on 11/7/16.
 */
public class OneWeekForecastFragment extends BaseFragment {

    private static final String TAG = OneWeekForecastFragment.class.getSimpleName();

    public static OneWeekForecastFragment newInstance(){
        Bundle b = new Bundle();
        OneWeekForecastFragment frag = new OneWeekForecastFragment();
        frag.setArguments(b);
        return frag;
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
