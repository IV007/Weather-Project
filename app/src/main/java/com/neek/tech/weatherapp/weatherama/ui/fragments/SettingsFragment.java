package com.neek.tech.weatherapp.weatherama.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.neek.tech.permissions.runtime_permission.PermissionConstants;
import com.neek.tech.permissions.runtime_permission.PermissionUtils;
import com.neek.tech.weatherapp.R;
import com.neek.tech.weatherapp.weatherama.WeatherLocationManager;
import com.neek.tech.weatherapp.weatherama.base.BaseActivity;
import com.neek.tech.weatherapp.weatherama.base.BaseFragment;
import com.neek.tech.weatherapp.weatherama.controllers.WeatherController;
import com.neek.tech.weatherapp.weatherama.model.weather.ReverseGeocodeAddress;
import com.neek.tech.weatherapp.weatherama.utilities.Logger;
import com.neek.tech.weatherapp.weatherama.utilities.WeatheramaDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Settings fragment
 */
public class SettingsFragment extends BaseFragment implements
        PermissionUtils.PermissionListener,
        WeatherLocationManager.LocationUpdater {

    private static final String TAG = SettingsFragment.class.getSimpleName();
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;

    @BindView(R.id.enableLocationButton)
    protected Button mEnableLocationButton;

    @BindView(R.id.listView)
    protected ListView mListView;

    @BindView(R.id.useMyLocationButton)
    protected TextView mUseMyLocationButton;

    @BindView(R.id.enterAddressTextView)
    protected TextView mEnterAddressButton;

    private List<String> mAddressList;

    private SettingsAdapter mAdapter;

    private String mSelectedAddress;

    private SparseBooleanArray mCheckedItems;

    private boolean mGoogleAPIClientFailed = false;


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);
        refreshAddressList();
    }

    private void refreshAddressList() {
        mAddressList = WeatherController.getSavedUserLocations(getActivity());
    }

    @Override
    protected int onCreateViewId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLocationButtonState();

        initializeAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();

        connect();
        getSelectedAddress();
        Logger.i(TAG, "Selected address " + mSelectedAddress);
        setLocationButtonState();
        initializeAdapter();

    }

    private void getSelectedAddress() {
        mSelectedAddress = WeatherController.getSelectedAddress(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (WeatherLocationManager.isLocationServicesConnected()) {
            WeatherLocationManager.disconnect();
        }

    }

    @OnClick(value = R.id.enableLocationButton)
    public void onEnableLocationClicked() {
        PermissionUtils.with(this).requestPermission(PermissionConstants.LOCATION_PERMISSION);
    }

    @OnClick(value = R.id.enterAddressTextView)
    public void onEnterAddressClicked() {
        launchAutoCompletePicker();
    }

    @OnClick(value = R.id.useMyLocationButton)
    public void onUseMyLocationClicked() {
        launchPlacesPicker();
    }

    private void connect() {
        if (!WeatherLocationManager.isLocationServicesConnected()) {
            WeatherLocationManager.setLocationUpdater(this);
            WeatherLocationManager.buildGoogleApiForGpsOrPlaces(getActivity(), true);
        }
    }

    private void launchPlacesPicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void launchAutoCompletePicker() {
        try {
            AutocompleteFilter filter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(filter)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                if (place != null) {
                    String address = (String) place.getAddress();
                    String name = (String) place.getName();
                    LatLng latLng = place.getLatLng();
                    double lat = latLng.latitude;
                    double lon = latLng.longitude;

                    buildAndUpdateAddressList(address, name, lat, lon);

                }
            }
        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                if (place != null) {
                    String address = (String) place.getAddress();
                    String name = (String) place.getName();
                    LatLng latLng = place.getLatLng();
                    double lat = latLng.latitude;
                    double lon = latLng.longitude;

                    buildAndUpdateAddressList(address, name, lat, lon);

                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Logger.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void buildAndUpdateAddressList(String address, String name, double lat, double lon) {
        ReverseGeocodeAddress userAddress;
        userAddress = new ReverseGeocodeAddress();
        userAddress.setKey(address);
        userAddress.setPlaceName(name);
        userAddress.setLatitude(lat);
        userAddress.setLongitude(lon);

        if(WeatherController.saveAddressToPrefs(getActivity(), null, null, userAddress)){
            refreshAddressList();
            refreshAdapter(true);

            mEnterAddressButton.setText(address);
            mEnterAddressButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        }

        Logger.i(TAG, "Name " + name);
        Logger.i(TAG, "Address " + address);
        Logger.i(TAG, "Latitude " + lat);
        Logger.i(TAG, "Longitude " + lon);
    }

    private void setLocationButtonState() {
        if (!PermissionUtils.isAndroidOSMarshmallowOrAbove()) {
            hideEnableLocationButton();
        } else {
            if (!PermissionUtils.hasPermission(getActivity(), PermissionConstants.LOCATION_PERMISSION)) {
                showEnableLocationButton();
            } else {
                hideEnableLocationButton();

            }
        }
    }

    private void showEnableLocationButton() {
        if (mEnableLocationButton != null && mEnableLocationButton.getVisibility() == View.GONE) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            mEnableLocationButton.setVisibility(View.VISIBLE);
            mEnableLocationButton.startAnimation(fadeIn);

        }
    }

    private void hideEnableLocationButton() {
        if (mEnableLocationButton != null && mEnableLocationButton.getVisibility() == View.VISIBLE) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            mEnableLocationButton.startAnimation(fadeOut);
            mEnableLocationButton.setVisibility(View.GONE);
        }
    }

    private void initializeAdapter() {
        if (mAdapter == null) {
            mAdapter = new SettingsAdapter(getActivity(), mAddressList);
        }

        mCheckedItems.clear();
        mListView.setAdapter(mAdapter);
    }

    private void refreshAdapter(boolean refreshData) {
        getSelectedAddress();


        if (mAdapter != null) {
            if (refreshData){
                mAdapter.setNewAddress(mAddressList);
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onPermissionGranted(String permission) {
        setLocationButtonState();
    }

    @Override
    public void onPermissionDenied(String permission, boolean permanentDenial) {
        setLocationButtonState();
    }

    @Override
    public void onRationaleRequested(String permission) {
        Log.i(TAG, "Rationale requested for permission = " + permission);
        PermissionUtils.with(this).continueRequest();
    }

    @Override
    public void onLocationRetrieved(Location location) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideProgressDialog();
        }

        if (mGoogleAPIClientFailed) {
            mGoogleAPIClientFailed = false;
            connect();
        }
    }

    @Override
    public void onConnectionError() {
        mGoogleAPIClientFailed = true;
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideProgressDialog();
        }
    }

    class SettingsAdapter extends BaseAdapter {

        private List<String> addresses;
        private Context mContext;
        private boolean status = true;


        SettingsAdapter(Context context, List<String> addresses) {
            this.mContext = context;
            this.addresses = addresses;
            mCheckedItems = new SparseBooleanArray();
        }

        public void setNewAddress(List<String> newAddress){
            if (newAddress != addresses){
                this.addresses = newAddress;
            }
        }

        @Override
        public int getCount() {
            return (addresses != null) ? addresses.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return addresses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final SettingsItemViewHolder vh;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_settings, parent, false);
                vh = new SettingsItemViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (SettingsItemViewHolder) convertView.getTag();
            }

            if (addresses != null && addresses.size() > 0) {
                final String address = addresses.get(position);
                if (!TextUtils.isEmpty(address) && vh.addressLabel != null) {
                    vh.addressLabel.setText(address);
                }


                if (vh.addressCheckbox != null) {

                    vh.addressCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (!status) {
                                status = true;
                                return;

                            }

                            if (isChecked) {
                                showUseAddressConfirmationDialog(mContext, address, position);

                            } else {
                                showRemoveAddressConfirmationDialog(mContext, position);
                            }
                        }
                    });

                    if (!TextUtils.isEmpty(mSelectedAddress) &&
                            mSelectedAddress.equalsIgnoreCase(address)) {
                        setCheckBoxState(vh, true);
                    } else {
                        setCheckBoxState(vh, false);
                    }
                }
            }

            return convertView;
        }

        public void setCheckBoxState(SettingsItemViewHolder vh, boolean isChecked) {
            status = vh.addressCheckbox.isChecked() == isChecked;
            vh.addressCheckbox.setChecked(isChecked);
        }

        class SettingsItemViewHolder {

            @BindView(R.id.addressLabel)
            TextView addressLabel;

            @BindView(R.id.addressCheckbox)
            CheckBox addressCheckbox;

            SettingsItemViewHolder(View v) {
                ButterKnife.bind(this, v);
            }
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    private void showUseAddressConfirmationDialog(final Context context, final String address, final int position) {
        WeatheramaDialog dialog = WeatheramaDialog.newInstance(null, getString(R.string.set_address_message),
                getString(R.string.yes), getString(R.string.no),
                new WeatheramaDialog.DialogClickedButton() {
                    @Override
                    public void onClickedButton(WeatheramaDialog dialog, int id, View v) {
                        if (id == WeatheramaDialog.POSITIVE_BUTTON) {

                            WeatherController.setSelectedAddress(context, address);

                            updateBooleanArray(position, true);

                        } else if (id == WeatheramaDialog.NEGATIVE_BUTTON) {
                            //Do nothing
                            updateBooleanArray(position, false);
                        }
                        refreshAdapter(false);

                    }
                });
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), WeatheramaDialog.TAG);
    }

    private void showLocationServicesDisabledDialog() {
//        WeatheramaDialog dialog = WeatheramaDialog.newInstance(getString(R.string.location_permission_required_title), )
    }

    private void showRemoveAddressConfirmationDialog(final Context context, final int position) {
        WeatheramaDialog dialog = WeatheramaDialog.newInstance(null, getString(R.string.remove_address_message),
                getString(R.string.remove), getString(R.string.cancel),
                new WeatheramaDialog.DialogClickedButton() {
                    @Override
                    public void onClickedButton(WeatheramaDialog dialog, int id, View v) {
                        if (id == WeatheramaDialog.POSITIVE_BUTTON) {

                            WeatherController.setSelectedAddress(context, null);

                            updateBooleanArray(position, false);
                            if (mAdapter != null) {
                                mAdapter.setStatus(false);
                            }

                        } else if (id == WeatheramaDialog.NEGATIVE_BUTTON) {
                            //Do nothing
                            updateBooleanArray(position, true);
                        }
                        refreshAdapter(false);


                    }
                });
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), WeatheramaDialog.TAG);
    }

    /**
     * Method to map address checked state to list view position.
     */
    private void updateBooleanArray(int position, boolean enable) {
        if (mCheckedItems != null) {
            Logger.i(TAG, "Position - " + position + ", enable - " + enable);
            if (mCheckedItems.size() == 0) {
                //mCheckedItems is empty
                mCheckedItems.put(position, enable);
            } else if (mCheckedItems.size() > 0) {
                //mCheckedItems has items, loop through
                for (int i = 0; i < mCheckedItems.size(); i++) {
                    //reset all values currently in mCheckedItems
                    if (mCheckedItems.keyAt(i) != position) {
                        mCheckedItems.put(i, false);
                    }
                }
                //finally put the position in mCheckedItems
                mCheckedItems.put(position, enable);

            }
        }
    }
}
