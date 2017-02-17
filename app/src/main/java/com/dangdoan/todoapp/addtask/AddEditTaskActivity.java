package com.dangdoan.todoapp.addtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.dangdoan.todoapp.R;
import com.dangdoan.todoapp.datasource.TaskRepository;

public class AddEditTaskActivity extends AppCompatActivity {
    private static final String EXTRA_TASK_ID = "EXTRA_TASK_ID";

    public static Intent newIntent(Context context, @Nullable String taskId) {
        Intent intent = new Intent(context, AddEditTaskActivity.class);
        if (taskId != null) {
            intent.putExtra(EXTRA_TASK_ID, taskId);
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        addAddTaskFragment();
    }

    private void addAddTaskFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment) fragmentManager
                .findFragmentById(R.id.contentFrame);
        if (addEditTaskFragment == null) {
            String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);
            TaskLoader taskLoader = new TaskLoader(this, TaskRepository.getInstance(this), taskId);
            addEditTaskFragment = AddEditTaskFragment.newInstance(
                    TaskRepository.getInstance(this), taskId, taskLoader);
            fragmentManager.beginTransaction().add(R.id.contentFrame, addEditTaskFragment).commit();
        }
    }
}
