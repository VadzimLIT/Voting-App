package com.mad1.myapplication.utility;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class Candidate {
    private  int candidate_id;
    private String
            first_name,
            second_name,
            phone;
    private Bitmap candidate_image;
    public static int numberOfCandidates = 0;
    public Candidate() {
    }

    public Candidate(int candidate_id, String first_name, String second_name, String phone, Bitmap candidate_image) {
        this.candidate_id = candidate_id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.phone = phone;
        this.candidate_image = candidate_image;
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getCandidate_image() {

        return candidate_image;
    }

    public void setCandidate_image(Bitmap candidate_image) {
        this.candidate_image = candidate_image;
    }
}
