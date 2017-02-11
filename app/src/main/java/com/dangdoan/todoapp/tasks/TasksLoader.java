package com.dangdoan.todoapp.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.dangdoan.todoapp.Task;
import com.dangdoan.todoapp.datasource.TaskRepository;
import com.dangdoan.todoapp.datasource.TaskRepositoryObserver;

import java.util.List;

/**
 * Created by dangdoan on 2/11/17.
 */

public class TasksLoader extends AsyncTaskLoader<List<Task>> implements TaskRepositoryObserver {
    private TaskRepository taskRepository;
    private List<Task> data;

    public TasksLoader(Context context, TaskRepository taskRepository) {
        super(context);
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> loadInBackground() {
        return taskRepository.getTasks();
    }

    @Override
    public void deliverResult(List<Task> data) {
        this.data = data;
        if (isReset()) {
            return;
        }
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        taskRepository.addObserver(this);
        if (data != null) {
            deliverResult(data);
        }
        if (takeContentChanged() || data == null) {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        onStopLoading();
        taskRepository.removeObserver(this);
    }

    @Override
    public void onTasksChanged() {
        onContentChanged();
    }
}
