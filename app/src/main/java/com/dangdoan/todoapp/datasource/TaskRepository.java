package com.dangdoan.todoapp.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;

import com.dangdoan.todoapp.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangdoan on 2/11/17.
 */

public class TaskRepository {
    private static TaskRepository instance;
    private TaskDbHelper taskDbHelper;
    private List<TaskRepositoryObserver> taskRepositoryObservers = new ArrayList<>();

    private TaskRepository(Context context) {
        taskDbHelper = new TaskDbHelper(context);
    }

    public static TaskRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TaskRepository(context);
        }
        return instance;
    }

    public List<Task> getTasks() {
        SQLiteDatabase database = taskDbHelper.getReadableDatabase();
        Cursor cursor = getCursor(database);
        List<Task> tasks = getTasks(cursor);
        cursor.close();
        database.close();
        return tasks;
    }

    @NonNull
    private List<Task> getTasks(Cursor cursor) {
        List<Task> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_NAME));
            Task task = new Task(name);
            tasks.add(task);
        }
        return tasks;
    }

    private Cursor getCursor(SQLiteDatabase database) {
        String[] columns = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_NAME_NAME
        };
        return database.query(
                TaskContract.TaskEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
    }

    public void saveTask(Task task) {
        SQLiteDatabase database = taskDbHelper.getWritableDatabase();
        ContentValues values = getContentValues(task);
        database.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        database.close();
        notifyObserver();
    }

    private void notifyObserver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            taskRepositoryObservers.forEach(TaskRepositoryObserver::onTasksChanged);
        } else {
            for (TaskRepositoryObserver observer : taskRepositoryObservers) {
                observer.onTasksChanged();
            }
        }
    }

    @NonNull
    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, task.getName());
        return values;
    }

    public void addObserver(TaskRepositoryObserver observer) {
        if (!taskRepositoryObservers.contains(observer)) {
            taskRepositoryObservers.add(observer);
        }
    }

    public void removeObserver(TaskRepositoryObserver observer) {
        taskRepositoryObservers.remove(observer);
    }
}
