package com.neek.tech.weatherapp.weatherama.service;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.neek.tech.weatherapp.weatherama.utilities.Constants;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

/**
 * ResultReceiver for Address used to handle
 */
public class AddressResultReceiver extends ResultReceiver {

    private static final String TAG = AddressResultReceiver.class.getSimpleName();

    private static AddressResultReceiverListener mListener;

    private static AddressResultReceiver singleton;

    private AddressResultReceiver(Handler handler) {
        super(handler);
        Logger.d(TAG, "Initialized");

    }

    public static void initialize(){
        if (singleton == null){
            singleton = new AddressResultReceiver(new Handler());
        }
    }

    public static AddressResultReceiver getInstance(){
        if (singleton == null){
            initialize();
        }
        return singleton;
    }


    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        Logger.create(TAG);
        if (resultCode == Constants.SUCCESS_RESULT){
            if (resultData.containsKey(Constants.RESULT_DATA_KEY) &&
                    resultData.containsKey(Constants.REVERSE_GEOCODED_LOCATION_DATA_EXTRA)) {

                if (mListener != null) {
                    mListener.onAddressRetrievalSuccess(resultData.getString(Constants.RESULT_DATA_KEY),
                            (Location) resultData.getParcelable(Constants.REVERSE_GEOCODED_LOCATION_DATA_EXTRA));
                }
            }
        } else {

            if (resultData.containsKey(Constants.RESULT_DATA_KEY)) {

                if (mListener != null) {
                    mListener.onAddressRetrievalError(resultData.getString(Constants.RESULT_DATA_KEY));
                }
            }
        }

    }

    public static void setListener(AddressResultReceiverListener listener){
        AddressResultReceiver.mListener = listener;
    }

    public interface AddressResultReceiverListener {

        void onAddressRetrievalSuccess(final String address, final Location location);

        void onAddressRetrievalError(final String errorMessage);
    }
}
