package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neek.tech.permissions.runtime_permission.PermissionConstants;
import com.neek.tech.permissions.runtime_permission.PermissionUtils;
import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.ui.activities.RuntimePermissionActivity;
import com.neek.tech.weatherapp.weatherama.utilities.WeatheramaPreferences;

/**
 * User friendly Fragment explaining runtime permission request for location
 */
public class RuntimeLocationPermissionFragment extends BaseFragment implements
        PermissionUtils.PermissionListener {

    private static final String TAG = RuntimeLocationPermissionFragment.class.getSimpleName();

    private void requestPermission() {
        if (!PermissionUtils.hasPermission(getActivity(), PermissionConstants.LOCATION_PERMISSION)) {
            Log.e(TAG, "Requesting permission");
            PermissionUtils.with(RuntimeLocationPermissionFragment.this).requestPermission(PermissionConstants.LOCATION_PERMISSION);
        }
    }


    public static RuntimeLocationPermissionFragment newInstance() {
        Bundle bundle = new Bundle();
        RuntimeLocationPermissionFragment fragment = new RuntimeLocationPermissionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "create");
        WeatheramaPreferences.setUserLocationRationaleShown(getActivity());

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button acceptButton = (Button) view.findViewById(R.id.acceptButton);
        Button declineButton = (Button) view.findViewById(R.id.declineButton);
        final TextView acceptMessageTextView = (TextView) view.findViewById(R.id.acceptMessageTextView);
        final LinearLayout buttonLayout = (LinearLayout) view.findViewById(R.id.buttonLayout);
        final LinearLayout messageLayout = (LinearLayout) view.findViewById(R.id.messageLayout);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons(buttonLayout);
                hideButtons(messageLayout);
                WeatheramaPreferences.setUserAcceptedLocationRationale(getActivity());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        acceptMessageTextView.setVisibility(View.VISIBLE);
                        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                        acceptMessageTextView.startAnimation(fadeIn);
                        acceptMessageTextView.requestFocus();

                    }
                });


                requestPermission();

            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatheramaPreferences.setUserDeniedLocationRationale(getActivity());
                getActivity().finish();

            }
        });

    }

    @Override
    protected int onCreateViewId() {
        return R.layout.fragment_locations_permission;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    private void dismissFragment() {
        if (getActivity() != null) {
            if (getActivity() instanceof RuntimePermissionActivity) {
                getActivity().finish();
            } else {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }

    private void hideButtons(final ViewGroup viewGroup) {
        if (viewGroup != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewGroup.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onPermissionGranted(String permission) {
        dismissFragment();
    }


    @Override
    public void onPermissionDenied(String permission, boolean permanentDenial) {
        dismissFragment();
    }

    @Override
    public void onRationaleRequested(String permission) {
        Log.e(TAG, "onRationaleRequested");
        dismissFragment();
    }
}
