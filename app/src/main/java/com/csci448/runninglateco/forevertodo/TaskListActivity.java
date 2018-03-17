package com.csci448.runninglateco.forevertodo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TaskListActivity extends AppCompatActivity
    implements TaskListFragment.Callbacks{
    private static final String TAG = "TaskListActivity";

    protected Fragment createFragment() {
        return TaskListFragment.newInstance();
    }

    protected int getLayoutResId() { return R.layout.activity_masterdetail; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onTaskSelected(ToDoTask task){
        Log.i(TAG, "Task Clicked!");
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = TaskActivity.newIntent(this, task.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = TaskFragment.newInstance(task.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
}

//TODO: create list scrolling activity similar to the one from CriminalIntent
//TODO: add a static "sort by" button to the scrolling page
//TODO: add a + menu item to add a new task
//TODO: create new ToDoTask class
//TODO: create new TaskWithImage class that extends ToDoTask
//TODO: create a TaskActivity to show the details of the activity
//TODO: give TaskActivity an Edit/Done button
//TODO: set up TaskActivity to "be a child" of TaskListActivity so that the up button takes it back

//TODO: set up a landscape view for TaskListActivity that shows the list and the details like CriminalActivity did
//TODO: implement a NavigationDrawer????