package com.palm.newbenefit.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.ClaimDetailActivity;
import com.palm.newbenefit.Activity.PlanHospitalActivity;
import com.palm.newbenefit.Activity.TrackActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.MyHosClaimModel;
import com.palm.newbenefit.Module.MyIntimateClaimModel;
import com.kmd.newbenefit.R;
import com.palm.newbenefit.Module.SpinnerModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyIntimateClaimAdapter extends
        RecyclerView.Adapter<MyIntimateClaimAdapter.ViewHolder> {
Constants con;
    private List<MyIntimateClaimModel> mTrain;
    private Context context = null;
    String policyname;
    String emp_id;
    String tpamemberid;
    String memeberid;
    // Pass in the contact array into the constructor
    public MyIntimateClaimAdapter(Context context, List<MyIntimateClaimModel> train,String policyname) {
        this.context = context;
        this.mTrain = train;
        this.policyname=policyname;
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
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        // Get the data model based on position
        MyIntimateClaimModel train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model
        SharedPreferences prefs =context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        String token = prefs.getString("api_token", null);
        String  user_id = prefs.getString("user_id", null);
        TextView member_name = viewHolder.mem_name;
        TextView mem_id = viewHolder.claim_id;
        TextView mem_type = viewHolder.claim_type;
        TextView mem_amout = viewHolder.claim_amount;

        TextView date = viewHolder.claim_date;
        viewHolder.claim_settle_amt.setText(train.getClaim_Amount());
        member_name.setText(train.getName());
        mem_id.setText(train.getClaim_intimate_id());
        mem_type.setText(train.getClaim_type());

        viewHolder.claim_reason.setText(train.getReason());

        if(train.getClaim_hospitalization_type().equalsIgnoreCase("")){
            viewHolder. subtype.setText("Hospitalization");
        }else {

            if(!train.getClaim_hospitalization_type().equalsIgnoreCase("null")){
                viewHolder. subtype.setText(train.getClaim_hospitalization_type());
            }


        }
      viewHolder.ailment.setText(train.getReason());
        String dateStr = train.getCreated_date();

        try{

            String strCurrentDate = train.getClaim_request_date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            viewHolder.claim_date_plan.setText(format.format(newDate));
        }catch (Exception e){
            viewHolder.claim_date_plan.setText(train.getClaim_request_date());
        }

       /* SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String newDateStr = curFormater.format(dateObj);
*/
        try{

            String strCurrentDate = train.getCreated_date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            date.setText(format.format(newDate));
        }catch (Exception e){
            date.setText(train.getCreated_date());
        }

try{

    if(train.getStatus().equalsIgnoreCase("1")){
        viewHolder.status.setText("Appointment Confirmed");
    }else if((train.getStatus().equalsIgnoreCase("2"))){
        viewHolder.status.setText("Processing");
    }else {
        viewHolder.status.setText("HR Approval Pending");

    }

}catch (Exception e){
    viewHolder.status_lin.setVisibility(View.GONE);
}
        if (train.getClaim_Amount().isEmpty()||train.getClaim_Amount().equalsIgnoreCase("null")||
                train.getClaim_Amount().equalsIgnoreCase("0"))
        {
            mem_amout.setText(train.getClaim_Amount());
        }else {

            try{
                int data= Integer.parseInt(train.getClaim_Amount());

                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                mem_amout.setText(cover_data);
            }catch (Exception e){
                mem_amout.setText(train.getClaim_Amount());
            }

        }





        try {
            if(train.getAlltype().equalsIgnoreCase("hos")){
              viewHolder.track.setVisibility(View.VISIBLE);
              viewHolder.tra.setVisibility(View.VISIBLE);
            }else {


                viewHolder.tra.setVisibility(View.GONE);
            }


        }
        catch (Exception e) {
            viewHolder.track.setVisibility(View.GONE);
        }


        viewHolder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();

                MyIntimateClaimModel train = mTrain.get(position);

               /* Intent intent = new Intent(context, TrackClaimActivity.class);

                context. startActivity(intent);
*/

             /*   Intent intent = new Intent(context, TrackActivity.class);
                intent.putExtra("policy", train.getStatus());
                intent.putExtra("member",train.getStatus());
                intent.putExtra("claim",train.getClaim_intimate_id());
                context. startActivity(intent);*/


                Intent intent = new Intent(context, ClaimDetailActivity.class);
                intent.putExtra("claim_id", train.getClaim_type());
                intent.putExtra("employee_id",train.getEmp_id());
                intent.putExtra("employer_id",train.getEmp_id());
                intent.putExtra("member_id",train.getStatus());
                intent.putExtra("policy_id",train.getPolicy_type());
                intent.putExtra("policy_type_id",train.getPolicy_sub_type());
                intent.putExtra("claim_type",train.getType());
                context. startActivity(intent);




            }

        });


        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
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

        if(viewHolder.claim_type.getText().toString().equalsIgnoreCase("e-cashless")){
            viewHolder.view_detail.setVisibility(View.VISIBLE);
            viewHolder.status_lin.setVisibility(View.VISIBLE);


        }else {
            viewHolder.view_detail.setVisibility(View.GONE);
            viewHolder.status_lin.setVisibility(View.GONE);

        }

        if(viewHolder.claim_amount.getText().toString().equalsIgnoreCase("null")||
                viewHolder.claim_amount.getText().toString().equalsIgnoreCase("")){
            viewHolder.claim_amount.setText("-");
        }

        if(viewHolder.claim_settle_amt.getText().toString().equalsIgnoreCase("null")||
                viewHolder.claim_settle_amt.getText().toString().equalsIgnoreCase("")){
            viewHolder.claim_settle_amt.setText("-");
        }


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
                            MyIntimateClaimModel train = mTrain.get(position);

                            Intent intent = new Intent(context, PlanHospitalActivity.class);

                            intent.putExtra("fam_id", train.getEmp_id());

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




        viewHolder.know_more.setOnClickListener(new View.OnClickListener() {
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




        String url = con.base_url+"/api/admin/user";

        RequestQueue rq = Volley.newRequestQueue(context,
                new HurlStack(null));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);





                        emp_id = explrObject.getString("employee_id");






                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
        rq.add(mStringRequest);



        viewHolder.tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {



                RequestQueue rq = Volley.newRequestQueue(context,
                        new HurlStack(null));

                rq.getCache().clear();
                String url = con.base_url+"/api/admin/get/emp_member";
                StringRequest smr = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("emp_member",response);

                                    JSONObject jsonArra = new JSONObject(response);

                                    String status= String.valueOf(jsonArra.getBoolean("status"));


                                        JSONArray array=jsonArra.getJSONArray("data");



                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject jsonObj = array.getJSONObject(i);

                                            if(train.getName().equalsIgnoreCase(jsonObj.getString("name"))){
                                                tpamemberid= jsonObj.getString("tpa_member_id");
                                                memeberid= jsonObj.getString("member_id");
                                            }

                                             }
                                    Context context = v.getContext();

                                    MyIntimateClaimModel train = mTrain.get(position);

                                    Intent intent = new Intent(context, TrackActivity.class);
                                    intent.putExtra("policy",memeberid);
                                    intent.putExtra("member",tpamemberid);
                                    intent.putExtra("claim",train.getClaim_intimate_id());
                                    context.startActivity(intent);



                                } catch (Exception e) {
                                    Log.e("onErrorResponse", e.toString());
                                }

                            }
                        },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", error.toString());

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();

                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };





                HashMap<String, String> params = new HashMap<>();

                params.put("policy_id", policyname);
                params.put("employee_id", emp_id);
                params.put("user_type_name", "Employee");



                smr.setParams(params);
                rq.add(smr);






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
        public TextView claim_id,status,
                subtype,mem_name,ailment,claim_date_plan,claim_type,claim_date,claim_amount,claim_settle_amt,claim_reason;

        public LinearLayout track;
        LinearLayout tra;
        TextView know;
        LinearLayout hide,know_more,view_detail,status_lin;
        ImageView expand;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            status_lin= (LinearLayout) itemView.findViewById(R.id.status_lin);
            status= (TextView) itemView.findViewById(R.id.status);
            ailment= (TextView) itemView.findViewById(R.id.ailment);
            claim_date_plan= (TextView) itemView.findViewById(R.id.claim_date_plan);
            claim_id = (TextView) itemView.findViewById(R.id.claim_id);
            subtype= (TextView) itemView.findViewById(R.id.subtype);
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
           // amt=(LinearLayout) itemView.findViewById(R.id.amt);
            tra = (LinearLayout) itemView.findViewById(R.id.tra);
            know_more=itemView.findViewById(R.id.know_more);
            view_detail=itemView.findViewById(R.id.view_detail);

        }
    }
}
