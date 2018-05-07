package com.neek.tech.weatherapp.weatherama.controllers;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.neek.tech.weatherapp.weatherama.base.BaseView;
import com.neek.tech.weatherapp.weatherama.model.user.ReverseGeocodeAddress;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.service.WeatherService;
import com.neek.tech.weatherapp.weatherama.service.WeatherUpdater;
import com.neek.tech.weatherapp.weatherama.utilities.Constants;
import com.neek.tech.weatherapp.weatherama.utilities.Json;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatheramaPreferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for Weather classes, responsible
 * for updating views with model objects.
 */
public class WeatherController implements WeatherUpdater {

    private static final String TAG = "WeatherController";

    private static WeatherController singleton;

    private static HomeActivityView mHomeActivityView;

    /**
     * Current selected Address by user
     */
    private String mSelectedAddress;


    public static void initialize() {
        singleton = new WeatherController();
        WeatherService.initialize();
        Logger.i(TAG, "Initialized");
    }

    public static WeatherController getInstance() {
        if (singleton == null) {
            initialize();
        }
        return singleton;
    }

    public void fetchWeatherData(final Location location) {
        String url = Constants.FORECAST_URL
                .replace("[latitude]", Double.toString(location.getLatitude()))
                .replace("[longitude]", Double.toString(location.getLongitude()));

        Log.e(TAG, "url - " + url);
        WeatherService.getInstance();
        WeatherService.setWeatherUpdater(this);
        WeatherService.getWeatherData(url);

    }

    public void fetchWeatherData(final ReverseGeocodeAddress address) {
        if (address != null) {
            String url = Constants.FORECAST_URL
                    .replace("[latitude]", Double.toString(address.getLatitude()))
                    .replace("[longitude]", Double.toString(address.getLongitude()));

            Logger.i(TAG, "ReverseGeocodeAddress - " + address.getKey());
            Log.e(TAG, "url - " + url);
            WeatherService.getInstance();
            WeatherService.setWeatherUpdater(this);
            WeatherService.getWeatherData(url);
        }

    }

    @Override
    public void onWeatherDataRetrieved(Weather weather) {
        if (mHomeActivityView != null && weather != null) {
            mHomeActivityView.onWeatherRetrieved(weather);
        }
    }

    @Override
    public void onError(String errorMessage) {
        if (mHomeActivityView != null) {
            mHomeActivityView.onError("What went wrong", errorMessage);
        }
    }

    /**
     * Retrieves user location from SharedPrefs.
     * If address is selected from settings,
     * compare with that address otherwise get the most recent address.
     */
    public static ReverseGeocodeAddress getUserLocationFromPrefs(Context context) {
        ReverseGeocodeAddress result = null;

        singleton.mSelectedAddress = WeatheramaPreferences.getSelectedAddress(context);
        final String prevAddresses = WeatheramaPreferences.getGeocodedAddressList(context);
        List<ReverseGeocodeAddress> mappedAddress;

        if (!TextUtils.isEmpty(prevAddresses)) {

            Type type = new TypeToken<List<ReverseGeocodeAddress>>() {
            }.getType();

            mappedAddress = Json.fromJson(prevAddresses, type);

            if (mappedAddress != null && mappedAddress.size() > 0) {

                for (int i = 0; i < mappedAddress.size(); i++) {

                    ReverseGeocodeAddress address = mappedAddress.get(i);


                    if (address.getKey().equalsIgnoreCase(singleton.mSelectedAddress)) {
                        Logger.i(TAG, "Address set - " + singleton.mSelectedAddress);
                        result = address;
                        break;
                    }
                }
            }
        }

        return result;

    }

    /**
     * Method to return a list of previously saved user locations
     *
     * @return List of addresses reordered with most recent first.
     */
    public static List<String> getSavedUserLocations(Context context) {
        List<String> result = null;
        final String prevAddresses = WeatheramaPreferences.getGeocodedAddressList(context);
        List<ReverseGeocodeAddress> mappedAddress;

        if (!TextUtils.isEmpty(prevAddresses)) {

            Type type = new TypeToken<List<ReverseGeocodeAddress>>() {
            }.getType();

            mappedAddress = Json.fromJson(prevAddresses, type);

            if (mappedAddress != null && mappedAddress.size() > 0) {

                //If user has selected an address, add it as first item in list to be displayed
                if (!TextUtils.isEmpty(singleton.mSelectedAddress)) {

                    for (ReverseGeocodeAddress address : mappedAddress) {
                        if (address.getKey().equalsIgnoreCase(singleton.mSelectedAddress)) {
                            result = new ArrayList<>();
                            result.add(address.getKey());
                            break;
                        }
                    }

                }

                //Add other items to list
                for (int i = mappedAddress.size() - 1; i >= 0; i--) {
                    ReverseGeocodeAddress address = mappedAddress.get(i);
                    if (result == null) {
                        result = new ArrayList<>();
                    }

                    if (!result.contains(address.getKey())) {
                        result.add(address.getKey());
                    }
                }
            }
        }

        return result;
    }

