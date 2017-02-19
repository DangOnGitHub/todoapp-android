package com.dangdoan.todoapp.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;

import com.dangdoan.todoapp.Task;

import java.util.ArrayList;
import java.util.Date;
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

    public Task getTask(String taskId) {
        SQLiteDatabase database = taskDbHelper.getReadableDatabase();
        Cursor cursor = getCursorForTaskQuery(taskId, database);
        Task task = getTaskFromReadingCursor(cursor);
        cursor.close();
        database.close();
        return task;
    }

    @NonNull
    private Task getTaskFromReadingCursor(Cursor cursor) {
        cursor.moveToFirst();
        return getTaskAtCurrentCursorPosition(cursor);
    }

    private Cursor getCursorForTaskQuery(String taskId, SQLiteDatabase database) {
        String[] columns = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_NAME_NAME,
                TaskContract.TaskEntry.COLUMN_NAME_DUE_DATE,
                TaskContract.TaskEntry.COLUMN_NAME_PRIORITY,
        };
        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = {taskId};
        return database.query(
                TaskContract.TaskEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public List<Task> getTasks() {
        SQLiteDatabase database = taskDbHelper.getReadableDatabase();
        Cursor cursor = getCursorForTasksQuery(database);
        List<Task> tasks = getTasksFromReadingCursor(cursor);
        cursor.close();
        database.close();
        return tasks;
    }

    @NonNull
    private List<Task> getTasksFromReadingCursor(Cursor cursor) {
        List<Task> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            Task task = getTaskAtCurrentCursorPosition(cursor);
            tasks.add(task);
        }
        return tasks;
    }

    @NonNull
    private Task getTaskAtCurrentCursorPosition(Cursor cursor) {
        String id = cursor.getString(
                cursor.getColumnIndexOrThrow(TaskContract.TaskEntry._ID));
        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_NAME));
        long date = cursor.getLong(
                cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_DUE_DATE));
        Date dueDate = new Date(date);
        int priorityValue = cursor.getInt(
                cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_PRIORITY));
        int priority = Task.getPriority(priorityValue);
        return Task.create(id, name, dueDate, priority);
    }

    private Cursor getCursorForTasksQuery(SQLiteDatabase database) {
        String[] columns = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_NAME_NAME,
                TaskContract.TaskEntry.COLUMN_NAME_DUE_DATE,
                TaskContract.TaskEntry.COLUMN_NAME_PRIORITY,
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

    public void insertTask(Task task) {
        SQLiteDatabase database = taskDbHelper.getWritableDatabase();
        ContentValues values = createContentValuesForNewTask(task);
        database.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        database.close();
        notifyObserver();
    }

    @NonNull
    private ContentValues createContentValuesForNewTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry._ID, task.id());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, task.name());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DUE_DATE, task.dueDate().getTime());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIORITY, task.priority());
        return values;
    }

    public void updateTask(Task task) {
        SQLiteDatabase database = taskDbHelper.getWritableDatabase();
        ContentValues values = createContentValuesForExistingTask(task);
        String whereClause = TaskContract.TaskEntry._ID + " = ?";
        String[] whereArgs = {task.id()};
        database.update(TaskContract.TaskEntry.TABLE_NAME, values, whereClause, whereArgs);
        database.close();
        notifyObserver();
    }

    @NonNull
    private ContentValues createContentValuesForExistingTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, task.name());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DUE_DATE, task.dueDate().getTime());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_PRIORITY, task.priority());
        return values;
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

    public void addObserver(TaskRepositoryObserver observer) {
        if (!taskRepositoryObservers.contains(observer)) {
            taskRepositoryObservers.add(observer);
        }
    }

    public void removeObserver(TaskRepositoryObserver observer) {
        taskRepositoryObservers.remove(observer);
    }
}
