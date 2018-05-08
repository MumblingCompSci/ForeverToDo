package com.csci448.runninglateco.forevertodo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class TaskListActivity extends SingleFragmentActivity
    implements TaskListFragment.Callbacks, TaskFragment.Callbacks{
    private static final String TAG = "TaskListActivity";

    @Override
    protected Fragment createFragment() {
        return TaskListFragment.newInstance();
    }

    @Override
    protected int getLayoutResId() { return R.layout.activity_masterdetail; }

    @Override
    public void onTaskSelected(ToDoTask task){
        Log.i(TAG, "Task Clicked!");
        if (findViewById(R.id.detail_fragment_container) == null) {
            Fragment newDetail = TaskFragment.newInstance(task.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newDetail).commit();
        } else {
            Fragment newDetail = TaskFragment.newInstance(task.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    public void onTaskUpdated(ToDoTask task){
        Fragment listFragment = createFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, listFragment)
                .commit();
    }
}

//TODO: create list scrolling activity similar to the one from CriminalIntent
//TODO: add a static "sort by" button to the scrolling page
//TODO: add a + menu item to add a new task
//TODO: create new ToDoTask class

//TODO: set up a landscape view for TaskListActivity that shows the list and the details like CriminalActivity did
//TODO: implement a NavigationDrawer????