package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tkarol on 3/15/18.
 */

public class ProfileFragment extends Fragment {
    private ImageView mProfilePic;
    private TextView mUserName;
    private TextView mPasswordChange;
    private Switch mNotificationSwitch;
    private Switch mAlarmSwitch;
    private TextView mSignOut;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mProfilePic = (ImageView) v.findViewById(R.id.profile_pic);
        mProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getContext(), "Edit your profile picture", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        mUserName = (TextView) v.findViewById(R.id.username);

        mPasswordChange = (TextView) v.findViewById(R.id.change_password_link);
        mPasswordChange.setOnClickListener(new View.OnClickListener() {
            //brings up a PasswordResetFragment
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PasswordResetFragment()).commit();
            }
        });

        mNotificationSwitch = (Switch) v.findViewById(R.id.notification_switch);
        mNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast toast;
                if(mNotificationSwitch.isChecked()) {
                    toast = Toast.makeText(getContext(), "Notifications turned on", Toast.LENGTH_SHORT);
                }
                else{
                    toast = Toast.makeText(getContext(), "Notifications turned off", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });

        mAlarmSwitch = (Switch) v.findViewById(R.id.alarm_switch);
        mAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast toast;
                if(mAlarmSwitch.isChecked()) {
                    toast = Toast.makeText(getContext(), "Alarms turned on", Toast.LENGTH_SHORT);
                }
                else{
                    toast = Toast.makeText(getContext(), "Alarms turned off", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });

        mSignOut = (TextView) v.findViewById(R.id.sign_out_link);
        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Decide on where we really want to go when user signs out
                Toast toast = Toast.makeText(getContext(), "Goes to login page", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return v;
    }
}
