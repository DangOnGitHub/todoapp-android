package com.dangdoan.todoapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by dangdoan on 2/11/17.
 */

public class ToDoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
