package com.mad1.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.VotingResultSMSData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendResultToVotersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendResultToVotersFragment extends Fragment {

    EditText vote_result;
    Button send_result;
    String result_message = "Voting Result\n";
    private static final int PERMISSION_TO_SEND_SMS = 0;

    public SendResultToVotersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendResultToVotersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendResultToVotersFragment newInstance(String param1, String param2) {
        SendResultToVotersFragment fragment = new SendResultToVotersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // get permissions to SEND SMS
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, PERMISSION_TO_SEND_SMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_result_to_voters, container, false);
        vote_result = view.findViewById(R.id.result_et);
        send_result = view.findViewById(R.id.send_result_bt);
        send_result.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                result_message = "";
                ArrayList<String> voter_phones = getVotersPhoneNumbers();
                ArrayList<VotingResultSMSData> voting_result = sendResults();
                SmsManager smsManager = SmsManager.getDefault();
                System.out.println("Before sending Results");
                if(voting_result != null && voter_phones != null){
                    int count = 1;
                    for(VotingResultSMSData data: voting_result){
                        result_message += data.getFirst_name() + " " + data.getSecond_name() + " " + data.getVote_rate() + "\n";
                    }
                    System.out.println("********************** Result Message\n " + result_message + " ********************************");
                    for(String s: voter_phones){
                        smsManager.sendTextMessage(s,null,result_message,null,null);
                        System.out.println("Voting Result Sent To " + s);
                        System.out.println("************Voting Result Sent****************");
                    }
                    Toast.makeText(getContext(), "Voter Result Sent", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Error On Getting Voting Data", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    // get voting results
    private ArrayList<VotingResultSMSData> sendResults() {
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        return utility.sendResults();
    }
    // get voters phones
    private ArrayList<String> getVotersPhoneNumbers(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        return utility.getVotersPhoneNumbers();
    }
}