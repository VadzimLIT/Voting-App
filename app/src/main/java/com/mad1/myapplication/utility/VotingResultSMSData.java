package com.mad1.myapplication.utility;

public class VotingResultSMSData {
    private String first_name, second_name, vote_rate;
    private int candidate_id;

    public VotingResultSMSData() {
    }

    public VotingResultSMSData(String first_name, String second_name, String vote_rate, int candidate_id) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.vote_rate = vote_rate;
        this.candidate_id = candidate_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getVote_rate() {
        return vote_rate;
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setVote_rate(String vote_rate) {
        this.vote_rate = vote_rate;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }
}
