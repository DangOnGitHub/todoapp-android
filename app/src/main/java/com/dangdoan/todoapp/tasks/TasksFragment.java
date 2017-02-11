package com.dangdoan.todoapp.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dangdoan.todoapp.R;
import com.dangdoan.todoapp.Task;
import com.dangdoan.todoapp.addtask.AddTaskActivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by dangdoan on 2/8/17.
 */

public class TasksFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Task>> {
    private static final int TASKS_LOADER_ID = 0;
    private RecyclerView tasksRecyclerView;
    private FloatingActionButton addButton;
    private TasksAdapter tasksAdapter;
    private TasksLoader tasksLoader;

    public TasksFragment() {
    }

    public static TasksFragment newInstance(TasksLoader tasksLoader) {
        TasksFragment fragment = new TasksFragment();
        fragment.tasksLoader = tasksLoader;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        setUpTasksAdapter();
        configureUi(rootView);
        return rootView;
    }

    private void configureUi(View rootView) {
        tasksRecyclerView = (RecyclerView) rootView.findViewById(R.id.tasksRecyclerView);
        configureTasksRecyclerView();
        addButton = (FloatingActionButton) rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = AddTaskActivity.newIntent(getActivity());
            startActivity(intent);
        });
    }

    private void configureTasksRecyclerView() {
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksRecyclerView.setAdapter(tasksAdapter);
    }

    private void setUpTasksAdapter() {
        List<Task> tasks = Collections.emptyList();
        tasksAdapter = new TasksAdapter(tasks);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(TASKS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Task>> onCreateLoader(int id, Bundle args) {
        return tasksLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Task>> loader, List<Task> data) {
        if (data == null) {
            // TODO: Show error
        } else {
            reloadTasks(data);
        }
    }

    private void reloadTasks(List<Task> data) {
        tasksAdapter.setTasks(data);
        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Task>> loader) {
        // No operation
    }
}
