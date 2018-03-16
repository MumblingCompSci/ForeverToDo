package com.csci448.runninglateco.forevertodo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by quintero on 3/14/18.
 */

public class TaskListFragment extends Fragment {
    private static final String TAG = "TaskListFragment";

    private class TaskHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ToDoTask mTask;
        private TextView mTitleText;
        private TextView mDueDateText;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));

            mTitleText = (TextView) itemView.findViewById(R.id.task_title);
            mDueDateText = (TextView) itemView.findViewById(R.id.task_due_date);

            itemView.setOnClickListener(this);
        }

        public void bind(ToDoTask task) {
            mTask = task;
            mTitleText.setText(task.getTitle());
            mDueDateText.setText(task.getDueDate().toString());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), "Task Clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<ToDoTask> mTasks;
        public TaskAdapter(List<ToDoTask> tasks) { mTasks = tasks; }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            ToDoTask task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() { return (mTasks.size()); }
    }

    public static TaskListFragment newInstance() {
        return null;
    }

    private RecyclerView mTaskRecyclerView;
    private TaskAdapter mAdapter;
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onTaskSelected(ToDoTask task);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                ToDoTask task = new ToDoTask();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
