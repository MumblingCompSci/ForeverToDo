package com.csci448.runninglateco.forevertodo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;


import java.util.ArrayList;


import java.util.Calendar;
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
    private Button mCompleteTime;
    private Button mSaveButton;
    private EditText mDescription;
    private SeekBar mPriorityLvl;
    private Spinner mCategory;
    private ToDoTask mTask;
    private ArrayList<String> mCategories;
    private Callbacks mCallbacks;
    private Button mDeleteButton;

    private Date tempDate = new Date();

    public interface Callbacks{
        void onTaskUpdated();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        mTaskName = (EditText) view.findViewById(R.id.task_name);
        mTaskName.setText(mTask.getTitle());

        mDueDate = (EditText) view.findViewById(R.id.task_due_date);
        if(mTask.getDueDate().getTime() != 0){

            String tempDate = convertToMonthString(mTask.getDueDate().getMonth())
                    + " " + mTask.getDueDate().getDate()
                    + " " + mTask.getDueDate().getYear();

            mDueDate.setText(tempDate);
        }

        //TODO: Set up date dialogue
        mDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDate = cal.get(Calendar.DAY_OF_MONTH);

                Log.d(TAG, "Setting Date picker dialog");
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String tempMonth = convertToMonthString(month);

                        mDueDate.setText(tempMonth + " " + day + " " + year);

                        tempDate.setYear(year);
                        tempDate.setMonth(month);
                        tempDate.setDate(day);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });

        mDueTime = (EditText) view.findViewById(R.id.task_due_time);
        if(mTask.getDueDate().getTime() != 0){
            String AMPM = "";
            if (mTask.getDueDate().getHours() > 12) {
                AMPM = "PM";
            } else {
                AMPM = "AM";
            }
            String tempTime = convertToStandardTime(mTask.getDueDate().getHours())
                    + ":" + mTask.getDueDate().getMinutes()
                    + AMPM;

            mDueTime.setText(tempTime);
        }
        mDueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                int mHour = cal.get(Calendar.HOUR_OF_DAY);
                int mMinute = cal.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        int tempHour = convertToStandardTime(hour);
                        String AMPM;
                        if (hour > 12) {
                            AMPM = "PM";
                        } else {
                            AMPM = "AM";
                        }
                        mDueTime.setText(tempHour + ":" + minute + AMPM);

                        tempDate.setHours(hour);
                        tempDate.setMinutes(minute);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), timeSetListener, mHour, mMinute, false);
                timePickerDialog.show();
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

        mCompleteTime = (Button) view.findViewById(R.id.completed_button);
        if(mTask.getCompleteDate().getTime() != 0){
            mCompleteTime.setText("Task completed on " + mTask.getCompleteDate().toString());
            mCompleteTime.setEnabled(false);
        }
        else{
            mCompleteTime.setText("Completed the task");
            mCompleteTime.setEnabled(true);
        }
        mCompleteTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Mark as complete?")
                        .setMessage("Are you sure you want to mark this complete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTask.setCompleteDate(new Date());
                                mCompleteTime.setText("Task completed on " + mTask.getCompleteDate().toString());
                                mCompleteTime.setEnabled(false);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });

        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Saving task");
                mTask.setPriority(mPriorityLvl.getProgress());
                mTask.setTitle(mTaskName.getText().toString());
                mTask.setDescription(mDescription.getText().toString());
                if(mDueDate.getText() != null && mDueDate.getText().toString() != ""){
                    Log.i(TAG, "here! This is the text: " + mDueDate.getText().toString() + " end");
                    mTask.setDueDate(tempDate);
                }
                ToDoTaskBank.get(getContext()).updateTask(mTask);
                mCallbacks.onTaskUpdated();
            }
        });

        mDeleteButton = (Button) view.findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoTaskBank.get(getContext()).deleteToDoTask(mTask.getId());
                mCallbacks.onTaskUpdated();
            }
        });

        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, TaskListFragment.newInstance(0))
                        .commit();

            }
        });

        return view;
    }

    private String convertToMonthString(int month) {
        String tempMonth = "";

        switch(month) {
            case 0:
                tempMonth = "January";
                break;
            case 1:
                tempMonth = "February";
                break;
            case 2:
                tempMonth = "March";
                break;
            case 3:
                tempMonth = "April";
                break;
            case 4:
                tempMonth = "May";
                break;
            case 5:
                tempMonth = "June";
                break;
            case 6:
                tempMonth = "July";
                break;
            case 7:
                tempMonth = "August";
                break;
            case 8:
                tempMonth = "September";
                break;
            case 9:
                tempMonth = "October";
                break;
            case 10:
                tempMonth = "November";
                break;
            case 11:
                tempMonth = "December";
                break;
        }
        return tempMonth;
    }

    private int convertToStandardTime(int hour) {
        return hour % 12;
    }
}
