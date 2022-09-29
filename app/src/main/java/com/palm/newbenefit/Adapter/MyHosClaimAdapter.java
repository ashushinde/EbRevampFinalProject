package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.Intent;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.Activity.TrackActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.MyHosClaimModel;
import com.kmd.newbenefit.R;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyHosClaimAdapter extends
        RecyclerView.Adapter<MyHosClaimAdapter.ViewHolder> {
Constants con;
    private List<MyHosClaimModel> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public MyHosClaimAdapter(Context context, List<MyHosClaimModel> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.my_hos_claim_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        MyHosClaimModel train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model

        TextView member_name = viewHolder.mem_name;
        TextView mem_id = viewHolder.claim_id;
        TextView mem_type = viewHolder.claim_type;
        TextView mem_amout = viewHolder.claim_amount;
        TextView setl_amount = viewHolder.claim_settle_amt;



        viewHolder.ailment.setText(train.getClaim_reimb_reason());
        TextView date = viewHolder.claim_date;




        member_name.setText(train.getName());
        mem_id.setText(train.getClaim_reimb_id());
        mem_type.setText(train.getClaim_type());

        setl_amount.setText(train.getClaim_approved_amount());
        viewHolder.claim_reason.setText(train.getClaim_reimb_reason());
        viewHolder.claim_settle_amt.setText(train.getTotal_claim_amount());
        viewHolder.subtype.setText("Hospitalization");



        try{

            String strCurrentDate = train.getClaim_request_date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            viewHolder.claim_date_plan.setText(format.format(newDate));
        }catch (Exception e){
            viewHolder.claim_date_plan.setText(train.getClaim_request_date());
        }
        viewHolder.status_lin.setVisibility(View.GONE);


        if (train.getTotal_claim_amount().isEmpty()||train.getTotal_claim_amount().equalsIgnoreCase("null")||train.getTotal_claim_amount().equalsIgnoreCase("0"))
        {
            mem_amout.setText("-");
            viewHolder.no.setVisibility(View.GONE);
        }else {

            try{
                viewHolder.no.setVisibility(View.VISIBLE);
                int data= Integer.parseInt(train.getTotal_claim_amount());

                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                mem_amout.setText(cover_data);
            }catch (Exception e){

                mem_amout.setText(train.getTotal_claim_amount());

            }

        }




    /*    String dateStr = train.getCreated_at();
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String newDateStr = curFormater.format(dateObj);*/

        try{

            String strCurrentDate = train.getCreated_at();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            date.setText(format.format(newDate));
        }catch (Exception e){
            date.setText(train.getCreated_at());
        }

        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);
                    viewHolder.know.setText("Know More");
                }
                else {
                    viewHolder.hide.setVisibility(View.VISIBLE);

                    viewHolder.expand.setImageResource(R.drawable.ic_remove_black_24dp);
                    viewHolder.know.setText("Know Less");
                }



            }

        });
        viewHolder.know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);
                    viewHolder.know.setText("View More");
                }
                else {
                    viewHolder.hide.setVisibility(View.VISIBLE);

                    viewHolder.expand.setImageResource(R.drawable.ic_remove_black_24dp);
                    viewHolder.know.setText("View Less");
                }



            }

        });




            viewHolder.view_detail.setVisibility(View.GONE);


//        if(train.getAlltype().equalsIgnoreCase("hos")){
//            viewHolder.track.setVisibility(View.VISIBLE);
//
//        }else {
//            viewHolder.track.setVisibility(View.GONE);
//
//        }

        viewHolder.track.setVisibility(View.GONE);
        viewHolder.tra.setVisibility(View.GONE);


        viewHolder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                            Context context = v.getContext();

                MyHosClaimModel train = mTrain.get(position);

               /* Intent intent = new Intent(context, TrackClaimActivity.class);

                context. startActivity(intent);
*/

                Intent intent = new Intent(context, TrackActivity.class);
                intent.putExtra("policy", train.getPolicy_id());
                intent.putExtra("member",train.getMember_id());
                intent.putExtra("claim",train.getClaim_reimb_id());

                Log.d("claim",train.getClaim_reimb_id());
                Log.d("member",train.getMember_id());
              context. startActivity(intent);

/*
                Intent intent = new Intent(context, ClaimDetailActivity.class);
                intent.putExtra("claim_id", train.getClaim_type());
                intent.putExtra("employee_id",train.getEmp_id());
                intent.putExtra("employer_id","132");
                intent.putExtra("member_id",train.getStatus());
                intent.putExtra("policy_id",train.getPolicy_type());
                intent.putExtra("policy_type_id",train.getPolicy_sub_type());
                intent.putExtra("claim_type",train.getType());
                context. startActivity(intent);*/



            }

        });



        viewHolder.tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();

                MyHosClaimModel train = mTrain.get(position);

               /* Intent intent = new Intent(context, TrackClaimActivity.class);

                context. startActivity(intent);
*/

                Intent intent = new Intent(context, TrackActivity.class);
                intent.putExtra("policy", train.getPolicy_id());
                intent.putExtra("member",train.getMember_id());
                intent.putExtra("claim",train.getClaim_reimb_id());
                context. startActivity(intent);



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
        public TextView claim_id,mem_name,ailment,claim_date_plan,claim_type,claim_date,claim_amount,claim_settle_amt,claim_reason;
        public LinearLayout track,tra;
        TextView know;
        LinearLayout hide,view_detail,status_lin;
        ImageView expand;
        TextView no,subtype;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            view_detail= (LinearLayout) itemView.findViewById(R.id.view_detail);
            claim_date_plan= (TextView) itemView.findViewById(R.id.claim_date_plan);
            ailment= (TextView) itemView.findViewById(R.id.ailment);
            no= (TextView) itemView.findViewById(R.id.no);
            claim_id = (TextView) itemView.findViewById(R.id.claim_id);
            tra= (LinearLayout) itemView.findViewById(R.id.tra);
            mem_name = (TextView) itemView.findViewById(R.id.mem_name);
            claim_type = (TextView) itemView.findViewById(R.id.claim_type);
            claim_date = (TextView) itemView.findViewById(R.id.claim_date);
            claim_amount = (TextView) itemView.findViewById(R.id.claim_amount);
            claim_settle_amt = (TextView) itemView.findViewById(R.id.claim_settle_amt);
            claim_reason= (TextView) itemView.findViewById(R.id.claim_reason);
            track= (LinearLayout) itemView.findViewById(R.id.track);
            know= (TextView) itemView.findViewById(R.id.know);
            expand= (ImageView) itemView.findViewById(R.id.expand);
            hide= (LinearLayout) itemView.findViewById(R.id.hide);
            status_lin= (LinearLayout) itemView.findViewById(R.id.status_lin);
            subtype=itemView.findViewById(R.id.subtype);
        }
    }
}
