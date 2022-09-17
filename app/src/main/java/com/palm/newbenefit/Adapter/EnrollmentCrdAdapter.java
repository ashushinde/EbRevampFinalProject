package com.palm.newbenefit.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Fragment.EnrollMentFragmentJava;
import com.palm.newbenefit.Module.EnrollmentCards;
import com.palm.newbenefit.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class EnrollmentCrdAdapter extends RecyclerView.Adapter<EnrollmentCrdAdapter.ViewHolder> {
    Constants con;
    private List<EnrollmentCards> mTrain;
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
    public EnrollmentCrdAdapter(Context context, List<EnrollmentCards> train, String emp_id) {
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
        View trainingView = inflater.inflate(R.layout.my_cards_new, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @SuppressLint({"ResourceAsColor", "WrongConstant", "NewApi"})
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        EnrollmentCards train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model

        TextView m_cov = viewHolder.m_cov;

        if(train.getPolicy_sub_type_id().equalsIgnoreCase("2")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("5")){
            viewHolder.two_five.setVisibility(View.VISIBLE);
            viewHolder.one_four.setVisibility(View.GONE);
            viewHolder.three_six.setVisibility(View.GONE);


        }else if(train.getPolicy_sub_type_id().equalsIgnoreCase("1")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("4")){

            viewHolder.two_five.setVisibility(View.GONE);
            viewHolder.one_four.setVisibility(View.VISIBLE);
            viewHolder.three_six.setVisibility(View.GONE);


        }else if(train.getPolicy_sub_type_id().equalsIgnoreCase("3")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("6")){

            viewHolder.two_five.setVisibility(View.GONE);
            viewHolder.one_four.setVisibility(View.GONE);
            viewHolder.three_six.setVisibility(View.VISIBLE);
        }

       if(train.getEnrollement_start_date().equalsIgnoreCase("null")
                ||train.getEnrollement_end_date().equalsIgnoreCase("null")
               ){
            viewHolder.opd_cover.setVisibility(View.GONE);
            viewHolder.opd_cover.setTextColor(R.color.green);
            viewHolder.cover.setVisibility(View.GONE);
           viewHolder.opd.setVisibility(View.GONE);
            viewHolder.cover.setBackgroundResource(R.drawable.btn_cancle);
        }


        else if(train.getEnrollement_confirmed().equalsIgnoreCase("1")
                &&train.getEnrollement_status().equalsIgnoreCase("1")
                &&train.getPolicy_enrollment_window().equalsIgnoreCase("1")){
            viewHolder.opd_cover.setText("Done");
            viewHolder.opd_cover.setTextColor(R.color.green);
            viewHolder.cover.setText("Enrolment Window Closed");
            viewHolder.cover.setBackgroundResource(R.drawable.btn_cancle);
        }else if(train.getEnrollement_start_date().equalsIgnoreCase("0")
                &&
                train.getEnrollement_end_date().equalsIgnoreCase("0")) {
            viewHolder.opd_cover.setText("Done");
            viewHolder.cover.setVisibility(View.GONE);
            viewHolder.opd.setVisibility(View.VISIBLE);
        }else if(train.getEnrollement_confirmed().equalsIgnoreCase("1")) {
            viewHolder.opd_cover.setText("Done");
            viewHolder.cover.setVisibility(View.GONE);
            viewHolder.opd.setVisibility(View.VISIBLE);
        }else  if(train.getEnrollement_confirmed().equalsIgnoreCase("0")
                ||train.getEnrollement_status().equalsIgnoreCase("0")
                ||train.getPolicy_enrollment_window().equalsIgnoreCase("0")){

            try {
                int status= new SimpleDateFormat("yyyy-MM-dd").parse(train.getEnrollement_end_date()).compareTo(new Date());
                Log.d("status_date",String.valueOf(status));
                if(String.valueOf(status).equalsIgnoreCase("-1")){
                    viewHolder.opd_cover.setText("Done");
                    viewHolder.opd_cover.setTextColor(R.color.green);
                    viewHolder.cover.setText("Enrolment Window Closed");
                    viewHolder.cover.setBackgroundResource(R.drawable.btn_cancle);
                }else {
                    viewHolder.opd_cover.setText("Pending");
                    viewHolder.opd_cover.setTextColor(R.color.dot_dark_screen1);
                    viewHolder.cover.setText("Enrolment Window Open");
                    viewHolder.cover.setBackgroundResource(R.drawable.btn_reg);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        else
        {

            viewHolder.opd_cover.setText("Pending");
            viewHolder.opd_cover.setTextColor(R.color.dot_dark_screen1);
            viewHolder.cover.setText("Enrolment Window Open");
            viewHolder.cover.setBackgroundResource(R.drawable.btn_reg);
        }







//        if(train.getEnrollement_status().equalsIgnoreCase("0")){
//
//            viewHolder.cover.setText("Closed");
//            viewHolder.cover.setBackgroundResource(R.drawable.btn_cancle);
//        }else {
//            viewHolder.cover.setText("Active");
//            viewHolder.cover.setBackgroundResource(R.drawable.btn_reg);
//
//        }




        viewHolder.frogm.setText(train.getName());
//        cover.setText(String.valueOf(train.getEmp_id()));
        m_cov.setText(train.getPolicy_number());







        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(viewHolder.opd_cover.getText().toString().trim().equalsIgnoreCase("Closed")){
                    Context context = view.getContext();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("policy_id",train.getId());
                    editor.putString("shown","onlylist");
                    editor.putString("start_date",train.getEnrollement_start_date());
                    editor.putString("end_date",train.getEnrollement_end_date());
                    editor.apply();

                    EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();
                }else {
                    if(train.getEnrollement_confirmed().equalsIgnoreCase("1")
                            &&train.getEnrollement_status().equalsIgnoreCase("1")
                            &&train.getPolicy_enrollment_window().equalsIgnoreCase("1")){
                        Context context = view.getContext();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("policy_id",train.getId());
                        editor.putString("shown","onlylist");
                        editor.putString("start_date",train.getEnrollement_start_date());
                        editor.putString("end_date",train.getEnrollement_end_date());
                        editor.apply();

                        EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                travel, travel.getTag()).addToBackStack("back").commit();
                    }else if(train.getEnrollement_start_date().equalsIgnoreCase("0")
                            &&
                            train.getEnrollement_end_date().equalsIgnoreCase("0")) {
                        Context context = view.getContext();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("policy_id",train.getId());
                        editor.putString("shown","onlylist");
                        editor.putString("start_date",train.getEnrollement_start_date());
                        editor.putString("end_date",train.getEnrollement_end_date());
                        editor.apply();

                        EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                travel, travel.getTag()).addToBackStack("back").commit();
                    }else if(train.getEnrollement_confirmed().equalsIgnoreCase("1")) {
                        Context context = view.getContext();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("policy_id",train.getId());
                        editor.putString("shown","onlylist");
                        editor.putString("start_date",train.getEnrollement_start_date());
                        editor.putString("end_date",train.getEnrollement_end_date());
                        editor.apply();

                        EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                travel, travel.getTag()).addToBackStack("back").commit();
                    }else  if(train.getEnrollement_confirmed().equalsIgnoreCase("0")
                            ||train.getEnrollement_status().equalsIgnoreCase("0")
                            ||train.getPolicy_enrollment_window().equalsIgnoreCase("0")){

                        try {
                            int status= new SimpleDateFormat("yyyy-MM-dd").parse(train.getEnrollement_end_date()).compareTo(new Date());
                            Log.d("status_date",String.valueOf(status));
                            if(String.valueOf(status).equalsIgnoreCase("-1")){
                                Context context = view.getContext();
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("policy_id",train.getId());
                                editor.putString("shown","onlylist");
                                editor.putString("start_date",train.getEnrollement_start_date());
                                editor.putString("end_date",train.getEnrollement_end_date());
                                editor.apply();

                                EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                        travel, travel.getTag()).addToBackStack("back").commit();
                            }else {



                                    String url = con.base_url + "/api/employee/get/enroll/members?policy_id=" + train.getId();



                                    RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                                    mRequestQueue.getCache().clear();
                                    StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject js = new JSONObject(response);

                                                Log.d("response", response);
                                                JSONArray jsonArr = js.getJSONArray("data");


                                                for (int i = 0; i < jsonArr.length(); i++) {



                                                    if(jsonArr.length()==1){

                                                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                                                        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = prefs.edit();
                                                        editor.putString("policy_id",train.getId());
                                                        editor.putString("shown","all");
                                                        editor.putString("start_date",train.getEnrollement_start_date());
                                                        editor.putString("end_date",train.getEnrollement_end_date());
                                                        editor.apply();
                                                        if (jsonObj.getString("relation_name").equalsIgnoreCase("self")) {
                                                            Context context = view.getContext();
                                                            AppCompatActivity activity = (AppCompatActivity) view.getContext();

                                                            //EnrollMentFragmentJavaWithoutAdd
                                                            EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                                                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                                                    travel, travel.getTag()).addToBackStack("back").commit();

                                                        }else {
                                                            Context context = view.getContext();
                                                            AppCompatActivity activity = (AppCompatActivity) view.getContext();

                                                            EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                                                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                                                    travel, travel.getTag()).addToBackStack("back").commit();

                                                        }

                                                    }else {
                                                        Context context = view.getContext();
                                                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                                        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = prefs.edit();
                                                        editor.putString("policy_id",train.getId());
                                                        editor.putString("shown","all");
                                                        editor.putString("start_date",train.getEnrollement_start_date());
                                                        editor.putString("end_date",train.getEnrollement_end_date());
                                                        editor.apply();

                                                        EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                                                travel, travel.getTag()).addToBackStack("back").commit();
                                                    }





                                                }


                                            } catch (Exception e) {


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
                                            SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);



                                           String token = prefs.getString("api_token", null);
                                            headers.put("Authorization", "Bearer " + token);
                                            return headers;
                                        }
                                    };
                                    mRequestQueue.add(mStringRequest);






                            }
                        } catch (ParseException e) {


                            e.printStackTrace();

                            Context context = view.getContext();
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("policy_id",train.getId());
                            editor.putString("shown","onlylist");
                            editor.putString("start_date",train.getEnrollement_start_date());
                            editor.putString("end_date",train.getEnrollement_end_date());
                            editor.apply();

                            EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                    travel, travel.getTag()).addToBackStack("back").commit();
                        }
                    }


                    else
                    {



                        Context context = view.getContext();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("policy_id",train.getId());
                        editor.putString("shown","all");
                        editor.putString("start_date",train.getEnrollement_start_date());
                        editor.putString("end_date",train.getEnrollement_end_date());
                        editor.apply();

                        EnrollMentFragmentJava travel = new EnrollMentFragmentJava();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                travel, travel.getTag()).addToBackStack("back").commit();
                    }



                }





            }
        });
    }






    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView know, benifit_name, cover,opd_cover,frogm,sudexo_price, m_cov, mobNo, view_data_info;
        public ImageView p_img, sudexo_check;
        public ImageView two_five, one_four,three_six,s_logo;
        LinearLayout mm;
        TextView sudexo_next;
        ImageView reset;
        LinearLayout knowMore;
        ImageView expand;
        LinearLayout sodexo_nxt_linear;
        LinearLayout opd;

        public ViewHolder(final Context context, View itemView) {
            super(itemView);
            cover= itemView.findViewById(R.id.cover);
            opd_cover= itemView.findViewById(R.id.opd_cover);
            frogm= itemView.findViewById(R.id.frogm);
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
            opd = itemView.findViewById(R.id.opd);
            sodexo_nxt_linear= itemView.findViewById(R.id.sodexo_nxt_linear);
            two_five = (ImageView) itemView.findViewById(R.id.two_five);
            one_four=itemView.findViewById(R.id.one_four);
            three_six=itemView.findViewById(R.id.three_six);
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
       // RequestQueue rq = Volley.newRequestQueue(context);
     RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
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


       /* RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(smr);*/

        HashMap<String, String> params = new HashMap<>();
        params.put("emp_id", emp_id);

        smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        smr.setParams(params);
        rq.add(smr);


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