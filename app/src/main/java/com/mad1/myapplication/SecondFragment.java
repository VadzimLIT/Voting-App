package com.mad1.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mad1.myapplication.adapters.CandidateListAdapter;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.Candidate;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    RecyclerView recyclerView_id;
    ArrayList<Candidate> candidatesList;
    CandidateListAdapter listAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        recyclerView_id = view.findViewById(R.id.candidates_recycle_view);
        getCandidatesData();

        // make adapter
        listAdapter = new CandidateListAdapter(getContext(), candidatesList);
        recyclerView_id.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView_id.setAdapter(listAdapter);
        listAdapter.setCandidatesOnClickListener(new CandidateListAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                NavController navController = Navigation.findNavController(view);
                int candidate_id = candidatesList.get(position).getCandidate_id();
                SecondFragmentDirections.SecondFragmentToCandidateListFragment action = SecondFragmentDirections.SecondFragmentToCandidateListFragment();
                action.setCandidateId(candidatesList.get(position).getCandidate_id());
                navController.navigate(action);
                // Navigate to candidate profile
            }
        });

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        // navigate to send vote results to voters
        view.findViewById(R.id.to_send_results_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.SecondFragment_to_sendResultToVotersFragment);
            }
        });
        // swipe to refresh data in recycler view
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCandidatesData();
                listAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.voter_functionality) {
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_voterListFragment);
            return true;
        }
        if(id == R.id.candidate_functionality){
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_candidateAddFragment);
            return true;
        }
        if(id == R.id.admin_functionality){
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_adminListFragment);
        }
        if(id == R.id.support){

        }
        if(id == R.id.start_voting){
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_sendVotingStartSmsToVotersFragment);
        }

        if(id == R.id.delete_all_voting_data_menu){
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_deleteVotingDataFragment);
        }


        return super.onOptionsItemSelected(item);
    }

    private void getCandidatesData(){
        DatabaseUtility utility = new DatabaseUtility();
        candidatesList = new ArrayList<>();
        utility.setDataBaseHelper(getContext());
        ArrayList<Candidate> result = utility.getAllCandidatesData();
        if(result != null) {
            for (Candidate candidate : result) {
               // if (candidate.getFirst_name().equalsIgnoreCase("vadim")) {
                 //   candidate.setThumbnail(R.drawable.me);
                    candidatesList.add(candidate);
              //  }
                //if (candidate.getFirst_name().equalsIgnoreCase("bill")) {
                 //   candidate.setThumbnail(R.drawable.bill_gates);
                   // candidatesList.add(candidate);
               // }
            }
        }
        else
            Toast.makeText(getContext(), "There is no candidates to show", Toast.LENGTH_LONG).show();
       // candidatesList = utility.getAllCandidatesData();
    }
}