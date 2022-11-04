package com.example.myultimateapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ActivitiesFragmentAdapter extends RecyclerView.Adapter<ActivitiesFragmentAdapter.ViewHolder> {

    private static ArrayList<String> localDataSet;
    private static Context context;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static TextView activityTextView;


        public ViewHolder(View view) {
            super(view);

            activityTextView = (TextView) view.findViewById(R.id.activityTextView);

        }


        public TextView getActivityTextView() {
            return activityTextView;
        }




        @Override
        public void onClick(View view) {




        }





    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ActivitiesFragmentAdapter(Context context, ArrayList<String> dataSet) {
        this.context = context;
        localDataSet = dataSet;

        Log.d("displayingActivity", "ActivitiesFragmentAdapter: "+localDataSet);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_view, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getActivityTextView().setText(localDataSet.get(position));
//        viewHolder.getActivityTextView().setText("item "+position);


        Log.d("displayingActivity", "onBindViewHolder: "+position);

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}