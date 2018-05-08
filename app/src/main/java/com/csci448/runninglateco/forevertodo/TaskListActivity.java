package com.csci448.runninglateco.forevertodo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.util.UUID;

import io.fabric.sdk.android.Fabric;

public class TaskListActivity extends AppCompatActivity
    implements TaskListFragment.Callbacks, TaskFragment.Callbacks{
    private static final String TAG = "TaskListActivity";
    private static final String EXTRA_TASK_SELECTED = "task_selected";
    private UUID mCurrentTaskId;

    protected Fragment createFragment() {
        return TaskListFragment.newInstance();
    }

    protected int getLayoutResId() { return R.layout.activity_masterdetail; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentTaskId = (UUID) savedInstanceState.getSerializable(EXTRA_TASK_SELECTED);
        Fabric.with(this, new Crashlytics());
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        else{

            fragment = createFragment();
            fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onTaskSelected(ToDoTask task){
        Log.i(TAG, "Task Clicked!");
        mCurrentTaskId = task.getId();
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
        mCurrentTaskId = null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, listFragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(EXTRA_TASK_SELECTED, mCurrentTaskId);
    }
}

//TODO: create list scrolling activity similar to the one from CriminalIntent
//TODO: add a static "sort by" button to the scrolling page
//TODO: add a + menu item to add a new task
//TODO: create new ToDoTask class

//TODO: set up a landscape view for TaskListActivity that shows the list and the details like CriminalActivity did
//TODO: implement a NavigationDrawer????