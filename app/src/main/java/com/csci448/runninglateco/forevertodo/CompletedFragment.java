package com.csci448.runninglateco.forevertodo;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by quintero on 3/14/18.
 */

public class CompletedFragment extends Fragment {
    private static final String TAG = "CompletedFragment";
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private Callbacks mCallbacks;

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


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_completed, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.completed_recycler);
        updateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public interface Callbacks {
        void onTaskSelected(ToDoTask task);
    }

    public static CompletedFragment newInstance() {
        return new CompletedFragment();
    }


    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ToDoTask mTask;
        private LinearLayout info_holder;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.task_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.task_due_date);
            info_holder = itemView.findViewById(R.id.task_info_holder);
        }

        public void bind(ToDoTask task) {
            mTask = task;
            if (info_holder != null) {
                switch (task.getPriority()) {
                    case 10:
                    case 9:
                    case 8:
                        info_holder.setBackgroundColor(Color.parseColor("#ed5d50"));
                        break;
                    case 7:
                    case 6:
                    case 5:
                        info_holder.setBackgroundColor(Color.parseColor("#d6a10e"));
                        break;
                    case 4:
                    case 3:
                    case 2:
                        info_holder.setBackgroundColor(Color.parseColor("#96d15c"));
                        break;
                    default:
                        info_holder.setBackgroundColor(Color.parseColor("#ffffff"));
                        break;
                }
            }

            mTitleTextView.setText(mTask.getTitle());
            mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "Calling onTaskSelected");
                    mCallbacks.onTaskSelected(mTask);
                }
            });

            if(mTask.getDueDate().getTime() != 0){
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm a", Locale.US);

                String dateString = format.format(task.getDueDate());

                mDateTextView.setText(dateString);
            }
            else{
                mDateTextView.setText("");
            }

            mDateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbacks.onTaskSelected(mTask);
                }
            });
        }

        @Override
        public void onClick(View view){
            mCallbacks.onTaskSelected(mTask);
        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<CompletedFragment.TaskHolder>{
        private List<ToDoTask> mTasks;

        public TaskAdapter(List<ToDoTask> tasks){
            setTasks(tasks);
        }

        public void setTasks(List<ToDoTask> tasks){
            List<ToDoTask> completed = new ArrayList<>();
            for(int i = 0; i < tasks.size(); ++i){
                if(tasks.get(i).getCompleteDate().getTime() != 0){
                    completed.add(tasks.get(i));
                }
            }
            mTasks = completed;
        }

        @Override
        public CompletedFragment.TaskHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CompletedFragment.TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CompletedFragment.TaskHolder holder, int position){
            ToDoTask task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount(){
            return mTasks.size();
        }
    }

    public void updateUI() {
        ToDoTaskBank toDoTaskBank = ToDoTaskBank.get(getActivity());
        List<ToDoTask> tasks = toDoTaskBank.getToDoTasks();
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(tasks);
            mTaskAdapter.notifyDataSetChanged();
        }
    }
}

//TODO: set up scrolling list similar to Criminal Intent
//TODO: make that list handle tasks that have been flagged as completed
//TODO: Should it show images if the task has an image?
/*          I don't think the completed list should need the image for a task */