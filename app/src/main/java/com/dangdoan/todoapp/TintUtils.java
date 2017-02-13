package com.dangdoan.todoapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;

/**
 * Created by dangdoan on 2/13/17.
 */

public class TintUtils {
    public static void tintMenuItem(Context context, MenuItem item, @ColorRes int resID) {
        Drawable icon = item.getIcon();
        icon = DrawableCompat.wrap(icon);
        DrawableCompat.setTint(icon, ContextCompat.getColor(context, resID));
        item.setIcon(icon);
    }
}
