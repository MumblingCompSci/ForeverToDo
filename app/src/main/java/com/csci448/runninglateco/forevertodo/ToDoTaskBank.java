package com.csci448.runninglateco.forevertodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.csci448.runninglateco.forevertodo.database.TaskBaseHelper;
import com.csci448.runninglateco.forevertodo.database.TaskCursorWrapper;
import com.csci448.runninglateco.forevertodo.database.TaskDbSchema;
import com.csci448.runninglateco.forevertodo.database.TaskDbSchema.TaskTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tkarol on 5/8/18.
 */

public class ToDoTaskBank {
    private static final String TAG = "ToDoTaskBank";
    private static ToDoTaskBank sToDoTaskBank;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ToDoTaskBank get(Context context){
        if(sToDoTaskBank == null){
            sToDoTaskBank = new ToDoTaskBank(context);
        }
        return sToDoTaskBank;
    }

    private ToDoTaskBank(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new TaskBaseHelper(mContext).getWritableDatabase();
    }

    public void addToDoTask(ToDoTask task){
        ContentValues values = getContentValues(task);
        mDatabase.insert(TaskTable.NAME, null, values);
    }

    public  List<ToDoTask> getToDoTasks(){
        List<ToDoTask> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryToDoTasks(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }

        return tasks;
    }

    public ToDoTask getToDoTask(UUID id){
        TaskCursorWrapper cursor = queryToDoTasks(TaskTable.Cols.UUID + " = ?",
                new String[] {id.toString()});
        Log.i(TAG, "Looking for this id: " + id.toString());
        try{
            if(cursor.getCount() == 0){
                Log.i(TAG, "Can't find task");
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close();
        }
    }

    public void updateTask(ToDoTask task){
        String uuidString = task.getId().toString();
        ContentValues values = getContentValues(task);
        mDatabase.update(TaskTable.NAME, values, TaskTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(ToDoTask task){
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.UUID, task.getId().toString());
        values.put(TaskTable.Cols.TITLE, task.getTitle());
        values.put(TaskTable.Cols.DESCRIPTION, task.getDescription());
        //values.put(TaskTable.Cols.DUEDATE, task.getDueDate().getTime());
        values.put(TaskTable.Cols.COMPLETEDATE, task.getCompleteDate().getTime());
        values.put(TaskTable.Cols.PRIORITY, task.getPriority());
        values.put(TaskTable.Cols.CATEGORY, task.getCategory());

        return values;
    }

    private TaskCursorWrapper queryToDoTasks(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
            return new TaskCursorWrapper(cursor);
    }
}
