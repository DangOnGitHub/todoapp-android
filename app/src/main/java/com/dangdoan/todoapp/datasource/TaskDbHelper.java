package com.dangdoan.todoapp.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dangdoan on 2/11/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Task.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + TaskContract.TaskEntry.TABLE_NAME + " ("
            + TaskContract.TaskEntry._ID + " TEXT PRIMARY KEY, "
            + TaskContract.TaskEntry.COLUMN_NAME_NAME + " TEXT, "
            + TaskContract.TaskEntry.COLUMN_NAME_DUE_DATE + " INTEGER, "
            + TaskContract.TaskEntry.COLUMN_NAME_PRIORITY + " INTEGER"
            + ")";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
