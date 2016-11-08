package com.neek.tech.weatherapp.weatherama.utilities;

/**
 * Class for App specific constants
 */
public class Constants {

    private static final String API_KEY = "32b4de8c5048f5a73410930128227279";
    private static final String TEMP_LAT = "33.653443,";
    private static final String TEMP_LONG = "-84.449372";
    public static String FORECAST_URL = "https://api.darksky.net/forecast/" + API_KEY + "/[latitude],[longitude]";
    public static String TEMP_FORECAST_URL = "https://api.darksky.net/forecast/" + API_KEY + "/" + TEMP_LAT + TEMP_LONG + "";
}
