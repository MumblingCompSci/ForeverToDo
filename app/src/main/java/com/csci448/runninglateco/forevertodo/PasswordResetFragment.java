package com.csci448.runninglateco.forevertodo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tkarol on 3/15/18.
 */

public class PasswordResetFragment extends Fragment {
    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mConfirmPassword;
    private Button mSubmitButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_password_change, container, false);

        mOldPassword = (EditText) v.findViewById(R.id.old_password_entry);

        mNewPassword = (EditText) v.findViewById(R.id.new_password_entry);

        mConfirmPassword = (EditText) v.findViewById(R.id.new_password_confirm);

        mSubmitButton = (Button) v.findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getContext(), "Goes to Login page", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return v;
    }
}
