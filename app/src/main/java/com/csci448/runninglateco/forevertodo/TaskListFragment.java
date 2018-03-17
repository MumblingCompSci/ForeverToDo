package com.csci448.runninglateco.forevertodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by quintero on 3/14/18.
 */

public class TaskListFragment extends Fragment {
    private static final String TAG = "TaskListFragment";
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private Button toProfile;
    private Button toHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        toProfile = (Button) view.findViewById(R.id.to_profile_button);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startProfile = new Intent(getActivity(), LoginActivity.class);
                startActivity(startProfile);
            }
        });
        toHistory = (Button) view.findViewById(R.id.to_history_button);
        toHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startHistory = new Intent(getActivity(), HistoryActivity.class);
                startActivity(startHistory);
            }
        });

        List<ToDoTask>tasks = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            ToDoTask task = new ToDoTask();
            task.setTitle(Integer.toString(rand.nextInt()));
            task.setDescription("I'm a description! :)");
            tasks.add(task);
        }
        mTaskAdapter = new TaskAdapter(tasks);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        mRecyclerView.setAdapter(mTaskAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public interface Callbacks {
        void onTaskSelected(ToDoTask task);
    }
    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }


    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ToDoTask mTask;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.task_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.task_due_date);
        }

        public void bind(ToDoTask task) {
            mTask = task;
            mTitleTextView.setText(mTask.getTitle());
            mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TaskListActivity) getActivity()).onTaskSelected(mTask);
                }
            });
            mDateTextView.setText(mTask.getCompleteDate().toString());
            mDateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TaskListActivity) getActivity()).onTaskSelected(mTask);
                }
            });
        }

        @Override
        public void onClick(View view){

        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{
        private List<ToDoTask> mTasks;

        public TaskAdapter(List<ToDoTask> tasks){
            mTasks = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position){
            ToDoTask task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount(){
            return mTasks.size();
        }
    }

}

//TODO: make this a recylcerview that actually is a list of tasks