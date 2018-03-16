package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by quintero on 3/14/18.
 */

public class CompletedFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_completed, container, false);

        return view;
    }

}

//TODO: set up scrolling list similar to Criminal Intent
//TODO: make that list handle tasks that have been flagged as completed
//TODO: Should it show images if the task has an image?
/*          I don't think the completed list should need the image for a task */