package com.neek.tech.weatherapp.weatherama.utilities;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neek.tech.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Generic Dialog fragment class for displaying
 * messages and handling user onClick events
 */
public class WeatheramaDialog extends DialogFragment implements View.OnClickListener {

    /**
     * Constants
     */
    public static final String TAG = WeatheramaDialog.class.getSimpleName();

    public static final int NEGATIVE_BUTTON = 1;

    public static final int POSITIVE_BUTTON = 2;

    private final static String PARAM_TITLE_TEXT = "com.neek.tech.weatherapp.weatherama.utilities.WeatheramaDialog.PARAM_TITLE_TEXT";

    private final static String PARAM_MSG_TEXT = "com.neek.tech.weatherapp.weatherama.utilities.WeatheramaDialog.PARAM_MSG_TEXT";

    private final static String PARAM_POSSTIVE_TEXT = "com.neek.tech.weatherapp.weatherama.utilities.WeatheramaDialog.PARAM_POSSTIVE_TEXT";

    private final static String PARAM_NEGATIVE_TEXT = "com.neek.tech.weatherapp.weatherama.utilities.WeatheramaDialog.PARAM_NEGATIVE_TEXT";

    /**
     * Views
     */
    @BindView(R.id.titleTextView)
    protected TextView titleTextView;

    @BindView(R.id.messageTextView)
    protected TextView messageTextView;

    @BindView(R.id.negativeButton)
    protected Button negativeButton;

    @BindView(R.id.positiveButton)
    protected Button positiveButton;

    /**
     * Listener to handle button click events
     */
    private DialogClickedButton dialogClickedButton;

    /**
     * Creates a new instance of this dialog fragment
     *
     * @param positiveText posstive text, null for hide button
     * @param negativeText  negative text, null for hide button
     * @param listener      listener of touches
     * @return a new isntance of BuzzAlertDialogFragment
     */
    public static WeatheramaDialog newInstance(
            String title,
            String message,
            String positiveText,
            String negativeText,
            DialogClickedButton listener) {

        WeatheramaDialog dialog = new WeatheramaDialog();
        Bundle b = new Bundle();
        b.putString(PARAM_TITLE_TEXT, title);
        b.putString(PARAM_MSG_TEXT, message);
        b.putString(PARAM_POSSTIVE_TEXT, positiveText);
        b.putString(PARAM_NEGATIVE_TEXT, negativeText);
        dialog.setArguments(b);
        dialog.setDialogClickedButton(listener);
        return dialog;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity(), R.style.WeatherDialog);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_weatherama_dialog, null);
        ButterKnife.bind(this, view);
        Logger.create(TAG);

        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        positiveButton.setTag(POSITIVE_BUTTON);
        negativeButton.setTag(NEGATIVE_BUTTON);

        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);

        Bundle arguments = getArguments();
        if (arguments != null) {

            String string = arguments.getString(PARAM_TITLE_TEXT, null);
            setTitle(string);

            string = arguments.getString(PARAM_MSG_TEXT, null);
            setMessage(string);

            string = arguments.getString(PARAM_NEGATIVE_TEXT, null);
            setNegativeButton(string);

            string = arguments.getString(PARAM_POSSTIVE_TEXT, null);
            setPositiveButton(string);

        }

        dialog.show();
        return dialog;

    }

    public void setTitle(String text) {

        if (TextUtils.isEmpty(text)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setVisibility(View.VISIBLE);
            if (StringUtils.isValidHtml(text)) {
                titleTextView.setText(Html.fromHtml(text));
            } else {
                titleTextView.setText(text);
            }
        }

    }

    public void setMessage(String text) {

        if (TextUtils.isEmpty(text)) {
            messageTextView.setVisibility(View.GONE);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            if (StringUtils.isValidHtml(text)) {
                messageTextView.setText(Html.fromHtml(text));
            } else {
                messageTextView.setText(text);
            }
        }

    }

    public void setNegativeButton(String text) {

        if (TextUtils.isEmpty(text)) {
            negativeButton.setVisibility(View.GONE);
        } else {
            negativeButton.setVisibility(View.VISIBLE);
            if (StringUtils.isValidHtml(text)) {
                negativeButton.setText(Html.fromHtml(text));
            } else {
                negativeButton.setText(text);
            }
        }

    }

    public void setPositiveButton(String text) {

        if (TextUtils.isEmpty(text)) {
            positiveButton.setVisibility(View.GONE);
        } else {
            positiveButton.setVisibility(View.VISIBLE);
            if (StringUtils.isValidHtml(text)) {
                positiveButton.setText(Html.fromHtml(text));
            } else {
                positiveButton.setText(text);
            }
        }

    }

    /**
     * On click on a button
     *
     * @param v button
     */
    @Override
    public void onClick(View v) {

        if (dialogClickedButton != null) {
            Object tag = v.getTag();
            if (tag instanceof Integer) {
                dialogClickedButton.onClickedButton(this, (Integer) tag, v);
                dismiss();
            }
        }

    }

    public void setDialogClickedButton(DialogClickedButton dialogClickedButton) {
        this.dialogClickedButton = dialogClickedButton;
    }

    public interface DialogClickedButton {

        void onClickedButton(WeatheramaDialog dialog, int id, View v);

    }


}
