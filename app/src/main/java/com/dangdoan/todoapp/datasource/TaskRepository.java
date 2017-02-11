package com.dangdoan.todoapp.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.dangdoan.todoapp.Task;

/**
 * Created by dangdoan on 2/11/17.
 */

public class TaskRepository {
    private static TaskRepository instance;
    private TaskDbHelper taskDbHelper;

    private TaskRepository(Context context) {
        taskDbHelper = new TaskDbHelper(context);
    }

    public static TaskRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TaskRepository(context);
        }
        return instance;
    }

    public void saveTask(Task task) {
        SQLiteDatabase database = taskDbHelper.getWritableDatabase();
        ContentValues values = getContentValues(task);
        database.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        database.close();
    }

    @NonNull
    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, task.getName());
        return values;
    }
}
