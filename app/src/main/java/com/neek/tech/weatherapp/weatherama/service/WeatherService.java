package com.neek.tech.weatherapp.weatherama.service;

import com.google.gson.JsonSyntaxException;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.utilities.Json;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Weather service for Calls
 */
public class WeatherService {

    /**
     * Singleton OkHttpClient object
     */
    private static OkHttpClient singleton;

    private static WeatherService mWeatherService;

    /**
     * Weather BaseUpdater interface object
     */
    private WeatherUpdater mWeatherUpdater;

    private WeatherService(){}

    public static void initialize(){
        if (singleton == null){
            singleton = new OkHttpClient();
        }
    }

    public static WeatherService getInstance(){
        if (mWeatherService == null){
            mWeatherService = new WeatherService();
        }

        return mWeatherService;
    }

    public void getWeatherData(String url){
        Request request = new Request.Builder().url(url).build();
        if (singleton != null) {
            singleton.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (mWeatherUpdater != null) {
                        mWeatherUpdater.onError(e.toString());
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        if (mWeatherUpdater != null) {
                            mWeatherUpdater.onWeatherDataRetrieved(Json.fromJson(response.body().charStream(), Weather.class));
                        }
                    } catch (JsonSyntaxException e) {
                        Logger.e("WeatherService", e);
                    }

                }
            });
        }
    }

    public void setWeatherUpdater(WeatherUpdater weatherUpdater){
        this.mWeatherUpdater = weatherUpdater;
    }
}
