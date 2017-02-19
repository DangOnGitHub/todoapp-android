package com.dangdoan.todoapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;

/**
 * Created by dangdoan on 2/13/17.
 */

public class TintUtils {
    public static void tintMenuItemWhite(Context context, MenuItem item) {
        Drawable icon = item.getIcon();
        icon = DrawableCompat.wrap(icon);
        DrawableCompat.setTint(icon, ContextCompat.getColor(context, android.R.color.white));
        item.setIcon(icon);
    }
}
