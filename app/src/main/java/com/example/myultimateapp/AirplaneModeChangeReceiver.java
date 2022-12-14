package com.example.myultimateapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import java.util.Set;

public class AirplaneModeChangeReceiver extends BroadcastReceiver {


    @Override

    public void onReceive(Context context, Intent intent) {

        String message = "";
        if (isAirplaneModeOn(context.getApplicationContext())) {
            message = "AirPlane mode is on";

        } else {
            message = "AirPlane mode is off";

        }



        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        AlertDialog alertDialog = builder.create();


        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);

        builder.setNeutralButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        alertDialog.show();

    }

    private boolean isAirplaneModeOn(Context applicationContext) {

        Log.d("broadcastmy", "isAirplaneModeOn: "+ Settings.System.getString(applicationContext.getContentResolver(),Settings.Global.AIRPLANE_MODE_ON));
        Log.d("broadcastmy", "bluetooth: "+ Settings.System.getString(applicationContext.getContentResolver(),Settings.Global.BLUETOOTH_ON));
        Log.d("broadcastmy", "wifi: "+ Settings.System.getString(applicationContext.getContentResolver(),Settings.Global.WIFI_ON));

        return Settings.System.getInt(applicationContext.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    private boolean isBluetoothOn(Context applicationContext) {
        return Settings.System.getInt(applicationContext.getContentResolver(), Settings.Global.BLUETOOTH_ON, 0) != 0;
    }


}
