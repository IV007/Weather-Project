package com.neek.tech.weatherapp.weatherama.utilities;

/**
 * Class for App specific constants
 */
public class Constants {

    private static final String FORECAST_API_KEY = "32b4de8c5048f5a73410930128227279";

    private static final String TEMP_LAT = "33.653443,";
    private static final String TEMP_LONG = "-84.449372";
    public static String FORECAST_URL = "https://api.darksky.net/forecast/" + FORECAST_API_KEY + "/[latitude],[longitude]";
    public static String TEMP_FORECAST_URL = "https://api.darksky.net/forecast/" + FORECAST_API_KEY + "/" + TEMP_LAT + TEMP_LONG + "";

    /**
     * Geocoder constants
     */
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME = "com.neek.tech.weatherapp.weatherama.utilities";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    public static final String REVERSE_GEOCODED_LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".REVERSE_GEOCODED_LOCATION_DATA_EXTRA";


    //TODO - Handle exceptions from OkHttp- UnknownHostException, NetworkTimeoutException
}
