package com.dangdoan.todoapp.addtask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dangdoan.todoapp.R;
import com.dangdoan.todoapp.Task;
import com.dangdoan.todoapp.datasource.TaskRepository;

/**
 * Created by dangdoan on 2/8/17.
 */

public class AddTaskFragment extends Fragment {
    private EditText nameEditText;
    private TaskRepository taskRepository;

    public AddTaskFragment() {
    }

    public static AddTaskFragment newInstance(TaskRepository taskRepository) {
        AddTaskFragment fragment = new AddTaskFragment();
        fragment.taskRepository = taskRepository;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        configureUi(rootView);
        return rootView;
    }

    private void configureUi(View rootView) {
        nameEditText = (EditText) rootView.findViewById(R.id.nameEditText);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_task_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveTask();
                break;
        }
        return true;
    }

    private void saveTask() {
        String name = nameEditText.getText().toString();
        Task task = new Task(name);
        taskRepository.saveTask(task);
    }
}
