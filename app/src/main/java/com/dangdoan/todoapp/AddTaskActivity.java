package com.dangdoan.todoapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddTaskActivity extends AppCompatActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, AddTaskActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        addAddTaskFragment();
    }

    private void addAddTaskFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddTaskFragment addTaskFragment = (AddTaskFragment) fragmentManager
                .findFragmentById(R.id.contentFrame);
        if (addTaskFragment == null) {
            addTaskFragment = AddTaskFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.contentFrame, addTaskFragment).commit();
        }
    }
}
