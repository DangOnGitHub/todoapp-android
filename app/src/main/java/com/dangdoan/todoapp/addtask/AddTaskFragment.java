package com.dangdoan.todoapp.addtask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.dangdoan.todoapp.R;
import com.dangdoan.todoapp.Task;
import com.dangdoan.todoapp.TintUtils;
import com.dangdoan.todoapp.datasource.TaskRepository;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dangdoan on 2/8/17.
 */

public class AddTaskFragment extends Fragment {
    private EditText nameEditText;
    private TaskRepository taskRepository;
    private AppCompatSpinner dueDateSpinner;
    private AppCompatSpinner prioritySpinner;
    private Date dueDate = new Date();

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
        setHasOptionsMenu(true);
        nameEditText = (EditText) rootView.findViewById(R.id.nameEditText);
        dueDateSpinner = (AppCompatSpinner) rootView.findViewById(R.id.dueDateSpinner);
        configureDueDateSpinner();
        prioritySpinner = (AppCompatSpinner) rootView.findViewById(R.id.prioritySpinner);
        configurePrioritySpinner();
    }

    private void configureDueDateSpinner() {
        ArrayAdapter<String> dueDateAdapter = setUpDateAdapter();
        dueDateSpinner.setAdapter(dueDateAdapter);
        dueDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDueDate((TextView) view, position);
            }

            private void updateDueDate(TextView view, int position) {
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
                switch (position) {
                    case 0:
                        dueDate = new Date();
                        break;
                    case 1:
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        dueDate = calendar.getTime();
                        break;
                    case 2:
                        showDatePickerDialog(date -> {
                            dueDate = date;
                            view.setText(dateFormat.format(dueDate));
                        });
                        break;
                }
                view.setText(dateFormat.format(dueDate));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No operation
            }
        });
    }

    private void showDatePickerDialog(DatePickerFragment.DatePickerFragmentListener listener) {
        DatePickerFragment fragment = DatePickerFragment.newInstance(listener, dueDate);
        fragment.show(getFragmentManager(), "datePicker");
    }

    private ArrayAdapter<String> setUpDateAdapter() {
        return new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.due_date)
        );
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
        MenuItem saveMenuItem = menu.findItem(R.id.menu_save);
        TintUtils.tintMenuItem(getActivity(), saveMenuItem, android.R.color.white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveTask();
                getActivity().finish();
                break;
        }
        return true;
    }

    private void saveTask() {
        String name = nameEditText.getText().toString();
        Task task = Task.create(name, dueDate);
        taskRepository.saveTask(task);
    }
}
