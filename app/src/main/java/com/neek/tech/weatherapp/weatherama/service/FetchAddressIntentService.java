package com.neek.tech.weatherapp.weatherama.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.utilities.Constants;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class to get user readable location from LatLng.
 */

public class FetchAddressIntentService extends IntentService {

    private static final String TAG = FetchAddressIntentService.class.getSimpleName();

    private ResultReceiver mResultReceiver;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public FetchAddressIntentService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);
        mResultReceiver = intent.getParcelableExtra(Constants.RECEIVER);

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            errorMessage = getString(R.string.geocoding_service_not_available);
            Log.e(TAG, errorMessage, e);
        } catch (IllegalArgumentException e){
            errorMessage = getString(R.string.geocoding_invalid_lat_lng_used);

            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), e);
        }

        if (addresses == null || addresses.size() == 0){
            if (errorMessage.isEmpty()){
                errorMessage = getString(R.string.geocoding_no_address_found);
                Logger.e(TAG, errorMessage);

                deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage, null);
            }

        } else {

            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                addressFragments.add(address.getAddressLine(i));
            }

            Log.i(TAG, getString(R.string.geocoding_address_found) + ", " + addressFragments);
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"), addressFragments), location);
        }
    }

    private void deliverResultToReceiver(int result, String message, Location location) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        bundle.putParcelable(Constants.REVERSE_GEOCODED_LOCATION_DATA_EXTRA, location);
        mResultReceiver.send(result, bundle);
    }
}
