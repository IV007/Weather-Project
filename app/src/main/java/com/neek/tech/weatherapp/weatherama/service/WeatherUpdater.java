package com.neek.tech.weatherapp.weatherama.service;

import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.base.BaseUpdater;

/**
 * Created by ivanutsalo on 11/7/16.
 */

public interface WeatherUpdater extends BaseUpdater {


    void onWeatherDataRetrieved(Weather weather);

}
