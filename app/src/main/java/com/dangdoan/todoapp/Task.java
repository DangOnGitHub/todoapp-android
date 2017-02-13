package com.dangdoan.todoapp;

import com.google.auto.value.AutoValue;

import java.util.Date;

/**
 * Created by dangdoan on 2/8/17.
 */

@AutoValue
public abstract class Task {
    public static Task create(String name, Date dueDate) {
        return new AutoValue_Task(name, dueDate);
    }

    public abstract String name();

    public abstract Date dueDate();
}
