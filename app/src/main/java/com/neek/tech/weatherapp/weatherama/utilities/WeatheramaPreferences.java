package com.neek.tech.weatherapp.weatherama.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ivanutsalo on 11/10/16.
 */

public class WeatheramaPreferences {


    public static final String LOCATION_PERMISSION_PERMANENTLY_DENIED = "LocationPermissionDenied";
    public static final String LOCATION_PERMISSION_ACCEPTED = "LocationPermissionAccepted";
    public static final String SHOULD_SHOW_LOCATION_PERMISSION_RATIONALE = "LocationPermissionDenied";

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences.Editor editSharedPreferences(Context context) {
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

}
