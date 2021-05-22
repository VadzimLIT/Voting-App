package com.mad1.myapplication.database_utility;

import android.content.Context;

import com.mad1.myapplication.model.MyDatabaseHelper;
import com.mad1.myapplication.utility.Admin;
import com.mad1.myapplication.utility.Candidate;
import com.mad1.myapplication.utility.Voter;
import com.mad1.myapplication.utility.VotingResultSMSData;

import java.util.ArrayList;

public class DatabaseUtility {

    private MyDatabaseHelper mdb;

    public void setDataBaseHelper(Context context){
        mdb = new MyDatabaseHelper(context);
    }

    //**************************************** LOGIN AND SIGNUP FUNCTIONALITY *****************************************
    // login check from first fragment
    public boolean loginCheck(Context context, String login, String password){
        MyDatabaseHelper mdb = new MyDatabaseHelper(context);
        return  mdb.login(login,password);
    }

    // add admin from signUp fragment
    public long addAdmin(String first_name, String second_name, String password, String mobile, String mother_name, String fav_place,String login_name){
       return mdb.addAdmin(first_name, second_name, password, mobile,mother_name,fav_place,login_name);
    }
    //*********************************** END LOGIN FUNCTIONALITY ****************************************************

    //*********************************** CANDIDATE FUNCTIONALITY *****************************************************

    public long addCandidate(String name, String surname, String mobile, byte[] imageInByte){
       return mdb.addCandidate(name,surname,mobile,imageInByte);
    }
    public int getNumberOfCandidates(){
        return mdb.getNumberOfCandidates();
    }

    public ArrayList<Candidate> getAllCandidatesData(){
        return mdb.getAllCandidates();
    }

    public int getVotesPerCandidate(int id){
        return mdb.getVotesPerCandidate(id);
    }

    public ArrayList<Candidate> getSingleCandidateData(int candidate_id){
        return  mdb.getSingleCandidateData(candidate_id);
    }

    public long updateCandidate(int candidate_id, String first_name, String second_name, String phone, byte[] image){
        return mdb.updateCandidate(candidate_id, first_name, second_name, phone, image);
    }

    public long deleteCandidate(int candidate_id){
        return mdb.deleteCandidate(candidate_id);
    }

    //*********************************** END CANDIDATE FUNCTIONALITY ********************************************

    //*********************************** VOTER FUNCTIONALITY **********************************************************

    public long addVoter(String mobile, String k_num, String name, String surname, String class_code){
        return mdb.addVoter(mobile, k_num, name, surname, class_code);
    }

    public ArrayList<Voter> getAllVoters(){
       return mdb.getAllVoters();
    }

    public  ArrayList<Voter> getSingleVoterData(String voter_phone){
        return mdb.getSingleVoterData(voter_phone);
    }

    public long updateVoter(String phone,String voter_phone, String voter_knum, String voter_name, String voter_surname, String class_code){
        return mdb.updateVoter(phone, voter_phone, voter_knum, voter_name, voter_surname, class_code);
    }

    public long deleteVoter(String voter_phone){
        return mdb.deleteVoter(voter_phone);
    }
    //*********************************** END VOTER FUNCTIONALITY *************************************************

    // ************************************** SEND RESULTS FUNCTIONALITY START *********************************************
    // get voting results
    public ArrayList<VotingResultSMSData> sendResults(){
        return mdb.sendResults();
    }
    // get voters phone numbers to send result sms
    public ArrayList<String> getVotersPhoneNumbers(){
        return mdb.getVotersPhoneNumbers();
    }

    //****************************************** VOTING FUNCTIONALITY ******************************
    public long addVotestoVotingTable(String vote, String phone){
        return mdb.addVotestoVotingTable(vote,phone);
    }

    // **********************************Admin Functionality ***************************

    public Boolean isDataInAdminTable(){
        return mdb.isDataInAdminTable();
    }
    public ArrayList<Admin> getAdminsForList(){
        return mdb.getAdminsForList();
    }

    public Admin getSingleAdmin(int admin_id){
        return mdb.getSingleAdmin(admin_id);
    }

    public long updateAdmin(int admin_id, String name, String surname, String password,String phone,String mother_maiden_name, String fav_place,String login_name){
        return mdb.updateAdmin(admin_id, name,surname,password,phone, mother_maiden_name,fav_place,login_name);
    }

    public long deleteAdmin(int admin_id) {
        return mdb.deleteAdmin(admin_id);
    }

    // get candidate date to send start vote sms
    public ArrayList<Candidate> getVoteCandidateData(){
        return mdb.getVoteData();
    }

    public int resetPassword(String mother_name, String fav_place){
        return mdb.resetPassword(mother_name,fav_place);
    }

    public long resetPasswordSecondStep(int id, String password){
        return mdb.resetPasswordSecondStep(id, password);
    }

    public void deleteAllVotingData(){
        mdb.deleteAllVotingData();
    }
}
