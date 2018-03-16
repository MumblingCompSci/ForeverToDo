package com.csci448.runninglateco.forevertodo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FragmentManager fm = getSupportFragmentManager();
        Fragment graphFragment = fm.findFragmentById(R.id.graph_frame);
        Fragment completedFragment = fm.findFragmentById(R.id.completed_frame);
        if (graphFragment == null) {
            graphFragment = new     GraphFragment();
        }
        if (completedFragment == null) {
            completedFragment = new CompletedFragment();
        }
        fm.beginTransaction()
                .replace(R.id.graph_frame, graphFragment)
                .replace(R.id.completed_frame, completedFragment)
                .commit();
    }
}

//TODO: set up a view(or fragment?) for the completion history graph
//TODO: set up the views for the text about the completion statistics
//TODO: create the scrolling fragment for recently completed tasks
//TODO: toolbar???????
//TODO: implement Navigation Drawer????
/*I'm cool with whichever kind of menu thing you wanna use!
//TODO: should this be a SingleFragmentActivity or something else??
/*It depends on whether you want to have the history page be a single fragment with all of its components on one layout or if you
 * want to have multiple fragments, like have one fragment show the chart, and then have a button that says see completed tasks that
  * triggers an intent for a fragment that has a list of the completed tasks. If you have multiple fragments, then it wouldn't be a Single
  * FragmentActivity, so it's really up to you on how you want this section to work.*/