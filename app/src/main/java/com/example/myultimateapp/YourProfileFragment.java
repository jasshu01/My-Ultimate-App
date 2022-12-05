package com.example.myultimateapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class YourProfileFragment extends Fragment {

    ImageView displayPic;
    TextView name, username, email, phone, dob, address, postalcode;

    public YourProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_profile, container, false);

        displayPic = view.findViewById(R.id.yourProfile_ProfilePic);
        name = view.findViewById(R.id.yourProfile_Name);
        username = view.findViewById(R.id.yourProfile_UserName);
        email = view.findViewById(R.id.yourProfile_Email);
        phone = view.findViewById(R.id.yourProfile__phone);
        dob = view.findViewById(R.id.yourProfile_dob);
        address = view.findViewById(R.id.yourProfile_Address);
        postalcode = view.findViewById(R.id.yourProfile_PostalCode);

        dbHandler handler = new dbHandler(getContext(), "myApp", null, 1);

        SharedPreferences sp = getActivity().getSharedPreferences("Current User", MODE_PRIVATE);
        String currentUser = sp.getString("LoggedInUser", "");

        UserDetails user = handler.fetchUserUsingUserName(currentUser);


        Bitmap myImage = handler.StringToBitMap(user.getImageurls());
        if (myImage != null)
            displayPic.setImageBitmap(myImage);
        else
            displayPic.setImageResource(R.drawable.person);


//        Log.d("profilepage", "onCreateView: "+user.toString());
        name.setText("Name: " + user.getTitle() + " " + user.getFirstName() + " " + user.getLastName());
        username.setText("Username: " + user.getUsername());
        email.setText("Email: " + user.getEmail());
        phone.setText("Phone: " + user.getPhone());
        dob.setText("DOB: " + user.getDob());
        address.setText("Address: " + user.getAddress());
        postalcode.setText("Postal Code: " + user.getPostalcode());

        return view;
    }
}