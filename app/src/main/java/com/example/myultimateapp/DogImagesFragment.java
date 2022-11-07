package com.example.myultimateapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class DogImagesFragment extends Fragment {



    ProgressBar pgb;
    RequestQueue requestQueue;
    ImageView dogImage;

    public DogImagesFragment() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pgb = (ProgressBar) view.findViewById(R.id.progressBar);
        dogImage = (ImageView) view.findViewById(R.id.dogImage);
        Button nextbtn = view.findViewById(R.id.nextDogImageBtn);
        requestQueue = Volley.newRequestQueue(view.getContext());
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pgb.setVisibility(view.VISIBLE);
                fetchDogImage(view);

            }
        });

        fetchDogImage(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dog_images, container, false);
    }


    public void fetchDogImage(View view) {

        String apiURL = "https://dog.ceo/api/breeds/image/random";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                apiURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response != null) {
                        String src = response.getString("message");
                        new GetImageFromUrl().execute(src);
                    }

                } catch (JSONException e) {
                    Log.d("fetchingJoke", e.toString());
                    fetchDogImage(view);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fetchingJoke", "Something went wrong" + error);
                fetchDogImage(view);
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {


        public GetImageFromUrl() {

        }

        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            Bitmap bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("fetching", "doInBackground: " + bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmapPhoto) {
            super.onPostExecute(bitmapPhoto);
            Bitmap bitmap = bitmapPhoto;
            pgb.setVisibility(View.GONE);
            dogImage.setImageBitmap(bitmap);
            Log.d("fetchBitmap", "onPostExecute: " + bitmap);
        }
    }
}