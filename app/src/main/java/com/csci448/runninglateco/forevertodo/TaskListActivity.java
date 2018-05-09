package com.csci448.runninglateco.forevertodo;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
        invalidateOptionsMenu();
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mCurrentTaskId = (UUID) savedInstanceState.getSerializable(EXTRA_TASK_SELECTED);
        }
        Fabric.with(this, new Crashlytics());
        setContentView(getLayoutResId());

        BottomNavigationView nav_menu = findViewById(R.id.navigationView);
        if (nav_menu != null) {
            nav_menu.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem item) {
                            Fragment selectedFragment = null;
                            switch (item.getItemId()) {
                                case R.id.nav_home:
                                    selectedFragment = TaskListFragment.newInstance();
                                    break;
                                case R.id.nav_history:
                                    selectedFragment = CompletedFragment.newInstance();
                                    /*TODO : create a history page fragment that handles the two parts */
                                    break;
                                case R.id.nav_settings:
                                    selectedFragment = new GraphFragment();
                                    break;
                                default:
                                    break;

                            }
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, selectedFragment)
                                    .commit();
                            return true;
                        }
                    });
        }

        if(mCurrentTaskId != null){
            onTaskSelected(ToDoTaskBank.get(this).getToDoTask(mCurrentTaskId));
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TaskListFragment.newInstance())
                .commit();
    }

    @Override
    public void onTaskSelected(ToDoTask task){
        invalidateOptionsMenu();
        Log.i(TAG, "Task Clicked!");
        mCurrentTaskId = task.getId();
        if (findViewById(R.id.detail_fragment_container) == null) {
            Log.i(TAG, "Creating portrait view");
            Fragment newDetail = TaskFragment.newInstance(task.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newDetail).commit();
        } else {
            Log.i(TAG, "Creating landscape view");
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
        invalidateOptionsMenu();
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