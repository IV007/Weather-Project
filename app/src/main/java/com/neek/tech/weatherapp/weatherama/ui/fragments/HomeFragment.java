package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.ui.SlidingTabLayout;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * HomeFragment acting as landing screen for HomeActivity
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.pagerHeader)
    SlidingTabLayout mSlidingTabLayout;


    private Weather mWeather;

    public static HomeFragment newInstance(Weather weather) {
        Bundle b = new Bundle();
        b.putSerializable(TAG, weather);
        HomeFragment frag = new HomeFragment();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

        if (getArguments() != null && getArguments().containsKey(TAG)) {
            mWeather = (Weather) getArguments().getSerializable(TAG);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewPager();

    }

    @Override
    protected int onCreateViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    private void initializeViewPager() {
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab_layout, R.id.customTabTextView);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getActivity(), R.color.colorAccent);
            }
        });

        ArrayList<BaseFragment> mFragments = new ArrayList<>();
        mFragments.add(CurrentConditionsFragment.newInstance(mWeather.getCurrentWeather()));
        mFragments.add(MinutelyConditionsFragment.newInstance(mWeather.getMinuteWeather()));
        mFragments.add(HourlyConditionsFragment.newInstance(mWeather.getHourlyWeather()));
        mFragments.add(DailyConditionsFragment.newInstance(mWeather.getDailyWeather()));

        HomeScreenPagerAdapter adapter = new HomeScreenPagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private class HomeScreenPagerAdapter extends FragmentStatePagerAdapter {

        private static final int COUNT = 4;
        private ArrayList<BaseFragment> fragmentList;

        public HomeScreenPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
            super(fm);
            this.fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.current_conditions);
                case 1:
                    return getString(R.string.minutely_conditions);
                case 2:
                    return getString(R.string.hourly_conditions);
                case 3:
                    return getString(R.string.daily_conditions);

            }
            return "";
        }
    }

}
