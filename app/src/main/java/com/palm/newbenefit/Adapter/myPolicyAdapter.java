package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.palm.newbenefit.Activity.PdfActivityjava;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Policy;
import com.palm.newbenefit.R;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

;

public class myPolicyAdapter extends RecyclerView.Adapter<myPolicyAdapter.ViewHolder> {
    Constants con;
    private List<Policy> mTrain;
    private Context context = null;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    // Pass in the contact array into the constructor
    public myPolicyAdapter(Context context, List<Policy> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.policy_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Policy train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;
        TextView cover = viewHolder.cov;
        TextView member = viewHolder.m_cov;
        TextView covera = viewHolder.cover;



        try{

            String strCurrentDate = train.getEnd_date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            viewHolder.premium.setText(format.format(newDate));
        }catch (Exception e){
            viewHolder.premium.setText(train.getEnd_date());
        }
        viewHolder.company_name.setText(train.getCompany_name());

        if(train.getStatus().equalsIgnoreCase("1")){
            viewHolder.status.setText("Active");
        }else {

            viewHolder.status.setText("Expired");

        }






        viewHolder.doc.setOnClickListener(new View.OnClickListener() {
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

                            Policy train = mTrain.get(position);

                            Intent intent = new Intent(context, PdfActivityjava.class);
                            intent.putExtra("pol", train.getRedirect_url());

                            context. startActivity(intent);

                        } catch (Exception e) {



                        }

// dismiss the progress dialog

                        progressDialog.dismiss();

                    }

                }.start();

            }

        });





        viewHolder.days.setText(train.getDay_left());



        viewHolder.frogm.setText(train.getCover_type());



            covera.setText(train.getPolicy_no());




            viewHolder.premium_data.setText(train.getStart_date());
        viewHolder.opd_cover.setText(train.getPremium());



        member.setText(train.getSuminsured());




        if(train.getRedirect_url().equalsIgnoreCase("")||train.getRedirect_url().isEmpty())
        {

        }else {
            Picasso.get().load(train.getRedirect_url()).into(viewHolder.s_logo);
        }




        viewHolder.s_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();
                Policy train = mTrain.get(position);
                final Dialog dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.queries_document);

                ImageView image = dialog.findViewById(R.id.image);
                ImageView ab = (ImageView) dialog.findViewById(R.id.ab);


                ab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                Picasso.get().load(train.getRedirect_url()).into(image);






        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);



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
        public TextView p_type,opd_cover,days,status,company_name, m_cov, cov, cover, premium, frogm, premium_data;
        public ImageView s_logo, p_img;
        LinearLayout balance, pre,opd,doc;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            frogm = (TextView) itemView.findViewById(R.id.frogm);
            premium = (TextView) itemView.findViewById(R.id.premium);
            m_cov = (TextView) itemView.findViewById(R.id.m_cov);
            cov = (TextView) itemView.findViewById(R.id.cov);
            s_logo = (ImageView) itemView.findViewById(R.id.s_logo);
            // p_img = (ImageView) itemView.findViewById(R.id.p_img);
            cover = (TextView) itemView.findViewById(R.id.cover);
            premium_data = (TextView) itemView.findViewById(R.id.premium_data);
            balance = (LinearLayout) itemView.findViewById(R.id.balance);
            pre = (LinearLayout) itemView.findViewById(R.id.pre);
            opd= (LinearLayout) itemView.findViewById(R.id.opd);
            opd_cover= (TextView) itemView.findViewById(R.id.opd_cover);
            doc= (LinearLayout) itemView.findViewById(R.id.doc);
            status= (TextView) itemView.findViewById(R.id.status);
            company_name= (TextView) itemView.findViewById(R.id.company_name);
            days= (TextView) itemView.findViewById(R.id.days);
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
