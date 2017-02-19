package com.dangdoan.todoapp;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dangdoan on 2/20/17.
 */

public class DateTimeUtils {
    public static Date getDate(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
