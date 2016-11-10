package com.neek.tech.permissions.runtime_permission;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.neek.tech.permissions.listeners.PermissionListener;


/**
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public class TechNeekPermissions {

    private static PermissionsInstance instance;

    public static void initialize(Context context) {
        if (instance == null) {
            Log.i("TechNeekPermissions", "initializing TechNeekPermissionSuite");
            PermissionsWrapper androidPermissionService = new PermissionsWrapper();
            instance = new PermissionsInstance(context, androidPermissionService);
        }
    }

    public static void checkPermission(PermissionListener listener, String permission) {
        checkInstanceNotNull();
        instance.checkPermission(listener, permission);
    }


    private static void checkInstanceNotNull() {
        if (instance == null) {
            throw new NullPointerException("context == null.Must call initialize");
        }
    }

    static void onActivityReady(Activity activity) {
        instance.onActivityReady(activity);
    }

    static void onPermissionsRequested(String grantedPermissions, String deniedPermissions) {

        if (grantedPermissions != null) {
            instance.onPermissionRequestGranted(grantedPermissions);
        }

        if (deniedPermissions != null) {
            instance.onPermissionRequestDenied(deniedPermissions);
        }
    }
}
