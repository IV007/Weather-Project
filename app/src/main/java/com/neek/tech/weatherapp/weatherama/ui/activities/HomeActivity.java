package com.neek.tech.weatherapp.weatherama.ui.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.neek.tech.suite.permissions.runtime_permission.PermissionConstants;
import com.neek.tech.suite.permissions.runtime_permission.PermissionUtils;
import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.WeatherLocationManager;
import com.neek.tech.weatherapp.weatherama.base.BaseActivity;
import com.neek.tech.weatherapp.weatherama.controllers.WeatherController;
import com.neek.tech.weatherapp.weatherama.model.weather.Weather;
import com.neek.tech.weatherapp.weatherama.ui.HomeActivityListener;
import com.neek.tech.weatherapp.weatherama.ui.fragments.HomeFragment;
import com.neek.tech.weatherapp.weatherama.utilities.LocationUtils;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatheramaPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements
        WeatherController.HomeActivityView,
        WeatherLocationManager.LocationUpdater,
        PermissionUtils.PermissionListener {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.error_message_textview)
    protected TextView mEmptyMessageTextView;

    /**
     * Listener to notify fragments when new data is retrieved
     */
    private List<HomeActivityListener> mHomeActivityListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

        setContentView(R.layout.activity_home);

        if (mHomeActivityListener == null) {
            mHomeActivityListener = new ArrayList<>();
        }

        navigateToFragment(HomeFragment.newInstance());

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LocationUtils.isNetworkAvailable(this)) {
            if (!PermissionUtils.hasPermission(this, PermissionConstants.LOCATION_PERMISSION)) {

                if (!showRuntimePermissionFragment(PermissionConstants.LOCATION)) {
                    fetchWeatherData(null);
                }

            } else {
                if (!WeatheramaPreferences.didUserEnableSelectedAddress(this)) {
                    connect();
                } else {
                    fetchWeatherData(null);
                }
            }
        } else {
            showErrorDialog(getString(R.string.network_unavailable_title), getString(R.string.network_unavailable_message));
            hideProgressDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        WeatherLocationManager.disconnect();
        WeatherLocationManager.removeUpdates(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            //Show settings Activity.
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchWeatherData(final Location location) {

        WeatherController.setHomeActivityView(this);

        if (location != null) {
            showProgressDialog();
            WeatherController.getInstance().fetchWeatherData(location);
        } else {
            showProgressDialog();
            WeatherController.getInstance().fetchWeatherData(WeatherController.getUserLocationFromPrefs(this));
        }

    }

    @Override
    public void onWeatherRetrieved(final Weather weather) {
        Logger.d(TAG, "Weather " + weather.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();

                if (weather != null) {

                    if (mHomeActivityListener != null) {
                        for (HomeActivityListener listener : mHomeActivityListener) {
                            listener.onWeatherRetrieved(weather);
                        }
                    }

                } else {

                    showErrorDialog(getString(R.string.okhttp_error_title), getString(R.string.okhttp_error_message));

                }

            }
        });

    }

    private void connect() {
        WeatherLocationManager.setLocationUpdater(this);

        if (!WeatherLocationManager.isLocationServicesConnected()) {
            WeatherLocationManager.buildGoogleApiForGpsOrPlaces(this, false);
            WeatherLocationManager.connect(this);
        } else {
            WeatherLocationManager.getLastLocation(this);
        }


    }

    /**
     * Method to Register for data updates
     */
    public void addListener(HomeActivityListener listener) {
        if (mHomeActivityListener != null && mHomeActivityListener.size() >= 0 &&
                !mHomeActivityListener.contains(listener)) {
            mHomeActivityListener.add(listener);
            Log.e(TAG, "Listener added " + listener.toString());
        }
    }

    /**
     * Method to un-register for data updates
     */
    public void removeListener(HomeActivityListener listener) {
        if (mHomeActivityListener != null && mHomeActivityListener.size() > 0 &&
                mHomeActivityListener.contains(listener)) {
            mHomeActivityListener.remove(listener);
            Log.e(TAG, "Listener removed " + listener.toString());

        }
    }

    @Override
    protected int getIdRootFragmentContainer() {
        return R.id.container;
    }

    @Override
    public void onLocationRetrieved(final Location location) {
        fetchWeatherData(location);
    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void onPermissionGranted(String permission) {
        connect();
    }

    @Override
    public void onPermissionDenied(String permission, boolean permanentDenial) {
        Logger.i(TAG, "Permission denied");
        //TODO - fetch last known location if it exists from DB or SharedPrefs.
    }

    @Override
    public void onRationaleRequested(String permission) {
        Log.e(TAG, "Permission rationale shown");
    }
}
