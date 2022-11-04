package com.example.myultimateapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JokesFragment extends Fragment {
    Context context;
    TextView textView;
    TextView textView2;
    ProgressBar pgb;

    public JokesFragment(Context context) {
        // Required empty public constructor
        this.context = context;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.jokeTextview);
        TextView textView2 = (TextView) view.findViewById(R.id.punchLineTextView);
        ProgressBar pgb = (ProgressBar) view.findViewById(R.id.progressBar);
        Button nextbtn = view.findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pgb.setVisibility(view.VISIBLE);
                fetchJoke(context, view);

            }
        });

        fetchJoke(context, view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_jokes, container, false);
    }


    public void fetchJoke(Context context, View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String apiURL = "https://official-joke-api.appspot.com/random_joke";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                apiURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response != null) {
                        pgb.setVisibility(View.GONE);
                        textView.setText(response.getString("setup"));
                        textView.setText(response.getString("setup"));
                        textView2.setText(response.getString("punchline"));
                        Log.d("fetchingJoke", "onResponse: " + textView.getText() + " " + textView2.getText());
                    }

                } catch (JSONException e) {
                    Log.d("fetchingJoke", e.toString());
                    fetchJoke(context, view);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fetchingJoke", "Something went wrong" + error);
                fetchJoke(context, view);
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}