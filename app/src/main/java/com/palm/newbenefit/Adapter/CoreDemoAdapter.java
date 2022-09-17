package com.palm.newbenefit.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.CoreData;
import com.palm.newbenefit.Module.amount;
import com.palm.newbenefit.R;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class CoreDemoAdapter extends RecyclerView.Adapter<CoreDemoAdapter.ViewHolder> {
    Constants con;
    private List<CoreData> mTrain;
    private Context context;
    private String emp_id;
    String sodexoTextHeader = "Meal Cards will be loaded every first business day of the month",
            sodexoMore = "As per the allocated/Selected amount on Flex Benefit programme.";
    String personalAccidentHeader = "A top up plan is basically a regular personal accident insurance policy",
            personalAccidentMore = "In which you can have wider protection at a lesser price. This cover starts when your existing group personal accident Sum Insured gets exhausted during the policy period. All covers available under your existing group personal accident policy shall be applicable even under the top-up plan.";
    String voluntaryTermLifeHeader = "This is a pure risk insurance cover which provides critical illness & death benefit.",
            voluntaryMore = "Premiums are defined age-wise and refer policy for more details. The coverage options available are: Option 1: Rs.25 Lacs (Rs.15 Lacs Basic Death cover and Rs.10 Lacs Accelerated Critical Illness cover) Option 2: Rs.50 Lacs (Rs.40 Lacs Basic Death cover and Rs.10 Lacs Accelerated Critical Illness cover)";
    String mediclaimHeader = "A top up plan is basically a regular health insurance policy in which you can have wider protection at a lesser price. ";
        String  mediclaimMore = "This cover starts when your existing group Mediclaim Sum Insured gets exhausted during the policy period. All covers available under your existing group Mediclaim policy shall be applicable even under the top-up plan.";
    String parental_more = "Premium per life is INRxxxx for employee share.\n" +
                   "1. You can add up to 2 dependants.\n" +
                   "2. Dependant Parents or In-laws (Any one set of Parents allowed. Cross combination of Parent or In-laws allowed.)";
    String balance,flexdata;
    String dummy = "Dummy data";

    String Parental="Company will continue to subsidize premiums and for FY 19-20 subsidy will be 50% of the overall premiums and rest to be borne by employees. ";

    // Pass in the contact array into the constructor
    public CoreDemoAdapter(Context context, List<CoreData> train, String emp_id) {
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
        View trainingView = inflater.inflate(R.layout.core_demo, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @SuppressLint({"ResourceAsColor", "WrongConstant", "NewApi"})
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        CoreData train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model

        TextView sudexoPrice = viewHolder.sudexo_price;
        TextView name = viewHolder.benifit_name;
        TextView cover = viewHolder.m_cov;



//        cover.setText(String.valueOf(train.getEmp_id()));
        name.setText(String.valueOf(train.getFlexi_benefit_name()));





        if(train.getFinal_amount().equalsIgnoreCase("null")||train.getFinal_amount().isEmpty()){
            sudexoPrice.setText((train.getFinal_amount()));
        }else {
            int data= Integer.parseInt(train.getFinal_amount());

            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

            sudexoPrice.setText(cover_data);
        }



//        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//
//                if(viewHolder.hide.getVisibility() == View.VISIBLE){
//
//                    viewHolder.hide.setVisibility(View.GONE);
//                    viewHolder.expand.setImageResource(R.drawable.ic_expand_more_black_24dp);
//                }
//                else {
//                    viewHolder.hide.setVisibility(View.VISIBLE);
//                    viewHolder.expand.setImageResource(R.drawable.ic_expand_less_black_24dp);
//                }
//
//
//
//            }
//
//        });

       RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
          con=new Constants();
        String urla = con.base_url+"/get_profile_per_details";
        StringRequest smra = new StringRequest(Request.Method.POST, urla,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonObjh = new JSONObject(response);


                            Log.d("datsdffa",response);






                            JSONObject jsonObj = jsonObjh.getJSONObject("flag");


                            String benifit_all_data=jsonObj.getString("benifit_all_data");
                            String nominee_data=jsonObj.getString("nominee_data");
                            String subQuery1=jsonObj.getString("subQuery1");


                            String enrollment_flag=jsonObj.getString("flex_flag");


                            if((enrollment_flag.equalsIgnoreCase("Y"))){

                                viewHolder.reset.setVisibility(View.GONE);
                               viewHolder.sudexo_next.setVisibility(View.GONE);
                                viewHolder.sodexo_nxt_linear.setVisibility(View.GONE);

                            }
                            else {


                                if (train.getFlexi_benefit_name().equalsIgnoreCase("Sodexo")) {



                                    if(train.getFinal_amount().equalsIgnoreCase("0")){
                                        viewHolder.reset.setVisibility(View.GONE);
                                        viewHolder.sudexo_next.setVisibility(View.VISIBLE);
                                        viewHolder.sodexo_nxt_linear.setVisibility(View.VISIBLE);
                                    }else {
                                        viewHolder.reset.setVisibility(View.VISIBLE);
                                        viewHolder.sudexo_next.setVisibility(View.GONE);
                                        viewHolder.sodexo_nxt_linear.setVisibility(View.GONE);
                                    }

                                }else {
                                    viewHolder.reset.setVisibility(View.GONE);
                                    viewHolder.sudexo_next.setVisibility(View.GONE);
                                    viewHolder.sodexo_nxt_linear.setVisibility(View.GONE);

                                }



                               // viewHolder.sudexo_next.setVisibility(View.VISIBLE);

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















        new DownloadImageFromInternet(viewHolder.p_img)
                .execute("https://preprod.lifekaplan-eb.com/" + train.getImg_name());

        if (train.getFlexi_benefit_name().equalsIgnoreCase("Sodexo")) {
            viewHolder.view_data_info.setText(sodexoTextHeader);
        } else if (train.getFlexi_benefit_name().equalsIgnoreCase("Voluntary Term Life")) {
            viewHolder.view_data_info.setText(voluntaryTermLifeHeader);
            viewHolder.sudexo_next.setText("    View    ");
            viewHolder.reset.setVisibility(View.GONE);
        } else if (train.getFlexi_benefit_name().equalsIgnoreCase("Parental Cover")) {
            viewHolder.view_data_info.setText(Parental);
            viewHolder.sudexo_next.setText("    View    ");
            viewHolder.reset.setVisibility(View.GONE);
        } else if (train.getFlexi_benefit_name().equalsIgnoreCase("Mediclaim Top-Up")) {
            viewHolder.view_data_info.setText(mediclaimHeader);
            viewHolder.sudexo_next.setText("    View    ");
            viewHolder.reset.setVisibility(View.GONE);
        }

        else if (train.getFlexi_benefit_name().equalsIgnoreCase("Personal Accident Top-Up")) {
            viewHolder.view_data_info.setText(personalAccidentHeader);
            viewHolder.sudexo_next.setText("    View    ");
            viewHolder.reset.setVisibility(View.GONE);
        }

     /*   Animation anim = AnimationUtils.loadAnimation(context, R.anim.antirotate);
        anim.reset();
        viewHolder.sudexo_next.clearAnimation();
        viewHolder.sudexo_next.startAnimation(anim);*/


        viewHolder.sudexo_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Context context = v.getContext();
                AmountAdapter fadapter = null;
                List<amount> ob = null;

                final CoreData train = mTrain.get(position);
                if (train.getFlexi_benefit_name().equalsIgnoreCase("Sodexo")) {

                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.top_up_dailog);

                    Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                    RadioButton wall = (RadioButton) dialog.findViewById(R.id.wall);
                    RadioButton sal = (RadioButton) dialog.findViewById(R.id.sal);
                    RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.rg);
                    RecyclerView recyclerView = dialog.findViewById(R.id.ghi_recycle);

                   // RequestQueue rq = Volley.newRequestQueue(context);

                   RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                   rq.getCache().clear();
                    String url = con.base_url+"/voluntary_coverage_fetch_sodexo_amount";

                    List<amount> finalOb = ob;
                    AmountAdapter finalFadapter = fadapter;
                    StringRequest smr = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        Log.d("response",response);

                                        ArrayList<String> stringArray = new ArrayList<String>();
                                        JSONArray jsonObj = new JSONArray(response);


                                        final RadioButton[] rb = new RadioButton[jsonObj.length()];
//
                                        final RadioButton[] rbg = new RadioButton[jsonObj.length()];
//                                                  RadioGroup rg = new RadioGroup(context); //create the RadioGroup
                                        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
                                        for (int i = 0; i < jsonObj.length(); i++) {
                                            rbg[i] = new RadioButton(context);
                                            rbg[i].setText((jsonObj.get(i).toString()));
                                            rbg[i].setId(Integer.parseInt(jsonObj.get(i).toString()));
                                            rg.addView(rbg[i]);
                                        }








                                    } catch (Exception e) {

                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });


                  /*  smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                            0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                    RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                    mRequestQueue.add(smr);*/


                    HashMap<String, String> params = new HashMap<>();
                    params.put("emp_id", emp_id);
                    smra.setParams(params);
                    rq.add(smr);


                    wall.setText("Wallet");
                    sal.setVisibility(View.GONE);
                   // RequestQueue rqa = Volley.newRequestQueue(context);

                    RequestQueue rqa = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                    rqa.getCache().clear();
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


                                      /*  if((!(benifit_all_data.equalsIgnoreCase(null)) && (!(nominee_data.equalsIgnoreCase(null)) && (subQuery1 .equalsIgnoreCase("Y"))))){
                                            btn_cancel.setVisibility(View.GONE);

                                        }
                                        if((!(benifit_all_data.equalsIgnoreCase("null")) || (subQuery1 .equalsIgnoreCase("Y")))){
                                            btn_cancel.setVisibility(View.GONE);

                                        }
                                        else {

                                            btn_cancel.setVisibility(View.VISIBLE);

                                        }*/


                                        if((enrollment_flag.equalsIgnoreCase("Y"))){

                                            viewHolder.reset.setVisibility(View.GONE);
                                            btn_cancel.setVisibility(View.GONE);

                                        }
                                        else {


                                            if (train.getFlexi_benefit_name().equalsIgnoreCase("Sodexo")) {
                                                viewHolder.reset.setVisibility(View.VISIBLE);
                                               btn_cancel.setVisibility(View.VISIBLE);
                                            } else {
                                                viewHolder.reset.setVisibility(View.GONE);
                                                btn_cancel.setVisibility(View.GONE);
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
                    mRequestQueuea.add(smra);
*/


                    HashMap<String, String> paramss = new HashMap<>();
                    paramss.put("emp_id", emp_id);
                    paramss.put("policy_no",policy_no);
                    smra.setParams(paramss);
                    rqa.add(smra);




                    getBalance();

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



//                            Toast.makeText(context, "Clcik submit", Toast.LENGTH_SHORT).show();

                            // Toast.makeText(context, String.valueOf(selectedRadioButtonID), Toast.LENGTH_LONG).show();
                           // RequestQueue rq = Volley.newRequestQueue(context);

                          RequestQueue rq = Volley.newRequestQueue(context,
                                  new HurlStack(null, getSocketFactory()));
                          rq.getCache().clear();
                            String url = con.base_url+"/update_flexi_details";
                            StringRequest smr = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {


                                                Log.d("response_sodexo",response);

                                                JSONObject json = new JSONObject(response);

                                                String status = String.valueOf(json.getBoolean("status"));
                                                String msg = json.getString("msg");

                                                if (msg.equalsIgnoreCase("Flex balance is not enough")) {


                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Error")
                                                            .setMessage(msg)
                                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                                            .setNegativeButton(android.R.string.ok, null).show();

                                                    dialog.cancel();


                                                } else {




                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Success")
                                                            .setMessage(msg)
                                                            .setIcon(R.drawable.checkmark)
                                                            .setNegativeButton(android.R.string.ok, null).show();

                                                   /* int selectedRadioButtonIDk = rg.getCheckedRadioButtonId();
                                                    int data=selectedRadioButtonIDk*12;

                                                    viewHolder.sudexo_price.setText(data);*/


                                                    int selectedRadioButtonIDk = rg.getCheckedRadioButtonId() ;



                                                    int data=selectedRadioButtonIDk*12;

                                                    viewHolder.sudexo_price.setText(String.valueOf(data));

                                                    viewHolder.reset.setVisibility(View.VISIBLE);
                                                    viewHolder.sodexo_nxt_linear.setVisibility(View.GONE);
                                                    viewHolder.sudexo_next.setVisibility(View.GONE);

                                                    dialog.cancel();

                                                }

                                            } catch (Exception e) {

                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            int selectedRadioButtonIDk = rg.getCheckedRadioButtonId() ;


                           int data=selectedRadioButtonIDk*12;


                           //viewHolder.sudexo_price.setText(String.valueOf(data));


                            /* smr.addStringParam("token", token);*/








                            HashMap<String, String> params = new HashMap<>();
                            params.put("policy_type", "Voluntary");
                            params.put("emp_id", emp_id);
                            params.put("master_flexi_benefit_id", train.getEmployee_flexi_benifit_id());
                            params.put("flex_amount", String.valueOf(data));
                            params.put("balance", String.valueOf(flexdata));
                            params.put("sum_insured", "");
                            params.put("deduction_type", "F");

                            smr.setParams(params);
                            rq.add(smr);

                        }
                    });
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
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


                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dental_care_dailogg);
                    EditText amount = dialog.findViewById(R.id.amount);
                    RadioButton deduction_type = (RadioButton) dialog.findViewById(R.id.deduction_type);
                    RadioButton flex = (RadioButton) dialog.findViewById(R.id.flex);

                    amount.setText(train.getSum_insured());

                    if(train.getDeduction_type().equalsIgnoreCase("F")){
                        deduction_type.setText("Wallet");

                    }else {
                        deduction_type.setText("Payroll");
                    }





                    flex.setVisibility(View.GONE);


                    TextView head = (TextView) dialog.findViewById(R.id.head);
                    ImageView ab = (ImageView) dialog.findViewById(R.id.ab);


                    ab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                    btn_cancel.setVisibility(View.GONE);


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
            }

        });


        viewHolder.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               // RequestQueue rq = Volley.newRequestQueue(context);

             RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
             rq.getCache().clear();
                Context context = v.getContext();
                final CoreData train = mTrain.get(position);
                String url = con.base_url+"/reset_flexi_benefits";
                StringRequest smr = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {


                                    // String Data=response;

                                    JSONObject jsonObject = new JSONObject(response);

                                    String status = String.valueOf(jsonObject.getBoolean("status"));

                                    viewHolder.sudexo_price.setText("0");
                                    viewHolder.reset.setVisibility(View.GONE);
                                    viewHolder.sodexo_nxt_linear.setVisibility(View.GONE);
                                    viewHolder.sudexo_next.setVisibility(View.GONE);
                                    //viewHolder.m_cov.setText("0");

                                    new AlertDialog.Builder(context)
                                            .setTitle("Success")
                                            .setMessage(status)
                                            .setIcon(R.drawable.checkmark)
                                            .setNegativeButton(android.R.string.ok, null).show();


                                } catch (Exception e) {

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                final CoreData trainh = mTrain.get(position);



                HashMap<String, String> params = new HashMap<>();
                params.put("policy_type", "Voluntary");
                params.put("emp_id", emp_id);
                params.put("master_flexi_benefit_id", trainh.getEmployee_flexi_benifit_id());
                smr.setParams(params);
                rq.add(smr);
            }

        });

        viewHolder.knowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (train.getFlexi_benefit_name().equalsIgnoreCase("Sodexo")) {
                    showMoreData("Sodexo");
                } else if (train.getFlexi_benefit_name().equalsIgnoreCase("Voluntary Term Life")) {
                    showMoreData("Voluntary Term Life");
                } else if (train.getFlexi_benefit_name().equalsIgnoreCase("Personal Accident Top-Up")) {
                    showMoreData("Personal Accident Top-Up");
                } else if (train.getFlexi_benefit_name().equalsIgnoreCase("Mediclaim Top-Up")) {
                    showMoreData("Mediclaim Top-Up");
                } else if (train.getFlexi_benefit_name().equalsIgnoreCase("Parental Cover")) {
                    showMoreData("Parental Cover");
                }
            }
        });
    }

    private void showMoreData(String BenefitName) {

        String moreData = null;

        if (BenefitName.equalsIgnoreCase("Sodexo")) {
            moreData = sodexoTextHeader+" "+sodexoMore;
        } else if (BenefitName.equalsIgnoreCase("Mediclaim Top-Up")) {
            moreData = mediclaimHeader+" "+mediclaimMore;
        } else if (BenefitName.equalsIgnoreCase("Voluntary Term Life")) {
            moreData = voluntaryTermLifeHeader+" "+voluntaryMore;
        } else if (BenefitName.equalsIgnoreCase("Personal Accident Top-Up")) {
            moreData = personalAccidentHeader+" "+personalAccidentMore;
        }else if (BenefitName.equalsIgnoreCase("Parental Cover")) {
            moreData = personalAccidentHeader+" "+parental_more;
        }

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.know_more_dialog);

        TextView textDialog;
        Button ok;

        textDialog = dialog.findViewById(R.id.knowMoreText);
        ok = dialog.findViewById(R.id.knowMoreOk);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textDialog.setText(moreData);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
    }













    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView know, benifit_name, sudexo_price, m_cov, mobNo, view_data_info;
        public ImageView p_img, sudexo_check;
        LinearLayout mm;
        TextView sudexo_next;
        ImageView reset;
        LinearLayout knowMore;
        ImageView expand;
        LinearLayout sodexo_nxt_linear;

        public ViewHolder(final Context context, View itemView) {
            super(itemView);

            sudexo_price = itemView.findViewById(R.id.sudexo_price);
            reset = itemView.findViewById(R.id.reset);
            sudexo_next = itemView.findViewById(R.id.sudexo_next);
            p_img = itemView.findViewById(R.id.p_img);
            expand = itemView.findViewById(R.id.expand);
            knowMore = itemView.findViewById(R.id.coreDemoKnowMore);
            benifit_name = itemView.findViewById(R.id.benifit_name);
            m_cov = itemView.findViewById(R.id.m_cov);
            mobNo = itemView.findViewById(R.id.mobNo);
            view_data_info = itemView.findViewById(R.id.view_data_info);
           // mm = itemView.findViewById(R.id.mm);
            sodexo_nxt_linear= itemView.findViewById(R.id.sodexo_nxt_linear);
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

    private void getBalance() {
     //RequestQueue rq = Volley.newRequestQueue(context);
        RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url+"/get_profile_per_details";

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            balance = jsonObject.getString("remain_amt");

                            JSONObject bala = jsonObject.getJSONObject("total_flex_balance");

                            flexdata = bala.getString("total_balance");




                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        HashMap<String, String> params = new HashMap<>();
        params.put("emp_id", emp_id);
        smr.setParams(params);
        rq.add(smr);




        smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

       /* RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(smr);*/

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