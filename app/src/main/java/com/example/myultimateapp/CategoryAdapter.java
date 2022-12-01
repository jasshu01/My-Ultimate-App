package com.example.myultimateapp;

import static java.lang.Math.round;
import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
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
    int color, textColor;
    String layout = "HORIZONTAL";

    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View view) {
            super(view);


        }


        @Override
        public void onClick(View view) {
        }


    }

    public CategoryAdapter() {

    }

    public CategoryAdapter(TreeMap<String, ArrayList<Product>> dataSet, String category, String layout) {


        Random rnd = new Random();
        color = Color.argb(10, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        textColor =  Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        textColor = Color.BLACK;

        this.layout = layout;
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


        View view;// = LayoutInflater.from(viewGroup.getContext())
        // .inflate(R.layout.fragment_shopping_product_view, viewGroup, false);

        if (layout == "HORIZONTAL") {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.fragment_shopping_product_view, viewGroup, false);

        } else {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.fragment_shopping_product_view_vertical, viewGroup, false);

        }
        details = view.findViewById(R.id.productDesc);
        price = view.findViewById(R.id.productPrice);
        rating = view.findViewById(R.id.productRating);
        thumbnail = view.findViewById(R.id.productThumbnail);
        productCard = view.findViewById(R.id.productCard);


        context = view.getContext();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {


        Log.d("displayingActivity2", "categoryadapter: " + position);
        Log.d("displayingActivity2", "categoryadapter: " + getItemCount());
        Log.d("displayingActivity2", "categoryadapter: " + localDataSet.get(category).get(position).toString());
        productCard.setCardBackgroundColor(color);
        details.setTextColor(textColor);
//        int pos = viewHolder.getAbsoluteAdapterPosition();
        int pos = position;


        String productdetails = "<strong>" + localDataSet.get(category).get(pos).getBrand() + "-" + localDataSet.get(category).get(pos).getTitle() + "</strong>" + "\n" + localDataSet.get(category).get(pos).getDescription();


        double discountVal = localDataSet.get(category).get(pos).getDiscount();
        double priceVal = localDataSet.get(category).get(pos).getPrice();
//        double finalPriceVal=Math.round((1-discountVal/100)*priceVal);
        double finalPriceVal = Double.parseDouble(new DecimalFormat("0.00").format((100.00 - discountVal) * priceVal / 100));
        String priceText = "<strong><i><s>$" + String.valueOf(priceVal) + "</s></i></strong>" + " <strong><i>" + "$" + String.valueOf(finalPriceVal) + "</i></strong>";

        details.setText(Html.fromHtml(productdetails));
        price.setText(Html.fromHtml(priceText));
        rating.setText(String.valueOf(localDataSet.get(category).get(pos).getRating()));
        thumbnail.setImageBitmap(localDataSet.get(category).get(pos).getThumbnail());

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(thumbnail.getContext(), localDataSet.get(category).get(pos).getTitle(), Toast.LENGTH_SHORT).show();


                Product currentProduct = localDataSet.get(category).get(position);
                Log.d("currProduct", currentProduct.toString());

                new MyTask2().execute(currentProduct);
            }
        });


    }


    public void createDialogBox(Product currentProduct) {


        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_product_details);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        TextView productDetailsBrand = dialog.findViewById(R.id.productDetailsBrand);
        TextView productDetailsTitle = dialog.findViewById(R.id.productDetailsTitle);
        TextView productDetailsDesc = dialog.findViewById(R.id.productDetailsDesc);
        TextView productDetailsPrice = dialog.findViewById(R.id.productDetailsPrice);
        TextView productDetailsRating = dialog.findViewById(R.id.productDetailsRating);
        ImageView productDetailsThumbnail = dialog.findViewById(R.id.productDetailsThumbnail);
        LinearLayout productDetailsImages = dialog.findViewById(R.id.productDetailsImages);

        for (Bitmap image :
                currentProduct.getImages()) {
            ImageView imageview = new ImageView(context);
            imageview.setImageBitmap(image);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    800,800
            );
            params.setMargins(20, 0, 20, 0);
            imageview.setLayoutParams(params);

            productDetailsImages.addView(imageview);
        }

        productDetailsBrand.setText(currentProduct.getBrand());
        productDetailsTitle.setText(currentProduct.getCategory() + " - " + currentProduct.getTitle());
        productDetailsDesc.setText(currentProduct.getDescription());


        double discountVal = currentProduct.getDiscount();
        double priceVal = currentProduct.getPrice();

        double finalPriceVal = Double.parseDouble(new DecimalFormat("0.00").format((100.00 - discountVal) * priceVal / 100));
        String priceText = "<strong><i><s>$" + String.valueOf(priceVal) + "</s></i></strong>" + " <strong><i>" + "$" + String.valueOf(finalPriceVal) + "</i></strong>";


        productDetailsPrice.setText(Html.fromHtml(priceText));
        productDetailsRating.setText(String.valueOf(currentProduct.getRating()));


        productDetailsThumbnail.setImageBitmap(currentProduct.getThumbnail());

        dialog.show();

    }

    private class MyTask2 extends AsyncTask<Product, String, Product> {


        @Override
        protected Product doInBackground(Product... products) {
            Product product = products[0];
            ArrayList<Bitmap> images = new ArrayList<>();

            for (int i = 0; i < product.getImagesURL().size(); i++) {
                String url = product.getImagesURL().get(i);
                InputStream inputStream = null;
                try {
                    inputStream = new URL(url).openConnection().getInputStream();
                    images.add(BitmapFactory.decodeStream(inputStream));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            product.setImages(images);
            return product;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Product product) {
            super.onPostExecute(product);

            createDialogBox(product);
        }
    }


    @Override
    public int getItemCount() {
        Log.d("getitemcountcalled", "yesss it is called " + localDataSet.size());
        return localDataSet.get(category).size();
    }
}
