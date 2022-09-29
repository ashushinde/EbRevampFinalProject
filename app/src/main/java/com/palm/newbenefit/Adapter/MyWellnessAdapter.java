package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.misc.AsyncTask;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Wellness;
import com.kmd.newbenefit.R;

import java.io.InputStream;
import java.util.List;

public class MyWellnessAdapter extends
        RecyclerView.Adapter<MyWellnessAdapter.ViewHolder> {
Constants con;
    private List<Wellness> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public MyWellnessAdapter(Context context, List<Wellness> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.fragment_wellness_second, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Wellness train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model


        viewHolder.title.setText(train.getBenefit_name());
        viewHolder.content.setText(train.getBenefit_content());
        viewHolder.amount.setText(train.getBenefit_name());

        if (train.getWellness_partner_name().equalsIgnoreCase("null")) {
            viewHolder.partner.setVisibility(View.GONE);
        } else {
            viewHolder.partner.setVisibility(View.VISIBLE);
            viewHolder.partner.setText(train.getWellness_partner_name());
        }


        // viewHolder.hide


        if (train.getWellness_partner_url().equalsIgnoreCase("null")) {
            viewHolder.hide.setVisibility(View.GONE);
        } else {
            viewHolder.hide.setVisibility(View.VISIBLE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Wellness train = mTrain.get(position);


                Context context = v.getContext();


                String url = train.getWellness_partner_url();
                Log.d("urlData", train.getWellness_partner_url());

                if (train.getWellness_partner_url().equalsIgnoreCase("null")) {


                } else {
                    if (train.getWellness_partner_url().contains("http") || train.getWellness_partner_url().contains("https")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        context.startActivity(browserIntent);

                    } else {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                        context.startActivity(browserIntent);

                    }
                }


// dismiss the progress dialog


            }

        });


        // Picasso.get().load(train.getWellness_partner_logo()).into(viewHolder.logo);

        new DownloadImageTask(viewHolder.logo).execute(train.getWellness_partner_logo());

    }







    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView title,content,more,amount,partner;
        LinearLayout hide;

      ImageView logo;
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            more = (TextView) itemView.findViewById(R.id.more);
            amount = (TextView) itemView.findViewById(R.id.amount);
            logo= (ImageView) itemView.findViewById(R.id.logo);
            hide=itemView.findViewById(R.id.hide);
            partner=itemView.findViewById(R.id.partner);

        }
    }
}
