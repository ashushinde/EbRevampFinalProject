package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Feature;
import com.kmd.newbenefit.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

;

public class MyFeatureAdapter extends RecyclerView.Adapter<MyFeatureAdapter.ViewHolder> {
    Constants con;
    private List<Feature> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public MyFeatureAdapter(Context context, List<Feature> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.feature_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Feature train = mTrain.get(position);
        con = new Constants();

        viewHolder.p_type.setText(train.getTitle());
        viewHolder.content.setText(train.getContent());



if(train.getImage().equalsIgnoreCase("null")||train.getImage().isEmpty()||
        train.getImage().equalsIgnoreCase("")){
    viewHolder.logos.setVisibility(View.GONE);
        }else {
    viewHolder.logos.setVisibility(View.VISIBLE);
    Picasso.get().load(train.getImage()).into(viewHolder.s_logo);
        }



    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView p_type,content;
        public ImageView s_logo;
        LinearLayout logos;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            //    policyname= (TextView) itemView.findViewById(R.id.policyname);

            s_logo = (ImageView) itemView.findViewById(R.id.s_logo);
            content= (TextView) itemView.findViewById(R.id.content);
            p_type= (TextView) itemView.findViewById(R.id.p_type);
            logos= (LinearLayout) itemView.findViewById(R.id.logos);
        }
    }


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {

                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
