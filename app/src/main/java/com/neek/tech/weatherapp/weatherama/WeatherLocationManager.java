package com.neek.tech.weatherapp.weatherama;

import android.Manifest;
import android.content.Context;
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
import com.neek.tech.permissions.runtime_permission.PermissionConstants;
import com.neek.tech.permissions.runtime_permission.PermissionUtils;
import com.neek.tech.weatherapp.weatherama.utilities.LocationUtils;


/**
 * Location Manager class for retrieving user location.
 */
public class WeatherLocationManager implements
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = WeatherLocationManager.class.getSimpleName();
    private static WeatherLocationManager singleton;
    private static LocationManager mLocationManager;
    private static String provider;
    private static LocationUpdater mLocationUpdater;
    private static Context mContext;
    private static long locationTimer;

    private static boolean mConnected;
    private static GoogleApiClient mLocationClient;

    private WeatherLocationManager() {
    }

    static void initialize(Context context) {
        mConnected = false;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        singleton = new WeatherLocationManager();
        buildGoogleApiClient(context);

    }

    public static WeatherLocationManager getInstance(Context context) {
        if (singleton == null) {
            initialize(context);
            mContext = context;
        }
        return singleton;
    }


    public static void connect(Context context) {
        if (mLocationClient != null && !mConnected) {
            Log.i(TAG, "connect");
            mLocationClient.connect();
            mContext = context;

        }
    }

    public static void disconnect() {
        if (mLocationClient != null) {
            Log.i(TAG, "disconnect");
            mLocationClient.disconnect();
            mConnected = false;
        }
    }


    public static boolean isLocationServicesConnected() {
        return mConnected;
    }

    public static boolean isPollingTimeExpired(){
        return false;
    }

    public static void getLastLocation(Context context) {
        if (mConnected && mLocationClient != null &&
                PermissionUtils.hasPermission(context, PermissionConstants.LOCATION_PERMISSION)) {
            Location location = null;
            if (PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                location = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
            } else if (!PermissionUtils.isAndroidOSMarshmallowOrAbove()) {
                location = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
            }
            if (mLocationUpdater != null) {
                mLocationUpdater.onLocationRetrieved(location);
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

    @Override
    public void onConnectionFailed(ConnectionResult failure) {
        mConnected = false;

    }

    @Override
    public void onConnected(Bundle arg0) {
        mConnected = true;
        Location currentLocation;
        if (PermissionUtils.hasPermission(mContext, PermissionConstants.LOCATION_PERMISSION)) {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);

            if (mLocationUpdater != null) {
                mLocationUpdater.onLocationRetrieved(currentLocation);
            }

        }

        if (!PermissionUtils.isAndroidOSMarshmallowOrAbove()) {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);

            if (mLocationUpdater != null) {
                mLocationUpdater.onLocationRetrieved(currentLocation);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            mConnected = false;

        }
    }

    //What is the replacement for this.
//	@Override
//	public void onDisconnected() {
//		mConnected = false;
//		if (!mSubscribers.isEmpty()) {
//        	for (ILocationServicesSubscriber subscriber: mSubscribers) {
//        		subscriber.disconnected();
//        	}
//        }
//	}

    protected static synchronized void buildGoogleApiClient(Context context) {
        if (context == null)
            return;

        if (mLocationClient == null &&
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            mLocationClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(singleton)
                    .addOnConnectionFailedListener(singleton)
                    .addApi(LocationServices.API)
                    .build();
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

    public static void removeUpdates() {
        mLocationManager.removeUpdates(singleton);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (mLocationUpdater != null) {
            mLocationUpdater.onLocationRetrieved(location);
        }

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
    }
}
