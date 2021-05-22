package com.mad1.myapplication;

import com.mad1.myapplication.validation_utility.ValidationUtility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class InputValidationUnitTest {

    private ValidationUtility validationUtility;

    @Before
    public void setUp(){
        validationUtility = new ValidationUtility();
    }
    @Test
    public void test_password_validation_pass(){
        boolean pass_val = ValidationUtility.password_validation("@@@Vadim1977");
        assertTrue(pass_val);
    }
   @Test
    public void test_password_validation_fail(){
       boolean pass_val = ValidationUtility.password_validation("@Vad19");
       assertFalse(pass_val);
    }
   @Test
    public void test_password_not_contain_letters_fail(){
       boolean pass_val = ValidationUtility.password_validation("12378@@@@@");
       assertFalse(pass_val);
    }
    @Test
    public void test_password_not_contain_special_characters_fail(){
        boolean pass_val = ValidationUtility.password_validation("12378Abcd");
        assertFalse(pass_val);
    }
    @Test
    public void test_password_not_contain_UpperCase_letter_fail(){
        boolean pass_val = ValidationUtility.password_validation("12378@bcd");
        assertFalse(pass_val);
    }
    @Test
    public void test_password_contains_white_spaces_fail(){
        boolean pass_val = ValidationUtility.password_validation("12378 @Acd");
        assertFalse(pass_val);
    }
    @Test
    public void test_password_not_contain_lower_case_letter_fail(){
        boolean pass_val = ValidationUtility.password_validation("12378@ABC");
        assertFalse(pass_val);
    }
    @Test
    public void test_password_empty_input_fail(){
        boolean pass_val = ValidationUtility.password_validation("");
        assertFalse(pass_val);
    }
    @Test
    public  void test_voter_sms_pass(){
        boolean candidate_number_pass = ValidationUtility.validate_candidate_number_from_sms("2");
        assertTrue(candidate_number_pass);
    }
    @Test
    public  void test_voter_sms_contains_not_numeric_input_fail(){
        boolean candidate_number = ValidationUtility.validate_candidate_number_from_sms("a");
        assertFalse(candidate_number);
    }
    @Test
    public  void test_voter_sms_contains_special_characters_fail(){
        boolean candidate_number = ValidationUtility.validate_candidate_number_from_sms("@");
        assertFalse(candidate_number);
    }
    @Test
    public  void test_voter_sms_empty_body_fail(){
        boolean candidate_number = ValidationUtility.validate_candidate_number_from_sms("");
        assertFalse(candidate_number);
    }
    @Test
    public  void test_login_name_starts_with_underscore_contains_alphanumeric_from_5_to_10_long_pass(){
        boolean login = ValidationUtility.login_validation("_Vad1977");
        assertTrue(login);
    }
    @Test
    public  void test_login_name_starts_with_uppercase_letter_contains_alphanumeric_from_5_to_10_long_pass(){
        boolean login = ValidationUtility.login_validation("Vad1977");
        assertTrue(login);
    }
    @Test
    public  void test_login_name_starts_with_uppercase_letter_contains_alphanumeric_and_Underscore_from_5_to_10_long_pass(){
        boolean login = ValidationUtility.login_validation("Vad_1977");
        assertTrue(login);
    }
    @Test
    public  void test_login_name_starts_with_uppercase_letter_contains_alphanumeric_and_Underscore_less_than_5_characters_long_fail(){
        boolean login = ValidationUtility.login_validation("Vd_1");
        assertFalse(login);
    }
    @Test
    public  void test_login_name_starts_with_number_fail(){
        boolean login = ValidationUtility.login_validation("123Vd_1");
        assertFalse(login);
    }
    @Test
    public  void test_login_name_starts_with_special_character_fail(){
        boolean login = ValidationUtility.login_validation("@123Vd_1");
        assertFalse(login);
    }
    @Test
    public  void test_login_name_contains_space_fail(){
        boolean login = ValidationUtility.login_validation("adc Vd_1");
        assertFalse(login);
    }
    @Test
    public  void test_login_name_more_than_15_characters_long_fail(){
        boolean login = ValidationUtility.login_validation("_somethVd_123454");
        assertFalse(login);
    }
    @Test
    public  void test_K_number_pass(){
        boolean k_num = validationUtility.k_number_validation("K00232729");
        assertTrue(k_num);
    }
    @Test
    public  void test_K_number_starts_with_number_fail(){
        boolean k_num = validationUtility.k_number_validation("900232729");
        assertFalse(k_num);
    }
    @Test
    public  void test_K_number_does_not_contain_zero_fail(){
        boolean k_num = validationUtility.k_number_validation("K0232729");
        assertFalse(k_num);
    }
    @Test
    public  void test_K_number_contains_spaice_fail(){
        boolean k_num = validationUtility.k_number_validation("K00 232729");
        assertFalse(k_num);
    }
    @Test
    public  void test_K_longer_than_9_characters_fail(){
        boolean k_num = validationUtility.k_number_validation("K000232729");
        assertFalse(k_num);
    }
    @Test
    public  void test_K_shorter_than_9_characters_fail(){
        boolean k_num = validationUtility.k_number_validation("K0023279");
        assertFalse(k_num);
    }
    @Test
    public  void test_K_number_contains_special_character_fail(){
        boolean k_num = validationUtility.k_number_validation("K0023@729");
        assertFalse(k_num);
    }
    @Test
    public  void test_K_number_starts_with_special_character_fail(){
        boolean k_num = validationUtility.k_number_validation("@K0032729");
        assertFalse(k_num);
    }
    @Test
    public  void test_K_number_empty_input_fail(){
        boolean k_num = validationUtility.k_number_validation("");
        assertFalse(k_num);
    }
    @Test
    public  void test_class_code_contains_3_uppercase_letters_followed_by_number_pass(){
        boolean class_code = validationUtility.validate_class_code_input("ISD4");
        assertTrue(class_code);
    }
    @Test
    public  void test_class_code_contains_2_uppercase_letters_followed_by_number_pass(){
        boolean class_code = validationUtility.validate_class_code_input("ID4");
        assertTrue(class_code);
    }
    @Test
    public  void test_class_code_contains_1_uppercase_letters_followed_by_number_pass(){
        boolean class_code = validationUtility.validate_class_code_input("D4");
        assertTrue(class_code);
    }
    @Test
    public  void test_class_code_contains_1_uppercase_letters_followed_by_2_numbers_pass(){
        boolean class_code = validationUtility.validate_class_code_input("D44");
        assertTrue(class_code);
    }
    @Test
    public  void test_class_code_does_not_contain_numbers_pass(){
        boolean class_code = validationUtility.validate_class_code_input("SD");
        assertFalse(class_code);
    }
    @Test
    public  void test_class_code_contains_3_uppercase_letters_followed_by_2_numbers_fail(){
        boolean class_code = validationUtility.validate_class_code_input("ISD12");
        assertFalse(class_code);
    }
    @Test
    public  void test_class_code_starts_with_special_character_fail(){
        boolean class_code = validationUtility.validate_class_code_input("@SD12");
        assertFalse(class_code);
    }
    @Test
    public  void test_class_contain_space_fail(){
        boolean class_code = validationUtility.validate_class_code_input("ISD 12");
        assertFalse(class_code);
    }
    @Test
    public  void test_class_empty_input_fail(){
        boolean class_code = validationUtility.validate_class_code_input("");
        assertFalse(class_code);
    }
    @Test
    public  void test_class_numeric_input_fail(){
        boolean class_code = validationUtility.validate_class_code_input("1234");
        assertFalse(class_code);
    }
    @Test
    public void test_irish_mobile_number_meteor_pass(){
        boolean mobile_number = validationUtility.phone_test("0852375955");
        assertTrue(mobile_number);
    }
    @Test
    public void test_irish_mobile_number_vodafone_pass(){
        boolean mobile_number = validationUtility.phone_test("0872375955");
        assertTrue(mobile_number);
    }
    @Test
    public void test_irish_mobile_number_tesco_pass(){
        boolean mobile_number = validationUtility.phone_test("0892375955");
        assertTrue(mobile_number);
    }
    @Test
    public void test_irish_mobile_number_086_pass(){
        boolean mobile_number = validationUtility.phone_test("0862375955");
        assertTrue(mobile_number);
    }
    @Test
    public void test_irish_mobile_number_083_pass(){
        boolean mobile_number = validationUtility.phone_test("0832375955");
        assertTrue(mobile_number);
    }
    @Test
    public void test_irish_mobile_number_081_fail(){
        boolean mobile_number = validationUtility.phone_test("0812375955");
        assertFalse(mobile_number);
    }
    @Test
    public void test_irish_mobile_number_080_fail(){
        boolean mobile_number = validationUtility.phone_test("0802375955");
        assertFalse(mobile_number);
    }
    @Test
    public void test_irish_mobile_number_starts_with_special_character_fail(){
        boolean mobile_number = validationUtility.phone_test("@0872375955");
        assertFalse(mobile_number);
    }
    @Test
    public void test_irish_mobile_number_contains_special_character_fail(){
        boolean mobile_number = validationUtility.phone_test("08723@5955");
        assertFalse(mobile_number);
    }
    @Test
    public void test_irish_mobile_landline_input_fail(){
        boolean mobile_number = validationUtility.phone_test("0612375955");
        assertFalse(mobile_number);
    }
    @Test
    public void test_irish_mobile_empty_input_fail(){
        boolean mobile_number = validationUtility.phone_test("");
        assertFalse(mobile_number);
    }
    @Test
    public void test_irish_mobile_shorter_than_10_fail(){
        boolean mobile_number = validationUtility.phone_test("08523759");
        assertFalse(mobile_number);
    }
    @Test
    public void test_irish_mobile_longer_than_10_fail(){
        boolean mobile_number = validationUtility.phone_test("08723759556");
        assertFalse(mobile_number);
    }
    @Test
    public void test_name_input_contains_just_upper_case_fail(){
        boolean is_alpha = validationUtility.word_input("VADIM");
        assertFalse(is_alpha);
    }
    @Test
    public void test_name_input_contains_just_lower_case_pass(){
        boolean is_alpha = validationUtility.word_input("vadim");
        assertTrue(is_alpha);
    }
    @Test
    public void test_name_input_starts_with_uppercase_letter_followed_by_lower_case_letters_pass(){
        boolean is_alpha = validationUtility.word_input("Vadim");
        assertTrue(is_alpha);
    }
    @Test
    public void test_name_input_contains_numbers_fail(){
        boolean is_alpha = validationUtility.word_input("Vadim1");
        assertFalse(is_alpha);
    }
    @Test
    public void test_name_input_contains_speicial_characters_fail(){
        boolean is_alpha = validationUtility.word_input("Vadi@m");
        assertFalse(is_alpha);
    }
    @Test
    public void test_name_input_contains_spase_fail(){
        boolean is_alpha = validationUtility.word_input("Vadi m");
        assertFalse(is_alpha);
    }
    @Test
    public void test_name_input_contains_speicial_fail(){
        boolean is_alpha = validationUtility.word_input("VaDim");
        assertFalse(is_alpha);
    }
    @Test
    public void test_name_less_than_2_letters_long_fail(){
        boolean is_alpha = validationUtility.word_input("V");
        assertFalse(is_alpha);
    }
    @Test
    public void test_name_empty_input_fail(){
        boolean is_alpha = validationUtility.word_input("");
        assertFalse(is_alpha);
    }
}