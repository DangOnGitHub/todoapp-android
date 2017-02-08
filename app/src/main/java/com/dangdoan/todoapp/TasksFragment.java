package com.dangdoan.todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangdoan on 2/8/17.
 */

public class TasksFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText taskEditText;
    private Button addButton;
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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        configureRecyclerView();
        taskEditText = (EditText) rootView.findViewById(R.id.task_edit_text);
        addButton = (Button) rootView.findViewById(R.id.add_button);
        configureAddButton();
    }

    private void configureAddButton() {
        addButton.setOnClickListener(v -> {
            tasksAdapter.addTask(taskEditText.getText().toString());
            tasksAdapter.notifyDataSetChanged();
        });
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(tasksAdapter);
    }

    private void setUpTasksAdapter() {
        List<String> tasks = new ArrayList<>();
        tasks.add("First item");
        tasks.add("Second item");
        tasksAdapter = new TasksAdapter(tasks);
    }
}
