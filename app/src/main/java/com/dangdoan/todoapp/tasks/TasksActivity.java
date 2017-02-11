package com.dangdoan.todoapp.tasks;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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
            TasksLoader tasksLoader = new TasksLoader(
                    this, TaskRepository.getInstance(getApplicationContext()));
            tasksFragment = TasksFragment.newInstance(tasksLoader);
            fragmentManager.beginTransaction().add(R.id.contentFrame, tasksFragment).commit();
        }
    }
}
