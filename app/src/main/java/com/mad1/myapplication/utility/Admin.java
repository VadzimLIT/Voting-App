package com.mad1.myapplication.utility;

public class Admin {
    private int admin_id;
    private String
        admin_first_name,
        admin_second_name,
        admin_password,
        admin_phone,
        mother_maiden_name,
        fav_place,
        login_name;

    public Admin() {
    }

    public String getMother_maiden_name() {
        return mother_maiden_name;
    }

    public void setMother_maiden_name(String mother_maiden_name) {
        this.mother_maiden_name = mother_maiden_name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getFav_place() {
        return fav_place;
    }

    public void setFav_place(String fav_place) {
        this.fav_place = fav_place;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public String getAdmin_first_name() {
        return admin_first_name;
    }

    public String getAdmin_second_name() {
        return admin_second_name;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public String getAdmin_phone() {
        return admin_phone;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public void setAdmin_first_name(String admin_first_name) {
        this.admin_first_name = admin_first_name;
    }

    public void setAdmin_second_name(String admin_second_name) {
        this.admin_second_name = admin_second_name;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public void setAdmin_phone(String admin_phone) {
        this.admin_phone = admin_phone;
    }
}
