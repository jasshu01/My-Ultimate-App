package com.example.myultimateapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Provider;

public class SendBroadCastFragment extends Fragment {


    EditText message;
    Button button;

    public SendBroadCastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_broad_cast, container, false);
        message = view.findViewById(R.id.broadCast_broadcastMessage);
        button = view.findViewById(R.id.broadCast_sendBroadCastMessage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = String.valueOf(message.getText());


                Intent i = new Intent("com.example.mybroadcastreceiverapp.START_MY_SERVICE");
                i.setPackage("com.example.mybroadcastreceiverapp");

//                i.setComponent(new ComponentName("com.example.mybroadcastreceiverapp", "com.example.mybroadcastreceiverapp.service.MyIntentService"));

                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        getContext().startForegroundService(i);
                        Log.d("NEWSTACK", "starting service inultimate 1 " + i.getAction());

                    }
                    else
                        getContext().startService(i);
                    Log.d("NEWSTACK", "starting service inultimate 2 " + i.getAction());



                } catch (Exception e) {
                    Log.d("NEWSTACK", e.toString());
                }



                Intent intent = new Intent();
                intent.setAction("com.jasshugarg.ultimateappsender");
                intent.putExtra("Broadcast Message", msg);
                getContext().sendBroadcast(intent);
                Toast.makeText(getContext(), "Message Broadcasted", Toast.LENGTH_SHORT).show();
                message.setText("");

            }
        });


        return view;
    }
}