package com.dangdoan.todoapp.addtask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import com.dangdoan.todoapp.DateTimeUtils;
import com.dangdoan.todoapp.R;
import com.dangdoan.todoapp.Task;
import com.dangdoan.todoapp.TintUtils;
import com.dangdoan.todoapp.datasource.TaskRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dangdoan on 2/8/17.
 */

public class AddEditTaskFragment extends Fragment implements LoaderManager.LoaderCallbacks<Task> {
    private static final String ARGUMENT_TASK_ID = "ARGUMENT_TASK_ID";
    private static final int TASK_LOADER_ID = 0;
    private EditText nameEditText;
    private TaskRepository taskRepository;
    private DatePicker dueDatePicker;
    private AppCompatSpinner prioritySpinner;
    private Loader<Task> taskLoader;
    private String taskId;

    public AddEditTaskFragment() {
    }

    public static AddEditTaskFragment newInstance(
            TaskRepository taskRepository, @Nullable String taskId, Loader<Task> taskLoader) {
        AddEditTaskFragment fragment = new AddEditTaskFragment();
        fragment.taskRepository = taskRepository;
        fragment.taskId = taskId;
        if (taskId != null) {
            Bundle arguments = new Bundle();
            arguments.putString(ARGUMENT_TASK_ID, taskId);
            fragment.setArguments(arguments);
        }
        fragment.taskLoader = taskLoader;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskId = getArguments() != null ? getArguments().getString(ARGUMENT_TASK_ID) : null;
        setTitle();
    }

    private void setTitle() {
        String title;
        if (isEdit()) {
            title = getString(R.string.edit_task);
        } else {
            title = getString(R.string.add_task);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit_task, container, false);
        configureUi(rootView);
        return rootView;
    }

    private void configureUi(View rootView) {
        setHasOptionsMenu(true);
        nameEditText = (EditText) rootView.findViewById(R.id.nameEditText);
        dueDatePicker = (DatePicker) rootView.findViewById(R.id.dueDatePicker);
        prioritySpinner = (AppCompatSpinner) rootView.findViewById(R.id.prioritySpinner);
        configurePrioritySpinner();
    }

    private void configurePrioritySpinner() {
        ArrayAdapter<String> priorityAdapter = setUpPriorityAdapter();
        prioritySpinner.setAdapter(priorityAdapter);
    }

    @NonNull
    private ArrayAdapter<String> setUpPriorityAdapter() {
        return new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.priority)
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_task_fragment_menu, menu);
        configureMenu(menu);
    }

    private void configureMenu(Menu menu) {
        removeUnneededMenuItem(menu);
        tintAllMenuItemsWhite(menu);
    }

    private void removeUnneededMenuItem(Menu menu) {
        if (isEdit()) {
            menu.removeItem(R.id.menu_save);
        } else {
            menu.removeItem(R.id.menu_done);
            menu.removeItem(R.id.menu_delete);
        }
    }

    private void tintAllMenuItemsWhite(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            TintUtils.tintMenuItemWhite(getActivity(), menuItem);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                createTask();
                getActivity().finish();
                break;
            case R.id.menu_done:
                editTask();
                getActivity().finish();
                break;
            case R.id.menu_delete:
                showDeleteConfirmation();
                break;
        }
        return true;
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_task_confirmation_title)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.delete, (dialog, which) -> handlePositiveDelete())
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                })
                .create()
                .show();
    }

    private void handlePositiveDelete() {
        deleteTask();
        getActivity().finish();
    }

    private void deleteTask() {
        taskRepository.deleteTask(taskId);
    }

    private void createTask() {
        String id = UUID.randomUUID().toString();
        Task task = taskFromUiInputs(id);
        taskRepository.insertTask(task);
    }

    private void editTask() {
        Task task = taskFromUiInputs(taskId);
        taskRepository.updateTask(task);
    }

    private Task taskFromUiInputs(String id) {
        String name = nameEditText.getText().toString();
        Date dueDate = DateTimeUtils.getDate(dueDatePicker);
        int priority = Task.getPriority(prioritySpinner.getSelectedItemPosition());
        return Task.create(id, name, dueDate, priority);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isEdit()) {
            getLoaderManager().initLoader(TASK_LOADER_ID, null, this);
        }
    }

    private boolean isEdit() {
        return taskId != null;
    }

    @Override
    public Loader<Task> onCreateLoader(int id, Bundle args) {
        return taskLoader;
    }

    @Override
    public void onLoadFinished(Loader<Task> loader, Task data) {
        if (data != null) {
            nameEditText.append(data.name());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data.dueDate());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dueDatePicker.updateDate(year, month, day);
            prioritySpinner.setSelection(data.priority());
        }
    }

    @Override
    public void onLoaderReset(Loader<Task> loader) {
        // No operation
    }
}
