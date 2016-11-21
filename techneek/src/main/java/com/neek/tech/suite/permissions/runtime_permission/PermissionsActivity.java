package com.neek.tech.suite.permissions.runtime_permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.WindowManager;

/**
 * Activity to handle previous or new permission request
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public class PermissionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PermissionsActivity", "onCreate()");

        TechNeekPermissions.onActivityReady(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        TechNeekPermissions.onActivityReady(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String grantedPermissions = null;
        String deniedPermissions = null;

        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedPermissions = permission;
            } else {
                grantedPermissions = permission;
            }
        }

        TechNeekPermissions.onPermissionsRequested(grantedPermissions, deniedPermissions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("PermissionsActivity", "Destroyed");

    }
}
