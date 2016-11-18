package com.neek.tech.weatherapp.weatherama;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.neek.tech.permissions.runtime_permission.PermissionConstants;
import com.neek.tech.permissions.runtime_permission.PermissionUtils;
import com.neek.tech.weatherapp.weatherama.controllers.WeatherController;
import com.neek.tech.weatherapp.weatherama.service.AddressResultReceiver;
import com.neek.tech.weatherapp.weatherama.service.FetchAddressIntentService;
import com.neek.tech.weatherapp.weatherama.utilities.Constants;
import com.neek.tech.weatherapp.weatherama.utilities.LocationUtils;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatheramaPreferences;


/**
 * Location Manager class for retrieving user location.
 * Geolocation v0.1
 */
public class WeatherLocationManager implements
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        AddressResultReceiver.AddressResultReceiverListener {

    private static final String TAG = WeatherLocationManager.class.getSimpleName();

    private static WeatherLocationManager singleton;

    private static LocationManager mLocationManager;

    private static String provider;

    private static LocationUpdater mLocationUpdater;

    private Context mContext;

    private static boolean mConnected;
    private static GoogleApiClient mLocationClient;
    private static boolean mUsePlacesAPI = false;

    private WeatherLocationManager() {
        Logger.d(TAG, "Initialized");
    }

    static void initialize(Context context) {
        mConnected = false;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        singleton = new WeatherLocationManager();
        AddressResultReceiver.initialize();
        AddressResultReceiver.setListener(singleton);
        buildGoogleApiClient(context, false);

    }

    public static WeatherLocationManager getInstance(Context context) {
        if (singleton == null) {
            initialize(context);
            singleton.mContext = context;
        }
        return singleton;
    }


    public static void connect(Context context) {
        if (mLocationClient != null && !mConnected) {
            Log.i(TAG, "connect");
            mLocationClient.connect();
            singleton.mContext = context;

        }
    }

    public static void disconnect() {
        if (mLocationClient != null) {
            Log.i(TAG, "disconnect");
            mLocationClient.disconnect();
            mConnected = false;
            mUsePlacesAPI = false;
        }
    }

    public static void removeUpdates(Context context) {
        if (PermissionUtils.hasPermission(context, PermissionConstants.LOCATION_PERMISSION)) {
            mLocationManager.removeUpdates(singleton);
        }
    }

    public static boolean isLocationServicesConnected() {
        return mConnected;
    }


    public static void getLastLocation(Context context) {
        if (mConnected && mLocationClient != null &&
                PermissionUtils.hasPermission(context, PermissionConstants.LOCATION_PERMISSION)) {
            Location location = null;
            if (PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    && PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                location = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);

            } else if (!PermissionUtils.isAndroidOSMarshmallowOrAbove()) {

                location = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);

            }

            if (mLocationUpdater != null && location != null) {
                mLocationUpdater.onLocationRetrieved(location);
            }

            if (!WeatherController.addressExistsAlready(singleton.mContext, location) &&
                    WeatheramaPreferences.isSaveUserLocationEnabledByDefault(singleton.mContext)) {
                startIntentService(location);
            }
        }
    }

    public static void updateLocation(Context context) {
        // Create the LocationRequest object
        if (mConnected && mLocationClient != null &&
                PermissionUtils.hasPermission(context, PermissionConstants.LOCATION_PERMISSION)) {
            if (PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, LocationRequest.create(), (com.google.android.gms.location.LocationListener) singleton);
            } else if (!PermissionUtils.isAndroidOSMarshmallowOrAbove()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, LocationRequest.create(), (com.google.android.gms.location.LocationListener) singleton);
            }
        }
    }

    private static void startIntentService(Location location) {
        Intent intent = new Intent(singleton.mContext, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, AddressResultReceiver.getInstance());
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        singleton.mContext.startService(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult failure) {
        mConnected = false;
        if (mLocationUpdater != null) {
            mLocationUpdater.onConnectionError();
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mConnected = true;
        if (!mUsePlacesAPI) {
            Location currentLocation = null;
            if (PermissionUtils.isAndroidOSMarshmallowOrAbove()) {
                if (PermissionUtils.hasPermission(singleton.mContext, PermissionConstants.LOCATION_PERMISSION)) {
                    currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);

                    if (mLocationUpdater != null) {
                        mLocationUpdater.onLocationRetrieved(currentLocation);
                    }
                }
            } else {
                currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);

                if (mLocationUpdater != null) {
                    mLocationUpdater.onLocationRetrieved(currentLocation);
                }
            }

            if (!WeatherController.addressExistsAlready(singleton.mContext, currentLocation) &&
                    WeatheramaPreferences.isSaveUserLocationEnabledByDefault(singleton.mContext)) {
                startIntentService(currentLocation);
            }
        } else {
            if (mLocationUpdater != null) {
                mLocationUpdater.onLocationRetrieved(null);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            mConnected = false;

        }
    }

    public static void buildGoogleApiForGpsOrPlaces(Context context, boolean usePlacesApi){
        if (mLocationClient != null){
            mLocationClient.disconnect();
            mLocationClient = null;
        }
        mUsePlacesAPI = usePlacesApi;
        buildGoogleApiClient(context, usePlacesApi);
    }

    private static synchronized void buildGoogleApiClient(Context context, boolean usePlacesApi) {
        if (context == null)
            return;

        if (mLocationClient == null &&
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            if (!usePlacesApi) {
                mLocationClient = new GoogleApiClient.Builder(context)
                        .addConnectionCallbacks(singleton)
                        .addOnConnectionFailedListener(singleton)
                        .addApi(LocationServices.API)
                        .build();
            } else {

                mLocationClient = new GoogleApiClient.Builder(context)
                        .addOnConnectionFailedListener(singleton)
                        .addApi(Places.PLACE_DETECTION_API)
                        .addApi(Places.GEO_DATA_API)
                        .build();
            }
        }
    }



    public static void getLastKnownLocation() {
        Criteria criteria = new Criteria();
        provider = mLocationManager.getBestProvider(criteria, true);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null && mLocationUpdater != null) {
            mLocationUpdater.onLocationRetrieved(location);
        }
    }

    public static void requestLocationUpdates(Context context) {
        if (mLocationManager != null) {
            provider = mLocationManager.getBestProvider(new Criteria(), true);
            mLocationManager.requestLocationUpdates(provider, LocationUtils.UPDATE_TIME, 1, singleton);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if (mLocationUpdater != null) {
            mLocationUpdater.onLocationRetrieved(location);
        }

    }

    @Override
    public void onAddressRetrievalSuccess(final String address, final Location location) {

        WeatherController.saveAddressToPrefs(singleton.mContext, address, location, null);

    }

    @Override
    public void onAddressRetrievalError(final String errorMessage) {
        Logger.e(TAG, errorMessage);
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public static void setLocationUpdater(LocationUpdater locationUpdater) {
        mLocationUpdater = locationUpdater;
    }

    public interface LocationUpdater {

        void onLocationRetrieved(final Location location);

        void onConnectionError();
    }

    public static boolean isPollingTimeExpired() {
        return false;//TODO - implement Polling for location updates. Geolocation v3.0.
    }
}
