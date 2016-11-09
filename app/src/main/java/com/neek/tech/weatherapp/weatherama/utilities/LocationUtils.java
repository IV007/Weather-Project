package com.neek.tech.weatherapp.weatherama.utilities;

import android.content.Context;
import android.location.LocationManager;

/**
 * Utility class for location related work.
 */
public class LocationUtils {

    public static final long UPDATE_TIME = 1000 * 60 * 60; // 1 hour


    public static boolean isLocationServicesEnabled(Context context) {
        boolean ret = false;
        boolean gpsEnabled, networkEnabled;

        if (context != null) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (lm != null) {
                gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                ret = gpsEnabled && networkEnabled;
            }
        }

        return ret;
    }
}
