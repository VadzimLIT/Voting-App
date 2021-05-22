package com.mad1.myapplication.voter_views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mad1.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoterMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoterMenuFragment extends Fragment {

    Button add_voter_button, voter_list_button;

    public VoterMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoterMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VoterMenuFragment newInstance(String param1, String param2) {
        VoterMenuFragment fragment = new VoterMenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_voter_menu, container, false);
        add_voter_button = view.findViewById(R.id.voter_list_to_voter_add_button);
        voter_list_button = view.findViewById(R.id.voter_menu_to_voter_list_button);
        add_voter_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(VoterMenuFragment.this).
                        navigate(R.id.action_voterListFragment_to_voterAddFragment);
            }
        });

        voter_list_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(VoterMenuFragment.this)
                        .navigate(R.id.action_voterListFragment_to_voterListFragment2);

            }
        });
        return view;
    }
}