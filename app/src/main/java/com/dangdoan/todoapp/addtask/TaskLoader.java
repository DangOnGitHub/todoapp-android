package com.dangdoan.todoapp.addtask;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.dangdoan.todoapp.Task;
import com.dangdoan.todoapp.datasource.TaskRepository;

/**
 * Created by dangdoan on 2/17/17.
 */

public class TaskLoader extends AsyncTaskLoader<Task> {
    private TaskRepository taskRepository;
    private String taskId;

    public TaskLoader(Context context, TaskRepository taskRepository, String taskId) {
        super(context);
        this.taskRepository = taskRepository;
        this.taskId = taskId;
    }

    @Override
    public Task loadInBackground() {
        return taskRepository.getTask(taskId);
    }

    @Override
    public void deliverResult(Task data) {
        if (isReset()) {
            return;
        }
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
