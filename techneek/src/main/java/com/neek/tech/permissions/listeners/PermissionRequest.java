package com.neek.tech.permissions.listeners;

import android.support.annotation.NonNull;

/**
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public class PermissionRequest {

    private final String name;

    public PermissionRequest(@NonNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PermissionRequest{" + "name='" + name + '}';
    }
}
