package com.mad1.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.validation_utility.ValidationUtility;

import java.time.temporal.ValueRange;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordSecondStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordSecondStepFragment extends Fragment {

    EditText change_password_et, change_confirm_password_et;
    Button change_admin_password_bt, change_password_back_to_login_bt;

    public ResetPasswordSecondStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordSecondStepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPasswordSecondStepFragment newInstance(String param1, String param2) {
        ResetPasswordSecondStepFragment fragment = new ResetPasswordSecondStepFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password_second_step, container, false);
        change_password_et = view.findViewById(R.id.change_password_et);
        change_confirm_password_et = view.findViewById(R.id.change_confirm_password_et);
        change_admin_password_bt = view.findViewById(R.id.change_admin_password_bt);
        change_password_back_to_login_bt = view.findViewById(R.id.change_password_back_to_login_bt);

        change_admin_password_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getArgs();
                if(id != -1) {
                    if (isPasswordValid()) {


                    long result = resetPasswordSecondStep(id);
                    if (result == 0) {

                    } else if (result == -1) {
                        Toast.makeText(getContext(), "Error Occured On Changing Password", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Password Changed Successfully", Toast.LENGTH_LONG).show();
                    }
                }
                    else{
                        Toast.makeText(getContext(), "Password is not Valid", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Error Occured On Tranfer Data", Toast.LENGTH_LONG).show();
                }
            }
        });

        change_password_back_to_login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ResetPasswordSecondStepFragment.this)
                        .navigate(R.id.action_resetPasswordSecondStepFragment_to_FirstFragment);
            }
        });
        return view;
    }

    private boolean isPasswordValid(){
        return ValidationUtility.password_validation(change_password_et.getText().toString());
    }

    private long resetPasswordSecondStep(int id){
        if(!ifPasswordsMatch()){
            Toast.makeText(getContext(), "Password Fields Not Match Or Empty", Toast.LENGTH_LONG).show();
            return 0;
        }
        else{
            DatabaseUtility utility = new DatabaseUtility();
            utility.setDataBaseHelper(getContext());
            return utility.resetPasswordSecondStep(id, change_password_et.getText().toString());
        }
    }

    private Boolean ifPasswordsMatch(){
        if(change_password_et.getText().toString().equals("") ||
                change_confirm_password_et.getText().toString().equals("")||
                !change_password_et.getText().toString().equals(change_confirm_password_et.getText().toString()))
            return false;
        else
            return true;
    }

    private int getArgs(){
        if(getArguments() != null){
            ResetPasswordSecondStepFragmentArgs args = ResetPasswordSecondStepFragmentArgs.fromBundle(getArguments());
            if(args.getAdminId() != -1)
               return args.getAdminId();
            else return -1;
        }
        else{
            return -1;
        }
    }
}