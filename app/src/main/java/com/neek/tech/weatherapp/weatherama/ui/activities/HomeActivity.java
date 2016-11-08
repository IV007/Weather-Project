package com.neek.tech.weatherapp.weatherama.ui.activities;

import android.os.Bundle;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseActivity;
import com.neek.tech.weatherapp.weatherama.controllers.WeatherController;
import com.neek.tech.weatherapp.weatherama.model.weather.CurrentWeather;
import com.neek.tech.weatherapp.weatherama.utilities.Constants;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.NetworkUtils;

public class HomeActivity extends BaseActivity implements
        WeatherController.HomeActivityView {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

        setContentView(R.layout.activity_home);

    }

    @Override
    protected void onResume() {
        super.onResume();

        fetchWeatherData();
    }

    private void fetchWeatherData() {
        if (NetworkUtils.isNetworkAvailable(this)) {
//            String url = Constants.FORECAST_URL;
            showProgressDialog();
            WeatherController.setHomeActivityView(this);
            WeatherController.getInstance().fetchWeatherData(Constants.TEMP_FORECAST_URL);
        } else {
            showErrorDialog(getString(R.string.network_unavailable_title), getString(R.string.network_unavailable_message));
        }
    }

    @Override
    public void onCurrentWeatherRetrieved(final CurrentWeather currentWeather) {
        Logger.d(TAG, "CurrentWeather " + currentWeather.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
                displayCurrentConditions(currentWeather);

            }
        });

    }

    private void displayCurrentConditions(final CurrentWeather currentWeather){

        if (currentWeather != null){
            //TODO - update UI here.


        } else {

            showErrorDialog(getString(R.string.okhttp_error_title), getString(R.string.okhttp_error_message));
        }
    }

}
