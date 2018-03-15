package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tkarol on 3/15/18.
 */

public class SignUpFragment extends Fragment {
    private EditText mUser;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private EditText mEmail;
    private EditText mPhone;
    private CheckBox mTerms;
    private Button mSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        mUser = (EditText) v.findViewById(R.id.username_entry);

        mPassword = (EditText) v.findViewById(R.id.password_entry);

        mPasswordConfirm = (EditText) v.findViewById(R.id.password_confirm);

        mEmail = (EditText) v.findViewById(R.id.email_entry);

        mPhone = (EditText) v.findViewById(R.id.phone_entry);

        mTerms = (CheckBox) v.findViewById(R.id.terms);

        mSubmit = (Button) v.findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO: Send an intent to start TaskListActivity or Kill LoginActivity to return to TaskListActivity
                Toast toast = Toast.makeText(getContext(), "Goes to TaskList view", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return v;
    }
}
