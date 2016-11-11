package com.neek.tech.permissions.listeners;

import android.support.annotation.NonNull;

/**
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public class PermissionDenied {

    private final PermissionRequest requestedPermission;
    private final boolean permanentlyDenied;

    public PermissionDenied(@NonNull PermissionRequest requestedPermission,
                            boolean permanentlyDenied) {
        this.requestedPermission = requestedPermission;
        this.permanentlyDenied = permanentlyDenied;
    }

    public static PermissionDenied from(@NonNull String permission,
                                        boolean permanentlyDenied) {
        return new PermissionDenied(new PermissionRequest(permission), permanentlyDenied);
    }

    public PermissionRequest getRequestedPermission() {
        return requestedPermission;
    }

    public String getPermissionName() {
        return requestedPermission.getName();
    }

    public boolean isPermanentlyDenied() {
        return permanentlyDenied;
    }

}
