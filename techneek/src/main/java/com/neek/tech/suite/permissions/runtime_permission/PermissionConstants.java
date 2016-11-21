package com.neek.tech.suite.permissions.runtime_permission;

import android.Manifest;

/**
 * Created by ivanutsalo on 8/16/16.
 */
public class PermissionConstants {

    public static final int REQUEST_PERMISSION_SETTING = 1023;

    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String READ_PHONE_STATE_PERMISSION = Manifest.permission.READ_PHONE_STATE;


    public static final String LOCATION = "LOCATION_PERMISSION";
    public static final String STORAGE = "STORAGE_PERMISSION";
    public static final String READ_PHONE_STATE = "READ_PHONE_STATE_PERMISSION";
}
