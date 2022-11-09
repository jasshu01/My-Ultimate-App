package com.example.myultimateapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class NetworkChangeBroadcast extends BroadcastReceiver {

    TextView updates;

    public NetworkChangeBroadcast(TextView updates) {
        this.updates = updates;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        checkInternet(context);
    }

    private boolean checkInternet(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        String message = "";


        if (networkInfo != null && networkInfo.isConnected()) {
            message = "Connected to " + networkInfo.getTypeName();
        } else {
            message = "No Available network!";
        }


        updates.setText(message);

        return networkInfo != null && networkInfo.isConnected();

    }
}