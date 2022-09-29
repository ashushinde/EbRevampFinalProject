package com.palm.newbenefit.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.Activity.EditDiscActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.DiscData;
import com.kmd.newbenefit.R;

import java.io.InputStream;
import java.util.List;

public class RecommandAdapter extends
        RecyclerView.Adapter<RecommandAdapter.ViewHolder> {
    Constants con;
    private List<DiscData> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public RecommandAdapter(Context context, List<DiscData> train) {

        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.disc_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final DiscData train = mTrain.get(position);
         con=new Constants();
        // Set item views based on your views and data model

        viewHolder.comment.setText(train.getRemark());
        viewHolder.response.setText(train.getResponse());
        viewHolder.at.setText(train.getCreated_at());


if(train.getStatus().equalsIgnoreCase("1")){
    viewHolder.status.setText("Resubmitted");
}else if(train.getStatus().equalsIgnoreCase("0")){

    viewHolder.status.setText("Resolved");
}
        viewHolder.response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);


                progressDialog.setMessage("Its loading....");

                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                new Thread() {

                    public void run() {

                        try {

                            Context context = v.getContext();

                            DiscData train = mTrain.get(position);

                            if(train.getStatus().equalsIgnoreCase("1")){

                            }else if(train.getStatus().equalsIgnoreCase("0")){

                            }else {
                                Intent intent = new Intent(context, EditDiscActivity.class);
                                intent.putExtra("pol", train.getId());
                                intent.putExtra("disc_id", train.getClaim_id());

                                context. startActivity(intent);
                            }



                        } catch (Exception e) {
                        }
                        progressDialog.dismiss();
                    }

                }.start();

            }

        });







    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView comment,at,status;

        public Button response ;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            comment = (TextView) itemView.findViewById(R.id.comment);
            status = (TextView) itemView.findViewById(R.id.status);
            at = (TextView) itemView.findViewById(R.id.at);

            response = (Button) itemView.findViewById(R.id.response);

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
