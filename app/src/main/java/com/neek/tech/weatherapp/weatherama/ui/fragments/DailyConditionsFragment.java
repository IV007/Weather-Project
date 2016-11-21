package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neek.tech.suite.ui.animations.Rotate3dAnimation;
import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.ui.SimpleRecyclerViewDividerItem;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.model.weather.DailyWeather;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.activities.HomeActivity;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatherUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * DailyConditions fragment for displaying weather conditions for
 * 1-week from current day.
 */
public class DailyConditionsFragment extends BaseFragment implements HomeActivityListener {

    private static final String TAG = DailyConditionsFragment.class.getSimpleName();

    private static final int FRONT_FACE = 0;

    private static final int BACK_FACE = 1;

    @BindView(R.id.dailyRecyclerView)
    protected RecyclerView mDailyRecyclerView;

    @BindView(R.id.dailySummaryLabel)
    protected TextView mDailySummaryLabel;

    @BindView(R.id.dailyDetailsRootLayout)
    protected RelativeLayout mDailyDetailsRootLayout;

    @BindView(R.id.dailyDetailsSummaryLabel)
    protected TextView mDailyDetailsSummaryLabel;

    @BindView(R.id.hiTempLabel)
    protected TextView mHiTempLabel;

    @BindView(R.id.loTempLabel)
    protected TextView mLoTempLabel;

    @BindView(R.id.rainLabel)
    protected TextView mRainLabel;

    @BindView(R.id.windSpeedLabel)
    protected TextView mWindSpeedLabel;

    @BindView(R.id.dailyDetailsIcon)
    protected ImageView mDailyDetailsIcon;

    @BindView(R.id.backgroundImage)
    protected ImageView mBackgroundImage;

    @BindView(R.id.seeMoreButton)
    protected Button mSeeMoreButton;

    private SparseBooleanArray selectedItems;

    private DailyConditionsAdapter mDailyConditionsAdapter;

    /**
     * Position for current detail position.
     */
    private int mDetailsPosition = -1;

    /**
     * The boolean for image
     */
    private int currentFace;


    public static DailyConditionsFragment newInstance() {
        Bundle b = new Bundle();
        DailyConditionsFragment frag = new DailyConditionsFragment();
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


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() instanceof HomeActivity) {
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
        if (weather != null && weather.getDailyWeather() != null) {
            displayDailyWeather(weather.getDailyWeather());
        }
    }

    @OnClick(value = R.id.seeMoreButton)
    public void onSeeMoreButtonClicked(){
        startAnimation();
    }

