package com.neek.tech.weatherapp.weatherama.model.weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Minutely weather model.
 */
public class MinuteWeather implements Serializable {

    @SerializedName("summary")
    private String summary;

    @SerializedName("icon")
    private String icon;

    @SerializedName("data")
    private ArrayList<MinuteData> data = new ArrayList<>();

    /**
     *
     * @return
     * The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary
     * The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     *
     * @return
     * The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @param icon
     * The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     *
     * @return
     * The data
     */
    public ArrayList<MinuteData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ArrayList<MinuteData> data) {
        this.data = data;
    }

    public class MinuteData implements Serializable  {

        @SerializedName("time")
        private long time;

        @SerializedName("precipIntensity")
        private double precipIntensity;

        @SerializedName("precipProbability")
        private double precipProbability;

        @SerializedName("summary")
        private String summary;

        @SerializedName("icon")
        private String icon;

        @SerializedName("temperature")
        private Double temperature;

        @SerializedName("apparentTemperature")
        private Double apparentTemperature;

        @SerializedName("dewPoint")
        private Double dewPoint;

        @SerializedName("humidity")
        private Double humidity;

        @SerializedName("windSpeed")
        private Double windSpeed;

        @SerializedName("windBearing")
        private Integer windBearing;

        @SerializedName("visibility")
        private double visibility;

        @SerializedName("cloudCover")
        private Double cloudCover;

        @SerializedName("pressure")
        private Double pressure;

        @SerializedName("ozone")
        private Double ozone;

        @SerializedName("precipType")
        private String precipType;

        /**
         *
         * @return
         * The time
         */
        public long getTime() {
            return time;
        }

        /**
         *
         * @param time
         * The time
         */
        public void setTime(long time) {
            this.time = time;
        }

        /**
         *
         * @return
         * The precipIntensity
         */
        public double getPrecipIntensity() {
            return precipIntensity;
        }

        /**
         *
         * @param precipIntensity
         * The precipIntensity
         */
        public void setPrecipIntensity(double precipIntensity) {
            this.precipIntensity = precipIntensity;
        }

        /**
         *
         * @return
         * The precipProbability
         */
        public double getPrecipProbability() {
            return precipProbability;
        }

        /**
         *
         * @param precipProbability
         * The precipProbability
         */
        public void setPrecipProbability(double precipProbability) {
            this.precipProbability = precipProbability;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }

        public Double getApparentTemperature() {
            return apparentTemperature;
        }

        public void setApparentTemperature(Double apparentTemperature) {
            this.apparentTemperature = apparentTemperature;
        }

        public Double getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(Double dewPoint) {
            this.dewPoint = dewPoint;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        public Double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(Double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public Integer getWindBearing() {
            return windBearing;
        }

        public void setWindBearing(Integer windBearing) {
            this.windBearing = windBearing;
        }

        public double getVisibility() {
            return visibility;
        }

        public void setVisibility(double visibility) {
            this.visibility = visibility;
        }

        public Double getCloudCover() {
            return cloudCover;
        }

        public void setCloudCover(Double cloudCover) {
            this.cloudCover = cloudCover;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public Double getOzone() {
            return ozone;
        }

        public void setOzone(Double ozone) {
            this.ozone = ozone;
        }

        public String getPrecipType() {
            return precipType;
        }

        public void setPrecipType(String precipType) {
            this.precipType = precipType;
        }

        @Override
        public String toString() {
            return "MinuteData{" +
                    "time=" + time + '\n' +
                    ", precipIntensity=" + precipIntensity + '\n' +
                    ", precipProbability=" + precipProbability + '\n' +
                    ", summary='" + summary + '\n' +
                    ", icon='" + icon + '\n' +
                    ", temperature=" + temperature + '\n' +
                    ", apparentTemperature=" + apparentTemperature + '\n' +
                    ", dewPoint=" + dewPoint + '\n' +
                    ", humidity=" + humidity + '\n' +
                    ", windSpeed=" + windSpeed + '\n' +
                    ", windBearing=" + windBearing + '\n' +
                    ", visibility=" + visibility + '\n' +
                    ", cloudCover=" + cloudCover + '\n' +
                    ", pressure=" + pressure + '\n' +
                    ", ozone=" + ozone + '\n' +
                    ", precipType='" + precipType + '\n' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "MinuteWeather{" +
                "summary='" + summary + '\n' +
                ", icon='" + icon + '\n' +
                ", data=" + Arrays.asList(data.toArray()) + '\n' +
                '}';
    }
}
