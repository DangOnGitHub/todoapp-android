package com.dangdoan.todoapp.tasks;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dangdoan.todoapp.R;
import com.dangdoan.todoapp.datasource.TaskRepository;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        addTasksFragment();
    }

    private void addTasksFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        TasksFragment tasksFragment = (TasksFragment) fragmentManager
                .findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance(TaskRepository.getInstance(this));
            fragmentManager.beginTransaction().add(R.id.contentFrame, tasksFragment).commit();
        }
    }
}
