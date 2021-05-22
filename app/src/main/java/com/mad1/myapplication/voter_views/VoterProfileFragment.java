package com.mad1.myapplication.voter_views;

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
import com.mad1.myapplication.utility.Voter;
import com.mad1.myapplication.validation_utility.ValidationUtility;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoterProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoterProfileFragment extends Fragment {

    private  EditText
            voter_phone_et,
            voter_knum_et,
            voter_first_name_et,
            voter_second_name_et,
            voter_class_code_et;

    private Button
            voter_update_bt,
            voter_delete_bt,
            back_to_voter_list_bt;
    private String voter_phone = "";

    public VoterProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoterProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VoterProfileFragment newInstance(String param1, String param2) {
        VoterProfileFragment fragment = new VoterProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_voter_profile, container, false);
        voter_phone_et = view.findViewById(R.id.voter_profile_mobile_et);
        voter_knum_et = view.findViewById(R.id.voter_profile_knumber_et);
        voter_first_name_et = view.findViewById(R.id.voter_profile_first_name_et);
        voter_second_name_et = view.findViewById(R.id.voter_profile_surname_et);
        voter_class_code_et = view.findViewById(R.id.voter_profile_class_code_et);

        voter_update_bt = view.findViewById(R.id.voter_update_bt);
        voter_delete_bt = view.findViewById(R.id.delete_voter_bt);
        back_to_voter_list_bt = view.findViewById(R.id.voter_home_bt);

        if(getArguments() != null){
            VoterProfileFragmentArgs args = VoterProfileFragmentArgs.fromBundle(getArguments());
            voter_phone = args.getVoterPhone();
            if(!voter_phone.equals("no_phone")){
                ArrayList<Voter> voter_profile_data = getSingleVoterData();
                if(voter_profile_data == null){

                }
                else{
                    ValidationUtility validationUtility = new ValidationUtility();
                    if(!validationUtility.phone_test(voter_profile_data.get(0).getVoter_phone())){
                        voter_phone_et.setText("0" + voter_profile_data.get(0).getVoter_phone());
                    }
                    else{
                        voter_phone_et.setText(voter_profile_data.get(0).getVoter_phone());
                    }
                    voter_knum_et.setText(voter_profile_data.get(0).getVoter_knum());
                    voter_first_name_et.setText(voter_profile_data.get(0).getVoter_first_name());
                    voter_second_name_et.setText(voter_profile_data.get(0).getVoter_second_name());
                    voter_class_code_et.setText(voter_profile_data.get(0).getClass_code());
                }
            }
            else{
                Toast.makeText(getContext(), "No Phone In The Database", Toast.LENGTH_LONG).show();
            }
        }

        voter_update_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTextFieldsEmpty()) {
                    // Edit Text Field Empty No Action Needed
                }
                else {
                    if (isValidationPassed()){
                        long result = updateVoter();
                    if (result == 1) {
                        Toast.makeText(getContext(), "Voter Data Updated Sucessfully", Toast.LENGTH_SHORT).show();
                    }
                    if (result == -1) {
                        Toast.makeText(getContext(), "All Filds Mandatory", Toast.LENGTH_SHORT).show();
                    }
                    if (result == -2) {
                        Toast.makeText(getContext(), "Wrong Format Input", Toast.LENGTH_SHORT).show();
                    }
                    if (result == 0) {
                        Toast.makeText(getContext(), "Database Error On Updating Voter", Toast.LENGTH_SHORT).show();
                    }
                }
                    else{
                        // Validation Failed No Action Needed
                    }
                }
            }
        });

        voter_delete_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = deleteVoter();
                if(result == 1){
                    Toast.makeText(getContext(), "Voter Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Error On Deleting Voter", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_to_voter_list_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(VoterProfileFragment.this)
                        .navigate(R.id.action_voterProfileFragment_to_voterListFragment2);
            }
        });
        return view;
    }

    private Boolean isTextFieldsEmpty(){
        if(voter_phone_et.getText().toString().equals("")){
            Toast.makeText(getContext(), "Fill Phone Text Field Please", Toast.LENGTH_LONG).show();
            return false;
        }
        if(voter_knum_et.getText().toString().equals("")){
            Toast.makeText(getContext(), "Fill KNumber Text Field Please", Toast.LENGTH_LONG).show();
            return false;
        }
        if(voter_first_name_et.getText().toString().equals("")){
            Toast.makeText(getContext(), "Fill First Name Text Field Please", Toast.LENGTH_LONG).show();
            return false;
        }
        if(voter_second_name_et.getText().toString().equals("")){
            Toast.makeText(getContext(), "Fill Second Name Text Field Please", Toast.LENGTH_LONG).show();
            return false;
        }
        if(voter_class_code_et.getText().toString().equals("")){
            Toast.makeText(getContext(), "Fill Class Code Text Field Please", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private Boolean isValidationPassed(){
        ValidationUtility validationUtility = new ValidationUtility();
        if(!validationUtility.word_input( voter_first_name_et.getText().toString())) {
            Toast.makeText(getContext(), "First Name Validation Failed", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validationUtility.word_input(voter_second_name_et.getText().toString())){
            Toast.makeText(getContext(), "Second Name Validation Failed", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validationUtility.phone_test(voter_phone_et.getText().toString())){
            Toast.makeText(getContext(), "Not Valid Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validationUtility.validate_class_code_input(voter_class_code_et.getText().toString())){
            Toast.makeText(getContext(), "Not Valid Class Code", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validationUtility.k_number_validation(voter_knum_et.getText().toString())){
            Toast.makeText(getContext(), "Not Valid K Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private long deleteVoter(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        return utility.deleteVoter(voter_phone);
    }

    private long updateVoter() {
        if(voter_phone_et.getText().toString().equals("")||
                voter_knum_et.getText().toString().equals("")||
                voter_first_name_et.getText().toString().equals("")||
                voter_second_name_et.getText().toString().equals("")||
                voter_class_code_et.getText().toString().equals("")){
            return -1;
        }
        System.out.println("First Success");
        ValidationUtility validationUtility = new ValidationUtility();
        if(!validationUtility.k_number_validation(voter_knum_et.getText().toString())||
        !validationUtility.phone_test(voter_phone_et.getText().toString())||
        !validationUtility.word_input(voter_first_name_et.getText().toString())||
        !validationUtility.word_input(voter_second_name_et.getText().toString())||
        !validationUtility.validate_class_code_input(voter_class_code_et.getText().toString())){
            return -2;
        }
        else {
            System.out.println("Second Passed");//=============================================================
            DatabaseUtility utility = new DatabaseUtility();
            utility.setDataBaseHelper(getContext());
            long result = utility.updateVoter(voter_phone,
                    voter_phone_et.getText().toString(),
                    voter_knum_et.getText().toString(),
                    voter_first_name_et.getText().toString(),
                    voter_second_name_et.getText().toString(),
                    voter_class_code_et.getText().toString());
            System.out.println("The voter update result is " + result);
            return result;
        }
    }

    private ArrayList<Voter> getSingleVoterData(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        ArrayList<Voter> voterList = utility.getSingleVoterData(voter_phone);
        if(voterList != null){
            return voterList;
        }
        Toast.makeText(getContext(), "Error On Retrieving Voter Data", Toast.LENGTH_LONG).show();
        return null;
    }
}