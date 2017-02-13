package com.dangdoan.todoapp.addtask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dangdoan on 2/12/17.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public interface DatePickerFragmentListener {
        void onDateSet(Date date);
    }

    private DatePickerFragmentListener listener;
    private Calendar calendar = Calendar.getInstance();

    public static DatePickerFragment newInstance(
            DatePickerFragmentListener listener, @Nullable Date date) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.listener = listener;
        if (date != null) {
            fragment.calendar.setTime(date);
        }
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        listener.onDateSet(calendar.getTime());
    }
}
