package com.csci448.runninglateco.forevertodo;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by tkarol on 3/15/18.
 */

public class ProfileActivity extends AppCompatActivity{

    //inflates a ProfileFragment
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = new ProfileFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

}
