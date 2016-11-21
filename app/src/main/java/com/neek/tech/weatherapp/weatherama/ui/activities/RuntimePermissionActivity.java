package com.neek.tech.weatherapp.weatherama.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.neek.tech.suite.permissions.runtime_permission.PermissionConstants;
import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseActivity;
import com.neek.tech.weatherapp.weatherama.ui.fragments.RuntimeLocationPermissionFragment;

/**
 * Generic container activity for
 * hosting runtime permission rationale fragments.
 */
public class RuntimePermissionActivity extends BaseActivity {

    public static final String TAG = "WPermissionActivity";

    /**
     * Permission type used by activity to dynamically
     * choose which runtime permission fragment to show
     * (default == Location).
     */
    private PermissionType mPermissionType = PermissionType.LOCATION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_up_from_bottom_with_accelerate_decelerate, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permissions);
        Log.e(TAG, "Created");

        if (getIntent() != null){
            final String permissionTypeStr = getIntent().getStringExtra(TAG);
            setPermissionType(permissionTypeStr);
        }

        setCurrentRuntimePermissionFragment();
    }

    @Override
    protected int getIdRootFragmentContainer() {
        return R.id.container;
    }


    private void setCurrentRuntimePermissionFragment() {
        switch (mPermissionType){
            case LOCATION:
                RuntimeLocationPermissionFragment fragment = RuntimeLocationPermissionFragment.newInstance();
                navigateToFragment(fragment);
                break;

            case STORAGE:
                //Show runtime permission fragment for storage
                break;

            case READ_PHONE_STATE:
                //Show runtime permission fragment for READ_PHONE_STATE
                break;
        }

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private void setPermissionType(String permissionType){
        if (!TextUtils.isEmpty(permissionType)){
            if (permissionType.equalsIgnoreCase(PermissionConstants.STORAGE)){
                mPermissionType = PermissionType.STORAGE;
            } else if (permissionType.equalsIgnoreCase(PermissionConstants.READ_PHONE_STATE)){
                mPermissionType = PermissionType.READ_PHONE_STATE;
            }
        }
    }

    public enum PermissionType{
        LOCATION,
        STORAGE, //TODO - split Storage into Read and write
        READ_PHONE_STATE
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_down_to_bottom_with_acelerate_decelerate);
    }
}
