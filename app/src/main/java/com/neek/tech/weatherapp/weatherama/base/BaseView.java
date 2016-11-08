package com.neek.tech.weatherapp.weatherama.base;

/**
 * BaseView super class for updating all UI classes.
 */
public interface BaseView {

    void onError(final String title, final String errorMessage);

    void showProgressDialog();

    void hideProgressDialog();

}
