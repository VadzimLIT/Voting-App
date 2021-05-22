package com.mad1.myapplication.utility;

public class Voter {
    private String voter_first_name,
            voter_second_name,
            voter_phone,
            voter_knum,
            class_code;

    public Voter() {
    }

    public Voter(String k_num, String voter_first_name, String voter_second_name, String voter_phone) {
        this.voter_knum = k_num;
        this.voter_first_name = voter_first_name;
        this.voter_second_name = voter_second_name;
        this.voter_phone = voter_phone;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getVoter_knum() {
        return voter_knum;
    }

    public String getVoter_first_name() {
        return voter_first_name;
    }

    public String getVoter_second_name() {
        return voter_second_name;
    }

    public String getVoter_phone() {
        return voter_phone;
    }

    public void setVoter_knum(String voter_knum) {
        this.voter_knum = voter_knum;
    }

    public void setVoter_first_name(String voter_first_name) {
        this.voter_first_name = voter_first_name;
    }

    public void setVoter_second_name(String voter_second_name) {
        this.voter_second_name = voter_second_name;
    }

    public void setVoter_phone(String voter_phone) {
        this.voter_phone = voter_phone;
    }
}
