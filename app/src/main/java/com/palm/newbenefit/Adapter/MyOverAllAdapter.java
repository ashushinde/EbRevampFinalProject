package com.palm.newbenefit.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.Activity.ViewOverAllClaimActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.OverAllClaim;
import com.kmd.newbenefit.R;

import java.util.Date;
import java.util.List;

public class MyOverAllAdapter extends
        RecyclerView.Adapter<MyOverAllAdapter.ViewHolder> {
Constants con;
    private List<OverAllClaim> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public MyOverAllAdapter(Context context, List<OverAllClaim> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.my_overall_claim_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @SuppressLint("RecyclerView")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        OverAllClaim train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model
        viewHolder.mem_name.setText(train.getMember_name());
        viewHolder.member_relation.setText(train.getMember_relation());
        viewHolder.claim_id.setText(train.getClaim_id());


        if(train.getSettled_amount().equalsIgnoreCase("null")){
            viewHolder.settled_amount.setText("-");
        }else {
            viewHolder.settled_amount.setText(train.getSettled_amount());
        }

        if(train.getClaim_amount().equalsIgnoreCase("null")){
            viewHolder.claim_amountt.setText("-");
        }else {
            viewHolder.claim_amountt.setText(train.getClaim_amount());

        }

        if(train.getDeduction_amount().equalsIgnoreCase("null")){
            viewHolder.deduction_amount.setText("-");
        }else {
            viewHolder.deduction_amount.setText(train.getDeduction_amount());

        }

        if(train.getClaim_status().equalsIgnoreCase("null")){
            viewHolder.claim_status.setText("-");
        }else {
            viewHolder.claim_status.setText(train.getClaim_status());

        }



        if(train.getTpa().equalsIgnoreCase("null")){
            viewHolder.tpa.setText("-");
        }else {
            viewHolder.tpa.setText(train.getTpa());
        }



        if(train.getClaim_tat().equalsIgnoreCase("null")){
            viewHolder.tat.setText("-");
        }else {
            viewHolder.tat.setText(train.getClaim_tat());
        }

        viewHolder.claim_type.setText(train.getClaim_type());
        viewHolder.client_name.setText(train.getEmployer_name());
        viewHolder.patient_name.setText(train.getMember_name());

        if(train.getType().equalsIgnoreCase("null")){
            viewHolder.type.setText("-");
        }else {
            viewHolder.type.setText(train.getType());
        }



        viewHolder.email.setText(train.getEmail());



        try{

            String strCurrentDate = train.getClaim_registration_date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            viewHolder.register_on.setText(format.format(newDate));
        }catch (Exception e){

            if(train.getClaim_registration_date().equalsIgnoreCase("null")){
                viewHolder.register_on.setText("-");
            }else {
                viewHolder.register_on.setText(train.getClaim_registration_date());
            }

        }


viewHolder.policy_no.setText(train.getPolicy_number());
        viewHolder.claimtype.setText(train.getClaimtypes());
        viewHolder.empcode.setText(train.getEmpcode());
        viewHolder.empname.setText(train.getEmployee_name());
        viewHolder.claim_substatus.setText(train.getClaim_sub_status());




        viewHolder.view_detail.setOnClickListener(new View.OnClickListener() {
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
                            OverAllClaim train = mTrain.get(position);

                            Intent intent = new Intent(context, ViewOverAllClaimActivity.class);

                            intent.putExtra("claim_id", train.getClaim_id());
                            intent.putExtra("claim_amnt", train.getClaim_amount());

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










    }







    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView mem_name,claim_id,client_name,patient_name,member_relation,claim_amountt,settled_amount,
                deduction_amount,claim_type,tpa,register_on,claim_status,tat,type,email,
                policy_no,claim_substatus,empname,empcode,claimtype;



        LinearLayout view_detail;


        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mem_name= (TextView) itemView.findViewById(R.id.mem_name);
            claim_id= (TextView) itemView.findViewById(R.id.claim_id);
            client_name= (TextView) itemView.findViewById(R.id.client_name);
            patient_name= (TextView) itemView.findViewById(R.id.patient_name);
            member_relation= (TextView) itemView.findViewById(R.id.member_relation);
            claim_amountt= (TextView) itemView.findViewById(R.id.claim_amountt);
            settled_amount= (TextView) itemView.findViewById(R.id.settled_amount);
            deduction_amount= (TextView) itemView.findViewById(R.id.deduction_amount);
            claim_type= (TextView) itemView.findViewById(R.id.claim_type);
            tpa= (TextView) itemView.findViewById(R.id.tpa);
            register_on= (TextView) itemView.findViewById(R.id.register_on);
            claim_status= (TextView) itemView.findViewById(R.id.claim_status);
            tat= (TextView) itemView.findViewById(R.id.tat);
            policy_no= (TextView) itemView.findViewById(R.id.policy_no);
            type=  itemView.findViewById(R.id.type);
            email=  itemView.findViewById(R.id.email);
            view_detail=itemView.findViewById(R.id.view_detail);
            claim_substatus=itemView.findViewById(R.id.claim_substatus);
            empname=itemView.findViewById(R.id.empname);
            empcode=itemView.findViewById(R.id.empcode);
            claimtype=itemView.findViewById(R.id.claimtype);
        }
    }
}
