package com.elyseswoverland.circleconnect.ui.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.elyseswoverland.circleconnect.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static final String TAG = DatePickerDialogFragment.class.getSimpleName();
    private DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.getDefault());
    private TextView resultsView;
    private Calendar cal;
    private Calendar minDate;
    private Calendar maxDate;
    private DateValidator dateValidator = new DefaultDateValidator();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (cal == null) {
            cal = Calendar.getInstance(Locale.getDefault());
        }
        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                R.style.AppTheme_Dialog_DatePicker, this, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        if (getMinDate() != null) {
            dialog.getDatePicker().setMinDate(getMinDate().getTime().getTime());
        }
        if (getMaxDate() != null) {
            dialog.getDatePicker().setMaxDate(getMaxDate().getTime().getTime());
        }
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (resultsView == null) {
            Log.e(TAG, "setResultsView() never called");
        }

        Calendar cal = (Calendar) getCalendar().clone();
        cal.set(year, month, dayOfMonth);

        boolean acceptDate = dateValidator == null || dateValidator.onDateSelected(cal);

        if (acceptDate) {
            getCalendar().setTime(cal.getTime());
            resultsView.setText(dateFormat.format(cal.getTime()));
            resultsView.setTag(new Date(cal.getTime().getTime()));
        }
    }

    public void setResultsView(TextView textView) {
        resultsView = textView;
    }

    public Calendar getCalendar() {
        if (cal == null) {
            cal = Calendar.getInstance(Locale.getDefault());
        }
        return cal;
    }

    public void setCalendar(Calendar calendar) {
        this.cal = calendar;
    }

    public void setDateValidator(DateValidator dateValidator) {
        this.dateValidator = dateValidator;
    }

    public Calendar getMinDate() {
        return minDate;
    }

    public void setMinDate(Calendar minDate) {
        this.minDate = minDate;
    }

    public Calendar getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Calendar maxDate) {
        this.maxDate = maxDate;
    }

    public void setDateFormatter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    private interface DateValidator {
        boolean onDateSelected(Calendar cal);
    }

    private class DefaultDateValidator implements DateValidator {
        @Override
        public boolean onDateSelected(Calendar cal) {
            return cal.after(minDate) && cal.before(maxDate);
        }
    }
}
