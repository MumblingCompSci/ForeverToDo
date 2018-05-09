package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tkarol on 5/8/18.
 */

public class SettingsFragment extends Fragment {
    public static SettingsFragment newInstance(){return new SettingsFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }
}
