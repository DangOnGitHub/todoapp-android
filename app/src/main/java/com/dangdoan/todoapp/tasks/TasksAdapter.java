package com.dangdoan.todoapp.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dangdoan.todoapp.Task;

import java.util.List;

/**
 * Created by dangdoan on 2/8/17.
 */

public class TasksAdapter extends RecyclerView.Adapter<TaskHolder> {
    private List<Task> tasks;

    public TasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bindTask(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
