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

import java.util.List;

/**
 * Created by quintero on 3/14/18.
 */

public class TaskListFragment extends Fragment {
    private static final String TAG = "TaskListFragment";

    private Button mTaskButton;
    private Button mHistoryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTaskButton = (Button) view.findViewById(R.id.to_task_button);
        mTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoTask testTask = new ToDoTask();
                ((TaskListActivity) getActivity()).onTaskSelected(testTask);
            }
        });
        mHistoryButton = (Button) view.findViewById(R.id.to_history_button);
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startHistory = new Intent(getActivity(), MainActivity.class);
                (getActivity()).startActivity(startHistory);
            }
        });


        return view;
    }

    public interface Callbacks {
        void onTaskSelected(ToDoTask task);
    }
    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }
}


//TODO: make this a recylcerview that actually is a list of tasks