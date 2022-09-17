package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.FlexBenefit;
import com.palm.newbenefit.R;

import java.io.InputStream;
import java.util.List;

;

public class myFlexsummaryAdapterBenefit extends RecyclerView.Adapter<myFlexsummaryAdapterBenefit.ViewHolder> {
    Constants con;
    private List<FlexBenefit> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public myFlexsummaryAdapterBenefit(Context context, List<FlexBenefit> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.flex_list_benefit, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        FlexBenefit train = mTrain.get(position);
        con = new Constants();



            viewHolder.flex_name.setText(train.getFlex_name());

        viewHolder.final_amount.setText(train.getFinal_amount());
        viewHolder.deduction_type.setText(train.getDeduction_type());























       // Picasso.get().load(train.getRedirect_url()).into(viewHolder.s_logo);




    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView flex_name,flex_discription,final_amount,allocate,deduction_type;
        public ImageView s_logo;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            flex_name = (TextView) itemView.findViewById(R.id.flex_name);
            flex_discription = (TextView) itemView.findViewById(R.id.flex_discription);
            final_amount = (TextView) itemView.findViewById(R.id.final_amount);
            allocate = (TextView) itemView.findViewById(R.id.allocate);
            s_logo = (ImageView) itemView.findViewById(R.id.s_logo);
            deduction_type= (TextView) itemView.findViewById(R.id.deduction_type);
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
