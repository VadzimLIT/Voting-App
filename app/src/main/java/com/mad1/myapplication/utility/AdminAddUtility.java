package com.mad1.myapplication.utility;

public class AdminAddUtility {

    // check if passwords match
    public Boolean isPasswordMatch(String pass1, String pass2){
        if(pass1.equals(pass2))
            return true;
        return false;
    }
    // check if edit texts not blank
    public Boolean isEditTextNotBlank(String name, String surname,
                                      String password, String conf_password, String phone, String mother_name, String fav_place, String login_name){
        System.out.println(name + " " + surname + " " + password +" " +conf_password+ " " + phone + "===============================");
        if(name.equals("")||
            surname.equals("") ||
            password.equals("")||
            conf_password.equals("")||
            phone.equals("")||
            mother_name.equals("")||
            fav_place.equals("")||
            login_name.equals("")){
            return false;
        }
        return true;
    }
}
