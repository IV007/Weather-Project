package com.neek.tech.weatherapp.weatherama.model.weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Minutely weather model.
 */
public class MinuteWeather implements Serializable {

    @SerializedName("summary")
    private String summary;

    @SerializedName("icon")
    private String icon;

    @SerializedName("data")
    private List<MinuteData> data = new ArrayList<>();

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
    public List<MinuteData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<MinuteData> data) {
        this.data = data;
    }

    private class MinuteData implements Serializable  {

        @SerializedName("time")
        private long time;

        @SerializedName("precipIntensity")
        private double precipIntensity;

        @SerializedName("precipProbability")
        private double precipProbability;

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


        @Override
        public String toString() {
            return "MinuteData{" +
                    "time=" + time + '\n' +
                    ", precipIntensity=" + precipIntensity + '\n' +
                    ", precipProbability=" + precipProbability + '\n' +
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
