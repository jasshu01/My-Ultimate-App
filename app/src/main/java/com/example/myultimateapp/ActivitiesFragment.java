package com.example.myultimateapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivitiesFragment extends Fragment {

    Context context;
    RequestQueue requestQueue;
    RecyclerView recyclerView;

    ArrayList<String> activities = new ArrayList<>();

    public ActivitiesFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        requestQueue = Volley.newRequestQueue(context);
        recyclerView = view.findViewById(R.id.recyclerViewActivities);


        for (int i = 0; i < 30; i++) {
            fetchActivity();
        }

        ActivitiesFragmentAdapter adapter = new ActivitiesFragmentAdapter(context, activities);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        Log.d("displayingActivities", "onViewCreated: " + activities);


    }


    public void fetchActivity() {

        String apiURL = "https://www.boredapi.com/api/activity";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                apiURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response != null) {
                        activities.add(response.getString("activity"));


                        Log.d("fetchingActivities", "onViewCreated: fetching " + activities);

                        ActivitiesFragmentAdapter adapter = new ActivitiesFragmentAdapter(context, activities);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fetchingActivity", "Something went wrong " + error);
            }
        });

        requestQueue.add(jsonObjectRequest);
        return;
    }
}