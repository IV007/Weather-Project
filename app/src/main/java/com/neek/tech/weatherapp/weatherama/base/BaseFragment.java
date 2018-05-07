package com.neek.tech.weatherapp.weatherama.base;

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
 * Base Implementation for fragments.
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
        return (BaseActivity) getActivity();
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
        if (getActivity() != null && getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).showErrorDialog(title, errorMessage);
        }
    }

    @Override
    public void showProgressDialog() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showProgressDialog();
        }

    }

    @Override
    public void hideProgressDialog() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showProgressDialog();
        }
    }

    public boolean onBackPressed() {
        return true;
    }
}
