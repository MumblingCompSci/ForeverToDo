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

import java.util.UUID;

/*
 * Created by Clara on 3/16/2018.
 */

public class EditNewTaskFragment extends Fragment {

    private EditText mTaskName;
    private EditText mDueDate;
    private EditText mDueTime;
    private EditText mDescription;
    private SeekBar mPriorityLvl;
    private Spinner mCategory;
    private EditText mNotifs;
    private EditText mAlarms;
    private Button mDoneButton;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        /* get arguments here later */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_newedit_task, container, false);

        return view;
    }

    public static EditNewTaskFragment newInstance() {
        EditNewTaskFragment fragment = new EditNewTaskFragment();

        return fragment;
    }
}
