package com.palm.newbenefit.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.Activity.UploadActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.MemberData;
import com.palm.newbenefit.R;
import com.squareup.picasso.Picasso;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PolicyMemberHomeAdapter extends
        RecyclerView.Adapter<PolicyMemberHomeAdapter.ViewHolder> {

    private List<MemberData> mTrain;
    private Context context = null;
    String emp_id;
    String booking_status;
    Constants con;

    // Pass in the contact array into the constructor
    public PolicyMemberHomeAdapter(Context context, List<MemberData> train, String emp_id, String booking_status) {
        this.context = context;
        this.emp_id = emp_id;
        mTrain = train;
        this.booking_status = booking_status;
        con=new Constants();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.member_home_list, parent, false);

        // Return a new viewHolder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through viewHolder
    DownloadManager downloadManager = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        MemberData train = mTrain.get(position);

        // Set item views based on your views and data model
        TextView name = viewHolder.member_name;
        TextView idd = viewHolder.member_id;
        TextView statuss = viewHolder.status;
        TextView date = viewHolder.dob;
        TextView cover = viewHolder.cover_date;
        TextView sumof = viewHolder.sum;
        TextView relation_type = viewHolder.relation;



        if(!train.getImage_url().equalsIgnoreCase("null")){
            Picasso.get().load(train.getImage_url()).into(viewHolder.user);

        }else {
            if(( train.getRelation().equalsIgnoreCase("Self"))
                    || train.getRelation().equalsIgnoreCase("Spouse/Partner")){


                if(train.getTp_id().equalsIgnoreCase("Male")){
                    viewHolder.user.setBackgroundResource(R.drawable.man);
                }else {
                    viewHolder.user.setBackgroundResource(R.drawable.woman);
                }


            }
            if(( train.getRelation().equalsIgnoreCase("Mother"))
                    ||( train.getRelation().equalsIgnoreCase("Mother-In-Law"))){
                viewHolder.user.setBackgroundResource(R.drawable.grandmother);
            }

            if( train.getRelation().equalsIgnoreCase("Father")
            ){
                viewHolder.user.setBackgroundResource(R.drawable.grandfather);
            }

            if( train.getRelation().equalsIgnoreCase("Father-in-law")
            ){
                viewHolder.user.setBackgroundResource(R.drawable.grandfather);
            }

            if( train.getRelation().equalsIgnoreCase("Mother")
            ){
                viewHolder.user.setBackgroundResource(R.drawable.grandmother);
            }

            if( train.getRelation().equalsIgnoreCase("Mother-in-law")
            ){
                viewHolder.user.setBackgroundResource(R.drawable.grandmother);
            }

            if( train.getRelation().equalsIgnoreCase("Daughter")
            ){
                viewHolder.user.setBackgroundResource(R.drawable.girl);
            }
            if( train.getRelation().equalsIgnoreCase("Son")
            ){
                viewHolder.user.setBackgroundResource(R.drawable.boy);
            }

        }







        String coverName = train.getCoverName();

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        String gmc_check= prefs.getString("gmc_check", null);

     /*   if(gmc_check.equalsIgnoreCase("0")){
            viewHolder.mem_id.setText("Employee Code");
        }
        else {
            viewHolder.mem_id.setText("Memeber Id");
        }*/


            if(train.getMember_id().equalsIgnoreCase("null")){
                viewHolder.health_card.setVisibility(View.GONE);
            }else {
                viewHolder.health_card.setVisibility(View.VISIBLE);
            }



      /*  2 , 3 ,5,6 k liye emp_code
                (PM 1:16:09) 1,4 k liye tpa member id*/

        String coverName_id = train.getPolicy_sub_type_id();
        if(coverName_id.equalsIgnoreCase("1")||coverName_id.equalsIgnoreCase("4")){

            viewHolder.mem_id.setText("Member Id");
        }
        else if (coverName_id.equalsIgnoreCase("2")||coverName_id.equalsIgnoreCase("3")||coverName_id.equalsIgnoreCase("5")
        ||coverName_id.equalsIgnoreCase("6")){
            viewHolder.mem_id.setText("Employee Code");

        }else {
            viewHolder.mem_id.setText("Client Id");

        }



        name.setText(train.getName()+"  "+train.getLast_name());
        idd.setText(train.getEmp_code());


if(train.getPolicy_sub_type_id().equalsIgnoreCase("1")
||train.getPolicy_sub_type_id().equalsIgnoreCase("4")){



    statuss.setText(train.getIn_progress_status());
}else {
    statuss.setText("Active");
}


if(statuss.getText().toString().trim().equalsIgnoreCase("In Progress")){
    idd.setText("-");
}else {
    idd.setText(train.getEmp_code());
}


        try{

            String strCurrentDate = train.getDate_of_birth();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            date.setText(format.format(newDate));
        }catch (Exception e){
            date.setText(train.getDate_of_birth());
        }


String startdate=null,enddate=null;


        try{

            String strCurrentDate = train.getEnd_date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            enddate=(format.format(newDate));
        }catch (Exception e){
            enddate=(train.getCover_date_of_birth());
        }



        try{

            String strCurrentDate = train.getCover_date_of_birth();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            startdate=(format.format(newDate));
        }catch (Exception e){
            startdate=(train.getCover_date_of_birth());
        }

        cover.setText(startdate +" to " +enddate);

        if(train.getSum_assured().equalsIgnoreCase("0")){
            viewHolder.sum_hide.setVisibility(View.GONE);
        }else {
            sumof.setText(train.getSum_assured());
            viewHolder.sum_hide.setVisibility(View.VISIBLE);
        }





        if(train.getSum_assured().equalsIgnoreCase("null")||train.getSum_assured().isEmpty()){

                sumof.setText(train.getSum_assured());


        }else {

            try {
                int data= Integer.parseInt(train.getSum_assured());
                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);
                sumof.setText(cover_data);
            }catch (Exception e){
                sumof.setText(train.getSum_assured());
            }

        }



        relation_type.setText(train.getRelation());

       /* if(train.getRelation().equalsIgnoreCase("Self")){
            viewHolder.sum_hide.setVisibility(View.VISIBLE);
        }else {
            viewHolder.sum_hide.setVisibility(View.GONE);
        }

*/
        if(train.getOpd_suminsured().equalsIgnoreCase("null")||
                train.getOpd_suminsured().equalsIgnoreCase("0")
                ||train.getOpd_suminsured().equalsIgnoreCase("")){
            viewHolder.sum_hide_opd.setVisibility(View.GONE);
        }else {
            viewHolder.sum_hide.setVisibility(View.VISIBLE);
            viewHolder.sum_opd.setText(train.getOpd_suminsured());
            viewHolder.sum_head.setText("Sum Insured IPD :");
        }







      /*  if(statuss.equals("InProgress")){
            viewHolder.status.setTextColor(R.color.ghi);
        }else {
            viewHolder.status.setBackgroundResource(R.drawable.progress);
        }

*/

        viewHolder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UploadActivity.class);
                       intent.putExtra("relation", train.getRelation());
                intent.putExtra("tpa", train.getTp_id());
                intent.putExtra("memberid", train.getEmp_codes());
