package com.neek.tech.weatherapp.weatherama.utilities;

import android.util.Log;

/**
 * Utility class for logs
 */
public class Logger {

    public static void create(final String tag){
        Log.d(tag, "Created");
    }

    public static void d(final String tag, final String message){
        Log.d(tag, message);
    }

    public static void e(final String tag, final String message){
        Log.e(tag, message);
    }

    public static void logException(final String tag, final Throwable e){
        Log.e(tag, e.toString());
    }

    public static void i(final String tag, final String message){
        Log.i(tag, message);
    }


}
