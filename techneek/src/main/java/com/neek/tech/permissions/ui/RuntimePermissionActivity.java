package com.neek.tech.permissions.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.neek.tech.permissions.runtime_permission.PermissionConstants;
import com.neek.tech.techneekpermissionsuite.R;

/**
 * Generic container activity for
 * hosting runtime permission rationale fragments.
 */
public class RuntimePermissionActivity extends FragmentActivity {

    public static final String TAG = "ermissionActivity";

    /**
     * Permission type used by activity to dynamically
     * choose which runtime permission fragment to show
     * (default == Location).
     */
    private PermissionType mPermissionType = PermissionType.LOCATION;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_up_from_bottom_with_accelerate_decelerate, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(0);
        Log.d(TAG, "created");


        if (getIntent() != null){
            final String permissionTypeStr = getIntent().getStringExtra(TAG);
            setPermissionType(permissionTypeStr);
        }

        setCurrentRuntimePermissionFragment();
    }


    private void setCurrentRuntimePermissionFragment() {
        switch (mPermissionType){
            case LOCATION:
                RuntimeLocationPermissionFragment fragment = RuntimeLocationPermissionFragment.newInstance();
                navigateToFragment(fragment, RuntimeLocationPermissionFragment.TAG);
                break;

            case STORAGE:
                //Show runtime permission fragment for storage
                break;

            case READ_PHONE_STATE:
                //Show runtime permission fragment for READ_PHONE_STATE
                break;
        }

    }

    public void navigateToFragment(Fragment fragment, String tag) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction trans = fragmentManager.beginTransaction();
//        trans.add(R.id.runtimeContainer, fragment, tag);
//        trans.commit();
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
