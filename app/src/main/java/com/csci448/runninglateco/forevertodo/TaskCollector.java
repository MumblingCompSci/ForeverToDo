package com.csci448.runninglateco.forevertodo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by quintero on 3/16/18.
 */

public class TaskCollector {
    private static TaskCollector sTaskCollector;

    private List<ToDoTask> mTasks;

    public static TaskCollector get(Context context) {
        if (sTaskCollector == null) {
            sTaskCollector = new TaskCollector(context);
        }

        return sTaskCollector;
    }

    private TaskCollector(Context context) { mTasks = new ArrayList<>(); }

    public ToDoTask getTask(UUID id) {
        for (ToDoTask task : mTasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public List<ToDoTask> getTasks() { return mTasks; }

    public void addTask(ToDoTask task) {
        mTasks.add(task);
    }
}
