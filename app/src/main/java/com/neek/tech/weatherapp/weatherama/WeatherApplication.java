package com.neek.tech.weatherapp.weatherama;

import android.app.Application;

import com.neek.tech.weatherapp.weatherama.controllers.WeatherController;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

/**
 * Application class for Weather App.
 */
public class WeatherApplication  extends Application {

    private static final String TAG = "WeatherApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.create(TAG);

        WeatherController.initialize();
        WeatherLocationManager.initialize(this);
    }

}
