package com.neek.tech.weatherapp.weatherama.model.weather;

/**
 * Created by ivanutsalo on 11/11/16.
 */

public class ReverseGeocodeAddress {

    private String key;

    private String mPlaceName;

    private double mLatitude;

    private double mLongitude;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String placeName) {
        mPlaceName = placeName;
    }
}
