package com.neek.tech.suite.permissions.listeners;

import android.support.annotation.NonNull;

/**
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public class PermissionGranted {


    private final PermissionRequest requestedPermission;

    public PermissionGranted(@NonNull PermissionRequest requestedPermission) {
        this.requestedPermission = requestedPermission;
    }

    /**
     * Builds a new instance of PermissionGrantedResponse from a given permission string
     */
    public static PermissionGranted from(@NonNull String permission) {
        return new PermissionGranted(new PermissionRequest(permission));
    }

    public PermissionRequest getRequestedPermission() {
        return requestedPermission;
    }

    public String getPermissionName() {
        return requestedPermission.getName();
    }
}
