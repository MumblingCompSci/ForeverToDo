package com.csci448.runninglateco.forevertodo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.Collections;
import java.util.Comparator;
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
                //showPopupMenu();

                Log.d(TAG, "Popup menu item selected");
                return true;
            case R.id.sortby:
                Log.d(TAG, "Sort by selected");
                // Dialog will pop up for user to choose to sort by Due Date, Priority, or Category
                showSortByDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortByDialog() {

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.select_option)
                .setItems(R.array.sortby_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int option) {
                        switch (option) {
                            case 0:
                                mTaskAdapter.sortByDueDate();
                                Log.d(TAG, "Sorting by due date");
                                break;
                            case 1:
                                mTaskAdapter.sortByPriority();
                                Log.d(TAG, "Sorting by priority");
                                break;
                            case 2:
                                mTaskAdapter.sortByCategory();
                                Log.d(TAG, "Sorting by category");
                                break;
                        }
                    }
                })
                .show();

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
            Collections.sort(mTasks, new Comparator<ToDoTask>() {
                @Override
                public int compare(ToDoTask task1, ToDoTask task2) {
                    return task1.getDueDate().compareTo(task2.getDueDate());
                }
            });
        }

        public void sortByPriority() {
            // Sort mTasks by priority
            Collections.sort(mTasks, new Comparator<ToDoTask>() {
                @Override
                public int compare(ToDoTask task1, ToDoTask task2) {
                    return task1.getPriority() - task2.getPriority();
                }
            });
        }

        public void sortByCategory() {
            Collections.sort(mTasks, new Comparator<ToDoTask>() {
                @Override
                public int compare(ToDoTask task1, ToDoTask task2) {
                    return task1.getCategory().compareTo(task2.getCategory());
                }
            });
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