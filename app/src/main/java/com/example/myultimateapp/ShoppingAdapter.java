package com.example.myultimateapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    //    private static TreeMap<String, ArrayList<Product>> localDataSet;
    private static ArrayList<Pair<String, ArrayList<Product>>> localDataSet = new ArrayList<Pair<String, ArrayList<Product>>>(0);
    private static Context context;
    private static String Category;
//    TextView categoryName;
//    RecyclerView categoryRecyclerView;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView categoryName;
        RecyclerView categoryRecyclerView;

        public ViewHolder(View view) {
            super(view);

            categoryName = view.findViewById(R.id.shoppingProductCategory);
            categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        }


        @Override
        public void onClick(View view) {

        }


    }

    public ShoppingAdapter(TreeMap<String, ArrayList<Product>> dataSet) {

//        localDataSet = dataSet;

        Set<Map.Entry<String, ArrayList<Product>>> keys = dataSet.entrySet();


        for (Map.Entry<String, ArrayList<Product>> key :
                keys) {
            Pair<String, ArrayList<Product>> pair = new Pair<String, ArrayList<Product>>(key.getKey(), key.getValue());
            Log.d("displayingActivity", pair.toString());
            localDataSet.add(pair);
        }


        Log.d("displayingActivity", "ShoppingAdapter: " + keys.toString());

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_shopping_view, viewGroup, false);


        RecyclerView categoryRecyclerView;
        context = viewGroup.getContext();
//        categoryName = view.findViewById(R.id.shoppingProductCategory);
//        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        CategoryAdapter childItemAdapter = new CategoryAdapter(localDataSet.get(position).second);
        viewHolder.categoryRecyclerView.setLayoutManager(layoutManager);
        viewHolder.categoryName.setText(localDataSet.get(position).first);
        viewHolder.categoryRecyclerView.setAdapter(childItemAdapter);


    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}