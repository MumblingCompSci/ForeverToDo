package com.csci448.runninglateco.forevertodo;

import android.support.annotation.LayoutRes;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        BottomNavigationView nav_menu = findViewById(R.id.navigationView);
        nav_menu.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = TaskListFragment.newInstance(1);
                                break;
                            case R.id.nav_history:
                                selectedFragment = CompletedFragment.newInstance();
                                /*TODO : create a history page fragment that handles the two parts */
                                break;
                            case R.id.nav_settings :
                                selectedFragment = new GraphFragment();
                                break;
                            default:
                                break;

                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                        return true;
                    }
                });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TaskListFragment.newInstance(1))
                .commit();
    }
}

// TODO : set up fargment manager stuff for menu onclink