package com.palm.newbenefit.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.palm.newbenefit.Activity.FamilyDataActivity;
import com.palm.newbenefit.Module.FamilyData;
import com.kmd.newbenefit.R;

import java.util.List;

public class FamilyAdapter extends
        RecyclerView.Adapter<FamilyAdapter.ViewHolder> {

    private List<FamilyData> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public FamilyAdapter(Context context, List<FamilyData> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.family_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final FamilyData train = mTrain.get(position);

        // Set item views based on your views and data model
        TextView email = viewHolder.email_id;
        TextView dot = viewHolder.date;
        TextView name = viewHolder.name;
        TextView relation_data = viewHolder.relation;
        TextView mobile = viewHolder.mobile_no;
        email.setText(train.getFamily_email());
        dot.setText(train.getFamily_dob());
        name.setText(train.getFamily_firstname() + " " + train.getFamily_lastname());

        mobile.setText(train.getFamily_contact());
        relation_data.setText(train.getFr_name());


        viewHolder.mobile_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final FamilyData train = mTrain.get(position);

                String phone = train.getFamily_contact();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);

            }

        });


        viewHolder.next.setOnClickListener(new View.OnClickListener() {
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
                            FamilyData train = mTrain.get(position);

                            Intent intent = new Intent(context, FamilyDataActivity.class);

                            intent.putExtra("fam_id", train.getFamily_id());

                           // intent.putExtra("location", train.getbui());


                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            context.startActivity(intent);

                        } catch (Exception e) {


                        }

// dismiss the progress dialog

                        progressDialog.dismiss();

                    }

                }.start();

            }

        });



        viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
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
                            FamilyData train = mTrain.get(position);

                            Intent intent = new Intent(context, FamilyDataActivity.class);
                            intent.putExtra("fam_id", train.getFamily_id());



                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            context.startActivity(intent);

                        } catch (Exception e) {


                        }

// dismiss the progress dialog

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
        public TextView email_id, name, relation, date, mobile_no;
        public ImageView next;
        public CardView card_view;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            relation = (TextView) itemView.findViewById(R.id.relation);
            email_id = (TextView) itemView.findViewById(R.id.email_id);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            mobile_no = (TextView) itemView.findViewById(R.id.mobile_no);
            next = (ImageView) itemView.findViewById(R.id.next);
            card_view= (CardView) itemView.findViewById(R.id.card_view);

        }
    }
}
