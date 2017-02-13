package com.dangdoan.todoapp.datasource;

import android.provider.BaseColumns;

/**
 * Created by dangdoan on 2/9/17.
 */

public final class TaskContract {
    private TaskContract() {
    }

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DUE_DATE = "duedate";
    }
}
