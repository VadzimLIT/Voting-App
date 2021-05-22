package com.mad1.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad1.myapplication.database_utility.DatabaseUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {
    EditText change_password_mother_name_et,
            change_password_favourite_place_et;
    Button change_admin_password_bt,
            change_admin_password_back_to_login_bt;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        change_password_mother_name_et = view.findViewById(R.id.change_password_mother_name_et);
        change_password_favourite_place_et = view.findViewById(R.id.change_password_favourite_place_et);
        change_admin_password_bt = view.findViewById(R.id.change_admin_password_bt);
        change_admin_password_back_to_login_bt = view.findViewById(R.id.change_admin_password_back_to_login_bt);
        change_admin_password_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                resetPassword(view);
            }
        });

        change_admin_password_back_to_login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ResetPasswordFragment.this)
                        .navigate(R.id.action_resetPasswordFragment_to_FirstFragment);
            }
        });
        return view;
    }

    private void resetPassword(View view){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        int result = -1;
        if(change_password_mother_name_et.getText().toString().equals("") ||
                change_password_favourite_place_et.getText().toString().equals("")){
            Toast.makeText(getContext(), "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
        }
        else{
            result = utility.resetPassword(change_password_mother_name_et.getText().toString(),
                    change_password_favourite_place_et.getText().toString());
            if( result == -1){
                Toast.makeText(getContext(), "Error Security Questions Not Found", Toast.LENGTH_SHORT).show();
            }
            else{
                NavController navController = Navigation.findNavController(view);
                ResetPasswordFragmentDirections.ActionResetPasswordFragmentToResetPasswordSecondStepFragment action =
                        ResetPasswordFragmentDirections.actionResetPasswordFragmentToResetPasswordSecondStepFragment();
                action.setAdminId(result);
                navController.navigate(action);
            }
        }

    }
}