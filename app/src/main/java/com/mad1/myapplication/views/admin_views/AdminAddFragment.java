package com.mad1.myapplication.views.admin_views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad1.myapplication.R;
import com.mad1.myapplication.utility.AdminAddUtility;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.validation_utility.ValidationUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAddFragment extends Fragment {

    EditText name_et,
            surname_et,
            password_et,
            conf_password_et,
            phone_et,
            add_admin_mother_name_et,
            add_admin_favourite_place_et,
            add_admin_login_name_et;

    Button add_bt,
            to_list_bt,
            home_bt;

    public AdminAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAddFragment newInstance(String param1, String param2) {
        AdminAddFragment fragment = new AdminAddFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_add, container, false);

        name_et = view.findViewById(R.id.add_admin_name_et);
        surname_et = view.findViewById(R.id.add_admin_surname_et);
        password_et = view.findViewById(R.id.add_admin_password_et);
        conf_password_et = view.findViewById(R.id.add_admin_password_conf_et);
        phone_et = view.findViewById(R.id.add_admin_mobile);
        add_admin_mother_name_et = view.findViewById(R.id.add_admin_mother_name_et);
        add_admin_favourite_place_et = view.findViewById(R.id. add_admin_favourite_place_et);
        add_admin_login_name_et = view.findViewById(R.id.add_admin_login_name_et);

        add_bt = view.findViewById(R.id.add_admin_bt);
        to_list_bt = view.findViewById(R.id.add_admin_back_to_list_bt);
        home_bt = view.findViewById(R.id.add_admin_home_bt);
        // add administrator
        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isEditTextNotBlank()){
                   if(isPasswordMatch()){
                       if(isValidationPassed()) {
                           if (addAdmin() != -1) {
                               Toast.makeText(getContext(), "Admin Successfully Added", Toast.LENGTH_SHORT).show();
                           } else {
                               Toast.makeText(getContext(), "Error Admin Already Added", Toast.LENGTH_SHORT).show();
                           }
                       }
                       else{
                           System.out.println("Add Admin Validation Failed");
                       }
                   }
                   else {
                       Toast.makeText(getContext(), "Passwords Do Not Match", Toast.LENGTH_LONG).show();
                       password_et.setText("");
                       conf_password_et.setText("");
                   }
               }
               else{
                   Toast.makeText(getContext(), "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
               }
            }
        });

        to_list_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminAddFragment.this)
                        .navigate(R.id.AdminAddFragment_to_AdminListFragment);
            }
        });

        home_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminAddFragment.this)
                        .navigate(R.id.AdminAddFragment_to_SecondFragment);
            }
        });

        return view;
    }
    // check edit texts are not blank
    private Boolean isEditTextNotBlank(){
        System.out.println(conf_password_et.getText().toString() + "AdminAdd Fragmnet conf password");
        AdminAddUtility util = new AdminAddUtility();
        Boolean result;
        if(util.isEditTextNotBlank(
                name_et.getText().toString(),
                surname_et.getText().toString(),
                password_et.getText().toString(),
                conf_password_et.getText().toString(),
                phone_et.getText().toString(),
                add_admin_mother_name_et.getText().toString(),
                add_admin_favourite_place_et.getText().toString(),
                add_admin_login_name_et.getText().toString())){
            return true;
        }
        return false;
    }

    private Boolean isValidationPassed(){
        Boolean is_valid = true;
        ValidationUtility validationUtility = new ValidationUtility();
        if(!validationUtility.word_input(name_et.getText().toString())){
            System.out.println();
            Toast.makeText(getContext(),"First Name Alphabetic Input Only", Toast.LENGTH_SHORT).show();
            is_valid = false;
        }
        if(!validationUtility.word_input(surname_et.getText().toString())){
            Toast.makeText(getContext(),"Second Name Alphabetic Input Only", Toast.LENGTH_SHORT).show();
            is_valid = false;
        }
        if(!validationUtility.phone_test(phone_et.getText().toString())){
            is_valid = false;
            Toast.makeText(getContext(),"Phone Number Format Error", Toast.LENGTH_SHORT).show();
        }
        if(!validationUtility.word_input(add_admin_mother_name_et.getText().toString())){
            is_valid = false;
            Toast.makeText(getContext(),"Mother Maiden Alphabetic Input Only", Toast.LENGTH_SHORT).show();
        }
        if(!validationUtility.word_input(add_admin_favourite_place_et.getText().toString())){
            is_valid = false;
            Toast.makeText(getContext(),"Favourite Place Alphabetic Input Only", Toast.LENGTH_SHORT).show();
        }
        if(!ValidationUtility.password_validation(password_et.getText().toString())){
            is_valid = false;
            Toast.makeText(getContext(),"Password Validation Failed", Toast.LENGTH_SHORT).show();
        }
        if(!ValidationUtility.login_validation(add_admin_login_name_et.getText().toString())){
            is_valid = false;
            Toast.makeText(getContext(),"Login Validation Failed", Toast.LENGTH_SHORT).show();
        }
        return is_valid;
    }

    // check passwords are match
    private Boolean isPasswordMatch(){
        AdminAddUtility util = new AdminAddUtility();
        if(util.isPasswordMatch(password_et.getText().toString(),conf_password_et.getText().toString())){
            return true;
        }
        return false;
    }

    private long addAdmin(){
        ValidationUtility vu = new ValidationUtility();
        if(vu.word_input(name_et.getText().toString()) || vu.word_input(surname_et.getText().toString())||vu.phone_test(phone_et.getText().toString())) {
            DatabaseUtility db_util = new DatabaseUtility();
            db_util.setDataBaseHelper(getContext());
            return db_util.addAdmin(
                    name_et.getText().toString(),
                    surname_et.getText().toString(),
                    password_et.getText().toString(),
                    phone_et.getText().toString(), add_admin_mother_name_et.getText().toString(),
                    add_admin_favourite_place_et.getText().toString(),
                    add_admin_login_name_et.getText().toString());
        }
        else
            return -1;
    }
}