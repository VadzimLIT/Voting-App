package com.mad1.myapplication.voter_views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mad1.myapplication.R;
import com.mad1.myapplication.adapters.VoterListAdapter;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.Voter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoterListFragment extends Fragment {

    RecyclerView recyclerView_id;
    ArrayList<Voter> voterArrayList = null;
    VoterListAdapter voterListAdapter;
    Button voter_list_back_to_candidates_list_bt;

    public VoterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VoterListFragment newInstance(String param1, String param2) {
        VoterListFragment fragment = new VoterListFragment();
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
        View view = inflater.inflate(R.layout.fragment_voter_list, container, false);
        recyclerView_id = view.findViewById(R.id.voter_list_recycle_view);
        voter_list_back_to_candidates_list_bt = view.findViewById(R.id.voter_list_back_to_candidates_list_bt);
        getAllVoters();
        voterListAdapter = new VoterListAdapter(getContext(), voterArrayList);
        recyclerView_id.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView_id.setAdapter(voterListAdapter);
        voterListAdapter.setVoterListOnClickListener(new VoterListAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                NavController navController = Navigation.findNavController(view);
                String voter_phone = voterArrayList.get(position).getVoter_phone();
                VoterListFragmentDirections.ActionVoterListFragment2ToVoterProfileFragment action =
                        VoterListFragmentDirections.actionVoterListFragment2ToVoterProfileFragment();
                action.setVoterPhone(voter_phone);
                navController.navigate(action);
            }
        });

        voter_list_back_to_candidates_list_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(VoterListFragment.this).navigate(R.id.action_voterListFragment2_to_SecondFragment);
            }
        });

        return view;
    }

    private void getAllVoters(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        voterArrayList = utility.getAllVoters();
    }
}