package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private Button mCancelButton;
    private Button mDoneButton;

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

        mCancelButton = (Button) view.findViewById(R.id.cancel_button);
        mCancelButton.setVisibility(View.INVISIBLE);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toastie = Toast.makeText(getContext(),
                        "Back to Task List with no changes saved",
                        Toast.LENGTH_SHORT);
                toastie.show();
            }
        });
        mDoneButton = (Button) view.findViewById(R.id.done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toastie = Toast.makeText(getContext(),
                        "Back to Task List with changes saved", Toast.LENGTH_SHORT);
                toastie.show();
            }
        });

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
            case R.id.edit_task:
                mTaskName.setEnabled(true);
                mDueDate.setEnabled(true);
                mDueTime.setEnabled(true);
                mDescription.setEnabled(true);
                mPriorityLvl.setEnabled(true);
                mCategory.setEnabled(true);
                mNotifs.setEnabled(true);
                mAlarms.setEnabled(true);
                mCancelButton.setVisibility(View.VISIBLE);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static TaskFragment newInstance(UUID taskId) {
        TaskFragment fragment = new TaskFragment();

        return fragment;
    }
}
