package com.dangdoan.todoapp;

import android.support.annotation.IntDef;

import com.google.auto.value.AutoValue;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Created by dangdoan on 2/8/17.
 */

@AutoValue
public abstract class Task {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PRIORITY_LOW, PRIORITY_NORMAL, PRIORITY_HIGH})
    public @interface Priority {
    }

    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;

    public static Task create(String id, String name, Date dueDate, @Priority int priority) {
        return new AutoValue_Task(id, name, dueDate, priority);
    }

    public abstract String id();

    public abstract String name();

    public abstract Date dueDate();

    @Priority
    public abstract int priority();

    @Priority
    public static int getPriority(int value) {
        return value;
    }
}
