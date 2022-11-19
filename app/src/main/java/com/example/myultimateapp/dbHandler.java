package com.example.myultimateapp;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class dbHandler extends SQLiteOpenHelper {

    public static String validUserNameCharacters = "abcdefghijklmnopqrstuvwxyz1234567890";

    public dbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE USERS (sno INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,TITLE TEXT,FIRSTNAME TEXT,LASTNAME TEXT,USERNAME TEXT NOT NULL,PASSWORD  TEXT NOT NULL,DOB TEXT,GENDER TEXT,EMAIL TEXT NOT NULL,PHONE TEXT,IMAGEURLS TEXT,ADDRESS TEXT,POSTALCODE TEXT,SECURITYQUESTION TEXT NOT NULL,SECURITYANSWER TEXT NOT NULL)";
        db.execSQL(createUsersTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public Boolean addUser(UserDetails user, Context context) {


        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

//        TITLE ,FIRSTNAME ,LASTNAME ,USERNAME ,PASSWORD ,DOB ,GENDER ,EMAIL ,PHONE ,IMAGEURLS ,ADDRESS ,POSTALCODE ,SECURITYQUESTION ,SECURITYANSWER

        if (fetchUserUsingUserName(user.getUsername()) != null) {
            Log.d("Checking", "Username already exists " + fetchUserUsingUserName(user.getUsername()));
            Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
            return false;
        }


        contentValues.put("title", user.getTitle());
        contentValues.put("firstname", user.getFirstName());
        contentValues.put("lastname", user.getLastName());
        contentValues.put("dob", user.getDob());
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("gender", user.getGender());
        contentValues.put("email", user.getEmail());
        contentValues.put("phone", user.getPhone());
        contentValues.put("imageurls", user.getImageurls());
        contentValues.put("address", user.getAddress());
        contentValues.put("postalcode", user.getPostalcode());
        contentValues.put("securityquestion", user.getSecurityquestion());
        contentValues.put("securityanswer", user.getSecurityanswer());


        long k = db.insert("USERS", null, contentValues);
        db.close();
        Log.d("Checking", "addUser: " + k);
        Log.d("Checking", "addUser: " + user);


        return true;
    }


    public UserDetails fetchUserUsingUserName(String username) {
        UserDetails user = new UserDetails();
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from USERS where USERNAME=\"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);

        Log.d("Checking", "fetchUserUsingUserName: " + username);
        Log.d("Checking", "fetchUserUsingUserName: " + cursor.toString());

        if (cursor != null && cursor.moveToFirst()) {
//       sno, TITLE ,FIRSTNAME ,LASTNAME ,USERNAME ,PASSWORD ,DOB ,GENDER ,EMAIL ,PHONE ,IMAGEURLS ,ADDRESS ,POSTALCODE ,SECURITYQUESTION ,SECURITYANSWER
            user.setSno(Integer.parseInt(cursor.getString(0)));
            user.setTitle(cursor.getString(1));
            user.setFirstName(cursor.getString(2));
            user.setLastName(cursor.getString(3));
            user.setUsername(cursor.getString(4));
            user.setPassword(cursor.getString(5));
            user.setDob(cursor.getString(6));
            user.setGender(cursor.getString(7));
            user.setEmail(cursor.getString(8));
            user.setPhone(cursor.getString(9));
            user.setImageurls(cursor.getString(10));
            user.setAddress(cursor.getString(11));
            user.setPostalcode(cursor.getString(12));
            user.setSecurityquestion(cursor.getString(13));
            user.setSecurityanswer(cursor.getString(14));


        } else {
            Log.d("Checking", "fetchUserUsingUserName: no user found");
            return null;
        }
        Log.d("Checking", "fetchUserUsingUserName: printing user " + user.toString());

        return user;
    }

    public UserDetails fetchUserUsingPhoneNumber(String phoneNumber) {
        UserDetails user = new UserDetails();
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from USERS where PHONE=\"" + phoneNumber + "\"";
        Cursor cursor = db.rawQuery(query, null);

        Log.d("Checking", "fetchUserUsingUserName: " + phoneNumber);
        Log.d("Checking", "fetchUserUsingUserName: " + cursor.toString());

        if (cursor != null && cursor.moveToFirst()) {
//       sno, TITLE ,FIRSTNAME ,LASTNAME ,USERNAME ,PASSWORD ,DOB ,GENDER ,EMAIL ,PHONE ,IMAGEURLS ,ADDRESS ,POSTALCODE ,SECURITYQUESTION ,SECURITYANSWER
            user.setSno(Integer.parseInt(cursor.getString(0)));
            user.setTitle(cursor.getString(1));
            user.setFirstName(cursor.getString(2));
            user.setLastName(cursor.getString(3));
            user.setUsername(cursor.getString(4));
            user.setPassword(cursor.getString(5));
            user.setDob(cursor.getString(6));
            user.setGender(cursor.getString(7));
            user.setEmail(cursor.getString(8));
            user.setPhone(cursor.getString(9));
            user.setImageurls(cursor.getString(10));
            user.setAddress(cursor.getString(11));
            user.setPostalcode(cursor.getString(12));
            user.setSecurityquestion(cursor.getString(13));
            user.setSecurityanswer(cursor.getString(14));


        } else {
            Log.d("Checking", "fetchUserUsingUserName: no user found");
            return null;
        }
        Log.d("Checking", "fetchUserUsingUserName: printing user " + user.toString());

        return user;
    }

    public UserDetails fetchUserUsingEmail(String email) {
        UserDetails user = new UserDetails();
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from USERS where EMAIL=\"" + email + "\"";
        Cursor cursor = db.rawQuery(query, null);

        Log.d("Checking", "fetchUserUsingUserName: " + email);
        Log.d("Checking", "fetchUserUsingUserName: " + cursor.toString());

        if (cursor != null && cursor.moveToFirst()) {
//       sno, TITLE ,FIRSTNAME ,LASTNAME ,USERNAME ,PASSWORD ,DOB ,GENDER ,EMAIL ,PHONE ,IMAGEURLS ,ADDRESS ,POSTALCODE ,SECURITYQUESTION ,SECURITYANSWER
            user.setSno(Integer.parseInt(cursor.getString(0)));
            user.setTitle(cursor.getString(1));
            user.setFirstName(cursor.getString(2));
            user.setLastName(cursor.getString(3));
            user.setUsername(cursor.getString(4));
            user.setPassword(cursor.getString(5));
            user.setDob(cursor.getString(6));
            user.setGender(cursor.getString(7));
            user.setEmail(cursor.getString(8));
            user.setPhone(cursor.getString(9));
            user.setImageurls(cursor.getString(10));
            user.setAddress(cursor.getString(11));
            user.setPostalcode(cursor.getString(12));
            user.setSecurityquestion(cursor.getString(13));
            user.setSecurityanswer(cursor.getString(14));


        } else {
            Log.d("Checking", "fetchUserUsingUserName: no user found");
            return null;
        }
        Log.d("Checking", "fetchUserUsingUserName: printing user " + user.toString());

        return user;
    }

    public ArrayList<String> fetchUsersUsingEmail(String email) {

        ArrayList<String> users = new ArrayList<String>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from USERS where EMAIL=\"" + email + "\"";
        Cursor cursor = db.rawQuery(query, null);

        Log.d("fetchUserUsingEmail", "fetchUserUsingEmail: " + email);

        if (cursor != null && cursor.moveToFirst()) {

            do {


//                UserDetails user = new UserDetails();
//       sno, TITLE ,FIRSTNAME ,LASTNAME ,USERNAME ,PASSWORD ,DOB ,GENDER ,EMAIL ,PHONE ,IMAGEURLS ,ADDRESS ,POSTALCODE ,SECURITYQUESTION ,SECURITYANSWER
//                user.setSno(Integer.parseInt(cursor.getString(0)));
//                user.setTitle(cursor.getString(1));
//                user.setFirstName(cursor.getString(2));
//                user.setLastName(cursor.getString(3));
//                user.setUsername(cursor.getString(4));
//                user.setPassword(cursor.getString(5));
//                user.setDob(cursor.getString(6));
//                user.setGender(cursor.getString(7));
//                user.setEmail(cursor.getString(8));
//                user.setPhone(cursor.getString(9));
//                user.setImageurls(cursor.getString(10));
//                user.setAddress(cursor.getString(11));
//                user.setPostalcode(cursor.getString(12));
//                user.setSecurityquestion(cursor.getString(13));
//                user.setSecurityanswer(cursor.getString(14));

                users.add(cursor.getString(4));
            } while (cursor.moveToNext());
        } else {
            Log.d("fetchUserUsingEmail", "fetchUserUsingEmail: no user found");
            return null;
        }

        Log.d("fetchUserUsingEmail", String.valueOf(users));
        return users;
    }


    public Boolean updateUser(UserDetails user, Context context) {


        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        UserDetails fetchedUser = fetchUserUsingUserName(user.getUsername());
        if (fetchedUser != null && fetchedUser.getSno() != user.getSno()) {
            Log.d("Checking", "Username already exists " + fetchUserUsingUserName(user.getUsername()));
            Toast.makeText(context, "updating: Username already exists", Toast.LENGTH_SHORT).show();
            return false;
        }

        contentValues.put("title", user.getTitle());
        contentValues.put("firstname", user.getFirstName());
        contentValues.put("lastname", user.getLastName());
        contentValues.put("username", user.getUsername());
        contentValues.put("dob", user.getDob());
        contentValues.put("password", user.getPassword());
        contentValues.put("gender", user.getGender());
        contentValues.put("email", user.getEmail());
        contentValues.put("phone", user.getPhone());
        contentValues.put("imageurls", user.getImageurls());
        contentValues.put("address", user.getAddress());
        contentValues.put("postalcode", user.getPostalcode());
        contentValues.put("securityquestion", user.getSecurityquestion());
        contentValues.put("securityanswer", user.getSecurityanswer());

        Log.d("updated", "onCreate: " + user.toString());

        db.update("USERS", contentValues, "sno=?", new String[]{String.valueOf(user.getSno())});
        return true;

    }

    public void deleteUser(int sno) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("USERS", "sno=?", new String[]{String.valueOf(sno)});
    }



}