//
//                        Log.d("healthcard",train.getMember_id());
//
                         context. startActivity(intent);

            }
        });

        viewHolder.health_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();


                MemberData train = mTrain.get(position);


                    if(train.getMember_id().equalsIgnoreCase("null")){
                        new AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("No PDF File To upload")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }else {



//                        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//                        Uri uri = Uri.parse(train.getMember_id());
//                        DownloadManager.Request request = new DownloadManager.Request(uri);
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                        Long reference = downloadManager.enqueue(request);

//
//                        Intent intent = new Intent(context, PdfActivityjava.class);
//                        intent.putExtra("pol", train.getMember_id());
//
//                        Log.d("healthcard",train.getMember_id());
//
//                         context. startActivity(intent);


                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(train.getMember_id()));
                        context. startActivity(browserIntent);
                        Log.d("healthcard",train.getMember_id());



                }

                    }
        });


    }

    @Override
    public int getItemCount()
    {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your viewHolder should contain a member variable
        // for any view that will be set as you render a row
        public TextView member_name,member_id,relation,dob,sum,cover_date,mem_id,status,sum_opd,sum_head;
        public Button health_card;
        ImageView user,upload;
        LinearLayout sum_hide,sum_hide_opd;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            user = (ImageView)itemView.findViewById(R.id.user);
            sum_hide= (LinearLayout) itemView.findViewById(R.id.sum_hide);
            member_name = (TextView) itemView.findViewById(R.id.member_name);
            member_id = (TextView) itemView.findViewById(R.id.member_id);
            relation = (TextView) itemView.findViewById(R.id.relation);
            dob = (TextView) itemView.findViewById(R.id.dob);
            mem_id = (TextView) itemView.findViewById(R.id.mem_id);
            sum = (TextView) itemView.findViewById(R.id.sum);
            cover_date = (TextView) itemView.findViewById(R.id.cover_date);
            status = (TextView) itemView.findViewById(R.id.status);
            health_card= (Button) itemView.findViewById(R.id.health_card);
            sum_opd= (TextView) itemView.findViewById(R.id.sum_opd);
            sum_head= (TextView) itemView.findViewById(R.id.sum_head);
            sum_hide_opd= (LinearLayout) itemView.findViewById(R.id.sum_hide_opd);
            upload= (ImageView)itemView.findViewById(R.id.upload);
        }
    }




    private SSLSocketFactory getSocketFactory() {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};

        SSLContext sslContext = null;
        SSLSocketFactory sslSocketFactory = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Log.e("TAG", StringUtils.EMPTY, e);
        } catch (KeyManagementException e) {
            //Log.e("TAG", StringUtils.EMPTY, e);
        }

        return sslSocketFactory;

    }
}
