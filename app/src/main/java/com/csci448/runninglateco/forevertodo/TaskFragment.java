package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * Created by quintero on 3/16/18.
 */

public class TaskFragment extends Fragment {

    private EditText mTaskName;
    private EditText mDueDate;
    private EditText mDueTime;
    private EditText mDescription;
    private SeekBar mPriorityLvl;
    private Spinner mCategory;
    private EditText mNotifs;
    private EditText mAlarms;

    // two different "views" where all items will be disabled or enabled
    // If false, clicking on the button will change title to "Save" and then enable all items
    // Else if true, clicking on button will change title back to "Edit" and then disable items
    private boolean mInEdit = false;

    public interface Callbacks {
        void onTaskUpdated(ToDoTask task);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        /* get arguments here later */
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        mTaskName = (EditText) view.findViewById(R.id.task_name);
        mTaskName.setEnabled(false);

        mDueDate = (EditText) view.findViewById(R.id.task_due_date);
        mDueDate.setEnabled(false);

        mDueTime = (EditText) view.findViewById(R.id.task_due_time);
        mDueTime.setEnabled(false);

        mDescription = (EditText) view.findViewById(R.id.task_description);
        mDescription.setEnabled(false);

        mPriorityLvl = (SeekBar) view.findViewById(R.id.task_priority);
        mPriorityLvl.setEnabled(false);

        mCategory = (Spinner) view.findViewById(R.id.task_category);
        mCategory.setEnabled(false);

        mNotifs = (EditText) view.findViewById(R.id.task_notifs);
        mNotifs.setEnabled(false);

        mAlarms = (EditText) view.findViewById(R.id.task_alarms);
        mAlarms.setEnabled(false);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_save:
                mInEdit = !mInEdit;
                updateTaskItems();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateTaskItems() {
        String subtitle;

        if (mInEdit) {
            subtitle = "Save";
            enableItems();
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(subtitle);
        } else {
            subtitle = "Edit";
            disableItems();
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(subtitle);
        }
    }

    private void enableItems() {
        mTaskName.setEnabled(true);
        mDueDate.setEnabled(true);
        mDueTime.setEnabled(true);
        mDescription.setEnabled(true);
        mPriorityLvl.setEnabled(true);
        mCategory.setEnabled(true);
        mNotifs.setEnabled(true);
        mAlarms.setEnabled(true);
    }

    private void disableItems() {
        mTaskName.setEnabled(false);
        mDueDate.setEnabled(false);
        mDueTime.setEnabled(false);
        mDescription.setEnabled(false);
        mPriorityLvl.setEnabled(false);
        mCategory.setEnabled(false);
        mNotifs.setEnabled(false);
        mAlarms.setEnabled(false);
    }

    public static TaskFragment newInstance(UUID taskId) {
        TaskFragment fragment = new TaskFragment();

        return fragment;
    }
}
