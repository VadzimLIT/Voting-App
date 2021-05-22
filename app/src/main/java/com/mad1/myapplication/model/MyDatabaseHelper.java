package com.mad1.myapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.mad1.myapplication.security.Cryptography;
import com.mad1.myapplication.utility.Admin;
import com.mad1.myapplication.utility.Candidate;
import com.mad1.myapplication.utility.Voter;
import com.mad1.myapplication.utility.VotingResultSMSData;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    String default_admin_password = "admin";
    String encrypted_default_test_password = "";

    {
        try {
            encrypted_default_test_password = Cryptography.encrypt(default_admin_password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Cursor adminCursor = null;
    SQLiteDatabase adminDB = null;
    private static final String DATABASE_NAME = "voting.db";
    private static final int DATABASE_VERSION = 3;

    // TABLE ADMINS
    private static final String TABLE_ADMINS = "Admins";

    // ADMIN COLUMNS
    private static final String ADMIN_ID = "admin_id";
    private static final String ADMIN_FIRST_NAME = "admin_first_name";
    private static final String ADMIN_SECOND_NAME = "admin_second_name";
    private static final String ADMIN_PASSWORD = "admin_password";
    private static final String ADMIN_PHONE = "admin_phone";
    private static final String ADMIN_LOGIN_NAME = "login_name";

    // TABLE CANDIDATES
    private static final String TABLE_CANDIDATES = "Candidates";

    // CANDIDATES COLUMNS
    private static final String CANDIDATE_ID = "candidate_id";
    private static final String CANDIDATE_FIRST_NAME = "candidate_first_name";
    private static final String CANDIDATE_SECOND_NAME = "candidate_second_name";
    private static final String CANDIDATE_MOBILE = "candidate_phone_number";
    private static final String CANDIDATE_IMAGE = "candidate_image";


    // TABLE VOTING
    private static final String TABLE_VOTING = "Voting";

    // VOTING COLUMNS
    private static final String VOTING_ID = "voting_id";
    private static final String VOTING_CANDIDATE_ID = "canditate_id";
    private static final String VOTER_MOBILE_NUMBER = "voter_mobile_number";

    // TABLE VOTERS
    private static final String TABLE_VOTERS = "Voters";

    // VOTERS COLUMNS
    private static final String K_NUMBER = "k_number";
    private static final String VOTER_FIRST_NAME = "voter_first_name";
    private static final String VOTER_SECOND_NAME = "voter_second_name";
    private static final String MOBILE_NUMBER = "voter_mobile_number";
    private static final String VOTER_CLASS_CODE = "voter_class_code";

    // TABLE SECURITY QUESTING FOR ADMIN
    private static final String TABLE_SECURITY = "SECURITY";

    //SECURITY COLUMNS
    private static final String SECURITY_ID = "security_id";
    private static final String SECURITY_ADMIN_ID = "admin_id";
    private static final String MOTHERS_MAIDEN_NAME = "mother_maiden_name";
    private static final String FAVORITE_PLACE = "favorite_place";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create admin table
        String create_table_admins = "CREATE TABLE " + TABLE_ADMINS + " (" +
                ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ADMIN_FIRST_NAME + " TEXT, " +
                ADMIN_SECOND_NAME + " TEXT, " +
                ADMIN_PASSWORD + " TEXT," +
                ADMIN_PHONE + " TEXT," +
                ADMIN_LOGIN_NAME + " TEXT );";
        db.execSQL(create_table_admins);

        // create candidates table
        String create_table_candidates = "CREATE TABLE " + TABLE_CANDIDATES + " (" +
                CANDIDATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CANDIDATE_FIRST_NAME + " TEXT, " +
                CANDIDATE_SECOND_NAME + " TEXT, " +
                CANDIDATE_MOBILE + " TEXT, " +
                CANDIDATE_IMAGE + " BLOB );";
        db.execSQL(create_table_candidates);

        // create table voters
        String create_table_voters = "CREATE TABLE " + TABLE_VOTERS + " (" +
                MOBILE_NUMBER + " TEXT PRIMARY KEY, " +
                K_NUMBER + " TEXT, " +
                VOTER_FIRST_NAME + " TEXT, " +
                VOTER_SECOND_NAME + " TEXT," +
                VOTER_CLASS_CODE + " TEXT );";
        db.execSQL(create_table_voters);

        // create candidates table
        String create_table_voting = "CREATE TABLE " + TABLE_VOTING + " (" +
                VOTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                VOTING_CANDIDATE_ID + " INTEGER, " +
                VOTER_MOBILE_NUMBER + " TEXT, " +
                "FOREIGN KEY (" + VOTING_CANDIDATE_ID + ") REFERENCES " + TABLE_CANDIDATES +
                "(" + CANDIDATE_ID + "), " +
                "FOREIGN KEY (" + VOTER_MOBILE_NUMBER + ") REFERENCES " + TABLE_VOTERS +
                "(" + MOBILE_NUMBER +"));";
        db.execSQL(create_table_voting);

        String create_table_security = "CREATE TABLE " + TABLE_SECURITY + " (" +
                SECURITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SECURITY_ADMIN_ID + " INTEGER, " +
                MOTHERS_MAIDEN_NAME + " TEXT, " +
                FAVORITE_PLACE + " TEXT," +
                "FOREIGN KEY (" + SECURITY_ADMIN_ID + ") REFERENCES " + TABLE_ADMINS  +
                "(" + ADMIN_ID +"));";
        db.execSQL(create_table_security);

        //************************************* FOR TESTING ********************************

       // db.execSQL("insert into Admins values('1','admin','admin',encrypted_default_test_password, '0852375955','admin');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean login(String login, String password){
        Boolean isValid = false;
        System.out.println("**************** login MyDBHelper*****************");
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from Admins where admin_first_name=? and admin_password=?",
//                new String[]{login,password});

        Cursor cursor = db.rawQuery("select admin_password from Admins where login_name=?", new String[]{login});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return isValid;
        }
        else{
            while (cursor.moveToNext()) {
                String encrypted_password = cursor.getString(0);
                System.out.println("444444444444444444444444444444444444 Encrypted password" + encrypted_password);
                try {
                    String decrypted_passoword = Cryptography.decrypt(encrypted_password);
                    System.out.println("444444444444444444444444444444444444" + decrypted_passoword);
                    if(decrypted_passoword.equals(password))
                        isValid = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        cursor.close();
        db.close();
        return isValid;
    }
    //************************************* Admin Functionality **************************************************************

    public long addAdmin(String first_name, String second_name, String password, String phone, String mother_name, String fav_place, String login_name){
        try {
            String encripted_pass = Cryptography.encrypt(password);
            System.out.println("Encrypted password checked" + encripted_pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long result = -1;
        System.out.println(mother_name + " " + fav_place + " ======================================");
        if(!isAdminExists(phone) && ifSecurityQuestionsExist(mother_name, fav_place) != -1) {
            if(!isLoginNameExists(login_name)) {
                String encripted_password = "";
                try {
                    encripted_password = Cryptography.encrypt(password);
                    System.out.println("Encrypted password checked" + encripted_password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(ADMIN_FIRST_NAME, first_name);
                contentValues.put(ADMIN_SECOND_NAME, second_name);
                contentValues.put(ADMIN_PASSWORD, encripted_password);
                contentValues.put(ADMIN_PHONE, phone);
                contentValues.put(ADMIN_LOGIN_NAME, login_name);
                result = db.insert(TABLE_ADMINS, null, contentValues);
                db.close();
                result = insertSecurityQuestions((int) result, mother_name, fav_place);
                return result;
            }
        }
        return result;
    }

    private long insertSecurityQuestions(int id, String mother_name, String fav_place){
        if(ifSecurityQuestionsExist(mother_name, fav_place) == -1){
            System.out.println("Got Here =====================================================");
            return -1;
        }
        else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SECURITY_ADMIN_ID, id);
            contentValues.put(MOTHERS_MAIDEN_NAME, mother_name);
            contentValues.put(FAVORITE_PLACE, fav_place);
            long result = db.insert(TABLE_SECURITY, null, contentValues);
            System.out.println("********************* Admin Exists *********************** " + result);
            db.close();
            return result;
        }
    }
    private int ifSecurityQuestionsExist(String mother_name, String fav_place){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from SECURITY where mother_maiden_name=? and favorite_place=?",new String[]{mother_name,fav_place});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return 1;
        }
        else{
            cursor.close();
            db.close();
            return -1;
        }
    }

    // check if data in admin table to allow sign up
    public boolean isDataInAdminTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Admins",null);
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    // check if admin exists ********************** add date of birth for furder improve
    private boolean isAdminExists(String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Admins where admin_phone=?", new String[]{phone});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    private boolean isLoginNameExists(String login_name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Admins where login_name=?", new String[]{login_name});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    public ArrayList<Admin> getAdminsForList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select admin_id,admin_first_name,admin_second_name,admin_phone " +
                "from Admins",null);
        ArrayList<Admin> adminList = new ArrayList<>();
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return null;
        }
        else{
            while (cursor.moveToNext()) {
                Admin admin = new Admin();
                admin.setAdmin_id(cursor.getInt(0));
                admin.setAdmin_first_name(cursor.getString(1));
                admin.setAdmin_second_name(cursor.getString(2));
                admin.setAdmin_phone(cursor.getString(3));
                adminList.add(admin);
            }
        }
        cursor.close();
        db.close();
        return adminList;
    }
    public Admin getSingleAdmin(int admin_id){
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery("select * from Admins where admin_id=? ",new String[]{String.valueOf(admin_id)});
        adminDB = this.getReadableDatabase();
        adminCursor = adminDB.rawQuery("select * from Admins where admin_id=? ",new String[]{String.valueOf(admin_id)});
        Admin admin = new Admin();
        if(adminCursor.getCount() == 0){
            adminCursor.close();
            adminDB.close();
            return null;
        }
        else{
            while (adminCursor.moveToNext()) {
                String decrypted_password = "";
                admin.setAdmin_id(adminCursor.getInt(0));
                admin.setAdmin_first_name(adminCursor.getString(1));
                admin.setAdmin_second_name(adminCursor.getString(2));
                try {
                    decrypted_password = Cryptography.decrypt(adminCursor.getString(3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                admin.setAdmin_password(decrypted_password);
                admin.setAdmin_phone(adminCursor.getString(4));
                admin.setLogin_name(adminCursor.getString(5));
            }
        }
        return getSecurityQuestions(admin_id, admin);
    }

    private Admin getSecurityQuestions(int admin_id, Admin admin){
        adminCursor = adminDB.rawQuery("select mother_maiden_name,favorite_place from SECURITY where admin_id=? ",new String[]{String.valueOf(admin_id)});
        if(adminCursor.getCount() == 0){
            adminCursor.close();
            adminDB.close();
            return null;
        }
        else{
            while (adminCursor.moveToNext()){
                admin.setMother_maiden_name(adminCursor.getString(0));
                admin.setFav_place(adminCursor.getString(1));
                System.out.println("Mother Maiden Name From Database " + admin.getMother_maiden_name());
            }
        }
        adminCursor.close();
        adminDB.close();
        return admin;
    }

    public long updateAdmin(int admin_id, String name, String surname, String password,String phone, String mother_maiden_name, String fav_place,String login_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String ecrypted_password = "";
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADMIN_FIRST_NAME, name);
        contentValues.put(ADMIN_SECOND_NAME,surname);
        try {
            ecrypted_password = Cryptography.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        contentValues.put(ADMIN_PASSWORD, ecrypted_password);
        contentValues.put(ADMIN_PHONE,phone);
        contentValues.put(ADMIN_LOGIN_NAME,login_name);
        long result = db.update(TABLE_ADMINS,contentValues,"admin_id=?",new String[]{String.valueOf(admin_id)});
        db.close();
        if(result != 0){
            result = update_security_questions(admin_id,mother_maiden_name,fav_place);
            if(result == 0){
                result = -2;
            }
        }
        return result;
    }
    private long update_security_questions(int admin_id, String mother_maiden_name, String fav_place){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOTHERS_MAIDEN_NAME, mother_maiden_name);
        contentValues.put(FAVORITE_PLACE,fav_place);
        long result = db.update(TABLE_SECURITY,contentValues,SECURITY_ADMIN_ID + "=?",new String[]{String.valueOf(admin_id)});
        db.close();
        return result;
    }

    public long deleteAdmin(int admin_id) {
        long result = 0;
        String whereClause = "admin_id=?";
        String[] whereArgs = {
                String.valueOf(admin_id)
        };

        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_ADMINS,whereClause,whereArgs);
        result = db.delete(TABLE_SECURITY,whereClause,whereArgs);
        db.close();
        return result;
    }

    public int resetPassword(String mother_name, String fav_place){
        System.out.println(mother_name + " " + fav_place + "==================================");
        int result = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select admin_id from SECURITY where mother_maiden_name=? and favorite_place=?", new String[]{mother_name,fav_place});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return result;
        }
        else{
            cursor.moveToNext();
            result = cursor.getInt(0);
            cursor.close();
            db.close();
            return result;
        }
    }

    public long resetPasswordSecondStep(int id, String password){
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            String enrypted_password = Cryptography.encrypt(password);
            contentValues.put(ADMIN_PASSWORD, enrypted_password );
            result = db.update(TABLE_ADMINS, contentValues,"admin_id=?",new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    // *********************************************************************************************************************

    //******************************* CANDIDATE FUNCTIONALITY ***************************************************

    public long addCandidate(String first_name, String second_name, String mobile, byte[] imageInByte){ // byte[] image//================================
        long result = -1;

        if(!isCandidateExists(mobile)) {
            SQLiteDatabase db = this.getWritableDatabase();
            System.out.println("********************* Candidate Does Not Exist ***********************");
            ContentValues contentValues = new ContentValues();
            contentValues.put( CANDIDATE_FIRST_NAME, first_name.toLowerCase());
            contentValues.put(CANDIDATE_SECOND_NAME, second_name.toLowerCase());
            contentValues.put(CANDIDATE_MOBILE, mobile);
            if(imageInByte != null) {
                contentValues.put(CANDIDATE_IMAGE, imageInByte);//=========================================================================
            }

            result = db.insert(TABLE_CANDIDATES, null, contentValues);
            db.close();
            return result;
        }
        System.out.println("Candidate Exists");
        return result;
    }

    private boolean isCandidateExists(String mobile){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Candidates where candidate_phone_number=?",
                new String[]{mobile});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    // get candidate number
    public int getNumberOfCandidates() {
        String countQuery = "SELECT  * FROM " + TABLE_CANDIDATES + " ORDER BY " + CANDIDATE_ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return 0;
        } else {
            cursor.moveToNext();
            int count = cursor.getInt(0);
            cursor.close();
            db.close();
            //int count = cursor.getCount();
            return count;
        }
    }

    // get all candidates data
    public ArrayList<Candidate> getAllCandidates(){
        ArrayList<Candidate> candidatesData = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from Candidates",null);
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return null;
        }
        else{
            while(cursor.moveToNext()){
                Bitmap bp = null;
                int count = 1;
                Candidate candidate = new Candidate();
                candidate.setCandidate_id(cursor.getInt(0));
                candidate.setFirst_name(cursor.getString(1));
                candidate.setSecond_name(cursor.getString(2));
                candidate.setPhone(cursor.getString(3));
                byte[] b = cursor.getBlob(4);
                bp = BitmapFactory.decodeByteArray(b,0,b.length);
                //ByteArrayInputStream bi = new ByteArrayInputStream(b);
                //bp = BitmapFactory.decodeStream(bi);
                candidate.setCandidate_image(bp);//=============================================================
                candidatesData.add(candidate);
                System.out.println("Candidate " + count++);
            }
        }
        cursor.close();
        db.close();
        return candidatesData;
    }

    public ArrayList<Candidate> getSingleCandidateData(int candidate_id){
        ArrayList<Candidate> candidateData = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from Candidates where candidate_id =?", new String[]{String.valueOf(candidate_id)});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return null;
        }
        else{
            while(cursor.moveToNext()){
                Bitmap bp = null;
                Candidate candidate = new Candidate();
                candidate.setCandidate_id(cursor.getInt(0));
                candidate.setFirst_name(cursor.getString(1));
                candidate.setSecond_name(cursor.getString(2));
                candidate.setPhone(cursor.getString(3));
                byte[] b = cursor.getBlob(4);
                bp = BitmapFactory.decodeByteArray(b,0,b.length);
                candidate.setCandidate_image(bp);//=============================================================
                candidateData.add(candidate);
            }
        }
        cursor.close();
        db.close();
        return candidateData;
    }

    public long updateCandidate(int candidate_id,
                                String first_name,
                                String second_name,
                                String phone,
                                byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CANDIDATE_FIRST_NAME, first_name);
        contentValues.put(CANDIDATE_SECOND_NAME,second_name);
        contentValues.put(CANDIDATE_MOBILE, phone);
        contentValues.put(CANDIDATE_IMAGE,image);
        long result = db.update(TABLE_CANDIDATES,contentValues,"candidate_id=?",new String[]{String.valueOf(candidate_id)});
        db.close();
        return result;

    }

    public long deleteCandidate(int candidate_id){
        long result = 0;
        String whereClause = "canditate_id = ?";
        String whereClause1 = "candidate_id =?";
        String[] whereArgs = {
                String.valueOf(String.valueOf(candidate_id))
        };
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_VOTING,whereClause,whereArgs);
        result = db.delete(TABLE_CANDIDATES,whereClause1,whereArgs);
        db.close();
        return result;
    }

        //********************************* Voter Functionality ********************************************

    public long addVoter(String mobile, String k_number, String first_name, String second_name, String class_code){
        long result = -1;
        if(!isVoterExists(mobile)) {
            if (!isKnumberExists(k_number)) {
                SQLiteDatabase db = this.getWritableDatabase();
                System.out.println("********************* Adding Voter NOT Exists My DatabaeHelper ***********************");
                ContentValues contentValues = new ContentValues();
                contentValues.put(MOBILE_NUMBER, mobile);
                contentValues.put(K_NUMBER, k_number);
                contentValues.put(VOTER_FIRST_NAME, first_name);
                contentValues.put(VOTER_SECOND_NAME, second_name);
                contentValues.put(VOTER_CLASS_CODE, class_code);
                result = db.insert(TABLE_VOTERS, null, contentValues);
                db.close();
                return result;
            }
        }
        return result;
    }

    private boolean isKnumberExists(String k_number){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Voters where k_number=? ", new String[]{k_number});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    private boolean isVoterExists(String mobile){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Voters where voter_mobile_number=?", new String[]{mobile});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    public ArrayList<Voter>getAllVoters(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Voter> result = new ArrayList<>();;

        Cursor cursor = db.rawQuery("select * from Voters",null);
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return null;
        }
        else{
            while (cursor.moveToNext()){
                Voter voter = new Voter();
                voter.setVoter_phone(cursor.getString(0));
                voter.setVoter_first_name(cursor.getString(1));
                voter.setVoter_first_name(cursor.getString(2));
                voter.setVoter_second_name(cursor.getString(3));
                voter.setClass_code(cursor.getString(4));
                // get class code here=====================================================
                result.add(voter);
            }
        }
        return result;
    }

    public  ArrayList<Voter> getSingleVoterData(String voter_phone){
        ArrayList<Voter> voterList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from Voters where voter_mobile_number =?", new String[]{voter_phone});
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return null;
        }
        else{
            while(cursor.moveToNext()){
                Voter voter = new Voter();
                voter.setVoter_phone(cursor.getString(0));
                voter.setVoter_knum(cursor.getString(1));
                voter.setVoter_first_name(cursor.getString(2));
                voter.setVoter_second_name(cursor.getString(3));
                voter.setClass_code(cursor.getString(4));
                // set class code here=============================================================================
                voterList.add(voter);
            }
        }
        cursor.close();
        db.close();
        return voterList;
    }

    public long updateVoter(String phone, String voter_phone, String voter_knum, String name, String surname, String class_code) {
        System.out.println(voter_phone + " " + voter_knum + " " + name + " " + surname + " ------------------------------------------------");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOBILE_NUMBER, voter_phone);
        contentValues.put(K_NUMBER,voter_knum);
        contentValues.put(VOTER_FIRST_NAME, name);
        contentValues.put(VOTER_SECOND_NAME,surname);
        contentValues.put(VOTER_CLASS_CODE ,class_code);
        long result = db.update(TABLE_VOTERS,contentValues,MOBILE_NUMBER + "=?",new String[]{phone});
        db.close();
        return result;
    }

    public long deleteVoter(String voter_phone){
        long result = -1;
        System.out.println("Delete Voter Voter phone is " + voter_phone);
        String whereClause = "voter_mobile_number=?";
        String[] whereArgs = {
                voter_phone
        };

        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_VOTING, whereClause,whereArgs);
        result = db.delete(TABLE_VOTERS,whereClause,whereArgs);
        if(result == 0){
            System.out.println("VOTE TABLE VOTERS DELETE FAIL");
            db.close();
            return result;
        }
        db.close();
        return result;
    }
    //******************************************************************************************************************************

    //************************************************ Voting Functionality ***********************************************************
    public ArrayList<VotingResultSMSData> sendResults(){
        SQLiteDatabase db = getReadableDatabase();
        VotingResultSMSData voteSMS;
        ArrayList<VotingResultSMSData> data = new ArrayList<>();

        Cursor cursor = db.rawQuery("select candidate_id,candidate_first_name," +
                "candidate_second_name from Candidates ",null);
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
        }
        else{
            while (cursor.moveToNext()){
                voteSMS = new VotingResultSMSData();
                voteSMS.setCandidate_id(cursor.getInt(0));
                voteSMS.setFirst_name(cursor.getString(1));
                voteSMS.setSecond_name(cursor.getString(2));
                voteSMS.setVote_rate(String.valueOf(getVotesPerCandidate(voteSMS.getCandidate_id())));
                data.add(voteSMS);
            }
        }
        cursor.close();
        db.close();
        return data;
    }

    public int getVotesPerCandidate(int id){
        SQLiteDatabase db = getReadableDatabase();
        System.out.println("Candidate Id" + id);
        Cursor cursor = db.rawQuery("select count(canditate_id) from " + TABLE_VOTING + " where canditate_id = ?"
                , new String[]{String.valueOf(id)});
//        Cursor cursor = db.rawQuery("select count(canditate_id) from " + TABLE_VOTING,null);
        if(cursor.getCount() == 0){
            System.out.println("No Raws in Voting Table For A Candidate");
            cursor.close();
            db.close();
            return 0;
        }
        else {
            int result = 0;
            cursor.moveToFirst();
            result = cursor.getInt(0);
            System.out.println("*********************** Voting Rate per candidate " + result + " *********************************");
            db.close();
            return result;
        }
    }

    public long addVotestoVotingTable(String vote, String phone){
        System.out.println("Adding a vote to a voting table ENTRY POINT");
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        // check if voter registered
        if(ifVoterExists(phone)) {
            System.out.println("Adding a vote to a voting table  VOTER EXISTS APPROVED");
            if(!isVotingNotFirstTime(phone)) {
                System.out.println("Adding a vote to a voting table");
                ContentValues contentValues = new ContentValues();
                contentValues.put(VOTING_CANDIDATE_ID, vote);
                contentValues.put(VOTER_MOBILE_NUMBER, phone);
                result = db.insert(TABLE_VOTING, null, contentValues);
                db.close();
                return result;
            }
        }
        db.close();
        return result;
    }
    // check if voter exists when vote about to be submitted
    private Boolean ifVoterExists(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("IF VOTER EXISTS VOTER PHONE NUMBER TO CHECK " + phone);
        Cursor cursor = null;
        cursor = db.rawQuery("select * from Voters where voter_mobile_number=?", new String[]{phone});
        if (cursor.getCount() == 0) {
            System.out.println("VOTER DOES NOT EXISTS IN VOTERS TABLE");
            return false;
        }
        else {
            System.out.println("VOTER DOES EXISTS IN VOTERS TABLE");
            return true;
        }
    }
    // check if voter voted already
    private Boolean isVotingNotFirstTime(String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("is voting first time check " + phone );
        Cursor cursor = db.rawQuery("select * from Voting where voter_mobile_number=?",
                new String[]{phone});
        if(cursor.getCount() == 0){
            System.out.println(" IS VOTING FIRST TIME - FIRST TIME VOTE");
            return false;
        }
        else {
            System.out.println(" IS VOTING FIRST TIME - VOTE ALREADY SUBMITTED SUBMITTED");
            return true;
        }
    }

    // get voter's phone numbers to send resulting sms
    public ArrayList<String> getVotersPhoneNumbers(){
        ArrayList<String> voter_phone_numbers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select voter_mobile_number from Voters", null);
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return null;
        }
        else {
            while(cursor.moveToNext()) {
                voter_phone_numbers.add(cursor.getString(0));
            }
        }
        cursor.close();
        db.close();
        return voter_phone_numbers;
        } // end of getVotersPhoneNumbers

    // get voter candidate data to send start voting sms
    public ArrayList<Candidate> getVoteData(){
        ArrayList<Candidate> candidatesData = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.rawQuery("select candidate_id,candidate_first_name,candidate_second_name," +
                "candidate_phone_number from Candidates",null);
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return null;
        }
        else{
            while(cursor.moveToNext()){
                Bitmap bp = null;
                int count = 1;
                Candidate candidate = new Candidate();
                candidate.setCandidate_id(cursor.getInt(0));
                candidate.setFirst_name(cursor.getString(1));
                candidate.setSecond_name(cursor.getString(2));
                candidate.setPhone(cursor.getString(3));
                candidatesData.add(candidate);
                System.out.println("Candidate " + count++);
            }
        }
        cursor.close();
        db.close();
        return candidatesData;
    }

    public long deleteAllVotingData(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ TABLE_VOTING);
        db.execSQL("delete from "+ TABLE_CANDIDATES);
        db.execSQL("delete from "+ TABLE_VOTERS);
        return 1;
    }
}// End of class


