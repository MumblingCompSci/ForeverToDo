package com.csci448.runninglateco.forevertodo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;



import java.util.ArrayList;

import java.util.Date;
import java.util.UUID;

/**
 * Created by quintero on 3/16/18.
 */

public class TaskFragment extends Fragment {

    private static final String TAG = "TaskFragment";
    private static final String ARG_TASK_ID = "task_id";
    private EditText mTaskName;
    private EditText mDueDate;
    private EditText mDueTime;
    private EditText mDescription;
    private SeekBar mPriorityLvl;
    private Spinner mCategory;
    private ToDoTask mTask;
    private ArrayList<String> mCategories;
    private Callbacks mCallbacks;

    public interface Callbacks{
        void onTaskUpdated(ToDoTask task);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }

    public static TaskFragment newInstance(UUID taskId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        mTask = ToDoTaskBank.get(getActivity()).getToDoTask(taskId);
        Log.i(TAG, "Got the task: " + mTask.getId().toString());
        mCategories = new ArrayList<String>();
        mCategories.add("Work");
        mCategories.add("School");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        mTaskName = (EditText) view.findViewById(R.id.task_name);
        mTaskName.setText(mTask.getTitle());

        mDueDate = (EditText) view.findViewById(R.id.task_due_date);
        mDueDate.setText(mTask.getDueDate().toString());

        //TODO: Set up date dialogue
        mDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toastie = Toast.makeText(getContext(),
                        "A DatePickerDialog will pull up for user to set the date with",
                        Toast.LENGTH_SHORT);
                toastie.show();
            }
        });

        mDueTime = (EditText) view.findViewById(R.id.task_due_time);
        mDueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toastie = Toast.makeText(getContext(),
                        "A TimePickerDialog will pull up for user to set the time with",
                        Toast.LENGTH_SHORT);
                toastie.show();
            }
        });

        mDescription = (EditText) view.findViewById(R.id.task_description);
        mDescription.setText(mTask.getDescription());

        mPriorityLvl = (SeekBar) view.findViewById(R.id.task_priority);
        mPriorityLvl.setProgress(mTask.getPriority());

        mCategory = (Spinner) view.findViewById(R.id.task_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory.setAdapter(adapter);
        mCategory.setSelection(adapter.getPosition(mTask.getCategory()));
        mCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTask.setCategory(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
            case R.id.edit_save:
                Log.i(TAG, "Saving task");
                mTask.setPriority(mPriorityLvl.getProgress());
                mTask.setTitle(mTaskName.getText().toString());
                mTask.setDescription(mDescription.getText().toString());
                mTask.setDueDate(new Date(mDueDate.getText().toString()));
                ToDoTaskBank.get(getContext()).updateTask(mTask);
                mCallbacks.onTaskUpdated(mTask);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void enableItems() {
        mTaskName.setEnabled(true);
        mDueDate.setEnabled(true);
        mDueTime.setEnabled(true);
        mDescription.setEnabled(true);
        mPriorityLvl.setEnabled(true);
        mCategory.setEnabled(true);
    }

    private void disableItems() {
        mTaskName.setEnabled(false);
        mDueDate.setEnabled(false);
        mDueTime.setEnabled(false);
        mDescription.setEnabled(false);
        mPriorityLvl.setEnabled(false);
        mCategory.setEnabled(false);
    }
}
