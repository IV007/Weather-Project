package com.neek.tech.weatherapp.weatherama.utilities;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class for location related work.
 */
public class LocationUtils {

    public static final long UPDATE_TIME = 1000 * 60 * 60; // 1 hour


    public static boolean isGPSEnabled(Context context) {
        boolean ret = false;
        boolean gpsEnabled;

        if (context != null) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (lm != null) {
                gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                ret = gpsEnabled;
            }
        }

        return ret;
    }

    public static boolean isGPSAndNetworkLocationEnabled(Context context) {
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

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
