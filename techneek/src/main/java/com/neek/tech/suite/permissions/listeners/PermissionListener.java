package com.neek.tech.suite.permissions.listeners;

import com.neek.tech.suite.permissions.runtime_permission.RationaleRequest;

/**
 * @author <a href="mailto:ivan.utsalo@accesscfa.com">Ivan Utsalo</a>
 */
public interface PermissionListener {

    void onPermissionGranted(PermissionGranted response);

    void onPermissionDenied(PermissionDenied response);

    void onPermissionRationaleShouldBeShown(PermissionRequest permission, RationaleRequest token);
}
