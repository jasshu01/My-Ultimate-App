package com.example.myultimateapp;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ShoppingFragment extends Fragment {


    TreeMap<String, ArrayList<Product>> data = new TreeMap<String, ArrayList<Product>>();
    RecyclerView shoppingRecyclerView;
    int totalProducts = 12;
    RequestQueue requestQueue;
    ShoppingAdapter adapter1;

    public ShoppingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
//
        shoppingRecyclerView = view.findViewById(R.id.shoppingRecyclerView);
//
//
////        https://dummyjson.com/products
//
//        requestQueue = Volley.newRequestQueue(getContext());
//        String apiURL = "https://dummyjson.com/products";
//        for (int i = 1; i <= totalProducts; i++) {
//            fetchProducts(apiURL + "/" + i);
//            try {
//                sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//


        adapter1 = new ShoppingAdapter(data);
        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingRecyclerView.setAdapter(adapter1);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        adapter1 = new ShoppingAdapter(data);
//        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        shoppingRecyclerView.setAdapter(adapter1);

//        https://dummyjson.com/products

        requestQueue = Volley.newRequestQueue(getContext());
        String apiURL = "https://dummyjson.com/products";
        for (int i = 1; i <= totalProducts; i++) {
            fetchProducts(apiURL + "/" + i);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void fetchProducts(String apiURL) {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("fetchedproducts", response.toString());
                Bitmap thumbnail = null;
                InputStream inputStream;
                try {

//
//                    {
//                        "id": 1,
//                            "title": "iPhone 9",
//                            "description": "An apple mobile which is nothing like apple",
//                            "price": 549,
//                            "discountPercentage": 12.96,
//                            "rating": 4.69,
//                            "stock": 94,
//                            "brand": "Apple",
//                            "category": "smartphones",
//                            "thumbnail": "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
//                            "images": [
//                        "https://dummyjson.com/image/i/products/1/1.jpg",
//                                "https://dummyjson.com/image/i/products/1/2.jpg",
//                                "https://dummyjson.com/image/i/products/1/3.jpg",
//                                "https://dummyjson.com/image/i/products/1/4.jpg",
//                                "https://dummyjson.com/image/i/products/1/thumbnail.jpg"
//]
//                    }

                    Product product = new Product();
                    product.setBrand(response.getString("brand"));
                    product.setTitle(response.getString("title"));
                    product.setCategory(response.getString("category"));
                    product.setId(Integer.parseInt(response.getString("id")));
                    product.setDescription(response.getString("description"));
                    product.setPrice(Integer.parseInt(response.getString("price")));
                    product.setRating(Double.parseDouble(response.getString("rating")));
                    product.setThumbnail(thumbnail);


//                    data.get(product.getCategory()).add(product);
//                    Log.d("fetchedproducts", product.toString());

                    Pair<Product, String> pair = new Pair<Product, String>(product, response.getString("thumbnail"));
                    new MyTask().execute(pair);
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

    private class MyTask extends AsyncTask<Pair<Product, String>, String, Pair<Product, Bitmap>> {


        @Override
        protected Pair<Product, Bitmap> doInBackground(Pair<Product, String>... pairs) {
            Bitmap thumbnail = null;

            InputStream inputStream = null;
            try {
                inputStream = new URL(pairs[0].second).openConnection().getInputStream();
                thumbnail = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }


            return new Pair<>(pairs[0].first, thumbnail);
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Pair<Product, Bitmap> pairPB) {
            super.onPostExecute(pairPB);
            pairPB.first.setThumbnail(pairPB.second);
            Product myProduct = pairPB.first;

            ArrayList<Product> myProducts = new ArrayList<Product>();
            if (data.get(myProduct.getCategory()) != null) {
                myProducts = data.get(myProduct.getCategory());
//                data.get(myProduct.getCategory()).clear();
            }

            myProducts.add(myProduct);
            data.put(myProduct.getCategory(), myProducts);


            if (myProduct.getId() == totalProducts)
            {
                ShoppingAdapter adapter = new ShoppingAdapter(data);
//                 adapter1 = new ShoppingAdapter(data);
                shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                shoppingRecyclerView.setAdapter(adapter);

            }
//
//            if (adapter1 != null)
//                adapter1.notifyDataSetChanged();
//            else{
//                adapter1=new ShoppingAdapter(data);
//                shoppingRecyclerView.setAdapter(adapter1);
//            }
        }
    }


}