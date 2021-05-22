package com.mad1.myapplication.SMSutility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.model.MyDatabaseHelper;
import com.mad1.myapplication.validation_utility.ValidationUtility;

public class SMSReceiver extends BroadcastReceiver {
    MyDatabaseHelper mdb;
    SQLiteDatabase db;
    Context context1;
    @Override
    public void onReceive(Context context, Intent intent) {
        context1 = context;
        Bundle bundle = intent.getExtras();
        SmsMessage[] sms = null;
        String messageReceived ="";
        if(bundle != null){
            Object[] pdus = (Object[])bundle.get("pdus");
            sms = new SmsMessage[pdus.length];
            for(int i = 0; i < sms.length; i++){
                sms[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                messageReceived += sms[i].getMessageBody().toString();
                messageReceived += "\n";
            }
            // display new SMS message
            Toast.makeText(context, messageReceived, Toast.LENGTH_LONG).show();
            //Get Sender Phone Number
            Toast.makeText(context, sms[0].getOriginatingAddress(), Toast.LENGTH_LONG).show();
            // get number of candidates for validation
            int number_of_candidates = getNumberOfCandidates(context);
            // log
            System.out.println("Message Received " + messageReceived.getClass().getTypeName());
            int candidate_number = castMessage(messageReceived, sms[0].getOriginatingAddress());
            if(!ValidationUtility.validate_candidate_number_from_sms(
                    String.valueOf(candidate_number))){
                sendFaultVotingSMS(sms[0].getOriginatingAddress(), "Voting message " +
                        "is " +
                        "incorrect. It must contain a candidate number");
            }
            else{
                System.out.println("CANDIDATE NUMBER IS " + candidate_number);
                if(candidate_number > number_of_candidates ){
                    // send sms to voter that candidate number is incorrect
                    sendFaultVotingSMS(sms[0].getOriginatingAddress(), "Candidate " +
                            "number does not exist. Please Try again");
                }
                else if (candidate_number <= 0){
                    // send sms to voter that candidate number is incorrect
                    sendFaultVotingSMS(sms[0].getOriginatingAddress(), "Candidate " +
                            "number does not exist. Please Try again");
                }
                else {
                    // log
                    System.out.println("ADD VOTERS TO TABLE PHONE " +
                            sms[0].getOriginatingAddress());
                    addVotestoVotingTable(messageReceived, sms[0].getOriginatingAddress());
                }
            }
        }
    }

    private int castMessage(String message, String phone){
        String trimed_message = message.trim();
        System.out.println("TRIMMED MESSAGE " + trimed_message);
        int message_int = -1;
        try {
            message_int = Integer.parseInt(trimed_message);
        }
        catch (NumberFormatException e){
            sendFaultVotingSMS(phone, "SMS must contain only a " +
                    "candidate number.\nCheck Voting SMS");
            // log
            System.out.println("Message Received Number Format Exception");
        }
        return message_int;
    }

    private void sendFaultVotingSMS(String voter_phone, String voting_message){
        // log
        System.out.println("SEND FAULT VOTE SMS " + voter_phone);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(voter_phone,null, voting_message,
                null,null);
    }

    private void addVotestoVotingTable(String vote, String phone){
        System.out.println("ADD VOTE TO VOTING TABLE " + "\n PHONE NUMBER: " + phone +
                "\nVOTE: " + vote);
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(context1);
        if(utility.addVotestoVotingTable(vote,phone) == -1){
            // log
            System.out.println("Vote Insert Error");
        }
    }

    private int getNumberOfCandidates(Context context){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(context);
        return utility.getNumberOfCandidates();
    }
}
