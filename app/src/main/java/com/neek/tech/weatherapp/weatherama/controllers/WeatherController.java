package com.neek.tech.weatherapp.weatherama.controllers;

import com.neek.tech.weatherapp.weatherama.base.BaseView;
import com.neek.tech.weatherapp.weatherama.model.weather.CurrentWeather;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.service.WeatherService;
import com.neek.tech.weatherapp.weatherama.service.WeatherUpdater;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

/**
 * Controller for Weather classes, responsible
 * for updating views with model objects.
 */
public class WeatherController implements WeatherUpdater {

    private static final String TAG = "WeatherController";

    private static WeatherController singleton;

    private static HomeActivityView mHomeActivityView;


    public static void initialize(){
        singleton = new WeatherController();
        WeatherService.initialize();
        Logger.i(TAG, "Initialized");
    }

    public static WeatherController getInstance(){
        if (singleton == null){
            initialize();
        }
        return singleton;
    }

    public void fetchWeatherData(final String url){
        final WeatherService service = WeatherService.getInstance();
        service.setWeatherUpdater(this);
        service.getWeatherData(url);

    }

    @Override
    public void onWeatherDataRetrieved(Weather weather) {
        if (mHomeActivityView != null && weather != null){
            mHomeActivityView.onCurrentWeatherRetrieved(weather.getCurrentWeather());
        }
    }

    @Override
    public void onError(String errorMessage) {
        if (mHomeActivityView != null) {
            mHomeActivityView.onError("What went wrong", errorMessage);
        }
    }

    public static void setHomeActivityView(HomeActivityView view){
        mHomeActivityView = view;
    }

    public interface HomeActivityView extends BaseView{

        void onCurrentWeatherRetrieved(final CurrentWeather currentWeather);
    }
}
