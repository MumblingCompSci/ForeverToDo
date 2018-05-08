package com.csci448.runninglateco.forevertodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
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
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        toProfile = (Button) view.findViewById(R.id.to_profile_button);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startProfile = new Intent(getActivity(), ProfileActivity.class);
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



        mRecyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        updateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                ToDoTask task = new ToDoTask();
                Log.i(TAG, "Created a new task: " + task.getId().toString());
                ToDoTaskBank.get(getActivity()).addToDoTask(task);
                updateUI();
                Log.i(TAG, "After updating UI, task's id is: " + task.getId().toString());
                mCallbacks.onTaskSelected(task);
                return true;

            // This is for the menu item with the 3 vertical dots
            case R.id.sort_popup_menu:
                // Method called to show the actual popup menu, set up this way to be a menu within the menu
                // Hopefully it will show the "Sort by" text..
                showPopupMenu();

                Log.d(TAG, "Popup menu item selected");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPopupMenu() {
        PopupMenu sortMenu = new PopupMenu(getActivity(), this.getView());

        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            // This will sort the list of tasks based on which menu item user selects
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Menu items selection
                // Sorting methods are in Task Adapter since that's where the list of tasks are made
                switch (menuItem.getItemId()) {
                    case R.id.sort_duedate:
                        Log.d(TAG, "Sorting by due date");
                        mTaskAdapter.sortByDueDate();

                        return true;
                    case R.id.sort_priority:
                        Log.d(TAG, "Sorting by priority");
                        mTaskAdapter.sortByPriority();

                        return true;
                    case R.id.sort_category:
                        Log.d(TAG, "Sorting by category");
                        mTaskAdapter.sortByCategory();

                        return true;
                }
                return false;
            }
        });

        sortMenu.show();
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

        public void setTasks(List<ToDoTask> tasks){mTasks = tasks;}

        public void sortByDueDate() {
            // Sort mTasks by due date
        }

        public void sortByPriority() {
            // Sort mTasks by priority
        }

        public void sortByCategory() {

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

//TODO: make this a recylcerview that actually is a list of tasks