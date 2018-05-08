package com.csci448.runninglateco.forevertodo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by quintero on 3/16/18.
 */

public class TaskActivity extends SingleFragmentActivity {
    private static UUID mTaskId;

    @Override
    protected Fragment createFragment() {
        return TaskFragment.newInstance(mTaskId);
    }

    public static Intent newIntent(Context context, UUID taskId) {
        mTaskId = taskId;
        return new Intent(context, TaskActivity.class);
    }

}
