package com.mad1.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.validation_utility.ValidationUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    EditText et_admin_signup_first_name,
            et_admin_signup_second_name,
            et_admin_signup_password,
            et_admin_confirm_signup_password,
            et_admin_phone,
            admin_mother_name_et,
            admin_favourite_place_et,
            admin_signup_login_name;


    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        et_admin_signup_first_name = view.findViewById(R.id.admin_signup_first_name);
        et_admin_signup_second_name = view.findViewById(R.id.admin_signup_second_name);
        et_admin_signup_password = view.findViewById(R.id.admin_signup_password);
        et_admin_confirm_signup_password = view.findViewById(R.id.admin_confirm_signup_password);
        et_admin_phone = view.findViewById(R.id.admin_mobile_et);
        admin_mother_name_et = view.findViewById(R.id.admin_mother_name_et);
        admin_favourite_place_et = view.findViewById(R.id.admin_favourite_place_et);
        admin_signup_login_name = view.findViewById(R.id.admin_signup_login_name);

        // login button logic
        view.findViewById(R.id.button_signup_admin).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseUtility databaseUtility = new DatabaseUtility();
                databaseUtility.setDataBaseHelper(getContext());
                if (!databaseUtility.isDataInAdminTable()) {
                    if (addAdmin() != -1) {
                        NavHostFragment.findNavController(SignUpFragment.this)
                                .navigate(R.id.action_signUpFragment_to_FirstFragment);
                        Toast.makeText(getContext(), "Admin Sucessfully Added", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getContext(), "Error Enter Different Favourite Place", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "You Can Not Register.Application Admin Registred\nTry to Login or change password or ask admin for registration", Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(SignUpFragment.this)
                            .navigate(R.id.action_signUpFragment_to_FirstFragment);
                }
            }
        });
        return view;
    }

    private long addAdmin(){

            if (et_admin_signup_first_name.getText().toString().equals("") ||
                    et_admin_signup_second_name.getText().toString().equals("") ||
                    et_admin_signup_password.getText().toString().equals("") ||
                    et_admin_confirm_signup_password.getText().toString().equals("") ||
                    et_admin_phone.getText().toString().equals("") ||
                    admin_mother_name_et.getText().equals("") ||
                    admin_favourite_place_et.getText().equals("") ||
                    admin_signup_login_name.getText().equals("")) {

                Toast.makeText(getContext(), "All Fields are Mandatory", Toast.LENGTH_LONG).show();
                return -1;
            } else {
                if (!et_admin_signup_password.getText().toString().
                        equals(et_admin_confirm_signup_password.getText().toString())) {
                    Toast.makeText(getContext(), "Password Fields Do Not Match", Toast.LENGTH_LONG).show();
                } else {
                    if (isValidationPassed()) {
                        DatabaseUtility dbu = new DatabaseUtility();
                        dbu.setDataBaseHelper(getContext());
                        return dbu.addAdmin(
                                et_admin_signup_first_name.getText().toString(),
                                et_admin_signup_second_name.getText().toString(),
                                et_admin_signup_password.getText().toString(),
                                et_admin_phone.getText().toString(),
                                admin_mother_name_et.getText().toString(),
                                admin_favourite_place_et.getText().toString(),
                                admin_signup_login_name.getText().toString()
                        );
                    } else {
                        // Validation Failed No Action Needed
                    }
                }
            }
       return -1;
    }

    private Boolean isValidationPassed(){
        ValidationUtility validationUtility = new ValidationUtility();
        if(!validationUtility.word_input(et_admin_signup_first_name.getText().toString())){
            Toast.makeText(getContext(), "First Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!validationUtility.word_input(et_admin_signup_second_name.getText().toString())){
            Toast.makeText(getContext(), "Second Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!ValidationUtility.password_validation(et_admin_signup_password.getText().toString())){
            Toast.makeText(getContext(), "Password Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!validationUtility.phone_test(et_admin_phone.getText().toString())) {
            Toast.makeText(getContext(), "Phone Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!validationUtility.word_input(admin_mother_name_et.getText().toString())){
            Toast.makeText(getContext(), "Mother Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!validationUtility.word_input(admin_favourite_place_et.getText().toString())){
            Toast.makeText(getContext(), "Mother Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!ValidationUtility.login_validation(admin_signup_login_name.getText().toString())){
            Toast.makeText(getContext(), "Login Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}