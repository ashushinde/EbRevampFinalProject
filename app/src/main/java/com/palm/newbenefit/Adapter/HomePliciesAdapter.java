package com.palm.newbenefit.Adapter;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.palm.newbenefit.Activity.MemberCoverHomeActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.PolicyData;
import com.kmd.newbenefit.R;

import java.io.InputStream;
import java.util.List;

public class HomePliciesAdapter extends
        RecyclerView.Adapter<HomePliciesAdapter.ViewHolder> {
    Constants con;
    private List<PolicyData> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public HomePliciesAdapter(Context context, List<PolicyData> train) {

        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.home_policy_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final PolicyData train = mTrain.get(position);
         con=new Constants();
        // Set item views based on your views and data model
        TextView type = viewHolder.policy_type;
        TextView policyies = viewHolder.policy;
        TextView member = viewHolder.member_cover;
        TextView isnusre = viewHolder.insure_name;
        String coverName = train.getPol_sub_type_name();

      /*  if (!mTrain.get(position).getInsure_com_img_path().isEmpty() && mTrain.get(position).getInsure_com_img_path() != null) {

            File imgFile = new File(con.base_url+"/"+ mTrain.get(position).getInsure_com_img_path());
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            viewHolder.logo_policy.setImageBitmap(myBitmap);

        }
*/



        Picasso.get().load(train.getInsure_com_img_path()).into(viewHolder.logo_policy);


        type.setText(train.getPol_sub_type_name());
        policyies.setText(train.getPolicy_type_id());
        member.setText(train.getMem_count());
        isnusre.setText(train.getIns_co_name());
        if (train.getPol_sum_premmium().isEmpty()||train.getPol_sum_premmium().equalsIgnoreCase(null)
        ||train.getPol_sum_premmium().equalsIgnoreCase("")||
                train.getPol_sum_premmium().equalsIgnoreCase("null")){

            viewHolder.policy_doc.setVisibility(View.GONE);
        }else {
            viewHolder.policy_doc.setVisibility(View.VISIBLE);
        }

        viewHolder.policy_detail.setOnClickListener(new View.OnClickListener() {
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

                            PolicyData train = mTrain.get(position);

                            Intent intent = new Intent(context, MemberCoverHomeActivity.class);
                            intent.putExtra("pol", train.getPol_id());
                            context. startActivity(intent);



                        } catch (Exception e) {
                        }
                        progressDialog.dismiss();
                    }

                }.start();

            }

        });





        viewHolder.policy_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);



                        try {

                            Context context = v.getContext();

                            PolicyData train = mTrain.get(position);

//                            Intent intent = new Intent(context, PdfActivityjava.class);
//                            intent.putExtra("pol", train.getPol_sum_premmium());
//
//                            context. startActivity(intent);


                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(train.getPol_sum_premmium()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setPackage("com.android.chrome");
                            try {
                                context. startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                // Chrome browser presumably not installed so allow user to choose instead
                                intent.setPackage(null);
                                context.  startActivity(intent);
                            }



                        } catch (Exception e) {


                        }

// dismiss the progre

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
        public TextView policy_type,policy,insure_name,member_cover;
        public ImageView logo_policy;
        public Button policy_detail,policy_doc;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            policy_type = (TextView) itemView.findViewById(R.id.policy_type);
            policy = (TextView) itemView.findViewById(R.id.policy);
            insure_name = (TextView) itemView.findViewById(R.id.insure_name);
            member_cover = (TextView) itemView.findViewById(R.id.member_cover);
            logo_policy = (ImageView) itemView.findViewById(R.id.logo_policy);
            policy_detail = (Button) itemView.findViewById(R.id.policy_detail);
            policy_doc = (Button) itemView.findViewById(R.id.policy_doc);

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
