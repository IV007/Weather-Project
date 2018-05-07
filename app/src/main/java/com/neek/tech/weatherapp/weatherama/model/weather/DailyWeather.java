package com.neek.tech.weatherapp.weatherama.model.weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Daily weather model
 */
public class DailyWeather implements Serializable {

    @SerializedName("summary")
    private String summary;

    @SerializedName("icon")
    private String icon;

    @SerializedName("data")
    private ArrayList<DailyData> data = new ArrayList<>();

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
    public ArrayList<DailyData> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(ArrayList<DailyData> data) {
        this.data = data;
    }


    public class DailyData implements Serializable {

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
        private Double moonPhase;

        @SerializedName("precipIntensity")
        private Double precipIntensity;

        @SerializedName("precipIntensityMax")
        private Double precipIntensityMax;

        @SerializedName("precipProbability")
        private Double precipProbability;

        @SerializedName("temperatureMin")
        private Double temperatureMin;

        @SerializedName("temperatureMinTime")
        private long temperatureMinTime;

        @SerializedName("temperatureMax")
        private Double temperatureMax;

        @SerializedName("temperatureMaxTime")
        private long temperatureMaxTime;

        @SerializedName("apparentTemperatureMin")
        private Double apparentTemperatureMin;

        @SerializedName("apparentTemperatureMinTime")
        private long apparentTemperatureMinTime;

        @SerializedName("apparentTemperatureMax")
        private Double apparentTemperatureMax;

        @SerializedName("apparentTemperatureMaxTime")
        private long apparentTemperatureMaxTime;

        @SerializedName("dewPoint")
        private Double dewPoint;

        @SerializedName("humidity")
        private Double humidity;

        @SerializedName("windSpeed")
        private Double windSpeed;

        @SerializedName("windBearing")
        private int windBearing;

        @SerializedName("visibility")
        private Double visibility;

        @SerializedName("cloudCover")
        private Double cloudCover;

        @SerializedName("pressure")
        private Double pressure;

        @SerializedName("ozone")
        private Double ozone;

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
        public Double getMoonPhase() {
            return moonPhase;
        }

        /**
         *
         * @param moonPhase
         * The moonPhase
         */
        public void setMoonPhase(Double moonPhase) {
            this.moonPhase = moonPhase;
        }

        /**
         *
         * @return
         * The precipIntensity
         */
        public Double getPrecipIntensity() {
            return precipIntensity;
        }

        /**
         *
         * @param precipIntensity
         * The precipIntensity
         */
        public void setPrecipIntensity(Double precipIntensity) {
            this.precipIntensity = precipIntensity;
        }

        /**
         *
         * @return
         * The precipIntensityMax
         */
        public Double getPrecipIntensityMax() {
            return precipIntensityMax;
        }

        /**
         *
         * @param precipIntensityMax
         * The precipIntensityMax
         */
        public void setPrecipIntensityMax(Double precipIntensityMax) {
            this.precipIntensityMax = precipIntensityMax;
        }

        /**
         *
         * @return
         * The precipProbability
         */
        public Double getPrecipProbability() {
            return precipProbability;
        }

        /**
         *
         * @param precipProbability
         * The precipProbability
         */
        public void setPrecipProbability(Double precipProbability) {
            this.precipProbability = precipProbability;
        }

        /**
         *
         * @return
         * The temperatureMin
         */
        public Double getTemperatureMin() {
            return temperatureMin;
        }

        /**
         *
         * @param temperatureMin
         * The temperatureMin
         */
        public void setTemperatureMin(Double temperatureMin) {
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
        public Double getTemperatureMax() {
            return temperatureMax;
        }

        /**
         *
         * @param temperatureMax
         * The temperatureMax
         */
        public void setTemperatureMax(Double temperatureMax) {
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
        public Double getApparentTemperatureMin() {
            return apparentTemperatureMin;
        }

        /**
         *
         * @param apparentTemperatureMin
         * The apparentTemperatureMin
         */
        public void setApparentTemperatureMin(Double apparentTemperatureMin) {
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
        public Double getApparentTemperatureMax() {
            return apparentTemperatureMax;
        }

        /**
         *
         * @param apparentTemperatureMax
         * The apparentTemperatureMax
         */
        public void setApparentTemperatureMax(Double apparentTemperatureMax) {
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
        public Double getDewPoint() {
            return dewPoint;
        }

        /**
         *
         * @param dewPoint
         * The dewPoint
         */
        public void setDewPoint(Double dewPoint) {
            this.dewPoint = dewPoint;
        }

        /**
         *
         * @return
         * The humidity
         */
        public Double getHumidity() {
            return humidity;
        }

        /**
         *
         * @param humidity
         * The humidity
         */
        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        /**
         *
         * @return
         * The windSpeed
         */
        public Double getWindSpeed() {
            return windSpeed;
        }

        /**
         *
         * @param windSpeed
         * The windSpeed
         */
        public void setWindSpeed(Double windSpeed) {
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
        public Double getVisibility() {
            return visibility;
        }

        /**
         *
         * @param visibility
         * The visibility
         */
        public void setVisibility(Double visibility) {
            this.visibility = visibility;
        }

        /**
         *
         * @return
         * The cloudCover
         */
        public Double getCloudCover() {
            return cloudCover;
        }

        /**
         *
         * @param cloudCover
         * The cloudCover
         */
        public void setCloudCover(Double cloudCover) {
            this.cloudCover = cloudCover;
        }

        /**
         *
         * @return
         * The pressure
         */
        public Double getPressure() {
            return pressure;
        }

        /**
         *
         * @param pressure
         * The pressure
         */
        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        /**
         *
         * @return
         * The ozone
         */
        public Double getOzone() {
            return ozone;
        }

        /**
         *
         * @param ozone
         * The ozone
         */
        public void setOzone(Double ozone) {
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
