package com.csci448.runninglateco.forevertodo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

/**
 * Created by tkarol on 5/8/18.
 */

public class SettingsFragment extends Fragment {
    public static SettingsFragment newInstance(){return new SettingsFragment();}
    private static final String EXTRA_SOUND_ON = "sound on";
    private static final String EXTRA_NOTIFICATION_ON = "notifications on";
    private static final String EXTRA_NOTIFICATION_TIME = "notification time";
    private boolean mSoundOn;
    private boolean mNotificationsOn;
    private int mNotificationTime;
    private Switch mNotificationsOnSwitch;
    private Switch mSoundOnSwitch;
    private Spinner mNotificationTimeSpinner;
    private SharedPreferences mSharedPreferences;
    private ArrayList<String> mTimes;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mSharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        mSoundOn = mSharedPreferences.getBoolean(EXTRA_SOUND_ON, true);
        mNotificationsOn = mSharedPreferences.getBoolean(EXTRA_NOTIFICATION_ON, true);
        mNotificationTime = mSharedPreferences.getInt(EXTRA_NOTIFICATION_TIME, 0);
        mTimes = new ArrayList<String>();
        mTimes.add("10 minutes");
        mTimes.add("30 minutes");
        mTimes.add("1 hour");
        mTimes.add("5 hours");
        mTimes.add("1 day");
        mTimes.add("2 days");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        mNotificationsOnSwitch = (Switch) view.findViewById(R.id.notification_on_switch);
        mNotificationsOnSwitch.setChecked(mNotificationsOn);
        mNotificationsOnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               editor.putBoolean(EXTRA_NOTIFICATION_ON, b);
               editor.commit();
            }
        });

        mSoundOnSwitch = (Switch) view.findViewById(R.id.sound_on_switch);
        mSoundOnSwitch.setChecked(mSoundOn);
        mSoundOnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(EXTRA_SOUND_ON, b);
                editor.commit();
            }
        });

        mNotificationTimeSpinner = (Spinner) view.findViewById(R.id.notification_time_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mTimes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNotificationTimeSpinner.setAdapter(adapter);
        mNotificationTimeSpinner.setSelection(mNotificationTime);
        mNotificationTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt(EXTRA_NOTIFICATION_TIME, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
}
