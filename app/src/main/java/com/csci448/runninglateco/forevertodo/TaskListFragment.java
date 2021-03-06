package com.csci448.runninglateco.forevertodo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by quintero on 3/14/18.
 */

public class TaskListFragment extends Fragment {
    private static final String TAG = "TaskListFragment";
    private static final String ARG_SORT_SELECTION = "sort selection";
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private int mSortSelection;
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
        mSortSelection = getArguments().getInt(ARG_SORT_SELECTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);



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
                                TaskListActivity.setSortingBy(0);
                                mSortSelection = 0;
                                updateUI();
                                Log.d(TAG, "Sorting by due date");
                                break;
                            case 1:
                                mTaskAdapter.sortByPriority();
                                TaskListActivity.setSortingBy(1);
                                mSortSelection = 1;
                                updateUI();
                                Log.d(TAG, "Sorting by priority");
                                break;
                            case 2:
                                mTaskAdapter.sortByCategory();
                                TaskListActivity.setSortingBy(2);
                                mSortSelection = 2;
                                updateUI();
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

    public static TaskListFragment newInstance(int sortSelection) {
        Bundle args = new Bundle();
        args.putInt(ARG_SORT_SELECTION, sortSelection);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
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

            mTask = task;
            mTitleTextView.setText(mTask.getTitle());
            mTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{
        private List<ToDoTask> mTasks;

        public TaskAdapter(List<ToDoTask> tasks){
            setTasks(tasks);
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

        public void setTasks(List<ToDoTask> tasks){
            List<ToDoTask> uncompleted = new ArrayList<>();
            for(int i = 0; i < tasks.size(); ++i){
                if(tasks.get(i).getCompleteDate().getTime() == 0){
                    uncompleted.add(tasks.get(i));
                }
            }
            mTasks = uncompleted;
            switch(mSortSelection){
                case 0:
                    sortByDueDate();
                    break;
                case 1:
                    sortByPriority();
                    break;
                case 2:
                    sortByCategory();
                    break;
            }
        }

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
                    Log.i(TAG, "" + task1.getPriority());
                    return task2.getPriority() - task1.getPriority();
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