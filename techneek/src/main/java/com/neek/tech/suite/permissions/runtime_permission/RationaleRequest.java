package com.neek.tech.suite.permissions.runtime_permission;

/**
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public class RationaleRequest {

    private final PermissionsInstance instance;
    private boolean isTokenResolved = false;

    public RationaleRequest(PermissionsInstance instance) {
        this.instance = instance;
    }

    public void continuePermissionRequest() {
        if (!isTokenResolved) {
            instance.onContinuePermissionRequest();
            isTokenResolved = true;
        }
    }

    public void cancelPermissionRequest(){
        if (!isTokenResolved){
            instance.onCancelPermissionRequest();
            isTokenResolved = true;
        }
    }
}
