package com.neek.tech.weatherapp.weatherama.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseActivity;
import com.neek.tech.weatherapp.weatherama.ui.fragments.SettingsFragment;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;

/**
 * Created by ivanutsalo on 11/11/16.
 */
public class SettingsActivity extends BaseActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.card_slide_left_in, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

        setContentView(R.layout.activity_settings);

        navigateToFragment(SettingsFragment.newInstance());
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.settings_menu);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getIdRootFragmentContainer() {
        return R.id.container;
    }
}
