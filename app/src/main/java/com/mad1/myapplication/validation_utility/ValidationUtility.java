package com.mad1.myapplication.validation_utility;

import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.Candidate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtility {

    private static final String PHONE_PATTERN = "^\\s*((\\(?(0)\\s?8[35679]\\)?\\s?\\d{3}\\s?\\d{4}))\\s*$";
    private static final String CLASS_CODE_PATTERN = "\\b[A-Z]{1,3}\\d{1}[a-zA-Z]?\\b";
    // starts with single K character followed by 2 zeros followed by 6 digits
    private static final String K_NUMBER_PATTERN = "^[K]{1}[0]{2}[0-9]{6}$";
    // starts with alpha or underscore followed by alphanumric and underscore length 5-10 Example Name2021
    private static final String LOGIN_PATTERN = "^[A-Za-z_][A-Za-z0-9_]{5,10}$";
    // contains one number from 1 to 20
    private static final String NUMBER_OF_CANDIDATES_PATTERN = "^[1-200]{1}$";

    //    ^                 * start of regex
    //    (?=.*[0-9])       * a digit must occur at least once
    //    (?=.*[a-z])       * a lower case letter must occur at least once
    //    (?=.*[A-Z])       * an upper case letter must occur at least once
    //    (?=.*[@#$%^&+=])  * a special character must occur at least once
    //    (?=\S+$)          * no whitespace allowed in the entire string
    //    .{8,}             * anything, at least eight places though
    //    $                 * end of regex
    //    example           * @@@Name2021

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static Boolean validate_candidate_number_from_sms(String candidate_number){
        Pattern pattern = Pattern.compile(NUMBER_OF_CANDIDATES_PATTERN);
        Matcher matcher = pattern.matcher(candidate_number);
        boolean is_cadidate_number_valid = matcher.find();
        //return is_cadidate_number_valid;
        return true;
    }

    public static Boolean password_validation(String password_to_validate){
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password_to_validate);
        boolean is_password_valid = matcher.find();
        return is_password_valid;
    }

    public static Boolean login_validation(String login_to_valicate){
        Pattern pattern = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = pattern.matcher(login_to_valicate);
        boolean is_login_valid = matcher.find();
        return is_login_valid ;
    }

    public Boolean k_number_validation(String k_num){
        Pattern pattern = Pattern.compile(K_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(k_num);
        boolean is_match = matcher.find();
        if(is_match)
            return true;
        else
            return false;
    }

    public Boolean word_input(String input){
        String pattern = "^[A-Za-z]{1}[a-z]{2,20}$";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(input);
        boolean is_match = matcher.find();
        if(is_match)
            return true;
        else
            return false;
    }

    public Boolean phone_test(String phone_to_test){
        Pattern pattern = Pattern.compile(PHONE_PATTERN );
        Matcher matcher = pattern.matcher(phone_to_test);
        boolean is_match = matcher.find();
        if(is_match)
            return true;
        else
            return false;
    }
    
    public boolean validate_class_code_input(String test_class_code){
        String CLASS_CODE_PATTERN = "^[A-Z]{1,3}[1-4]{1}$";
        Pattern pattern = Pattern.compile(CLASS_CODE_PATTERN );
        Matcher matcher = pattern.matcher(test_class_code);
        boolean is_match = matcher.find();
        if(is_match)
            return true;
        else
            return false;
    }
}
