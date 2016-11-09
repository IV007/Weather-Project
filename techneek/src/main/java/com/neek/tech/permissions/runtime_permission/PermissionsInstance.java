package com.neek.tech.permissions.runtime_permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.neek.tech.permissions.listeners.PermissionDenied;
import com.neek.tech.permissions.listeners.PermissionGranted;
import com.neek.tech.permissions.listeners.PermissionListener;
import com.neek.tech.permissions.listeners.PermissionRequest;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public final class PermissionsInstance {

    /**
     * Constants
     */
    private static final String TAG = PermissionsInstance.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_CODE = 2175379;

    /**
     * TechNeekPermissions listener
     */
    private PermissionListener listener;

    private final Context context;
    private final PermissionsWrapper androidPermissionService;
    private String permissionToBeChecked;
    private final AtomicBoolean rationaleAccepted;
    private final Object pendingPermissionsMutex = new Object();

    private Activity activity;

    public PermissionsInstance(Context context,
                               PermissionsWrapper androidPermissionService) {
        this.context = context;
        this.androidPermissionService = androidPermissionService;
        this.rationaleAccepted = new AtomicBoolean();
    }

    /**
     * Checks the state of a specific permission reporting it when ready to the listener.
     */
    void checkPermission(PermissionListener listener, String permission) {
        if (permission == null || permission.isEmpty()) {
            throw new IllegalStateException("No permission was passed. I'm in " + TAG);
        }

        permissionToBeChecked = permission;
        this.listener = listener;
        startPermissionsActivity();
    }

    private void startPermissionsActivity() {
        Intent intent = new Intent(context, PermissionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Method called whenever the inner activity has been created or restarted and is ready to be
     * used.
     */
    void onActivityReady(Activity activity) {
        this.activity = activity;

        PermissionState permissionState = PermissionState.UNKNOWN;
        synchronized (pendingPermissionsMutex) {
            if (activity != null) {
                permissionState = getPermissionState(permissionToBeChecked);
            }
        }

        switch (permissionState) {
            case GRANTED:
            default:
                updatePermissionsAsGranted(permissionToBeChecked);
                break;
            case DENIED:
                handleDeniedPermissions(permissionToBeChecked);
                break;
        }
    }

    private PermissionState getPermissionState(String permission) {
        PermissionState result = PermissionState.DENIED;

        if (!TextUtils.isEmpty(permission)) {

            int permissionState = androidPermissionService.checkSelfPermission(activity, permission);
            switch (permissionState) {
                case PackageManager.PERMISSION_DENIED:
                    result = PermissionState.DENIED;
                    break;
                case PackageManager.PERMISSION_GRANTED:
                default:
                    result = PermissionState.GRANTED;
                    break;

            }
        }

        return result;
    }

    /**
     * Method called whenever the permissions has been granted by the user
     */
    void onPermissionRequestGranted(String permission) {
        updatePermissionsAsGranted(permission);
    }

    /**
     * Method called whenever the permissions has been denied by the user
     */
    void onPermissionRequestDenied(String permission) {
        updatePermissionsAsDenied(permission);
    }

    /**
     * Method called when the user has been informed with a rationale and agrees to continue
     * with the permission request process
     */
    void onContinuePermissionRequest() {
        rationaleAccepted.set(true);
        requestPermissionToSystem(permissionToBeChecked);
    }

    /**
     * Method called when the user has been informed with a rationale and decides to cancel
     * the permission request process
     */
    void onCancelPermissionRequest() {
        rationaleAccepted.set(false);
        updatePermissionsAsDenied(permissionToBeChecked);
    }

    /**
     * Starts the native request permissions process
     */
    void requestPermissionToSystem(String permission) {
        androidPermissionService.requestPermissions(activity, permission, PERMISSIONS_REQUEST_CODE);
    }


    private void handleDeniedPermissions(String permission) {
        PermissionRequest shouldShowRequestRationale = null;

        if (permission != null && !permission.isEmpty()) {
            if (androidPermissionService.shouldShowRequestPermissionRationale(activity, permission)) {
                shouldShowRequestRationale = new PermissionRequest(permission);
            }

            if (shouldShowRequestRationale == null) {
                requestPermissionToSystem(permission);
            } else if (!rationaleAccepted.get()) {
                RationaleRequest permissionToken = new RationaleRequest(this);
                listener.onPermissionRationaleShouldBeShown(shouldShowRequestRationale, permissionToken);
            }
        }
    }

    private void updatePermissionsAsGranted(String permission) {
        PermissionGranted response = PermissionGranted.from(permission);
        rationaleAccepted.set(false);
        activity.finish();

        if(listener != null) {
            listener.onPermissionGranted(response);
        }
    }

    private void updatePermissionsAsDenied(String permission) {
        PermissionDenied response = PermissionDenied.from(permission, !androidPermissionService.shouldShowRequestPermissionRationale(activity, permission));
        rationaleAccepted.set(false);
        activity.finish();

        if(listener != null) {
            listener.onPermissionDenied(response);
        }
    }

    private enum PermissionState {
        UNKNOWN,
        GRANTED,
        DENIED
    }
}
