package com.palm.newbenefit.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Ecard;
import com.palm.newbenefit.Module.MemberData;
import com.palm.newbenefit.Module.amount;
import com.kmd.newbenefit.R;

import java.util.List;

public class ecardAdapter extends
        RecyclerView.Adapter<ecardAdapter.ViewHolder> {
Constants con;
    private List<Ecard> mTrain;
    private Context context = null;
    private String compareCount = null;
    // Pass in the contact array into the constructor
    public ecardAdapter(Context context, List<Ecard> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.download_member, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        // Get the data model based on position
        Ecard train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;

        if(train.getMember_id().equalsIgnoreCase("null")){
            viewHolder.member_id.setText("NA");
        }else {
            viewHolder.member_id.setText(train.getMember_id());
        }


        viewHolder.cover_date.setText(train.getStart_date()+" - "+train.getEnd_date());
        viewHolder.policyno.setText(train.getPolicy_no());
        viewHolder.relation.setText(train.getRelation_name());
        viewHolder.member_name.setText(train.getName());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {




                          try{
                              if(train.getTpa_member_id().equalsIgnoreCase("null")){
                                  new AlertDialog.Builder(context)
                                          .setTitle("Error")
                                          .setMessage("No PDF File To upload")
                                          .setIcon(android.R.drawable.ic_dialog_alert)
                                          .setNegativeButton(android.R.string.ok, null).show();
                              }else {






                                  Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                          Uri.parse(train.getEcard_url()));
                                  context. startActivity(browserIntent);


                              }

                          }catch (Exception e){
                              new AlertDialog.Builder(context)
                                      .setTitle("Error")
                                      .setMessage("E-card not available")
                                      .setIcon(android.R.drawable.ic_dialog_alert)
                                      .setNegativeButton(android.R.string.ok, null).show();


                }

            }
        });






        viewHolder.healcrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {






                try{
                    if(train.getTpa_member_id().equalsIgnoreCase("null")){
                        new AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("No PDF File To upload")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }else {






                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(train.getEcard_url()));
                        context.startActivity(browserIntent);


                    }

                }catch (Exception e){
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("E-card not available")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();



                }
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
        public TextView member_name, relation,member_id,policyno,cover_date;
        LinearLayout health_card;
        Button healcrd;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

           // p_type = (TextView) itemView.findViewById(R.id.p_type);
            member_name = itemView.findViewById(R.id.member_name);
            relation = itemView.findViewById(R.id.relation);
            member_id = itemView.findViewById(R.id.member_id);
            policyno = itemView.findViewById(R.id.policyno);
            cover_date = itemView.findViewById(R.id.cover_date);
            health_card = itemView.findViewById(R.id.health_card);

            healcrd= itemView.findViewById(R.id.healcrd);



        }
    }
}
