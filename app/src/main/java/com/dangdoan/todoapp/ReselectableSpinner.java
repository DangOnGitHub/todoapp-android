package com.dangdoan.todoapp;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

/**
 * Created by dangdoan on 2/13/17.
 */

public class ReselectableSpinner extends AppCompatSpinner {
    public ReselectableSpinner(Context context) {
        super(context);
    }

    public ReselectableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelection = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelection) {
            getOnItemSelectedListener().onItemSelected(
                    this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelection = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelection) {
            getOnItemSelectedListener().onItemSelected(
                    this, getSelectedView(), position, getSelectedItemId());
        }
    }
}
