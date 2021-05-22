package com.mad1.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.mad1.myapplication.database_utility.DatabaseUtility;

public class FirstFragment extends Fragment {

    EditText et_login,
             et_password;

    Button button_login,
           button_signup,
           button_forgot;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_login = view.findViewById(R.id.login);
        et_password = view.findViewById(R.id.password);
        button_login = view.findViewById(R.id.button_login);
        button_signup = view.findViewById(R.id.button_signup);
        button_forgot = view.findViewById(R.id.retrieve_login_and_password);

        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login() == true) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                }
                else{
                    Toast.makeText(getContext(), "Invalid User", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_signUpFragment);
            }
        });

        button_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_resetPasswordFragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        et_login.setText("");
        et_password.setText("");
    }

    private boolean login(){
        DatabaseUtility dbutil = new DatabaseUtility();
        System.out.println("/************* " + getContext().toString() + " Context *****************************");
        return dbutil.loginCheck(getContext(), et_login.getText().toString(), et_password.getText().toString());
    }
}