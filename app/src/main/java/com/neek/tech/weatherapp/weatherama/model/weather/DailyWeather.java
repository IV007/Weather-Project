package com.neek.tech.weatherapp.weatherama.model.weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Daily weather model
 */
public class DailyWeather implements Serializable {

    @SerializedName("summary")
    private String summary;

    @SerializedName("icon")
    private String icon;

    @SerializedName("data")
    private List<DailyData> data = new ArrayList<>();

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return The data
     */
    public List<DailyData> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<DailyData> data) {
        this.data = data;
    }


    private class DailyData implements Serializable {

        @SerializedName("time")
        private long time;

        @SerializedName("summary")
        private String summary;

        @SerializedName("icon")
        private String icon;

        @SerializedName("sunriseTime")
        private long sunriseTime;

        @SerializedName("sunsetTime")
        private long sunsetTime;

        @SerializedName("moonPhase")
        private double moonPhase;

        @SerializedName("precipIntensity")
        private double precipIntensity;

        @SerializedName("precipIntensityMax")
        private double precipIntensityMax;

        @SerializedName("precipProbability")
        private double precipProbability;

        @SerializedName("temperatureMin")
        private double temperatureMin;

        @SerializedName("temperatureMinTime")
        private long temperatureMinTime;

        @SerializedName("temperatureMax")
        private double temperatureMax;

        @SerializedName("temperatureMaxTime")
        private long temperatureMaxTime;

        @SerializedName("apparentTemperatureMin")
        private double apparentTemperatureMin;

        @SerializedName("apparentTemperatureMinTime")
        private long apparentTemperatureMinTime;

        @SerializedName("apparentTemperatureMax")
        private double apparentTemperatureMax;

        @SerializedName("apparentTemperatureMaxTime")
        private long apparentTemperatureMaxTime;

        @SerializedName("dewPoint")
        private double dewPoint;

        @SerializedName("humidity")
        private double humidity;

        @SerializedName("windSpeed")
        private double windSpeed;

        @SerializedName("windBearing")
        private int windBearing;

        @SerializedName("visibility")
        private double visibility;

        @SerializedName("cloudCover")
        private double cloudCover;

        @SerializedName("pressure")
        private double pressure;

        @SerializedName("ozone")
        private double ozone;

        @SerializedName("precipIntensityMaxTime")
        private long precipIntensityMaxTime;

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
         * The sunriseTime
         */
        public long getSunriseTime() {
            return sunriseTime;
        }

        /**
         *
         * @param sunriseTime
         * The sunriseTime
         */
        public void setSunriseTime(long sunriseTime) {
            this.sunriseTime = sunriseTime;
        }

        /**
         *
         * @return
         * The sunsetTime
         */
        public long getSunsetTime() {
            return sunsetTime;
        }

        /**
         *
         * @param sunsetTime
         * The sunsetTime
         */
        public void setSunsetTime(long sunsetTime) {
            this.sunsetTime = sunsetTime;
        }

        /**
         *
         * @return
         * The moonPhase
         */
        public double getMoonPhase() {
            return moonPhase;
        }

        /**
         *
         * @param moonPhase
         * The moonPhase
         */
        public void setMoonPhase(double moonPhase) {
            this.moonPhase = moonPhase;
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
         * The precipIntensityMax
         */
        public double getPrecipIntensityMax() {
            return precipIntensityMax;
        }

        /**
         *
         * @param precipIntensityMax
         * The precipIntensityMax
         */
        public void setPrecipIntensityMax(double precipIntensityMax) {
            this.precipIntensityMax = precipIntensityMax;
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

        /**
         *
         * @return
         * The temperatureMin
         */
        public double getTemperatureMin() {
            return temperatureMin;
        }

        /**
         *
         * @param temperatureMin
         * The temperatureMin
         */
        public void setTemperatureMin(double temperatureMin) {
            this.temperatureMin = temperatureMin;
        }

        /**
         *
         * @return
         * The temperatureMinTime
         */
        public long getTemperatureMinTime() {
            return temperatureMinTime;
        }

        /**
         *
         * @param temperatureMinTime
         * The temperatureMinTime
         */
        public void setTemperatureMinTime(long temperatureMinTime) {
            this.temperatureMinTime = temperatureMinTime;
        }

        /**
         *
         * @return
         * The temperatureMax
         */
        public double getTemperatureMax() {
            return temperatureMax;
        }

        /**
         *
         * @param temperatureMax
         * The temperatureMax
         */
        public void setTemperatureMax(double temperatureMax) {
            this.temperatureMax = temperatureMax;
        }

        /**
         *
         * @return
         * The temperatureMaxTime
         */
        public long getTemperatureMaxTime() {
            return temperatureMaxTime;
        }

        /**
         *
         * @param temperatureMaxTime
         * The temperatureMaxTime
         */
        public void setTemperatureMaxTime(long temperatureMaxTime) {
            this.temperatureMaxTime = temperatureMaxTime;
        }

        /**
         *
         * @return
         * The apparentTemperatureMin
         */
        public double getApparentTemperatureMin() {
            return apparentTemperatureMin;
        }

        /**
         *
         * @param apparentTemperatureMin
         * The apparentTemperatureMin
         */
        public void setApparentTemperatureMin(double apparentTemperatureMin) {
            this.apparentTemperatureMin = apparentTemperatureMin;
        }

        /**
         *
         * @return
         * The apparentTemperatureMinTime
         */
        public long getApparentTemperatureMinTime() {
            return apparentTemperatureMinTime;
        }

        /**
         *
         * @param apparentTemperatureMinTime
         * The apparentTemperatureMinTime
         */
        public void setApparentTemperatureMinTime(long apparentTemperatureMinTime) {
            this.apparentTemperatureMinTime = apparentTemperatureMinTime;
        }

        /**
         *
         * @return
         * The apparentTemperatureMax
         */
        public double getApparentTemperatureMax() {
            return apparentTemperatureMax;
        }

        /**
         *
         * @param apparentTemperatureMax
         * The apparentTemperatureMax
         */
        public void setApparentTemperatureMax(double apparentTemperatureMax) {
            this.apparentTemperatureMax = apparentTemperatureMax;
        }

        /**
         *
         * @return
         * The apparentTemperatureMaxTime
         */
        public long getApparentTemperatureMaxTime() {
            return apparentTemperatureMaxTime;
        }

        /**
         *
         * @param apparentTemperatureMaxTime
         * The apparentTemperatureMaxTime
         */
        public void setApparentTemperatureMaxTime(long apparentTemperatureMaxTime) {
            this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
        }

        /**
         *
         * @return
         * The dewPoint
         */
        public double getDewPoint() {
            return dewPoint;
        }

        /**
         *
         * @param dewPoint
         * The dewPoint
         */
        public void setDewPoint(double dewPoint) {
            this.dewPoint = dewPoint;
        }

        /**
         *
         * @return
         * The humidity
         */
        public double getHumidity() {
            return humidity;
        }

        /**
         *
         * @param humidity
         * The humidity
         */
        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        /**
         *
         * @return
         * The windSpeed
         */
        public double getWindSpeed() {
            return windSpeed;
        }

        /**
         *
         * @param windSpeed
         * The windSpeed
         */
        public void setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
        }

        /**
         *
         * @return
         * The windBearing
         */
        public int getWindBearing() {
            return windBearing;
        }

        /**
         *
         * @param windBearing
         * The windBearing
         */
        public void setWindBearing(int windBearing) {
            this.windBearing = windBearing;
        }

        /**
         *
         * @return
         * The visibility
         */
        public double getVisibility() {
            return visibility;
        }

        /**
         *
         * @param visibility
         * The visibility
         */
        public void setVisibility(double visibility) {
            this.visibility = visibility;
        }

        /**
         *
         * @return
         * The cloudCover
         */
        public double getCloudCover() {
            return cloudCover;
        }

        /**
         *
         * @param cloudCover
         * The cloudCover
         */
        public void setCloudCover(double cloudCover) {
            this.cloudCover = cloudCover;
        }

        /**
         *
         * @return
         * The pressure
         */
        public double getPressure() {
            return pressure;
        }

        /**
         *
         * @param pressure
         * The pressure
         */
        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        /**
         *
         * @return
         * The ozone
         */
        public double getOzone() {
            return ozone;
        }

        /**
         *
         * @param ozone
         * The ozone
         */
        public void setOzone(double ozone) {
            this.ozone = ozone;
        }

        /**
         *
         * @return
         * The precipIntensityMaxTime
         */
        public long getPrecipIntensityMaxTime() {
            return precipIntensityMaxTime;
        }

        /**
         *
         * @param precipIntensityMaxTime
         * The precipIntensityMaxTime
         */
        public void setPrecipIntensityMaxTime(long precipIntensityMaxTime) {
            this.precipIntensityMaxTime = precipIntensityMaxTime;
        }

        /**
         *
         * @return
         * The precipType
         */
        public String getPrecipType() {
            return precipType;
        }

        /**
         *
         * @param precipType
         * The precipType
         */
        public void setPrecipType(String precipType) {
            this.precipType = precipType;
        }


        @Override
        public String toString() {
            return "DailyData{" +
                    "time=" + time + '\n' +
                    ", summary='" + summary + '\n' +
                    ", icon='" + icon + '\n' +
                    ", sunriseTime=" + sunriseTime + '\n' +
                    ", sunsetTime=" + sunsetTime + '\n' +
                    ", moonPhase=" + moonPhase + '\n' +
                    ", precipIntensity=" + precipIntensity + '\n' +
                    ", precipIntensityMax=" + precipIntensityMax + '\n' +
                    ", precipProbability=" + precipProbability + '\n' +
                    ", temperatureMin=" + temperatureMin + '\n' +
                    ", temperatureMinTime=" + temperatureMinTime + '\n' +
                    ", temperatureMax=" + temperatureMax + '\n' +
                    ", temperatureMaxTime=" + temperatureMaxTime + '\n' +
                    ", apparentTemperatureMin=" + apparentTemperatureMin + '\n' +
                    ", apparentTemperatureMinTime=" + apparentTemperatureMinTime + '\n' +
                    ", apparentTemperatureMax=" + apparentTemperatureMax + '\n' +
                    ", apparentTemperatureMaxTime=" + apparentTemperatureMaxTime + '\n' +
                    ", dewPoint=" + dewPoint + '\n' +
                    ", humidity=" + humidity + '\n' +
                    ", windSpeed=" + windSpeed + '\n' +
                    ", windBearing=" + windBearing + '\n' +
                    ", visibility=" + visibility + '\n' +
                    ", cloudCover=" + cloudCover + '\n' +
                    ", pressure=" + pressure + '\n' +
                    ", ozone=" + ozone + '\n' +
                    ", precipIntensityMaxTime=" + precipIntensityMaxTime + '\n' +
                    ", precipType='" + precipType + '\n' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "DailyWeather{" +
                "summary='" + summary + '\n' +
                ", icon='" + icon + '\n' +
                ", data=" + Arrays.asList(data.toArray()) + '\n' +
                '}';
    }
}
