package com.dangdoan.todoapp.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dangdoan.todoapp.Task;

/**
 * Created by dangdoan on 2/8/17.
 */

public class TaskHolder extends RecyclerView.ViewHolder {
    public interface OnTaskSelectedListener {
        void onTaskSelected(String taskId);
    }

    private TextView textView;
    private OnTaskSelectedListener listener;
    private Task task;

    public TaskHolder(View itemView, OnTaskSelectedListener listener) {
        super(itemView);
        textView = (TextView) itemView.findViewById(android.R.id.text1);
        this.listener = listener;
        itemView.setOnClickListener(v -> listener.onTaskSelected(task.id()));
    }

    public void bindTask(Task task) {
        this.task = task;
        textView.setText(task.name());
    }
}
