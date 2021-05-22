package com.mad1.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.Candidate;
import com.mad1.myapplication.utility.Voter;
import com.mad1.myapplication.validation_utility.ValidationUtility;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendVotingStartSmsToVotersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendVotingStartSmsToVotersFragment extends Fragment {

    Button start_vote_bt, send_start_vote_home_bt;
    String voting_message = "";
    String voter_phone = "";
    private static final int PERMISSION_TO_SEND_SMS = 0;

    public SendVotingStartSmsToVotersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendVotingStartSmsToVotersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendVotingStartSmsToVotersFragment newInstance(String param1, String param2) {
        SendVotingStartSmsToVotersFragment fragment = new SendVotingStartSmsToVotersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // get permissions to SEND SMS
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.SEND_SMS}, PERMISSION_TO_SEND_SMS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("Send Voting SMS start");
        View view = inflater.inflate(R.layout.fragment_send_voting_start_sms_to_voters,
                container, false);
        start_vote_bt = view.findViewById(R.id.start_vote_bt);
        start_vote_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                int numberOfCandidates = getNumberOfCandidates();
                if(numberOfCandidates != 0) {
                    Candidate.numberOfCandidates = numberOfCandidates;
                    ArrayList<Candidate> candidates = getVoteCandidateData();
                    ArrayList<Voter> voters = getVotersData();
                   // createVotingMessage(candidates, voters);
                    if(voters != null) {
                        int count = voters.size();
                    }
                    else {
                        Toast.makeText(getContext(), "Unable to Send SMS Voter Data Empty",
                                Toast.LENGTH_LONG).show();
                    }
                    if(candidates != null && voters != null) {
                        for (Voter vts : voters) {
                            voter_phone = vts.getVoter_phone();
                            voting_message = vts.getVoter_first_name() + " " +
                                    vts.getVoter_second_name() + "\n" +
                                    " Here the candidates list for our election:\n " +
                                    "The Vote Is Made By Sending a Number In Front Of Each " +
                                    "Candidate To This Number \n";
                            for (Candidate c : candidates) {
                                System.out.println("Cadidate id: " + c.getCandidate_id() +
                                        c.getFirst_name() + " " + c.getSecond_name());
                                voting_message += c.getCandidate_id() + " " + c.getFirst_name()
                                        + " " + c.getSecond_name() + "\n" +
                                        c.getPhone() + "\n";
                            }
                            // send sms
                            smsManager.sendTextMessage(voter_phone,null,voting_message,
                                    null,null);
                            System.out.println("\nStart Voting SMS Data " + voting_message);
                            voting_message = " ";
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Error On Getting Voting Data",
                                Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getContext(), "Start Vote SMS Sent Successfully",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "There are no cadidates registered",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        send_start_vote_home_bt = view.findViewById(R.id.send_start_vote_home_bt);
        send_start_vote_home_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SendVotingStartSmsToVotersFragment.this)
                        .navigate(R.id.action_sendVotingStartSmsToVotersFragment_to_SecondFragment);
            }
        });
        return view;
    }

    private int getNumberOfCandidates(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        return utility.getNumberOfCandidates();
    }

    private ArrayList<Candidate> getVoteCandidateData(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        return utility.getVoteCandidateData();
    }

    private ArrayList<Voter> getVotersData(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        return utility.getAllVoters();
    }

//    private void createVotingMessage(ArrayList<Candidate>candidates, ArrayList<Voter>voters){
//        if(voters != null) {
//            int count = voters.size();
//        }
//        else {
//            Toast.makeText(getContext(), "Unable to Send SMS Voter Data Empty", Toast.LENGTH_LONG).show();
//        }
////        String voting_message = "";**********************************************************************************************
////        String voter_phone = "";
//        if(candidates != null && voters != null) {
//            for (Voter v : voters) {
//                voter_phone = v.getVoter_phone();
//                voting_message = v.getVoter_first_name() + " " + v.getVoter_second_name() + "\n" +
//                        " Here the candidates list for our election:\n " +
//                        "The Vote Is Made By Sending a Number In Front Of Each Candidate To This Number \n";
//                for (Candidate c : candidates) {
//                    System.out.println("Cadidate id: " + c.getCandidate_id() + c.getFirst_name() + " " + c.getSecond_name());
//                    voting_message += c.getCandidate_id() + " " + c.getFirst_name() + " " + c.getSecond_name() + "\n" +
//                            c.getPhone() + "\n";
//                }
//                // send sms
//                sendSMS(voter_phone, voting_message);
//                System.out.println("\nStart Voting SMS Data " + voting_message);
//                voting_message = " ";
//            }
//        }
//        else{
//            Toast.makeText(getContext(), "Error On Getting Voting Data", Toast.LENGTH_LONG).show();
//        }
//        Toast.makeText(getContext(), "Start Vote SMS Sent Successfully", Toast.LENGTH_LONG).show();
//    }
//
//    private void sendSMS(String voter_phone, String voting_message){
//        System.out.println("Phone before validation " + voter_phone);
//        SmsManager smsManager = SmsManager.getDefault();
//        System.out.println("Send start vote sms " + voter_phone);
//        //smsManager.sendTextMessage(voter_phone,null, voting_message, null,null);
//        smsManager.sendTextMessage(voter_phone,null,voting_message,null,null);
//    }

}