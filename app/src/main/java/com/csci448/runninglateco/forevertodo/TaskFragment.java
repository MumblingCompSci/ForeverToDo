package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by quintero on 3/16/18.
 */

public class TaskFragment extends Fragment {

    private TextView mTaskName;
    private TextView mDueDate;
    private TextView mDueTime;
    private TextView mDescription;
    private SeekBar mPriorityLvl;
    private Spinner mCategory;
    private TextView mNotifs;
    private TextView mAlarms;
    private Button mCancelButton;
    private Button mDoneButton;

    public interface Callbacks {
        void onTaskUpdated(ToDoTask task);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        /* get arguments here later */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        return view;
    }

    public static TaskFragment newInstance(UUID taskId) {
        TaskFragment fragment = new TaskFragment();

        return fragment;
    }
}
