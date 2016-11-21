package com.neek.tech.suite.permissions.runtime_permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.neek.tech.suite.permissions.listeners.PermissionDenied;
import com.neek.tech.suite.permissions.listeners.PermissionGranted;
import com.neek.tech.suite.permissions.listeners.PermissionListener;
import com.neek.tech.suite.permissions.listeners.PermissionRequest;

/**
 * Created by ivanutsalo on 11/8/16.
 */
public class PermissionUtils implements PermissionListener {

    private static final String TAG = "PermissionUtils";

    private static PermissionUtils singleton = null;

    /**
     * Permissions listener to comunicate the permission request results
     */
    private PermissionListener listener;

    /**
     * Pending token when a rationale was asked to be displayed
     */
    private RationaleRequest pendingPermissionRequest;

    //Methods *************************************

    private PermissionUtils() {
    } // Avoiding to instantiate objects

    private PermissionUtils(Context context) {
        TechNeekPermissions.initialize(context);
    }

    /**
     * Initializes the permissions system
     */
    public static void initialize(Context context) {
        if (singleton == null) {
            singleton = new PermissionUtils(context);
            Log.d(TAG, "Initialized");

        }
    }

    /**
     * Sets a listener to the singleton and returns it
     */
    public static PermissionUtils with(PermissionListener listener) {
        singleton.listener = listener;
        return singleton;
    }

    public static boolean hasPermission(Context context, String permission) {

        PackageManager manager = context.getPackageManager();
        int hasPermission = manager.checkPermission(permission, context.getPackageName());
        return (hasPermission == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Requests a permission
     */
    public void requestPermission(String permission) {
        if (singleton != null) {
            TechNeekPermissions.checkPermission(singleton, permission);
        }
    }

    /**
     * Continues the request process after a rationale was asked
     */
    public void continueRequest() {
        if (pendingPermissionRequest != null) {
            pendingPermissionRequest.continuePermissionRequest();
        }
    }

    /**
     * Cancels existing request process after rationale was shown
     * and user refuses to grant permission request.
     */
    public void cancelPermissionRequest() {
        if (pendingPermissionRequest != null) {
            pendingPermissionRequest.cancelPermissionRequest();
        }
    }

    @Override
    public void onPermissionGranted(PermissionGranted response) {
        listener.onPermissionGranted(response.getPermissionName());

    }

    @Override
    public void onPermissionDenied(PermissionDenied response) {
        listener.onPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, RationaleRequest token) {
        this.pendingPermissionRequest = token;
        listener.onRationaleRequested(permission.getName());
    }


    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static boolean isAndroidOSMarshmallowOrAbove() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static boolean isLollipopOrAbove() {
        return Build.VERSION.SDK_INT >= 21;
    }

    /**
     * Listener to communicate permissions to whoever asks
     */
    public interface PermissionListener {

        /**
         * When the permission is granted (or already has it)
         */
        void onPermissionGranted(String permission);

        /**
         * When the permission is denied
         */
        void onPermissionDenied(String permission, boolean permanentDenial);

        /**
         * When a rationale is requested.
         */
        void onRationaleRequested(String permission);

    }

}
