package com.neek.tech.weatherapp.weatherama.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neek.tech.weatherapp.weatherama.WeatherApplication;

import javax.annotation.CheckForNull;

import butterknife.ButterKnife;

/**
 * Created by ivanutsalo on 11/7/16.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    private final static int NO_ID = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int layoutId = onCreateViewId();
        if (layoutId != NO_ID) {

            View fragmentView = inflater.inflate(layoutId, container, false);
            ButterKnife.bind(this, fragmentView);

            return fragmentView;

        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @CheckForNull
    protected BaseActivity getBaseActivity() {

        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }

        return getWeatherApplication().getCurrentActivity();

    }


    private WeatherApplication getWeatherApplication(){
        return (WeatherApplication) getActivity().getApplication();
    }
    /**
     * Invoked before onViewCreated
     */
    protected abstract int onCreateViewId();

    /**
     * Returns a Tag to identify the fragment
     */
    public abstract String getFragmentTag();

    @Override
    public void onError(String title, String errorMessage) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }
}
