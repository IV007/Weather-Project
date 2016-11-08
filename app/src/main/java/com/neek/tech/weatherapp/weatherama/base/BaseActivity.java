package com.neek.tech.weatherapp.weatherama.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatherErrorDialog;

/**
 * Super class for all Activity's
 */
public abstract class BaseActivity extends FragmentActivity implements BaseView {

    private static final String TAG = "BaseActivity";

    private static final String ERROR_DIALOG_TAG = "ERROR_DIALOG";

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
        WeatherErrorDialog dialog = WeatherErrorDialog.newInstance(title, errorMessage);
        dialog.show(getFragmentManager(), ERROR_DIALOG_TAG);
    }

    @Override
    public void showProgressDialog() {

        if (progressIndicator != null){
            progressIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressIndicator != null){
            progressIndicator.setVisibility(View.GONE);
        }
    }

    public void showErrorDialog(String title, String message){
        WeatherErrorDialog dialog = WeatherErrorDialog.newInstance(title, message);
        dialog.show(getFragmentManager(), ERROR_DIALOG_TAG);
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

}
