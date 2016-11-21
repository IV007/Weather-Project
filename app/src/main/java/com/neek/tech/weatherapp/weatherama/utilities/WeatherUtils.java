package com.neek.tech.weatherapp.weatherama.utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.neek.tech.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility class for Weather Model class.
 * Use this class to make data readable by user.
 */
public class WeatherUtils {

    private static final String TAG = "WeatherUtils";

    /**
     * Static method to return int matching drawable id, based on @param supplied.
     */
    public static Drawable getIconId(Context context, final String mIcon) {

        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        int iconId = R.drawable.clear_day;

        switch (mIcon) {
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "cloudy":
                iconId = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
        }

        return ContextCompat.getDrawable(context, iconId);
    }


    /**
     * Format supplied time with timezone supplied using format "9:30 AM"
     */
    public static String getFormattedTime(final String timeZone, final long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date dateTime = new Date(time * 1000);

        return formatter.format(dateTime);
    }


    /**
     * Static method that returns drawable used to update background of view.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable setLayoutBackground(Context context, final String icon) {

        Log.e(TAG, "iconName: " + icon);
        Drawable gradientDrawable = null;
        if (!TextUtils.isEmpty(icon)) {
            switch (icon) {
                case "clear-day":
                    gradientDrawable = ContextCompat.getDrawable(context, R.drawable.bg_gradient_clear_day);
                    break;

                case "clear-night":
                    gradientDrawable = ContextCompat.getDrawable(context, R.drawable.bg_gradient_clear_night);
                    break;

                case "partly-cloudy-night":
                    gradientDrawable = ContextCompat.getDrawable(context, R.drawable.bg_gradient_cloudy_night);
                    break;

                case "partly-cloudy-day":
                    gradientDrawable = ContextCompat.getDrawable(context, R.drawable.bg_gradient_partly_cloudy_day);
                    break;

                case "rain":
                    gradientDrawable = ContextCompat.getDrawable(context, R.drawable.bg_gradient_light_rain);
                    break;
                case "snow":
                    gradientDrawable = ContextCompat.getDrawable(context, R.drawable.ic_snow);
                    break;

            }

        }

        return gradientDrawable;
    }

    public static int setLayoutBackgroundResource(final String icon) {

        Log.e(TAG, "iconName: " + icon);
        int gradientDrawable = 0;
        if (!TextUtils.isEmpty(icon)) {
            switch (icon) {
                case "clear-day":
                    gradientDrawable = R.drawable.bg_gradient_clear_day;
                    break;

                case "clear-night":
                    gradientDrawable = R.drawable.bg_gradient_clear_night;
                    break;

                case "partly-cloudy-night":
                    gradientDrawable = R.drawable.bg_gradient_cloudy_night;
                    break;

                case "partly-cloudy-day":
                    gradientDrawable = R.drawable.bg_gradient_partly_cloudy_day;
                    break;

                case "rain":
                    gradientDrawable = R.drawable.bg_gradient_light_rain;
                    break;
            }


        }
        return gradientDrawable;
    }

    /**
     * Static method to return integer values @param supplied
     *
     * @param temp temperature to be rounded up
     * @return int value.
     */
    public static String getTemperature(final double temp) {
        return String.format("%s%s", (int) Math.round(temp), "°F");
    }

    /**
     * Static method to return integer values @param supplied
     *
     * @param precipChance temperature to be rounded up
     * @return int value.
     */
    public static String getPrecipProbability(final double precipChance) {
        return String.format ("%s %s", (int) Math.round(precipChance * 100), "%");
    }

    /**
     * Method to return day of week from long time provided
     *
     * @param time time in milliseconds to be converted.
     * @return String time for @param time supplied (in format "MON", "TUE", etc)
     */
    public static String getDayFromDailyWeatherTime(long time) {
        String result = "";
        if (time != 0) {
            final Date date = new Date(time * 1000);
            SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.US);
            result = format.format(date);
        }

        Logger.i(TAG, "Day of the week " + result);
        return result;
    }
}
