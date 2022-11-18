package com.example.myultimateapp;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    //    private static ArrayList<Product> localDataSet;
    private static TreeMap<String, ArrayList<Product>> localDataSet;
    String category;
    TextView details, rating, price;
    ImageView thumbnail;
    CardView productCard;
    int color;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View view) {
            super(view);

        }


        @Override
        public void onClick(View view) {

        }


    }

    public CategoryAdapter(TreeMap<String, ArrayList<Product>> dataSet, String category) {


        Random rnd = new Random();
        color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        this.category = category;
        localDataSet = dataSet;

        for (int i = 0; i < localDataSet.get(category).size(); i++)
            Log.d("displayingActivity6", "new : " + localDataSet.get(category).get(i).getTitle());

        Log.d("displayingActivity2", "CategoryAdapter: " + localDataSet.size());
        Log.d("displayingActivity2", "CategoryAdapter: " + dataSet.size());

    }

    // Create new views (invoked by the layout manager)
    @SuppressLint("MissingInflatedId")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_shopping_product_view, viewGroup, false);

        details = view.findViewById(R.id.productDesc);
        price = view.findViewById(R.id.productPrice);
        rating = view.findViewById(R.id.productRating);
        thumbnail = view.findViewById(R.id.productThumbnail);
        productCard = view.findViewById(R.id.productCard);


        return new ViewHolder(view);
    }


//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        Log.d("displayingActivity2", "categoryadapter: " + position);
        Log.d("displayingActivity2", "categoryadapter: " + getItemCount());
        Log.d("displayingActivity2", "categoryadapter: " + localDataSet.get(category).get(position).toString());
        productCard.setCardBackgroundColor(color);
//        int pos = viewHolder.getAbsoluteAdapterPosition();
        int pos = position;


        String productdetails = localDataSet.get(category).get(pos).getBrand() + "-" + localDataSet.get(category).get(pos).getTitle() + "\n" + localDataSet.get(category).get(pos).getDescription();
        productdetails = productdetails.substring(0, 50);
        details.setText(productdetails);
        price.setText("$" + String.valueOf(localDataSet.get(category).get(pos).getPrice()));
        rating.setText(String.valueOf(localDataSet.get(category).get(pos).getRating()));
        thumbnail.setImageBitmap(localDataSet.get(category).get(pos).getThumbnail());

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(thumbnail.getContext(), category, Toast.LENGTH_SHORT).show();

            }
        });

//        String productdetails = localDataSet.get(position).getBrand() + "-" + localDataSet.get(position).getTitle() + "\n" + localDataSet.get(position).getDescription();
//        productdetails = productdetails.substring(0, 50);
//        details.setText(productdetails);
//        price.setText("$" + String.valueOf(localDataSet.get(position).getPrice()));
//        rating.setText(String.valueOf(localDataSet.get(position).getRating()));
//        thumbnail.setImageBitmap(localDataSet.get(position).getThumbnail());


    }


    @Override
    public int getItemCount() {
        Log.d("getitemcountcalled", "yesss it is called " + localDataSet.size());
        return localDataSet.get(category).size();
    }
}
