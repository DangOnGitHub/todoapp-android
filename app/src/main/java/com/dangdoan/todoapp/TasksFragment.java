package com.dangdoan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangdoan on 2/8/17.
 */

public class TasksFragment extends Fragment {
    private RecyclerView tasksRecyclerView;
    private FloatingActionButton addButton;
    private TasksAdapter tasksAdapter;

    public TasksFragment() {
    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        configureUi(rootView);
        return rootView;
    }

    private void configureUi(View rootView) {
        setUpTasksAdapter();
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
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("First item"));
        tasks.add(new Task("Second item"));
        tasksAdapter = new TasksAdapter(tasks);
    }
}
