package com.neek.tech.weatherapp.weatherama.model.weather;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Weather Model class with custom weather objects.
 */
public class Weather implements Serializable {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("offset")
    private int offset;

    @SerializedName("currently")
    private CurrentWeather currently;

    @SerializedName("minutely")
    private MinuteWeather minutely;

    @SerializedName("hourly")
    private HourlyWeather hourly;

    @SerializedName("daily")
    private DailyWeather daily;

    @SerializedName("flags")
    private Flags flags;

    @Nullable
    @SerializedName("alerts")
    private List<Alerts> alerts = new ArrayList<>();

    /**
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @param timezone The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * @return The offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset The offset
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @return The currently
     */
    public CurrentWeather getCurrentWeather() {
        return currently;
    }

    /**
     * @param currently The currently
     */
    public void setCurrentWeather(CurrentWeather currently) {
        this.currently = currently;
    }

    /**
     * @return The minutely
     */
    public MinuteWeather getMinuteWeather() {
        return minutely;
    }

    /**
     * @param minutely The minutely
     */
    public void setMinuteWeather(MinuteWeather minutely) {
        this.minutely = minutely;
    }

    /**
     * @return The hourly
     */
    public HourlyWeather getHourlyWeather() {
        return hourly;
    }

    /**
     * @param hourly The hourly
     */
    public void setHourlyWeather(HourlyWeather hourly) {
        this.hourly = hourly;
    }

    /**
     * @return The daily
     */
    public DailyWeather getDailyWeather() {
        return daily;
    }

    /**
     * @param daily The daily
     */
    public void setDailyWeather(DailyWeather daily) {
        this.daily = daily;
    }

    /**
     * @return The flags
     */
    public Flags getFlags() {
        return flags;
    }

    /**
     * @param flags The flags
     */
    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    /**
     *
     * @return
     * The alerts
     */
    public List<Alerts> getAlerts() {
        return alerts;
    }

    /**
     *
     * @param alerts
     * The alerts
     */
    public void setAlerts(List<Alerts> alerts) {
        this.alerts = alerts;
    }



    private class Flags implements Serializable {

        @SerializedName("sources")
        private List<String> sources = new ArrayList<>();

        @SerializedName("darksky-stations")
        private List<String> darkskyStations = new ArrayList<>();

        @SerializedName("lamp-stations")
        private List<String> lampStations = new ArrayList<>();

        @SerializedName("isd-stations")
        private List<String> isdStations = new ArrayList<>();

        @SerializedName("madis-stations")
        private List<String> madisStations = new ArrayList<>();

        @SerializedName("units")
        private String units;

        /**
         * @return The sources
         */
        public List<String> getSources() {
            return sources;
        }

        /**
         * @param sources The sources
         */
        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        /**
         * @return The darkskyStations
         */
        public List<String> getDarkskyStations() {
            return darkskyStations;
        }

        /**
         * @param darkskyStations The darksky-stations
         */
        public void setDarkskyStations(List<String> darkskyStations) {
            this.darkskyStations = darkskyStations;
        }

        /**
         * @return The lampStations
         */
        public List<String> getLampStations() {
            return lampStations;
        }

        /**
         * @param lampStations The lamp-stations
         */
        public void setLampStations(List<String> lampStations) {
            this.lampStations = lampStations;
        }

        /**
         * @return The isdStations
         */
        public List<String> getIsdStations() {
            return isdStations;
        }

        /**
         * @param isdStations The isd-stations
         */
        public void setIsdStations(List<String> isdStations) {
            this.isdStations = isdStations;
        }

        /**
         * @return The madisStations
         */
        public List<String> getMadisStations() {
            return madisStations;
        }

        /**
         * @param madisStations The madis-stations
         */
        public void setMadisStations(List<String> madisStations) {
            this.madisStations = madisStations;
        }

        /**
         * @return The units
         */
        public String getUnits() {
            return units;
        }

        /**
         * @param units The units
         */
        public void setUnits(String units) {
            this.units = units;
        }

    }


    @Override
    public String toString() {
        return "Weather{" +
                "latitude=" + latitude + '\n' +
                ", longitude=" + longitude + '\n' +
                ", timezone='" + timezone + '\n' +
                ", offset=" + offset + '\n' +
                ", flags=" + flags + '\n' +
                '}';
    }
}
