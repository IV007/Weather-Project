package com.neek.tech.weatherapp.weatherama.ui;

import com.neek.tech.weatherapp.weatherama.model.weather.Weather;

/**
 * Event callback listener for HomeActivity
 */
public interface HomeActivityListener {

    void onWeatherRetrieved(final Weather weather);

}
