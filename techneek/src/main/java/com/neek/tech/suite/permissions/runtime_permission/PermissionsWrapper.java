package com.neek.tech.suite.permissions.runtime_permission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Wraps the permission system
 *
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public class PermissionsWrapper {

    /**
     * @see ContextCompat#checkSelfPermission
     */
    int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * @see ActivityCompat#requestPermissions
     */
    void requestPermissions(@NonNull Activity activity, @NonNull String permission,
                            int requestCode) {
        String[] permissions = new String[]{permission};
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     * @see ActivityCompat#shouldShowRequestPermissionRationale
     */
    boolean shouldShowRequestPermissionRationale(@NonNull Activity activity,
                                                 @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }
}
