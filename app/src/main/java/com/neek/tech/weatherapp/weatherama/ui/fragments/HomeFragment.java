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

    public static HomeFragment newInstance(/*Weather weather*/) {
        Bundle b = new Bundle();
//        b.putSerializable(TAG, weather);
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
                return ContextCompat.getColor(getActivity(), R.color.colorPrimaryLight1);
            }
        });

        ArrayList<BaseFragment> mFragments = new ArrayList<>(4);
        mFragments.add(CurrentConditionsFragment.newInstance());
//        mFragments.add(MinutelyConditionsFragment.newInstance());
        mFragments.add(HourlyConditionsFragment.newInstance());
        mFragments.add(DailyConditionsFragment.newInstance());

        HomeScreenPagerAdapter adapter = new HomeScreenPagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2); //TODO - if you add MinuteWeather to mFragments, then increment by 1.

        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private class HomeScreenPagerAdapter extends FragmentStatePagerAdapter {

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
            return (fragmentList != null) ? fragmentList.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (fragmentList.get(position) instanceof CurrentConditionsFragment) {

                return getString(R.string.current_conditions);

            } else if (fragmentList.get(position) instanceof MinutelyConditionsFragment) {

                return getString(R.string.minutely_conditions);

            } else if (fragmentList.get(position) instanceof HourlyConditionsFragment) {

                return getString(R.string.hourly_conditions);

            } else if (fragmentList.get(position) instanceof DailyConditionsFragment) {

                return getString(R.string.daily_conditions);

            }


            return "";
        }
    }

}
