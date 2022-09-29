package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.NonCoreData;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class NonCoreAdapter extends RecyclerView.Adapter<NonCoreAdapter.ViewHolder> {
    Constants con;
    private List<NonCoreData> mTrain;
    private Context context = null;
    String emp_id,flag;
    String balance,flexdata;
    String deduction_type_data;
    int walletFlex=0;
    int wallet_utilization=0;
    String toPay;
    String benefit_id;
    // Pass in the contact array into the constructor
    public NonCoreAdapter(Context context, List<NonCoreData> train, String emp_id) {
        this.context = context;
        this.emp_id = emp_id;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.non_core_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {



        // Get the data model based on position
        NonCoreData train = mTrain.get(position);
        con = new Constants();

        // Set item views based on your views and data model

        TextView sudexoPrice = viewHolder.sudexo_price;
        TextView sudexoPricebalance = viewHolder.sudexo_price_a;
        TextView name = viewHolder.m_cov;

        benefit_id=train.getEmployee_flexi_benifit_id();

        if(train.getBalance_amount().equalsIgnoreCase("null")||train.getBalance_amount().isEmpty()){
            sudexoPricebalance.setText(String.valueOf(train.getBalance_amount()));
        }else {
            int data= Integer.parseInt(train.getBalance_amount());

            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

            sudexoPricebalance.setText(cover_data);
        }



        if(train.getFinal_amount().equalsIgnoreCase("null")||train.getFinal_amount().isEmpty()){
            sudexoPrice.setText(String.valueOf(train.getFinal_amount()));
        }else {
            int data= Integer.parseInt(train.getFinal_amount());

            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

            sudexoPrice.setText(cover_data);
        }

       // RequestQueue rq = Volley.newRequestQueue(context);



     RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String urla = con.base_url+"/get_profile_per_details";
        StringRequest smra = new StringRequest(Request.Method.POST, urla,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonObjh = new JSONObject(response);


                            JSONObject jsonObj = jsonObjh.getJSONObject("flag");


                            String benifit_all_data=jsonObj.getString("benifit_all_data");
                            String nominee_data=jsonObj.getString("nominee_data");
                            String subQuery1=jsonObj.getString("subQuery1");


                            String enrollment_flag=jsonObj.getString("flex_flag");


                            if((enrollment_flag.equalsIgnoreCase("Y"))){

                                viewHolder.reset.setVisibility(View.GONE);
                                viewHolder.sudexo_next.setVisibility(View.GONE);
                                viewHolder.allocate.setVisibility(View.GONE);

                            }

                            else {

                                if(train.getFinal_amount().equalsIgnoreCase("0")){




                                    viewHolder.allocate.setVisibility(View.VISIBLE);
                                    viewHolder.reset_bb.setVisibility(View.GONE);
                                    viewHolder.sudexo_next.setVisibility(View.VISIBLE);
                                    viewHolder.reset.setVisibility(View.GONE);

                                }else {
                                    viewHolder.allocate.setVisibility(View.GONE);
                                    viewHolder.reset_bb.setVisibility(View.VISIBLE);
                                    viewHolder.sudexo_next.setVisibility(View.GONE);
                                    viewHolder.reset.setVisibility(View.GONE);
                                }






                            }





                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String policy_no = prefs.getString("policy_no", null);




       /* smra.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueuea = Volley.newRequestQueue(context);
        mRequestQueuea.add(smra);*/



        HashMap<String, String> params = new HashMap<>();
        params.put("emp_id", emp_id);
        params.put("policy_no",policy_no);
        smra.setParams(params);
        rq.add(smra);





        //name.setText(train.getFlexi_benefit_name());


      /*   File imgFile = new File("http://192.170.7.171/"+train.getImg_name());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        viewHolder.p_img.setImageBitmap(myBitmap);
*/


        new DownloadImageFromInternet(viewHolder.p_img)
                .execute(con.base_url + train.getImg_name());

        //   Picasso.get().load("https://uat.lifekaplan-eb.com/"+train.getImg_name()).resize(120, 60).into(viewHolder.p_img);


     /*   if(train.getFlexi_benefit_name().equalsIgnoreCase("Gym")){
            //viewHolder.card_view.setBackgroundResource(R.drawable.gym_cardview);
            viewHolder.p_img.setImageResource(R.drawable.gym_flex);
        }else   if(train.getFlexi_benefit_name().equalsIgnoreCase("WearableDevise\\/SmartWatch")){
           // viewHolder.card_view.setBackgroundResource(R.drawable.smartwatch_cadview);
            viewHolder.p_img.setImageResource(R.drawable.smartwatch);

        }else   if(train.getFlexi_benefit_name().equalsIgnoreCase("Vaccination & Immunization Care")){
          //  viewHolder.card_view.setBackgroundResource(R.drawable.vacc_cardview);
            viewHolder.p_img.setImageResource(R.drawable.teleconsulant_flex);
        }else   if(train.getFlexi_benefit_name().equalsIgnoreCase("Health Check Up")){
           // viewHolder.card_view.setBackgroundResource(R.drawable.health_checkup_cardview);
            viewHolder.p_img.setImageResource(R.drawable.health_flex);
        }else   if(train.getFlexi_benefit_name().equalsIgnoreCase("Yoga / zumba\\\\r\\\\n\\\\r\\\\n")){
           // viewHolder.card_view.setBackgroundResource(R.drawable.yoga_cardview);
            viewHolder.p_img.setImageResource(R.drawable.yoga_flex);
        }else   if(train.getFlexi_benefit_name().equalsIgnoreCase("Elder Care")) {
           // viewHolder.card_view.setBackgroundResource(R.drawable.elder_cardview);
            viewHolder.p_img.setImageResource(R.drawable.elder_flex);

         }else   if(train.getFlexi_benefit_name().equalsIgnoreCase("Condition Mgmt Program")) {
      //  viewHolder.card_view.setBackgroundResource(R.drawable.condition_cardview);
        viewHolder.p_img.setImageResource(R.drawable.cmp_flex);
           }else   if(train.getFlexi_benefit_name().equalsIgnoreCase("Nutrition & Dietician Counselling")) {
           // viewHolder.card_view.setBackgroundResource(R.drawable.nutri_cardview);
            viewHolder.p_img.setImageResource(R.drawable.nurition_flex);
        }else   if(train.getFlexi_benefit_name().equalsIgnoreCase("Home HealthCare")) {
           // viewHolder.card_view.setBackgroundResource(R.drawable.home_cardview);
            viewHolder.p_img.setImageResource(R.drawable.home_flex);
        }
*/

      /*  Animation anim  = AnimationUtils.loadAnimation(context, R.anim.antirotate);
        anim.reset();
        viewHolder.sudexo_next.clearAnimation();
        viewHolder.sudexo_next.startAnimation(anim);
*/

//        if((!(flag.equalsIgnoreCase(null)) && (!(nominee_data.equalsIgnoreCase(null)) && (subQuery1 .equalsIgnoreCase("Y"))))){
//            submit.setVisibility(View.GONE);
//        }
//        if((!(benifit_all_data.equalsIgnoreCase(null)) || (subQuery1 .equalsIgnoreCase("Y")))){
//            submit.setVisibility(View.GONE);
//        }
//        else {
//            submit.setVisibility(View.VISIBLE);
//        }


        viewHolder.m_cov.setText(train.getFlexi_benefit_name());

        if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("17")) {

            // viewHolder.m_cov.setText("Home HealthCare");

            viewHolder.mobNo.setText("Lorem ipsum dolor sit amet consectetur, adipisicing elit.");

        }  else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("16")) {
            // viewHolder.m_cov.setText("Nutrition & Dietician Counselling");

            viewHolder.mobNo.setText("We have a panel of nutritionists and dieticians who help you set health goals");

        } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("10")) {
            //  viewHolder.m_cov.setText("Yoga / zumba");

            viewHolder.mobNo.setText("We are partnered with third party fitness providers and gym partners who provide one stop solutions.");

        } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("11")) {
            // viewHolder.m_cov.setText("Elder Care");

            viewHolder.mobNo.setText("We are partnered with third party providers whose caregivers have vast range of experience");

        } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("15")) {
            // viewHolder.m_cov.setText("Vaccination & Immunization Care");

            viewHolder.mobNo.setText("Increasing demand for new vaccines is placing ever greater pressure on immunization delivery systems");

        } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("13")) {
            // viewHolder.m_cov.setText("Condition Mgmt Program");

            viewHolder.mobNo.setText("Chronic health conditions are the most common of all health problems");

        } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("14")) {
            // viewHolder.m_cov.setText("Smart Watch");

            viewHolder.mobNo.setText("Smartwatches can do many things that your smartphone can’t.");

        } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("12")) {
            // viewHolder.m_cov.setText("Gym");

            viewHolder.mobNo.setText("We are partnered with third party fitness providers and gym partners");
        }

        else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("6")) {
            //  viewHolder.m_cov.setText("Health CheckUp");

            viewHolder.mobNo.setText("Find health check-up packages & specialized tests");
        }






        viewHolder.reset_bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RequestQueue rq = Volley.newRequestQueue(context);


               RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
               rq.getCache().clear();
                String url = con.base_url+"/reset_flexi_benefits";
                StringRequest smr = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    GetAllFlex();
                                    // String Data=response;

                                    JSONObject jsonObject = new JSONObject(response);

                                    String status = String.valueOf(jsonObject.getBoolean("status"));

                                    viewHolder.sudexo_price.setText("0");
                                    viewHolder.sudexo_price_a.setText("0");

                                    viewHolder.reset_bb.setVisibility(View.GONE);
                                    viewHolder.allocate.setVisibility(View.VISIBLE);
                                    viewHolder.sudexo_next.setVisibility(View.VISIBLE);



                                } catch (Exception e) {

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                NonCoreData train = mTrain.get(position);



                HashMap<String, String> params = new HashMap<>();
                params.put("policy_type", "Voluntary");
                params.put("emp_id", emp_id);
                params.put("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());



                Log.d("policy_type", "Voluntary");
                Log.d("emp_id", emp_id);
                Log.d("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());


                smr.setParams(params);
                rq.add(smr);


            }
        });








        viewHolder.sudexo_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();
                NonCoreData train = mTrain.get(position);
                getBalance();
                final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dental_care_dailogg_data);

                TextView amount = dialog.findViewById(R.id.amount);
                RadioButton deduction_type = (RadioButton) dialog.findViewById(R.id.deduction_type);
                RadioButton flex = (RadioButton) dialog.findViewById(R.id.flex);
                // amount.setText(train.getSum_insured());


               GetAllFlex();


                if(train.getFlexi_benefit_name().equalsIgnoreCase("Health Check Up")){
                    flex.setVisibility(View.VISIBLE);
                }else {
                    flex.setVisibility(View.GONE);
                }



                TextView head = (TextView) dialog.findViewById(R.id.head);
                ImageView ab = (ImageView) dialog.findViewById(R.id.ab);


                ab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

                btn_cancel.setVisibility(View.VISIBLE);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amt= Integer.parseInt(amount.getText().toString());


                        GetAllFlex();

                            if(train.getFlexi_benefit_name().equalsIgnoreCase("Health Check Up")){





                                if (flex.isChecked()) {
                                    deduction_type_data = "S";
                                } else {
                                    deduction_type_data = "F";
                                }


                                if(deduction_type.isChecked()) {







                                                        if(walletFlex==0) {
                                                            new AlertDialog.Builder(context)
                                                                    .setTitle("Error")
                                                                    .setMessage("Flex Balance Not Enough")
                                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                                    .setNegativeButton(android.R.string.ok, null).show();

                                                        }
                                                        else {



                                                            if (amt > walletFlex) {
                                                                int payrolla=amt-walletFlex;



                                                                String data = "Premium deduction from , Wallet : " + " "+walletFlex +" & "+ "Payroll : " + " "+payrolla +" , "+ "Would you like to continue?";

                                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                                                alertDialogBuilder.setMessage(data);
                                                                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface arg0, int arg1) {



                                                                       // RequestQueue rq = Volley.newRequestQueue(context);


                                                                       RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                                                                       rq.getCache().clear();
                                                                        con=new Constants();
                                                                        String url = con.base_url+"/update_flexi_details";
                                                                        StringRequest smr = new StringRequest(Request.Method.POST, url,
                                                                                new Response.Listener<String>() {
                                                                                    @Override
                                                                                    public void onResponse(String response) {
                                                                                        try {


                                                                                            Log.d("update response",response);




                                                                                            String data =response.replace("\r\n\r\n","");
                                                                                            JSONObject json = new JSONObject(data);

                                                                                            // String status = json.getString("status");
                                                                                            String msg = json.getString("msg");



                                                                                            if(msg.equalsIgnoreCase("Flex balance is not enough")){
                                                                                                new AlertDialog.Builder(context)
                                                                                                        .setTitle("Error")
                                                                                                        .setMessage(msg)
                                                                                                        .setIcon(android.R.drawable.btn_dialog)
                                                                                                        .setNegativeButton(android.R.string.ok, null).show();
                                                                                            }else {
                                                                                                GetAllFlex();



                                                                                                new AlertDialog.Builder(context)
                                                                                                        .setTitle("Success")
                                                                                                        .setMessage(msg)
                                                                                                        .setIcon(R.drawable.checkmark)
                                                                                                        .setNegativeButton(android.R.string.ok, null).show();
                                                                                                viewHolder.sudexo_price.setText(amount.getText().toString());
                                                                                                viewHolder.sudexo_price_a.setText(amount.getText().toString());
                                                                                                viewHolder.reset_bb.setVisibility(View.VISIBLE);
                                                                                                viewHolder.allocate.setVisibility(View.GONE);
                                                                                                viewHolder.sudexo_next.setVisibility(View.GONE);


                                                                                            }

                                                                                            dialog.cancel();

                                                                                        } catch (Exception e) {
                                                                                            e.printStackTrace();
                                                                                        }

                                                                                    }
                                                                                }, new Response.ErrorListener() {
                                                                            @Override
                                                                            public void onErrorResponse(VolleyError error) {

                                                                            }
                                                                        });


                                                                        int payrolla=amt-walletFlex;


                                                                        Log.d("walletFlex", String.valueOf(walletFlex));

                                                                        int final_amt=walletFlex+payrolla;








                       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                        mRequestQueue.add(smr);*/

                                                                        HashMap<String, String> params = new HashMap<>();




                                                                        params.put("flex_amount", String.valueOf(walletFlex));
                                                                        params.put("payroll_amt", String.valueOf(payrolla));
                                                                        params.put("final_amount", String.valueOf(final_amt));

                                                                        params.put("deduction_type", "F,S");






                                                                        /* smr.addStringParam("token", token);*/
                                                                        params.put("policy_type", "Wellness");
                                                                        params.put("emp_id", emp_id);
                                                                        params.put("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());

                                                                        params.put("sum_insured", "");

                                                                        String wallet= String.valueOf(walletFlex);
                                                                        if(balance.equalsIgnoreCase("0")){
                                                                            params.put("balance",flexdata);

                                                                        }else {
                                                                            params.put("balance",balance);

                                                                        }

                                                                        smr.setParams(params);
                                                                        rq.add(smr);




                                                                    }
                                                                });
                                                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {



                                                                    }
                                                                });
                                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                                alertDialog.show();





                                                            }else {
                                                               // RequestQueue rqa = Volley.newRequestQueue(context);


                                                              RequestQueue rqa = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                                                              rqa.getCache().clear();
                                                                con=new Constants();
                                                                String urla = con.base_url+"/update_flexi_details";
                                                                StringRequest smra = new StringRequest(Request.Method.POST, urla,
                                                                        new Response.Listener<String>() {
                                                                            @Override
                                                                            public void onResponse(String response) {
                                                                                try {


                                                                                    Log.d("update response",response);




                                                                                    String data =response.replace("\r\n\r\n","");
                                                                                    JSONObject json = new JSONObject(data);

                                                                                    // String status = json.getString("status");
                                                                                    String msg = json.getString("msg");



                                                                                    if(msg.equalsIgnoreCase("Flex Balance Is Not Enough")){
                                                                                        new AlertDialog.Builder(context)
                                                                                                .setTitle("Error")
                                                                                                .setMessage(msg)
                                                                                                .setIcon(android.R.drawable.btn_dialog)
                                                                                                .setNegativeButton(android.R.string.ok, null).show();
                                                                                    }else {

                                                                                        new AlertDialog.Builder(context)
                                                                                                .setTitle("Success")
                                                                                                .setMessage(msg)
                                                                                                .setIcon(R.drawable.checkmark)
                                                                                                .setNegativeButton(android.R.string.ok, null).show();
                                                                                        viewHolder.sudexo_price.setText(amount.getText().toString());
                                                                                        viewHolder.sudexo_price_a.setText(amount.getText().toString());
                                                                                        viewHolder.reset_bb.setVisibility(View.VISIBLE);
                                                                                        viewHolder.allocate.setVisibility(View.GONE);
                                                                                        viewHolder.sudexo_next.setVisibility(View.GONE);


                                                                                    }

                                                                                    dialog.cancel();
                                                                                } catch (Exception e) {
                                                                                    e.printStackTrace();
                                                                                }

                                                                            }
                                                                        }, new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {

                                                                    }
                                                                });

                                                                /* smr.addStringParam("token", token);*/




                       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                        mRequestQueue.add(smr);*/


                                                                HashMap<String, String> params = new HashMap<>();
                                                                params.put("policy_type", "Wellness");
                                                                params.put("emp_id", emp_id);
                                                                params.put("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());
                                                                params.put("flex_amount", amount.getText().toString());
                                                                params.put("sum_insured", "");



                                                                params.put("balance", String.valueOf(walletFlex));



                                                                params.put("deduction_type", "F");




                                                                smra.setParams(params);
                                                                rqa.add(smra);
                                                            }





                                                        }













                                    //Comment end
                                }else {

                                    //payroll

                                     GetAllFlex();
                                    //RequestQueue rqa = Volley.newRequestQueue(context);

                                    RequestQueue rqa = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                                    con=new Constants();
                                    rqa.getCache().clear();
                                    String urla = con.base_url+"/update_flexi_details";
                                    StringRequest smra = new StringRequest(Request.Method.POST, urla,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {


                                                        Log.d("update response",response);




                                                        String data =response.replace("\r\n\r\n","");
                                                        JSONObject json = new JSONObject(data);

                                                        // String status = json.getString("status");
                                                        String msg = json.getString("msg");



                                                        if(msg.equalsIgnoreCase("Flex balance is not enough")){
                                                            new AlertDialog.Builder(context)
                                                                    .setTitle("Error")
                                                                    .setMessage(msg)
                                                                    .setIcon(android.R.drawable.btn_dialog)
                                                                    .setNegativeButton(android.R.string.ok, null).show();
                                                        }else {

                                                            new AlertDialog.Builder(context)
                                                                    .setTitle("Success")
                                                                    .setMessage(msg)
                                                                    .setIcon(R.drawable.checkmark)
                                                                    .setNegativeButton(android.R.string.ok, null).show();
                                                            viewHolder.sudexo_price.setText(amount.getText().toString());
                                                            viewHolder.sudexo_price_a.setText(amount.getText().toString());


                                                            viewHolder.reset_bb.setVisibility(View.VISIBLE);
                                                            viewHolder.allocate.setVisibility(View.GONE);
                                                            viewHolder.sudexo_next.setVisibility(View.GONE);


                                                        }



                                                        dialog.cancel();

                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });





                       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                        mRequestQueue.add(smr);*/

                                    HashMap<String, String> params = new HashMap<>();
                                    /* smr.addStringParam("token", token);*/
                                    params.put("policy_type", "Wellness");
                                    params.put("emp_id", emp_id);
                                    params.put("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());
                                    params.put("flex_amount", amount.getText().toString());
                                    params.put("sum_insured", "");

                                    String wallet= String.valueOf(walletFlex);
                                    if(wallet.equalsIgnoreCase("0")){
                                        params.put("balance",flexdata);

                                    }else {
                                        params.put("balance",balance);

                                    }

                                    params.put("deduction_type", "S");




                                    smra.setParams(params);
                                    rqa.add(smra);



                                    //payroll





                                }

                            }else {
                                GetAllFlex();
                               // RequestQueue rqs = Volley.newRequestQueue(context);

                              RequestQueue rqs = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                                con=new Constants();
                                rqs.getCache().clear();
                                String urls = con.base_url+"/update_flexi_details";
                                StringRequest smrs = new StringRequest(Request.Method.POST, urls,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {


                                                    Log.d("update response",response);




                                                    String data =response.replace("\r\n\r\n","");
                                                    JSONObject json = new JSONObject(data);

                                                    // String status = json.getString("status");
                                                    String msg = json.getString("msg");



                                                    if(msg.equalsIgnoreCase("Flex balance is not enough")){
                                                        new AlertDialog.Builder(context)
                                                                .setTitle("Error")
                                                                .setMessage(msg)
                                                                .setIcon(android.R.drawable.btn_dialog)
                                                                .setNegativeButton(android.R.string.ok, null).show();
                                                    }else {

                                                        new AlertDialog.Builder(context)
                                                                .setTitle("Success")
                                                                .setMessage(msg)
                                                                .setIcon(R.drawable.checkmark)
                                                                .setNegativeButton(android.R.string.ok, null).show();
                                                        viewHolder.sudexo_price.setText(amount.getText().toString());
                                                        viewHolder.sudexo_price_a.setText(amount.getText().toString());
                                                        viewHolder.reset_bb.setVisibility(View.VISIBLE);
                                                        viewHolder.allocate.setVisibility(View.GONE);
                                                        viewHolder.sudexo_next.setVisibility(View.GONE);


                                                    }



                                                    dialog.cancel();

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });






                       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                        mRequestQueue.add(smr);*/

                                HashMap<String, String> params = new HashMap<>();
                                /* smr.addStringParam("token", token);*/
                                params.put("policy_type", "Wellness");
                                params.put("emp_id", emp_id);
                                params.put("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());
                                params.put("flex_amount", amount.getText().toString());
                                params.put("sum_insured", "");



                                Log.d("policy_type", "Wellness");
                                Log.d("emp_id", emp_id);
                                Log.d("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());
                                Log.d("flex_amount", amount.getText().toString());
                                Log.d("sum_insured", "");

                                String wallet= String.valueOf(walletFlex);



                                if(wallet.equalsIgnoreCase("0")){
                                    params.put("balance",wallet);
                                    Log.d("balance",wallet);

                                }else {
                                    params.put("balance",wallet);
                                    Log.d("balance",wallet);

                                }

                                params.put("deduction_type", "F");
                                Log.d("deduction_type", "F");




                                smrs.setParams(params);
                                rqs.add(smrs);
                            }































                    }
                });


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


            }

        });






        viewHolder.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();

                final NonCoreData train = mTrain.get(position);
               // RequestQueue rq = Volley.newRequestQueue(context);
                //RequestQueue rq = Volley.newRequestQueue(context);


               RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                rq.getCache().clear();
                String url = con.base_url+"/update_flexi_details";
                StringRequest smr= new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    viewHolder.sudexo_price.setText("0");
                                    viewHolder.sudexo_price_a.setText("0");
                                    String data =response.replace("\r\n\r\n","");
                                    JSONObject json = new JSONObject(data);

                                    // String status = json.getString("status");
                                    String msg = json.getString("msg");

                                    new AlertDialog.Builder(context)
                                            .setTitle("Success")
                                            .setMessage(msg)
                                            .setIcon(R.drawable.checkmark)
                                            .setNegativeButton(android.R.string.ok, null).show();



                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


                /* smr.addStringParam("token", token);*/


              /*  smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                        0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                mRequestQueue.add(smr);*/


                HashMap<String, String> params = new HashMap<>();
                params.put("policy_type", "Wellness");
                params.put("emp_id", emp_id);
                params.put("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());

                smr.setParams(params);
                rq.add(smr);


            }

        });

        viewHolder.mm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Context context = v.getContext();
                NonCoreData train = mTrain.get(position);


                if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("17")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);

                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);

                    viewHolder.mobNo.setText("Lorem ipsum dolor sit amet consectetur, adipisicing elit.");

                    info.setText("Lorem ipsum dolor sit amet consectetur, adipisicing elit." +
                            " Recusandae voluptate repellendus magni illo ea animi? Lorem ipsum dolor sit amet consectetur, " +
                            "adipisicing elit. Recusandae voluptate repellendus magni illo ea animi?");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("6")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);

                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);
                    viewHolder.mobNo.setText("Find health check-up packages & specialized tests which can be availed at reputeddiagnostic");
                    info.setText("Find health check-up packages & specialized tests which can be availed at reputeddiagnostic " +
                            "centers at your convenient location. Book appointments through Vivant sconcierge services and receive " +
                            "regular reminder notifications. We offer multiple screening options, ranging from a specific test" +
                            " to a complete health checkup across the nation, making it easy and convenient for members to take" +
                            " the critical step towards understanding their health risks.");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("16")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);
                    viewHolder.mobNo.setText("We have a panel of nutritionists and dieticians who help you set health goals");
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);

                    info.setText("We have a panel of nutritionists and dieticians who help " +
                            "you set health goals and incorporate healthy habits with customized " +
                            "diet plans and exercise schedules. They help members make a positive impact on their total health.");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("10")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);
                    viewHolder.mobNo.setText("We are partnered with third party fitness providers and gym partners who provide one stop solutions.");
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);

                    info.setText("We are partnered with third party fitness providers and gym partners" +
                            " who provide one stop solutions. The platform provides services like yoga, dance," +
                            " Pilates, kick boxing, Aerobics, Zumba etc. depending on the service provider opted by the member.");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("11")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);
                    viewHolder.mobNo.setText("We are partnered with third party providers whose caregivers have vast range of experience");
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);

                    info.setText("We are partnered with third party providers whose caregivers " +
                            "have vast range of experience and training backing their expertise." +
                            " All their attendants and nurses have a thorough background " +
                            "verification done prior to appointment. We also provide medicines " +
                            "delivery and diagnostics to our customers at their doorsteps," +
                            " second opinion services and home health care services.");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("15")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);
                    viewHolder.mobNo.setText("Increasing demand for new vaccines is placing ever greater pressure on immunization delivery systems");
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);

                    info.setText("Increasing demand for new vaccines is placing" +
                            " ever greater pressure on immunization delivery systems in developing countries." +
                            " We are partnered with service providers who organize various " +
                            "immunization campaigns for corporates. The vaccines include Influenza, Hepatitis, Typhoid, etc.");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("13")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);
                    viewHolder.mobNo.setText("Chronic health conditions are the most common of all health problems");
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);

                    info.setText("Chronic health conditions are the most common of all health " +
                            "problems which are largely sparked by a set of well-known risk behaviors" +
                            " like poor nutrition, physical inactivity, and tobacco use." +
                            " There's nothing easy about keeping chronic health conditions in check. " +
                            "If those conditions get out of hand, they can lead to painful complications and disabilities" +
                            " and they can affect quality of life. We inspire individuals to incorporate healthy habits " +
                            "into their daily activities through our condition management programs so that they positively " +
                            "impact the physical, emotional, and social aspects of their lives.");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("14")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);
                    viewHolder.mobNo.setText("Smartwatches can do many things that your smartphone can’t.");
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);

                    info.setText("Smartwatches can do many things that your smartphone can’t. " +
                            "Benefits include tracking your heart rate, sleep, " +
                            "activity and overall fitness level. You can also play music" +
                            " and do phone free activities and even pay at the store.");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                } else if (train.getEmployee_flexi_benifit_id().equalsIgnoreCase("12")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.view_dailoge);
                    viewHolder.mobNo.setText("We are partnered with third party fitness providers and gym partners");
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
                    TextView info = (TextView) dialog.findViewById(R.id.info);

                    info.setText("We are partnered with third party fitness " +
                            "providers and gym partners who provide one stop solutions. The platform provides services like yoga, dance, " +
                            "Pilates, kick boxing, Aerobics, Zumba etc. depending on the service provider opted by the member.");
                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                }


            }

        });


    }



    void showDialog2() {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dental_care_dailogg);

        TextView head = (TextView) dialog.findViewById(R.id.head);
        ImageView ab = (ImageView) dialog.findViewById(R.id.ab);


        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


    }


    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView m_cov, sudexo_price, sudexo_price_a;
        LinearLayout card_view;
        public ImageView p_img;
        TextView sudexo_next;
        ImageView reset;
        LinearLayout mm;
        TextView mobNo;
        LinearLayout allocate,reset_bb;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            m_cov = (TextView) itemView.findViewById(R.id.benifit_name);
