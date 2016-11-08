package com.neek.tech.weatherapp.weatherama.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Json Utility
 */
public class Json {

    private static Gson gson = new GsonBuilder().create();

    private Json() {
    }

    public static Gson getGson() {
        return gson;
    }

    public static <T> T fromJson(String json, Class<T> responseClass) {
        return getGson().fromJson(json, responseClass);
    }

    public static <T> T fromJson(Reader reader, Class<T> responseClass){
        return getGson().fromJson(reader, responseClass);
    }
    public static <T> T fromJson(String json, Type type) {
        return getGson().fromJson(json, type);
    }

    public static String toJson(Object src) {
        return getGson().toJson(src);
    }


}
