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
import com.mad1.myapplication.SignUpFragment;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.validation_utility.ValidationUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoterAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoterAddFragment extends Fragment {

    EditText mobile_et,
            knumber_et,
            name_et,
            surname_et,
            class_code_et;

    Button add_voter_bt,
            back_to_voter_list_bt,
            back_to_main_bt;

    public VoterAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoterAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VoterAddFragment newInstance(String param1, String param2) {
        VoterAddFragment fragment = new VoterAddFragment();
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
        View view = inflater.inflate(R.layout.fragment_voter_add, container, false);
//
//        Button add_voter_bt,
//                back_to_voter_list_bt,
//                back_to_main_bt;
        mobile_et = view.findViewById(R.id.voter_add_mobile_et);
        knumber_et = view.findViewById(R.id.voter_add_knumber_et);
        name_et = view.findViewById(R.id.voter_name_et);
        surname_et = view.findViewById(R.id.voter_surname_et);
        class_code_et = view.findViewById(R.id.voter_class_code_et);

        add_voter_bt = view.findViewById(R.id.voter_add_button);
        back_to_voter_list_bt = view.findViewById(R.id.cadidate_back_to_list_button);
        back_to_main_bt = view.findViewById(R.id.voter_home_bt);

        add_voter_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(
                        mobile_et.getText().toString().equals("") ||
                        knumber_et.getText().toString().equals("") ||
                        name_et.getText().toString().equals("") ||
                        surname_et.getText().toString().equals("") ||
                        class_code_et.getText().toString().equals("")){

                    Toast.makeText(getContext(), "Fields Empty Or Incorrect Format", Toast.LENGTH_LONG).show();
                }
                else {
                    if (isValidationPassed()) {
                        if (addVoter(
                                mobile_et.getText().toString(),
                                knumber_et.getText().toString(),
                                name_et.getText().toString(),
                                surname_et.getText().toString(),
                                class_code_et.getText().toString()) != -1) {
                            Toast.makeText(getContext(), " Voter Data Added Successfully", Toast.LENGTH_SHORT).show();
                            resetFields();
                        } else {
                            Toast.makeText(getContext(), "Voter Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        //Validation Failed
                    }
                }
            }
        });

        back_to_voter_list_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(VoterAddFragment.this)
                        .navigate(R.id.action_voterAddFragment_to_voterListFragment2);
            }
        });

        back_to_main_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(VoterAddFragment.this).navigate(R.id.action_voterAddFragment_to_SecondFragment);
            }
        });

        return view;
    }

    private Boolean isValidationPassed(){
        ValidationUtility validationUtility = new ValidationUtility();
        if(!validationUtility.word_input( name_et.getText().toString())) {
            Toast.makeText(getContext(), "First Name Validation Failed", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validationUtility.word_input(surname_et.getText().toString())){
            Toast.makeText(getContext(), "Second Name Validation Failed", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validationUtility.phone_test(mobile_et.getText().toString())){
            Toast.makeText(getContext(), "Not Valid Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validationUtility.validate_class_code_input(class_code_et.getText().toString())){
            Toast.makeText(getContext(), "Not Valid Class Code", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validationUtility.k_number_validation(knumber_et.getText().toString())){
            Toast.makeText(getContext(), "Not Valid K Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private long addVoter(String mobile, String k_num, String name, String surname, String class_code){
        DatabaseUtility du = new DatabaseUtility();
        du.setDataBaseHelper(getContext());
        return du.addVoter(mobile, k_num, name, surname, class_code);
    }

    private void resetFields(){
        mobile_et.setText("");
        knumber_et.setText("");
        name_et.setText("");
        surname_et.setText("");
        class_code_et.setText("");
    }
}