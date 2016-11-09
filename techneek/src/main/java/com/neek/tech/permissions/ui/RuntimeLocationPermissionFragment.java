package com.neek.tech.permissions.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neek.tech.permissions.runtime_permission.PermissionConstants;
import com.neek.tech.permissions.runtime_permission.PermissionUtils;

/**
 * User friendly Fragment explaining runtime permission request for location
 */
public class RuntimeLocationPermissionFragment extends Fragment implements
        PermissionUtils.PermissionListener {

    public static final String TAG = RuntimeLocationPermissionFragment.class.getSimpleName();

    private void requestPermission() {
        if (!PermissionUtils.hasPermission(PermissionConstants.LOCATION_PERMISSION)) {
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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_location_permission_explanation, container, false);
//        Button acceptButton = (Button) view.findViewById(R.id.acceptButton);
//        Button declineButton = (Button) view.findViewById(R.id.declineButton);
//        final TextView acceptMessageTextView = (TextView) view.findViewById(R.id.acceptMessageTextView);
//        final LinearLayout buttonLayout = (LinearLayout) view.findViewById(R.id.buttonLayout);
//
//        acceptButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hideButtons(buttonLayout);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        acceptMessageTextView.setVisibility(View.VISIBLE);
//                        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
//                        acceptMessageTextView.startAnimation(fadeIn);
//                        acceptMessageTextView.requestFocus();
//
//                    }
//                });
//
//                requestPermission();
//
//            }
//        });
//
//        declineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().finish();
//            }
//        });
//        return view;

        return super.onCreateView(inflater,container, savedInstanceState);
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
