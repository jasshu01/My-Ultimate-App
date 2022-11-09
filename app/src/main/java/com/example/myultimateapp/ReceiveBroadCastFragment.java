package com.example.myultimateapp;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ReceiveBroadCastFragment extends Fragment {


    public ReceiveBroadCastFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    NetworkChangeBroadcast networkChangeBroadcast;

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intent = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(networkChangeBroadcast, intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(networkChangeBroadcast);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_receive_broad_cast, container, false);
        TextView updates = view.findViewById(R.id.connectivityUpdates);
        networkChangeBroadcast = new NetworkChangeBroadcast(updates);

        return view;
    }
}