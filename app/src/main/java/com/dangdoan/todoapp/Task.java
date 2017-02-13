package com.dangdoan.todoapp;

import com.google.auto.value.AutoValue;

/**
 * Created by dangdoan on 2/8/17.
 */

@AutoValue
public abstract class Task {
    public static Task create(String name) {
        return new AutoValue_Task(name);
    }

    public abstract String name();
}
