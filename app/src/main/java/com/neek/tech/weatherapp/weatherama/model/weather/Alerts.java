package com.neek.tech.weatherapp.weatherama.model.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ivanutsalo on 11/30/16.
 */

public class Alerts {


    @SerializedName("title")
    private String title;

    @SerializedName("time")
    private Long time;

    @SerializedName("expires")
    private Long expires;

    @SerializedName("description")
    private String description;

    @SerializedName("uri")
    private String uri;

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The time
     */
    public Long getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * @return The expires
     */
    public Long getExpires() {
        return expires;
    }

    /**
     * @param expires The expires
     */
    public void setExpires(Long expires) {
        this.expires = expires;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }


}
