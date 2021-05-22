package com.mad1.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.views.admin_views.AdminListFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteVotingDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteVotingDataFragment extends Fragment {

    Button delete_voting_data_bt, delete_voting_data_back_home_bt;

    public DeleteVotingDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteVotingDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteVotingDataFragment newInstance(String param1, String param2) {
        DeleteVotingDataFragment fragment = new DeleteVotingDataFragment();
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
        View view = inflater.inflate(R.layout.fragment_delete_voting_data, container, false);
        delete_voting_data_bt = view.findViewById(R.id.delete_voting_data_bt);
        delete_voting_data_back_home_bt = view.findViewById(R.id.delete_voting_data_back_home_bt);

        delete_voting_data_back_home_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DeleteVotingDataFragment.this)
                        .navigate(R.id.action_deleteVotingDataFragment_to_SecondFragment);
            }
        });

        delete_voting_data_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                deleteAllVotingData();
            }
        });
        return view;
    }

    private void deleteAllVotingData(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        utility.deleteAllVotingData();
    }
}