package com.neek.tech.weatherapp.weatherama.model.weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Current weather model
 */
public class CurrentWeather implements Serializable {

    @SerializedName("time")
    private long time;

    @SerializedName("summary")
    private String summary;

    @SerializedName("icon")
    private String icon;

    @SerializedName("nearestStormDistance")
    private double nearestStormDistance;

    @SerializedName("nearestStormBearing")
    private int nearestStormBearing;

    @SerializedName("precipIntensity")
    private double precipIntensity;

    @SerializedName("precipProbability")
    private double precipProbability;

    @SerializedName("temperature")
    private double temperature;

    @SerializedName("apparentTemperature")
    private double apparentTemperature;

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

    /**
     * @return The time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(long time) {
        this.time = time;
    }

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
     * @return The nearestStormDistance
     */
    public double getNearestStormDistance() {
        return nearestStormDistance;
    }

    /**
     * @param nearestStormDistance The nearestStormDistance
     */
    public void setNearestStormDistance(double nearestStormDistance) {
        this.nearestStormDistance = nearestStormDistance;
    }

    /**
     * @return The nearestStormBearing
     */
    public int getNearestStormBearing() {
        return nearestStormBearing;
    }

    /**
     * @param nearestStormBearing The nearestStormBearing
     */
    public void setNearestStormBearing(int nearestStormBearing) {
        this.nearestStormBearing = nearestStormBearing;
    }

    /**
     * @return The precipIntensity
     */
    public double getPrecipIntensity() {
        return precipIntensity;
    }

    /**
     * @param precipIntensity The precipIntensity
     */
    public void setPrecipIntensity(double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    /**
     * @return The precipProbability
     */
    public double getPrecipProbability() {
        return precipProbability;
    }

    /**
     * @param precipProbability The precipProbability
     */
    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    /**
     * @return The temperature
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * @param temperature The temperature
     */
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    /**
     * @return The apparentTemperature
     */
    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    /**
     * @param apparentTemperature The apparentTemperature
     */
    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    /**
     * @return The dewPoint
     */
    public Double getDewPoint() {
        return dewPoint;
    }

    /**
     * @param dewPoint The dewPoint
     */
    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    /**
     * @return The humidity
     */
    public Double getHumidity() {
        return humidity;
    }

    /**
     * @param humidity The humidity
     */
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    /**
     * @return The windSpeed
     */
    public Double getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed The windSpeed
     */
    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * @return The windBearing
     */
    public int getWindBearing() {
        return windBearing;
    }

    /**
     * @param windBearing The windBearing
     */
    public void setWindBearing(int windBearing) {
        this.windBearing = windBearing;
    }

    /**
     * @return The visibility
     */
    public Double getVisibility() {
        return visibility;
    }

    /**
     * @param visibility The visibility
     */
    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    /**
     * @return The cloudCover
     */
    public Double getCloudCover() {
        return cloudCover;
    }

    /**
     * @param cloudCover The cloudCover
     */
    public void setCloudCover(Double cloudCover) {
        this.cloudCover = cloudCover;
    }

    /**
     * @return The pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     * @param pressure The pressure
     */
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    /**
     * @return The ozone
     */
    public Double getOzone() {
        return ozone;
    }

    /**
     * @param ozone The ozone
     */
    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }


    @Override
    public String toString() {
        return "CurrentWeather{" +
                "time=" + time + '\n' +
                ", summary='" + summary + '\n' +
                ", icon='" + icon + '\n' +
                ", nearestStormDistance=" + nearestStormDistance + '\n' +
                ", nearestStormBearing=" + nearestStormBearing + '\n' +
                ", precipIntensity=" + precipIntensity + '\n' +
                ", precipProbability=" + precipProbability + '\n' +
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
                '}';
    }
}
