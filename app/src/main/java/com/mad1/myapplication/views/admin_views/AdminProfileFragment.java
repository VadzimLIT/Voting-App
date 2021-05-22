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
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.Admin;
import com.mad1.myapplication.validation_utility.ValidationUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProfileFragment extends Fragment {

    private EditText
            admin_profile_name_et,
            admin_profile_surname_et,
            admin_profile_password_et,
            admin_profile_confirm_password_et,
            admin_profile_phone_et,
            admin_profile_mother_name_et,
            admin_profile_fav_place_et,
            admin_profile_login_name_et;
    private Button
            admin_update_bt,
            delete_admin_bt,
            admin_profile_home_bt;
    private int admin_id = -1;
    Admin admin;

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProfileFragment newInstance(String param1, String param2) {
        AdminProfileFragment fragment = new AdminProfileFragment();
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
        View view =  inflater.inflate(R.layout.fragment_admin_profile, container, false);
        admin_profile_name_et = view .findViewById(R.id.admin_profile_name_et);
        admin_profile_surname_et = view.findViewById(R.id. admin_profile_surname_et);
        admin_profile_password_et = view.findViewById(R.id.admin_profile_password_et);
        admin_profile_confirm_password_et = view.findViewById(R.id.admin_profile_confirm_password_et);
        admin_profile_phone_et = view.findViewById(R.id. admin_profile_phone_et);
        admin_profile_mother_name_et = view.findViewById(R.id.admin_profile_mother_name_et);
        admin_profile_fav_place_et = view.findViewById(R.id.admin_profile_fav_place_et);
        admin_profile_login_name_et = view.findViewById(R.id.admin_profile_login_name_et);

        admin_update_bt = view.findViewById(R.id.admin_update_bt);
        delete_admin_bt = view.findViewById(R.id.delete_admin_bt);
        admin_profile_home_bt = view.findViewById(R.id.admin_profile_home_bt);
        admin_profile_home_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminProfileFragment.this).navigate(R.id.action_adminProfileFragment_to_adminListFragment);
            }
        });

        if(getArguments() != null){
            AdminProfileFragmentArgs args = AdminProfileFragmentArgs.fromBundle(getArguments());
            admin_id = args.getAdminId();
            if(admin_id != -1){
                admin = getSingleAdmin();
                if(admin != null){
                    admin_profile_name_et.setText(admin.getAdmin_first_name());
                    admin_profile_surname_et.setText(admin.getAdmin_second_name());
                    admin_profile_password_et.setText(admin.getAdmin_password());
                    admin_profile_phone_et.setText(admin.getAdmin_phone());
                    admin_profile_mother_name_et.setText(admin.getMother_maiden_name());
                    admin_profile_fav_place_et.setText(admin.getFav_place());
                    admin_profile_login_name_et.setText(admin.getLogin_name());
                }
                else{
                    Toast.makeText(getContext(), "Error On Getting Admin Data", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getContext(), "Error On Getting Admin Data", Toast.LENGTH_LONG).show();
            }

            admin_update_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(updateAdmin()== 1){
                        Toast.makeText(getContext(), "Admin Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                    else if(updateAdmin() == -2){
                        Toast.makeText(getContext(), "Security Questions Update Technical Fail", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Error On Updating Admin", Toast.LENGTH_LONG).show();
                    }
                }
            });

            delete_admin_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(deleteAdmin() == 1){
                        Toast.makeText(getContext(), "Admin Deleted Successfully", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Error On Deleting Admin", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return view;
    }

    private long deleteAdmin(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        return utility.deleteAdmin(admin_id);
    }

    private long updateAdmin(){
       String name =  admin_profile_name_et.getText().toString();
       String surname = admin_profile_surname_et.getText().toString();
       String password =  admin_profile_password_et.getText().toString();
       String conf_pass = admin_profile_confirm_password_et.getText().toString();
       String phone = admin_profile_phone_et.getText().toString();
       String mother_maiden_name = admin_profile_mother_name_et.getText().toString();
       String fav_place = admin_profile_fav_place_et.getText().toString();
       String login_name = admin_profile_login_name_et.getText().toString();

       if(name.equals("") ||
               surname.equals("")||
               password.equals("")||
               conf_pass.equals("")||
               phone.equals("") ||
               mother_maiden_name.equals("")||
               fav_place.equals("")||
               login_name.equals("")
       ){
          Toast.makeText(getContext(), "All Text Fields Are Mandatory", Toast.LENGTH_LONG).show();
       }
       else {
           if (isValidationPassed()) {
               if (password.equals(conf_pass)) {
                   DatabaseUtility utility = new DatabaseUtility();
                   utility.setDataBaseHelper(getContext());
                   return utility.updateAdmin(admin_id, name, surname, password, phone,mother_maiden_name,fav_place,login_name);
               } else {
                   Toast.makeText(getContext(), "Passwords Not Match", Toast.LENGTH_LONG).show();
                   return -1;
               }
           }
           else{

           }
       }
       return -1;
    }

    private Boolean isValidationPassed() {
        ValidationUtility validationUtility = new ValidationUtility();
        if (!validationUtility.word_input(admin_profile_name_et.getText().toString())) {
            Toast.makeText(getContext(), "First Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!validationUtility.word_input(admin_profile_surname_et.getText().toString())){
            Toast.makeText(getContext(), "Second Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!validationUtility.phone_test(admin_profile_phone_et.getText().toString())){
            Toast.makeText(getContext(), "Phone Number Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!validationUtility.word_input(admin_profile_mother_name_et.getText().toString())){
            Toast.makeText(getContext(), "Mother Maiden Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!validationUtility.word_input(admin_profile_fav_place_et.getText().toString())){
            Toast.makeText(getContext(), "Favourite Place Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!ValidationUtility.login_validation(admin_profile_login_name_et.getText().toString())){
            Toast.makeText(getContext(), "Login Name Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!ValidationUtility.password_validation(admin_profile_password_et.getText().toString())){
            Toast.makeText(getContext(), "Password Validation Failed", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private Admin getSingleAdmin(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        return utility.getSingleAdmin(admin_id);
    }
}