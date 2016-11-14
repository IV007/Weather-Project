package com.neek.tech.weatherapp.weatherama.utilities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by ivanutsalo on 11/7/16.
 */

public class WeatherErrorDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG = WeatherErrorDialog.class.getSimpleName();
    private static final String TITLE = "TITLE";
    private static final String MESSAGE = "MESSAGE";

    private String mTitle, mMessage, mPositiveBtnText, mNegativeBtnText;


    public static WeatherErrorDialog newInstance(final String title, final String message){
        Bundle b = new Bundle();
        WeatherErrorDialog frag = new WeatherErrorDialog();
        b.putString(TITLE, title);
        b.putString(MESSAGE, message);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.create(TAG);

        if (getArguments() != null){
            if (getArguments().containsKey(TITLE)){
                mTitle = getArguments().getString(TITLE);
            }

            if (getArguments().containsKey(MESSAGE)){
                mMessage = getArguments().getString(MESSAGE);
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (TextUtils.isEmpty(mPositiveBtnText)){
            mPositiveBtnText = "OK";
        }

        if (TextUtils.isEmpty(mNegativeBtnText)){
            mNegativeBtnText = null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle).setMessage(mMessage).setPositiveButton(mPositiveBtnText, null)
                .setNegativeButton(mNegativeBtnText, null);
        builder.setCancelable(true);


        return builder.create();
    }

    @Override
    public void onClick(View view) {

    }
}
