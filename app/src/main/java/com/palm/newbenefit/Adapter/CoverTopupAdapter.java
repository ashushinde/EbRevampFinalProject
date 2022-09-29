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

import com.squareup.picasso.Picasso;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Cover;
import com.kmd.newbenefit.R;

import java.io.InputStream;
import java.util.List;

public class CoverTopupAdapter extends
        RecyclerView.Adapter<CoverTopupAdapter.ViewHolder> {
    Constants con;
    private List<Cover> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public CoverTopupAdapter(Context context, List<Cover> train) {

        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.seventh_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Cover train = mTrain.get(position);
         con=new Constants();
        // Set item views based on your views and data model



        Picasso.get().load(train.getCover_type_logo()).into(viewHolder.logo);


        viewHolder.price.setText(train.getPrice_per_employee());
        viewHolder.name.setText(train.getCover_type_title());
        viewHolder.detail.setText(train.getCover_type_content());


//        viewHolder.next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
//
//
//                progressDialog.setMessage("Its loading....");
//
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progressDialog.show();
//
//                new Thread() {
//
//                    public void run() {
//
//                        try {
//
//                            Context context = v.getContext();
//
//                            Cover train = mTrain.get(position);
//
//                            Intent intent = new Intent(context, EightRfqActivity.class);
//                            intent.putExtra("pol", train.getPolicy_sub_type_id());
//
//                            context. startActivity(intent);
//
//                        } catch (Exception e) {
//                        }
//                        progressDialog.dismiss();
//                    }
//
//                }.start();
//
//            }
//
//        });














    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView price,name,detail;
        public ImageView logo;
        LinearLayout next;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            price = (TextView) itemView.findViewById(R.id.price);
            name = (TextView) itemView.findViewById(R.id.name);
            detail = (TextView) itemView.findViewById(R.id.detail);

            logo = (ImageView) itemView.findViewById(R.id.logo);
            next= (LinearLayout) itemView.findViewById(R.id.next);

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
