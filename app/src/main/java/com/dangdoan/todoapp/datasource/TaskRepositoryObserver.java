package com.dangdoan.todoapp.datasource;

/**
 * Created by dangdoan on 2/11/17.
 */
public interface TaskRepositoryObserver {
    void onTasksChanged();
}
