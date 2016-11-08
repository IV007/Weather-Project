package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.ui.SlidingTabLayout;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.activities.HomeActivity;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * HomeFragment acting as landing screen for HomeActivity
 */
public class HomeFragment extends BaseFragment implements HomeActivityListener {

    private static final String TAG = "HomeFragment";


    @BindView(R.id.viewPager)
    private ViewPager mViewPager;

    @BindView(R.id.pagerHeader)
    private SlidingTabLayout mSlidingTabLayout;

    private List<BaseFragment> mFragments;

    public static HomeFragment newInstance(Weather weather){
        Bundle b = new Bundle();
        b.putSerializable(TAG, weather);
        HomeFragment frag = new HomeFragment();
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
    public void onDetach() {
        super.onDetach();

        if (getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).removeListener(this);
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
    protected int onCreateViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onWeatherRetrieved(Weather weather) {

    }
}
