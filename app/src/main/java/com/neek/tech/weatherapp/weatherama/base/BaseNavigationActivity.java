package com.neek.tech.weatherapp.weatherama.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.neek.tech.weatherapp.weatherama.utilities.Logger;

/**
 * Navigation class for activities with NavDrawer.
 */
public abstract class BaseNavigationActivity extends BaseActivity {

    private static final String TAG = BaseNavigationActivity.class.getSimpleName();

    @Override
    protected int getIdRootFragmentContainer() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Logger.create(TAG);


    }
}
