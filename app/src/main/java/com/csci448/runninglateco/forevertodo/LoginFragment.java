package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tkarol on 3/15/18.
 */

public class LoginFragment extends Fragment {
    private EditText mUser;
    private EditText mPassword;
    private Button mSignIn;
    private TextView mSignUp;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mUser = (EditText) v.findViewById(R.id.username_entry);

        mPassword = (EditText) v.findViewById(R.id.password_entry);

        mSignIn = (Button) v.findViewById(R.id.submit_button);
        mSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO: Start TaskListActivity or Kill LoginActivity to return to TaskListActivity
                Toast toast = Toast.makeText(getContext(), "Goes to TaskList view", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        mSignUp = (TextView) v.findViewById(R.id.sign_up_link);
        mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SignUpFragment()).commit();
            }
        });

        return v;
    }
}
