package com.csci448.runninglateco.forevertodo.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.csci448.runninglateco.forevertodo.ToDoTask;
import com.csci448.runninglateco.forevertodo.database.TaskDbSchema.TaskTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by tkarol on 5/8/18.
 */

public class TaskCursorWrapper extends CursorWrapper{

    public TaskCursorWrapper(Cursor cursor) {super(cursor);}

    public ToDoTask getTask(){
        String uuidString = getString(getColumnIndex(TaskTable.Cols.UUID));
        String title = getString(getColumnIndex(TaskTable.Cols.TITLE));
        String description = getString(getColumnIndex(TaskTable.Cols.DESCRIPTION));
        long dueDate = getLong(getColumnIndex(TaskTable.Cols.DUEDATE));
        long completeDate = getLong(getColumnIndex(TaskTable.Cols.COMPLETEDATE));
        int priority = getInt(getColumnIndex(TaskTable.Cols.PRIORITY));
        String category = getString(getColumnIndex(TaskTable.Cols.CATEGORY));

        ToDoTask task = new ToDoTask(UUID.fromString(uuidString));
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(new Date(dueDate));
        task.setCompleteDate(new Date(completeDate));
        task.setPriority(priority);
        task.setCategory(category);

        return task;
    }
}