    private void startAnimation(){

        if (mBackgroundImage != null) {

            // Find the center of the container
            final float centerX = mBackgroundImage.getWidth() / 2.0f;
            final float centerY = mBackgroundImage.getHeight() / 2.0f;
            final float depthZ = centerX;

            // Create a new 3D rotation with the supplied parameter
            // The animation listener is used to trigger the next animation
            final Rotate3dAnimation rotation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, true);
            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            rotation.setAnimationListener(new FirstHalfRotationCompleted());

            mBackgroundImage.startAnimation(rotation);

        }
    }

    /**
     * Setup a new 3D rotation on the container view.
     */
    private void startSecondHalfRotation() {

        if (mBackgroundImage != null) {

            final float centerX = mBackgroundImage.getWidth() / 2.0f;
            final float centerY = mBackgroundImage.getHeight() / 2.0f;
            final float depthZ = centerX;

            Rotate3dAnimation rotation;

            rotation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);

            if (currentFace == FRONT_FACE) {
                //Show front
            } else {
                //show back
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            rotation.setAnimationListener(new SecondHalfRotationCompleted());

            mBackgroundImage.startAnimation(rotation);

        }

    }

    /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class FirstHalfRotationCompleted implements Animation.AnimationListener {

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (mBackgroundImage != null) {
                mBackgroundImage.post(new Runnable() {
                    public void run() {
                        startSecondHalfRotation();
                    }
                });
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }

    }

    /**
     * This class listens for the end of the second half of the animation.
     * It then shows the WebView where is possible to do a zoom
     */
    private final class SecondHalfRotationCompleted implements Animation.AnimationListener {

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (currentFace == FRONT_FACE) {
                currentFace = BACK_FACE;
            } else {
                currentFace = FRONT_FACE;
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }

    }

    private void displayDailyWeather(final DailyWeather dailyWeather) {
        Log.i(TAG, "DailyWeather " + dailyWeather.toString());
        initializeRecyclerView(dailyWeather.getData());

        if (!TextUtils.isEmpty(dailyWeather.getSummary())) {
            mDailySummaryLabel.setText(dailyWeather.getSummary());
        }

        if (!TextUtils.isEmpty(dailyWeather.getIcon())) {
            Logger.i(TAG, "Icon " + dailyWeather.getIcon());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                final Drawable drawable = WeatherUtils.setLayoutBackground(getActivity(), dailyWeather.getIcon());
                if (drawable != null) {
                    mDailyRecyclerView.setBackground(drawable);
                }
            } else {
                final int drawableId = WeatherUtils.setLayoutBackgroundResource(dailyWeather.getIcon());
                if (drawableId != 0) {
                    mDailyRecyclerView.setBackgroundResource(drawableId);
                }
            }
        }

    }

    private void initializeRecyclerView(ArrayList<DailyWeather.DailyData> dailyDataArrayList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDailyRecyclerView.setHasFixedSize(false);
        mDailyRecyclerView.addItemDecoration(new SimpleRecyclerViewDividerItem(getActivity()));
        mDailyRecyclerView.setLayoutManager(layoutManager);
        if (mDailyConditionsAdapter == null) {
            mDailyConditionsAdapter = new DailyConditionsAdapter(getActivity(), dailyDataArrayList);
        } else {
            if (dailyDataArrayList != null && dailyDataArrayList.size() > 0)
                mDailyConditionsAdapter.setNewDailyDataList(dailyDataArrayList);
        }
        mDailyRecyclerView.setAdapter(mDailyConditionsAdapter);
    }

    private void itemClicked(int position) {
        if (mDailyConditionsAdapter != null && position != mDetailsPosition) {
            mDetailsPosition = position;
            DailyWeather.DailyData data = mDailyConditionsAdapter.getItem(position);
            Logger.i(TAG, "Data selected " + data.toString());
            setDailyDetailsView(data);
        }

    }

    private void setDailyDetailsView(DailyWeather.DailyData data) {
        if (data != null) {

            if (!TextUtils.isEmpty(data.getSummary()) && mDailyDetailsSummaryLabel != null) {
                mDailyDetailsSummaryLabel.setText(data.getSummary());
            }

            if (data.getTemperatureMax() != 0 && mHiTempLabel != null) {
                mHiTempLabel.setText(WeatherUtils.getTemperature(data.getTemperatureMax()));
            }

            if (mLoTempLabel != null) {
                mLoTempLabel.setText(WeatherUtils.getTemperature(data.getTemperatureMin()));
            }

            if (mWindSpeedLabel != null) {
                mWindSpeedLabel.setText(WeatherUtils.getTemperature(data.getWindSpeed()));
            }

            if (mRainLabel != null) {
                mRainLabel.setText(WeatherUtils.getPrecipProbability(data.getPrecipProbability()));
            }

            if(!TextUtils.isEmpty(data.getIcon()) && mDailyDetailsIcon != null){
                mDailyDetailsIcon.setImageDrawable(WeatherUtils.getIconId(getActivity(), data.getIcon()));
            }

            if (!TextUtils.isEmpty(data.getIcon()) && mDailyDetailsRootLayout != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mBackgroundImage.setBackground(WeatherUtils.setLayoutBackground(getActivity(), data.getIcon()));
                } else {
                    mBackgroundImage.setBackgroundResource(WeatherUtils.setLayoutBackgroundResource(data.getIcon()));

                }

                if (data.getIcon().equalsIgnoreCase("snow")){
                    mHiTempLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    mLoTempLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    mWindSpeedLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    mRainLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

                } else {
                    mHiTempLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    mLoTempLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    mWindSpeedLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    mRainLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

                }
                Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up_from_bottom_with_accelerate_decelerate);
                mDailyDetailsRootLayout.startAnimation(slideUp);
            }


        }
    }

    public class DailyConditionsAdapter extends RecyclerView.Adapter<DailyConditionsViewHolder> {

        private ArrayList<DailyWeather.DailyData> dailyDataList;
        private Context mContext;

        public DailyConditionsAdapter(Context context, ArrayList<DailyWeather.DailyData> dailyDataList) {
            this.dailyDataList = dailyDataList;
            this.mContext = context;
            if (dailyDataList != null && dailyDataList.size() > 0)
                selectedItems = new SparseBooleanArray(dailyDataList.size());
        }

        public void setNewDailyDataList(ArrayList<DailyWeather.DailyData> newDailyDataList) {
            if (newDailyDataList != this.dailyDataList) {
                this.dailyDataList = newDailyDataList;
            }
        }

        @Override
        public DailyConditionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather_list_item, parent, false);
            return new DailyConditionsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DailyConditionsViewHolder holder, int position) {
            if (dailyDataList != null && dailyDataList.size() > 0) {
                DailyWeather.DailyData dailyData = dailyDataList.get(position);

                if (dailyData != null) {

                    if (holder.mDailyItemLayout != null && !TextUtils.isEmpty(dailyData.getIcon())) {
                        final boolean isSelected = selectedItems.get(position);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            if (holder.mDailyItemRootLayout != null) {
                                holder.mDailyItemRootLayout.setBackground(isSelected
                                        ? ContextCompat.getDrawable(getActivity(), R.drawable.dialog_selected_button)
                                        : ContextCompat.getDrawable(getActivity(), R.drawable.dialog_unselected_button));
                            }

                        } else {

                            if (holder.mDailyItemRootLayout != null) {
                                if (isSelected) {
                                    holder.mDailyItemRootLayout.setBackgroundResource(R.drawable.dialog_selected_button);
                                } else {
                                    holder.mDailyItemRootLayout.setBackgroundResource(R.drawable.dialog_unselected_button);

                                }
                            }

                        }
                    }

                    if (holder.mIconImageView != null && !TextUtils.isEmpty(dailyData.getIcon())) {
                        holder.mIconImageView.setImageDrawable(WeatherUtils.getIconId(mContext, dailyData.getIcon()));
                    }

                    if (holder.mDayLabel != null && dailyData.getTime() != 0) {
                        holder.mDayLabel.setText(WeatherUtils.getDayFromDailyWeatherTime(dailyData.getTime()));
                    }


                    if (holder.mTempLabel != null && dailyData.getApparentTemperatureMax() != 0) {
                        holder.mTempLabel.setText(WeatherUtils.getTemperature(dailyData.getApparentTemperatureMax()));
                    }
                }
            }

        }

        @Override
        public int getItemCount() {
            return (dailyDataList != null) ? dailyDataList.size() : 0;
        }

        public DailyWeather.DailyData getItem(int position) {
            return dailyDataList.get(position);
        }
    }


    public class DailyConditionsViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        @Nullable
        @BindView(R.id.dayLabel)
        TextView mDayLabel;

        @Nullable
        @BindView(R.id.iconImageView)
        ImageView mIconImageView;

        @Nullable
        @BindView(R.id.temperatureLabel)
        TextView mTempLabel;

        @Nullable
        @BindView(R.id.dailyItemLayout)
        protected LinearLayout mDailyItemLayout;

        @Nullable
        @BindView(R.id.dailyItemRootLayout)
        protected LinearLayout mDailyItemRootLayout;


        DailyConditionsViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            final boolean checked = selectedItems.get(position);
            updateBooleanArray(position, !checked);

            if (checked) {
                if (mDailyItemRootLayout != null) {
                    mDailyItemRootLayout.setBackgroundResource(R.drawable.dialog_unselected_button);
                }
            } else {
                if (mDailyItemRootLayout != null) {
                    mDailyItemRootLayout.setBackgroundResource(R.drawable.dialog_selected_button);
                }
            }

            itemClicked(position);
            if (mDailyConditionsAdapter != null) {
                mDailyConditionsAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Method to map address checked state to list view position.
     */
    private void updateBooleanArray(int position, boolean enable) {
        if (selectedItems != null) {
            Logger.i(TAG, "Position - " + position + ", enable - " + enable);
            if (selectedItems.size() == 0) {
                //mCheckedItems is empty
                selectedItems.put(position, enable);
            } else if (selectedItems.size() > 0) {
                //mCheckedItems has items, loop through
                for (int i = 0; i < selectedItems.size(); i++) {
                    //reset all values currently in mCheckedItems
                    if (selectedItems.keyAt(i) != position) {
                        selectedItems.put(i, false);
                    }
                }
                //finally put the position in mCheckedItems and set true.
                selectedItems.put(position, enable);
            }
        }
    }
}
