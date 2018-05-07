package com.neek.tech.weatherapp.weatherama.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Shared prefs for app.
 */
public class WeatheramaPreferences {

    private static final String LOCATION_PERMISSION_PERMANENTLY_DENIED = "LocationPermissionDenied";
    private static final String LOCATION_PERMISSION_ACCEPTED = "LocationPermissionAccepted";
    private static final String SHOULD_SHOW_LOCATION_PERMISSION_RATIONALE = "LocationPermissionDenied";
    private static final String SAVE_USER_LOCATIONS_DEFAULT = "SaveUserLocations";
    private static final String SAVE_USER_LOCATIONS = "SaveUserLocations";
    private static final String GEOCODE_ADDRESSES = "GEOCODE_ADDRESSES";
    private static final String SELECTED_ADDRESS = "SELECTED_ADDRESS";
    private static final String USER_ENABLED_SELECTED_ADDRESS = "USER_ENABLED_SELECTED_ADDRESS";
    private static final String SELECTED_TEMPERATURE_SCALE = "SELECTED_TEMPERATURE_SCALE";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor editSharedPreferences(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static boolean setUserLocationRationaleShown(Context context) {
        return editSharedPreferences(context).putBoolean(SHOULD_SHOW_LOCATION_PERMISSION_RATIONALE, true).commit();
    }

    public static boolean isUserLocationRationaleShown(Context context) {
        return getSharedPreferences(context).getBoolean(SHOULD_SHOW_LOCATION_PERMISSION_RATIONALE, false);
    }

    public static boolean setUserDeniedLocationRationale(Context context) {
        return editSharedPreferences(context).putBoolean(LOCATION_PERMISSION_PERMANENTLY_DENIED, true).commit();
    }

    public static boolean isUserLocationRationaleDenied(Context context) {
        return getSharedPreferences(context).getBoolean(LOCATION_PERMISSION_PERMANENTLY_DENIED, false);
    }

    public static boolean setUserAcceptedLocationRationale(Context context) {
        return editSharedPreferences(context).putBoolean(LOCATION_PERMISSION_ACCEPTED, true).commit();
    }

    public static boolean isUserLocationRationaleAccepted(Context context) {
        return getSharedPreferences(context).getBoolean(LOCATION_PERMISSION_ACCEPTED, false);
    }

     public static boolean setSaveUserLocationDefault(Context context) {
        return editSharedPreferences(context).putBoolean(SAVE_USER_LOCATIONS_DEFAULT, true).commit();
    }

    public static boolean isSaveUserLocationEnabledByDefault(Context context) {
        return getSharedPreferences(context).getBoolean(SAVE_USER_LOCATIONS_DEFAULT, false);
    }

    public static boolean setSaveUserLocation(Context context){
        return editSharedPreferences(context).putBoolean(SAVE_USER_LOCATIONS, true).commit();
    }

    public static boolean isSaveUserLocationEnabled(Context context){
        return getSharedPreferences(context).getBoolean(SAVE_USER_LOCATIONS, false);
    }

    public static boolean saveGeocodedAddressToPrefs(Context context, String addressList){
        return editSharedPreferences(context).putString(GEOCODE_ADDRESSES, addressList).commit();
    }

    public static String getGeocodedAddressList(Context context){
        return getSharedPreferences(context).getString(GEOCODE_ADDRESSES, null);
    }
    public static boolean saveSelectedAddress(Context context, String address){
        if (!TextUtils.isEmpty(address)){
            editSharedPreferences(context).putBoolean(USER_ENABLED_SELECTED_ADDRESS, true).commit();
        } else {
            editSharedPreferences(context).putBoolean(USER_ENABLED_SELECTED_ADDRESS, false).commit();
        }

        return editSharedPreferences(context).putString(SELECTED_ADDRESS, address).commit();
    }

    public static String getSelectedAddress(Context context){
        return getSharedPreferences(context).getString(SELECTED_ADDRESS, null);
    }

    public static boolean didUserEnableSelectedAddress(Context context){
        return getSharedPreferences(context).getBoolean(USER_ENABLED_SELECTED_ADDRESS, false);
    }

    public static boolean setCurrentTemperatureScale(Context context, int scale){
        return editSharedPreferences(context).putInt(SELECTED_TEMPERATURE_SCALE, scale).commit();
    }

    public static int getCurrentTemperatureScale(Context context){
        return getSharedPreferences(context).getInt(SELECTED_TEMPERATURE_SCALE, 0);
    }
}
