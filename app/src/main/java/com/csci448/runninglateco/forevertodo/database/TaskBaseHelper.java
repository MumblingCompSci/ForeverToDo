package com.csci448.runninglateco.forevertodo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewGroup;
import com.csci448.runninglateco.forevertodo.database.TaskDbSchema.TaskTable;

import io.fabric.sdk.android.services.concurrency.Task;

/**
 * Created by tkarol on 5/8/18.
 */

public class TaskBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "taskBase.db";

    public TaskBaseHelper(Context context){super(context, DATABASE_NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + TaskTable.NAME + "("
                + "_id integer primary key autoincrement, " +
                TaskTable.Cols.UUID + ", " +
                TaskTable.Cols.TITLE + ", " +
                TaskTable.Cols.DESCRIPTION + "," +
                TaskTable.Cols.DUEDATE + ", " +
                TaskTable.Cols.COMPLETEDATE + ", " +
                TaskTable.Cols.PRIORITY + ", " +
                TaskTable.Cols.CATEGORY + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
