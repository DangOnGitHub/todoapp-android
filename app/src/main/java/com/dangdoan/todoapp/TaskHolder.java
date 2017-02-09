package com.dangdoan.todoapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dangdoan on 2/8/17.
 */

public class TaskHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public TaskHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(android.R.id.text1);
    }

    public void bindTask(Task task) {
        textView.setText(task.getName());
    }
}
