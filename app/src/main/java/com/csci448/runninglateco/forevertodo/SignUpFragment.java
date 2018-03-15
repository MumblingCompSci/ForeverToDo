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
    private EditText user;
    private EditText password;
    private EditText passwordConfirm;
    private EditText email;
    private EditText phone;
    private CheckBox terms;
    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        user = (EditText) v.findViewById(R.id.username_entry);

        password = (EditText) v.findViewById(R.id.password_entry);

        passwordConfirm = (EditText) v.findViewById(R.id.password_confirm);

        email = (EditText) v.findViewById(R.id.email_entry);

        phone = (EditText) v.findViewById(R.id.phone_entry);

        terms = (CheckBox) v.findViewById(R.id.terms);

        submit = (Button) v.findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast toast = Toast.makeText(getContext(), "You're signed up!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return v;
    }
}
