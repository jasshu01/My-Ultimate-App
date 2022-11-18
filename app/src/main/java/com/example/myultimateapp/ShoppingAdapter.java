package com.example.myultimateapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.UserDictionary;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private static ArrayList<Pair<String, ArrayList<Product>>> localDataSet = new ArrayList<Pair<String, ArrayList<Product>>>(0);
    private static Context context;
    private static String Category;
    private static TreeMap<String, ArrayList<Product>> dataToSend;

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
        if (localDataSet != null)
            localDataSet.clear();

        dataToSend = dataSet;
        Set<Map.Entry<String, ArrayList<Product>>> keys = dataSet.entrySet();


        for (Map.Entry<String, ArrayList<Product>> key :
                keys) {

            Pair<String, ArrayList<Product>> pair = new Pair<String, ArrayList<Product>>(key.getKey(), key.getValue());
            Log.d("displayingActivity4", pair.toString());
            localDataSet.add(pair);
        }


        Log.d("displayingActivity", "ShoppingAdapter: " + keys.toString());

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_shopping_view, viewGroup, false);

        context = viewGroup.getContext();


        return new ViewHolder(view);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

//        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);


        Log.d("displayingActivity6", "\n\n\ncategory " + localDataSet.get(position).first);
        Log.d("currpos", "\n\n\ncategory " + localDataSet.get(position).first);
        Log.d("displayingActivity2", "\n\n\ncategory " + localDataSet.get(position).first);


//        int pos=viewHolder.getBindingAdapterPosition();
        int pos = position;

        viewHolder.categoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, localDataSet.get(viewHolder.getAbsoluteAdapterPosition()).first, Toast.LENGTH_SHORT).show();

            }
        });
        String category=localDataSet.get(pos).first;
        viewHolder.categoryName.setText(category.substring(0, 1).toUpperCase() + category.substring(1));
        CategoryAdapter childItemAdapter = new CategoryAdapter(dataToSend, localDataSet.get(pos).first);
//        viewHolder.categoryName.setText(localDataSet.get(position).first);
//        CategoryAdapter childItemAdapter = new CategoryAdapter(localDataSet.get(position).second);
        viewHolder.categoryRecyclerView.setLayoutManager(layoutManager);
        viewHolder.categoryRecyclerView.setAdapter(childItemAdapter);
        childItemAdapter.notifyDataSetChanged();
//        viewHolder.categoryRecyclerView.setItemViewCacheSize(0);

    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}