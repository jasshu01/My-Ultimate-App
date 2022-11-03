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

    public static String validUserNameCharacters="abcdefghijklmnopqrstuvwxyz1234567890";
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


    public Boolean addUser(UserDetails user,Context context) {


        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

//        TITLE ,FIRSTNAME ,LASTNAME ,USERNAME ,PASSWORD ,DOB ,GENDER ,EMAIL ,PHONE ,IMAGEURLS ,ADDRESS ,POSTALCODE ,SECURITYQUESTION ,SECURITYANSWER

        if(fetchUserUsingUserName(user.getUsername())!=null)
        {
            Log.d("Checking","Username already exists "+fetchUserUsingUserName(user.getUsername()));
            Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
            return false;
        }



        contentValues.put("title", user.getTitle());
        contentValues.put("firstname", user.getFirstName());
        contentValues.put("lastname", user.getLastName());
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
        Log.d("Checking", "addUser: "+k);
        Log.d("Checking", "addUser: "+ user);


        return true;
    }


    public UserDetails fetchUserUsingUserName(String username) {
        UserDetails user = new UserDetails();

        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from USERS where USERNAME=\"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);

        Log.d("Checking", "fetchUserUsingUserName: "+username);
        Log.d("Checking", "fetchUserUsingUserName: "+cursor.toString());

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
        Log.d("Checking", "fetchUserUsingUserName: printing user "+user.toString());

        return user;
    }


    public void updateUser(UserDetails user,Context context) {
        
        
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        UserDetails fetchedUser=fetchUserUsingUserName(user.getUsername());
        if(fetchedUser!=null && fetchedUser.getSno()!=user.getSno())
        {
            Log.d("Checking","Username already exists "+fetchUserUsingUserName(user.getUsername()));
            Toast.makeText(context, "updating: Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        contentValues.put("title", user.getTitle());
        contentValues.put("firstname", user.getFirstName());
        contentValues.put("lastname", user.getLastName());
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

        Log.d("updated", "onCreate: "+ user.toString());

        db.update("USERS", contentValues, "sno=?", new String[]{String.valueOf(user.getSno())});

    }

    public void deleteUser(int sno) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("USERS", "sno=?", new String[]{String.valueOf(sno)});

    }


    private String saveToInternalStorage(Bitmap bitmapImage, String filename, Context context) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, filename + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("address", directory.getAbsolutePath());
        return directory.getAbsolutePath();
    }


    public void fetchDataFromAPI(Context context, String apiURL) {
//
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiURL, null, new Response.Listener<JSONArray>() {
//
//
//            public void onResponse(JSONArray response) {
//                try {
//
//
//                    for (int i = 0; i < response.length(); i++) {
//                        Contact contact = new Contact();
//
//                        JSONObject obj = response.getJSONObject(i);
//                        contact.setFirstName(obj.getString("fname"));
//                        contact.setLastName(obj.getString("lname"));
//                        contact.setPhone1(obj.getString("p1"));
//                        contact.setPhone2(obj.getString("p2"));
//                        contact.setEmail(obj.getString("email"));
////                        Log.d("fetching API", obj.getString("fname"));
////                        Log.d("fetching API", obj.getString("lname"));
////                        Log.d("fetching API", obj.getString("p1"));
////                        Log.d("fetching API", obj.getString("p2"));
////                        Log.d("fetching API", obj.getString("email"));
//
//
//                        String src = obj.getString("imgSource");
//                        String bitmap = null;
//                        Log.d("fetching", "before: " + bitmap);
//                        new GetImageFromUrl(contact).execute(src);
//                        Log.d("fetching", "after: " + bitmap);
////                        handler.addContact(contact, MainActivity.this, bitmap);
//                    }
//
//
////                    Log.d("fetching API", response.get(0).toString());
////                    Log.d("fetching API", response.get(1).toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("fetching API", String.valueOf(error));
//                    }
//                });
//
//        requestQueue.add(jsonArrayRequest);


    }

//    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
//
//        Contact contact = new Contact();
//
//        public GetImageFromUrl(Contact contact) {
//            this.contact = contact;
//        }
//
//        protected Bitmap doInBackground(String... url) {
//            String stringUrl = url[0];
//            Bitmap bitmap = null;
//            InputStream inputStream;
//            try {
//                inputStream = new java.net.URL(stringUrl).openStream();
//                bitmap = BitmapFactory.decodeStream(inputStream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.d("fetching", "doInBackground: " + bitmap);
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmapPhoto) {
//            super.onPostExecute(bitmapPhoto);
//            Bitmap bitmap = bitmapPhoto;
//
//            addContact(contact, mainActivityContext, bitmap);
//            Log.d("fetchBitmap", "onPostExecute: " + bitmap);
//        }
//    }

}