package com.neek.tech.weatherapp.weatherama.ui.activities;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseActivity;
import com.neek.tech.weatherapp.weatherama.controllers.WeatherController;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.fragments.HomeFragment;
import com.neek.tech.weatherapp.weatherama.utilities.Constants;
import com.neek.tech.weatherapp.weatherama.utilities.LocationUtils;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.NetworkUtils;
import com.neek.tech.weatherapp.weatherama.WeatherLocationManager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements
        WeatherController.HomeActivityView, WeatherLocationManager.LocationUpdater {

    private static final String TAG = "HomeActivity";

    /**
     * Listener to notify fragments when new data is retrieved
     */
    private List<HomeActivityListener> mHomeActivityListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

        setContentView(R.layout.activity_home);

        if (mHomeActivityListener == null){
            mHomeActivityListener = new ArrayList<>();
        }
        navigateToFragment(HomeFragment.newInstance());

        WeatherLocationManager.getInstance(this);
        WeatherLocationManager.setLocationUpdater(this);

        if (LocationUtils.isLocationServicesEnabled(this) && WeatherLocationManager.isLocationServicesConnected()) {
            WeatherLocationManager.connect(this);
            WeatherLocationManager.getLastLocation(this);
        } else {
            showGpsDisabledDialog();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (LocationUtils.isLocationServicesEnabled(this) &&
                WeatherLocationManager.isLocationServicesConnected()) {
            WeatherLocationManager.updateLocation(this);
        } else {
            showGpsDisabledDialog();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        WeatherLocationManager.disconnect();
        WeatherLocationManager.removeUpdates();

    }

    private void fetchWeatherData(final Location location) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            if (location != null) {
                String url = Constants.FORECAST_URL
                        .replace("[latitude]", Double.toString(location.getLatitude()))
                        .replace("[longitude]", Double.toString(location.getLongitude()));
                showProgressDialog();
                WeatherController.setHomeActivityView(this);
                WeatherController.getInstance().fetchWeatherData(url);
            }
        } else {
            showErrorDialog(getString(R.string.network_unavailable_title), getString(R.string.network_unavailable_message));
        }
    }

    @Override
    public void onWeatherRetrieved(final Weather weather) {
        Logger.d(TAG, "CurrentWeather " + weather.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();

                if (weather != null) {

                    if (mHomeActivityListener != null) {
                        for (HomeActivityListener listener : mHomeActivityListener) {
                            listener.onWeatherRetrieved(weather);
                        }
                    }

                } else {

                    showErrorDialog(getString(R.string.okhttp_error_title), getString(R.string.okhttp_error_message));

                }

            }
        });

    }

    /**
     * Method to Register for data updates
     */
    public void addListener(HomeActivityListener listener){
        if (mHomeActivityListener != null && mHomeActivityListener.size() >= 0 &&
                !mHomeActivityListener.contains(listener)){
            mHomeActivityListener.add(listener);
            Log.e(TAG, "Listener added " + listener.toString());
        }
    }

    /**
     * Method to un-register for data updates
     */
    public void removeListener(HomeActivityListener listener){
        if (mHomeActivityListener != null && mHomeActivityListener.size() > 0 &&
                mHomeActivityListener.contains(listener)){
            mHomeActivityListener.remove(listener);
            Log.e(TAG, "Listener removed " + listener.toString());

        }
    }

    @Override
    protected int getIdRootFragmentContainer() {
        return R.id.container;
    }

    @Override
    public void onLocationRetrieved(final Location location) {
        fetchWeatherData(location);
    }
}
