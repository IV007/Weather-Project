package com.neek.tech.weatherapp.weatherama.base;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.ui.activities.RuntimePermissionActivity;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatherErrorDialog;
import com.neek.tech.weatherapp.weatherama.utilities.WeatheramaPreferences;

/**
 * Super class for all Activity's
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private static final String TAG = "BaseActivity";

    private View progressIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();

        initializeProgressIndicator();
    }


    /**
     * Initialize progress indicator
     */
    @SuppressLint("InflateParams")
    private void initializeProgressIndicator() {

        int visibility = View.GONE;

        if (progressIndicator != null) {
            visibility = progressIndicator.getVisibility();
            progressIndicator.setVisibility(View.GONE);
        }

        LayoutInflater from = LayoutInflater.from(this);
        progressIndicator = from.inflate(R.layout.view_progress_indicator, null);
        progressIndicator.setVisibility(visibility);
        addContentView(progressIndicator, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


    }

    @Override
    public void onError(String title, String errorMessage) {
        hideProgressDialog();
        WeatherErrorDialog dialog = WeatherErrorDialog.newInstance(title, errorMessage);
        dialog.show(getFragmentManager(), WeatherErrorDialog.TAG);
    }

    @Override
    public void showProgressDialog() {

        if (progressIndicator != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressIndicator.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressIndicator != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressIndicator.setVisibility(View.GONE);
                }
            });
        }
    }

    public void showErrorDialog(String title, String message){
        WeatherErrorDialog dialog = WeatherErrorDialog.newInstance(title, message);
        dialog.show(getFragmentManager(), WeatherErrorDialog.TAG);
    }

    /**
     * Navigates to a fragment adding to back stack
     */
    public void navigateToFragment(BaseFragment baseFragment) {

        if (baseFragment != null) {
            addFragmentToStack(baseFragment, baseFragment.getFragmentTag(), true);
        }

    }

    /**
     * Fragment to stack addition
     */
    protected void addFragmentToStack(BaseFragment fragment, String tag, boolean addToBackStack) {
        addFragmentToStackWithAnimations(fragment, tag, addToBackStack,
                R.anim.card_slide_right_in, R.anim.card_slide_left_out,
                R.anim.card_slide_left_in, R.anim.card_slide_right_out);
    }

    /**
     * Removes ALL
     */
    public void popAllBackStack() {

        try {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragmentManager.getBackStackEntryCount() > 0) {
                String name = fragmentManager.getBackStackEntryAt(0).getName();
                fragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            fragmentManager.executePendingTransactions();

            invalidateOptionsMenu();

        } catch (Exception exception) {
            Logger.logException(TAG, exception);
        }

    }

    /**
     * Pops backstack
     */
    public void popBackStack() {

        try {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
            }

            fragmentManager.executePendingTransactions();

            invalidateOptionsMenu();

        } catch (Exception exception) {
            Logger.logException(TAG, exception);
        }

    }


    /**
     * Fragment to stack addition
     */
    protected void addFragmentToStackWithAnimations(BaseFragment fragment, String tag, boolean addToBackStack,
                                                    int anim1, int anim2, int anim3, int anim4) {
        try {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();

            if (addToBackStack) {
                trans.addToBackStack(tag);
                trans.setCustomAnimations(anim1, anim2, anim3, anim4);
            }

//            currentRoot = fragment;

            trans.replace(getIdRootFragmentContainer(), fragment, tag);
            trans.commitAllowingStateLoss();

            fragmentManager.executePendingTransactions();

            invalidateOptionsMenu();

        } catch (Exception exception) {
            Logger.logException(TAG, exception);
        }

    }

    /**
     * Returns an int of the container to attach fragments
     */
    protected abstract int getIdRootFragmentContainer();


    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     * */
    public void showGpsDisabledDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS disabled");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to enable GPS from settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean showRuntimePermissionFragment(String permissionType){
        if (getApplicationContext() == null)
            return false;

        boolean result = false;
        if (!WeatheramaPreferences.isUserLocationRationaleShown(this)) {
            Log.i(TAG, "Launching Runtime permission activity for type " + permissionType);
            Intent intent = new Intent(this, RuntimePermissionActivity.class);
            intent.putExtra(RuntimePermissionActivity.TAG, permissionType);
            startActivity(intent);
            result = true;
        }
        return result;
    }


}
