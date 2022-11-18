package com.example.myultimateapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private static ArrayList<Product> localDataSet;

    TextView details, rating, price;
    ImageView thumbnail;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View view) {
            super(view);

        }


        @Override
        public void onClick(View view) {

        }


    }

    public CategoryAdapter(ArrayList<Product> dataSet) {


        if(localDataSet!=null)
        localDataSet.clear();


        localDataSet = dataSet;

for(int i=0;i<dataSet.size();i++)
        Log.d("displayingActivity2", "categoryadapter: " + dataSet.get(i).toString());

        Log.d("displayingActivity2", "CategoryAdapter: " + localDataSet.size());
        Log.d("displayingActivity2", "CategoryAdapter: " + dataSet.size());

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_shopping_product_view, viewGroup, false);

        details = view.findViewById(R.id.productDesc);
        price = view.findViewById(R.id.productPrice);
        rating = view.findViewById(R.id.productRating);
        thumbnail = view.findViewById(R.id.productThumbnail);


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
        Log.d("displayingActivity2", "categoryadapter: " + localDataSet.get(position).toString());

        String productdetails = localDataSet.get(position).getBrand() + "-" + localDataSet.get(position).getTitle() + "\n" + localDataSet.get(position).getDescription();
        productdetails = productdetails.substring(0, 50);
//
//
        details.setText(productdetails);
        price.setText("$"+String.valueOf(localDataSet.get(position).getPrice()));
        rating.setText(String.valueOf(localDataSet.get(position).getRating()));

        thumbnail.setImageBitmap(localDataSet.get(position).getThumbnail());


    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