    /**
     * Method to check if address was already saved,
     * helps prevent unnecessary reverse geocoding.
     * //TODO - Find a better way to compare Location object.
     */
    public static boolean addressExistsAlready(Context context, Location location) {
        boolean result = false;
        String prevAddresses = WeatheramaPreferences.getGeocodedAddressList(context);

        List<ReverseGeocodeAddress> mappedAddress;

        if (!TextUtils.isEmpty(prevAddresses)) {

            Type type = new TypeToken<List<ReverseGeocodeAddress>>() {
            }.getType();
            mappedAddress = Json.fromJson(prevAddresses, type);

            if (mappedAddress != null && location != null) {

                for (ReverseGeocodeAddress add : mappedAddress) {
                    if (add.getLatitude() == location.getLatitude() &&
                            add.getLongitude() == location.getLongitude()) {
                        result = true;
                        break;
                    }

                }
            }
        }

        return result;
    }

    /**
     * Method to Save address to SharedPrefs after reverse geocoding is complete.
     *
     * @param context     context required by shared prefs
     * @param address     String address after reverse geocode
     * @param location    Location object for retrieving LatLng
     * @param userAddress ReverseGeocodeAddress
     */
    public static boolean saveAddressToPrefs(Context context, String address, Location location, ReverseGeocodeAddress userAddress) {
        boolean result;
        Map<String, ReverseGeocodeAddress> locationMap = new LinkedHashMap<>();
        String prevAddresses = WeatheramaPreferences.getGeocodedAddressList(context);
        final ReverseGeocodeAddress geocodeAddress;
        List<ReverseGeocodeAddress> mappedAddress;

        if (!TextUtils.isEmpty(prevAddresses)) {

            Type type = new TypeToken<List<ReverseGeocodeAddress>>() {
            }.getType();
            mappedAddress = Json.fromJson(prevAddresses, type);
            if (mappedAddress != null) {
                for (ReverseGeocodeAddress add : mappedAddress) {
                    locationMap.put(add.getKey(), add);
                }
            }

            if (!TextUtils.isEmpty(address) && location != null) {
                geocodeAddress = new ReverseGeocodeAddress();
                geocodeAddress.setKey(address);
                geocodeAddress.setLatitude(location.getLatitude());
                geocodeAddress.setLongitude(location.getLongitude());
                locationMap.put(address, geocodeAddress);
            }

            if (userAddress != null) {
                locationMap.put(userAddress.getKey(), userAddress);
            }

            mappedAddress = new ArrayList<>(locationMap.values());
            String json = Json.toJson(mappedAddress);
            result = WeatheramaPreferences.saveGeocodedAddressToPrefs(context, json);
            Logger.i(TAG, "Geocode mSelectedAddress saved to shared prefs = " + result);


        } else {

            if (!TextUtils.isEmpty(address) && location != null) {

                geocodeAddress = new ReverseGeocodeAddress();
                geocodeAddress.setKey(address);
                geocodeAddress.setLatitude(location.getLatitude());
                geocodeAddress.setLongitude(location.getLongitude());
                locationMap.put(address, geocodeAddress);

            }

            if (userAddress != null) {
                locationMap.put(userAddress.getKey(), userAddress);
            }

            mappedAddress = new ArrayList<>(locationMap.values());
            String json = Json.toJson(mappedAddress);
            result = WeatheramaPreferences.saveGeocodedAddressToPrefs(context, json);
            Logger.i(TAG, "Geocode mSelectedAddress saved to shared prefs = " + result);
        }

        return result;

    }

    public static void setSelectedAddress(Context context, final String address) {
        singleton.mSelectedAddress = address;
        WeatheramaPreferences.saveSelectedAddress(context, address);
        Logger.i(TAG, "Address set - " + address);
    }

    public static String getSelectedAddress(Context context) {
        return (!TextUtils.isEmpty(singleton.mSelectedAddress) ? singleton.mSelectedAddress : WeatheramaPreferences.getSelectedAddress(context));
    }

    public static void setHomeActivityView(HomeActivityView view) {
        mHomeActivityView = view;
    }

    public interface HomeActivityView extends BaseView {

        void onWeatherRetrieved(final Weather weather);
    }
}