//            card_view = (LinearLayout) itemView.findViewById(R.id.card_view);

            mobNo = (TextView) itemView.findViewById(R.id.mobNo);
            sudexo_price = (TextView) itemView.findViewById(R.id.sudexo_price);
            sudexo_next = itemView.findViewById(R.id.sudexo_next);
            p_img = (ImageView) itemView.findViewById(R.id.p_img);

            reset = itemView.findViewById(R.id.reset);

        }
    }





    private void getBalance() {
      //  RequestQueue rq = Volley.newRequestQueue(context);

        RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/get_profile_per_details";

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject bala = jsonObject.getJSONObject("total_flex_balance");
                            balance = bala.getString("total_balance");
                            flexdata = bala.getString("Flex_Wallet");



                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(smr);*/



        HashMap<String, String> params = new HashMap<>();
        params.put("emp_id", emp_id);
        smr.setParams(params);
        rq.add(smr);


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

    void getprofile(){
        RequestQueue rq = Volley.newRequestQueue(context);

       // RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        String url = con.base_url+"/get_profile_per_details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonObjh = new JSONObject(response);


                            JSONObject jsonObj = jsonObjh.getJSONObject("flag");


                            String benifit_all_data=jsonObj.getString("benifit_all_data");
                            String nominee_data=jsonObj.getString("nominee_data");
                            String subQuery1=jsonObj.getString("subQuery1");



                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


//        smr.addStringParam("policy_no",policy_no);

        /*smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(smr);*/


        HashMap<String, String> params = new HashMap<>();
        params.put("emp_id", emp_id);
        smr.setParams(params);


        rq.add(smr);
    }


    public void GetUtilizedValue() {
//        String url = /*con.base_url + "/flexi_benifit/get_utilised_data"*/"http://eb.benefitz.in/flexi_details";

        RequestQueue rq = Volley.newRequestQueue(context);

        //RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));

        String url = con.base_url+"/flexi_details";

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("response",response);

                            JSONArray jsonObjectResponse = new JSONArray(response);

                            //Premium

                            //JSONObject jsonObj = new JSONObject(jsonObjectResponse);


                           // String wallet=jsonObj.getString("");






                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        /*      smr.addStringParam("token", token);*/



       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/


        HashMap<String, String> params = new HashMap<>();
        params.put("emp_id", emp_id);
        params.put("benifit_id", benefit_id);

        smr.setParams(params);
        rq.add(smr);

    }















    public void GetAllFlex() {
//        String url = /*con.base_url + "/flexi_benifit/get_utilised_data"*/"http://eb.benefitz.in/flexi_details";


       // RequestQueue rq = Volley.newRequestQueue(context);


      RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
        String url = con.base_url+"/flexi_details";

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("response",response);

                            JSONObject jsonObjectResponse = new JSONObject(response);

                            //Premium


                            JSONObject flexBalance = jsonObjectResponse.getJSONObject("total_flex_balence");

                            String walletBalance = flexBalance.getString("total_balance");
                            String walletBalances = flexBalance.getString("Flex_Wallet");
                            walletFlex = Integer.parseInt(flexBalance.getString("total_balance"));
                            toPay = flexBalance.getString("to_pay");
                            wallet_utilization = Integer.parseInt(flexBalance.getString("Wallet_Utilization"));

                            int sum_premium = flexBalance.getInt("sum_premium");


                            balance = flexBalance.getString("total_balance");
                            flexdata = flexBalance.getString("Flex_Wallet");

                            String sum_premiuma= String.valueOf(sum_premium);



                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        /*      smr.addStringParam("token", token);*/


       /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/

        HashMap<String, String> params = new HashMap<>();
        params.put("policy_type", "Flex Summary");
        params.put("emp_id", emp_id);

        smr.setParams(params);
        rq.add(smr);
    }















    private SSLSocketFactory getSocketFactory() {

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.cert);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.e("CERT", "ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }


            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);




            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());






           /* HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            SSLContext context = null;
            context = SSLContext.getInstance("TLS");

            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
*/
            SSLSocketFactory sf = context.getSocketFactory();


            return sf;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return  null;
    }

}
