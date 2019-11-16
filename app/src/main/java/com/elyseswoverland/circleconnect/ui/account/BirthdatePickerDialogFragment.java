package com.elyseswoverland.circleconnect.ui.account;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.elyseswoverland.circleconnect.BuildConfig;
import com.elyseswoverland.circleconnect.R;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BirthdatePickerDialogFragment extends DialogFragment {
    public static final String TAG = BirthdatePickerDialogFragment.class.getSimpleName();

    private NumberPicker dayPicker;
    private NumberPicker monthPicker;

    private EditText resultsView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog_DatePicker);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_birthdate_picker, null);
        monthPicker = view.findViewById(R.id.month_number_picker);
        dayPicker = view.findViewById(R.id.day_number_picker);

        Calendar cal = Calendar.getInstance();
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        if (!TextUtils.isEmpty(resultsView.getText())) {
            String monthString = resultsView.getText().subSequence(0,2).toString();
            if (resultsView.getText().length() == 4) {
                String dayString = resultsView.getText().subSequence(3,5).toString();
                try {
                    dayPicker.setValue(Integer.parseInt(dayString));
                    monthPicker.setValue(Integer.parseInt(monthString));
                    monthPicker.setMinValue(Integer.parseInt(monthString));
                } catch (NumberFormatException e) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }
            }
        }

//        updateMinimumMonth(cal);
        updateResultsView();

//        dayPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateMinimumMonth(cal));

        builder.setTitle("Select Birthdate");
        builder.setPositiveButton("Done", (dialog, which) -> updateResultsView());
        builder.setNegativeButton("Cancel", (dialog, which) -> {});

        return builder.setView(view).create();
    }

//    private void updateMinimumMonth(Calendar cal) {
//        if (dayPicker.getValue() == cal.get(Calendar.YEAR)) {
//            monthPicker.setMinValue(cal.get(Calendar.MONTH) + 1);
//        } else {
//            monthPicker.setMinValue(1);
//        }
//    }

    private void updateResultsView() {
        String birthdate = String.format(getString(R.string.birthdate_format), monthPicker.getValue(), dayPicker.getValue());
        if (resultsView != null) {
            if (TextUtils.isEmpty(resultsView.getText().toString())) {
                resultsView.setText(" ");
                new Handler().postDelayed(() -> resultsView.setText(birthdate), 500);
            } else {
                resultsView.setText(birthdate);
            }
        }
    }

    public void setResultsView(EditText resultsView) {
        this.resultsView = resultsView;
    }
}























































