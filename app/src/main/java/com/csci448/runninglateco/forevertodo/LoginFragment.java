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
    private EditText user;
    private EditText password;
    private Button signIn;
    private TextView signUp;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        user = (EditText) v.findViewById(R.id.username_entry);

        password = (EditText) v.findViewById(R.id.password_entry);

        signIn = (Button) v.findViewById(R.id.submit_button);
        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast toast = Toast.makeText(getContext(), "You've signed in", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        signUp = (TextView) v.findViewById(R.id.sign_up_link);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SignUpFragment()).commit();
            }
        });

        return v;
    }
}
