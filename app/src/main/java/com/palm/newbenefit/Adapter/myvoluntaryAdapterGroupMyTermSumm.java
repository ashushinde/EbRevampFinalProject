package com.palm.newbenefit.Adapter;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.VoluntaryBenefit;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

;import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class myvoluntaryAdapterGroupMyTermSumm extends RecyclerView.Adapter<myvoluntaryAdapterGroupMyTermSumm.ViewHolder> {
    Constants con;
    private List<VoluntaryBenefit> mTrain;
    private Context context = null;
    String token,policy_id;
    String statusa="null";
    String IPD ="no";
    String familicover="0";
    int selfsuminsured=0;
    String show="yes";
    String showprmium="yes";
    String onlyself="yes";
    // Pass in the contact array into the constructor
    public myvoluntaryAdapterGroupMyTermSumm(Context context, List<VoluntaryBenefit> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.policy_list_summary, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Get the data model based on position
        VoluntaryBenefit train = mTrain.get(position);
        con = new Constants();





        holder.two_five.setVisibility(View.GONE);
        holder.one_four.setVisibility(View.GONE);
        holder.three_six.setVisibility(View.VISIBLE);




        holder.frogm.setText(train.getName());


        List<VoluntaryBenefit> finalGroupCoverMemberList = new ArrayList<>();


        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        policy_id = prefs.getString("policy_id", null);





        String url = con.base_url+"/api/employee/get/all-policy-member-app";
       // RequestQueue rq = Volley.newRequestQueue(context);

       RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        int allsum = 0;
                        int allenhance_suminsured=0;
                        int allopsum = 0;
                        Double allenhance_premium=0.0;
                        Double allPremium=0.0;
                        try {

                            JSONObject data= new JSONObject(response);







                         JSONArray jsonObj =data.getJSONArray("data");






                            int opdsuminsured=0;
                        for (int k = 0;k < jsonObj.length(); k++) {


                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            if(train.getSuminsured().equalsIgnoreCase(jo_areag.getString("policy_number"))){
                                int suminsured = jo_areag.getInt("suminsured");
                                double employee_premium= jo_areag.getDouble("employee_premium");
                                try{
                                     opdsuminsured=jo_areag.getInt("opd_suminsured");
                                }catch (Exception e){
                                     opdsuminsured=0;
                                }

                                int enhance_suminsured=0;
                                try{
                                     enhance_suminsured=jo_areag.getInt("enhance_suminsured");
                                }catch (Exception e){
                                     enhance_suminsured=0;
                                }

                                double enhance_premium=0.0;
                                try{
                                    enhance_premium=jo_areag.getDouble("enhance_premium");
                                }catch (Exception e){
                                    enhance_premium=0.0;
                                }




                                if(jo_areag.getString("relation_name").equalsIgnoreCase("Self")){
                                    selfsuminsured=  jo_areag.getInt("suminsured");
                                }

                                if(!jo_areag.getString("relation_name").equalsIgnoreCase("Self")){
                                    onlyself="no";
                                }




                                allenhance_suminsured=enhance_suminsured+allenhance_suminsured;

                                allsum=suminsured+allsum;
                                allPremium=employee_premium+allPremium;
                                allenhance_premium=enhance_premium+allenhance_premium;
                                allopsum=opdsuminsured+allopsum;

                            }




                        }

                            allsum=allsum+allenhance_suminsured;
                            allPremium=allPremium+allenhance_premium;

                        Log.d("onlyself",onlyself);


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);
                            String selfsum= String.valueOf(selfsuminsured);



                        if(selfsum.equalsIgnoreCase(allsumdata)){


                            if(onlyself.equalsIgnoreCase("yes")){
                                holder.sumhead.setVisibility(View.VISIBLE);
                                show="yes";
                            }else {
                                holder.sumhead.setVisibility(View.GONE);
                                show="no";
                            }


                        }else {
                            show="yes";
                            holder.sumhead.setVisibility(View.VISIBLE);
                        }



                            if(train.getRelation_id().equalsIgnoreCase("1")){
                                holder.head.setText("Individual");



                                if(allopsum==0){


                                    holder.cover.setText("View Detail");

                                }else {

                                    try{
                                        int datas= Integer.parseInt(String.valueOf(allsumdata));

                                        String cover_data = NumberFormat.getNumberInstance(Locale.US).format(datas);


                                        int datass= Integer.parseInt(String.valueOf(allopsum));

                                        String cover_datas = NumberFormat.getNumberInstance(Locale.US).format(datass);




                                        holder.cover.setText(cover_data+" (IPD) "+cover_datas+" (OPD)");
                                        IPD ="yes";

                                        holder.sumhead.setText("SumInsured (IPD)");
                                    }catch (Exception e){
                                        holder.cover.setText(allsumdata);
                                    }




                                }




                            }else {
                                holder.head.setText("Family Cover");
                                if(allsumdata.equalsIgnoreCase("0")){
                                    holder.cover.setText("-");

                                }else {
                                    
                                    
                                    if(train.getMember_mob_no().equalsIgnoreCase("null")||
                                    train.getMember_mob_no().equalsIgnoreCase("0")||
                                    train.getMember_mob_no().equalsIgnoreCase("0.0")){
                                        try{

//                                            int datas= Integer.parseInt(String.valueOf(allsumdata));
//
//                                            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(datas);





                                            Long longval = Long.parseLong(allsumdata);

                                            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                                            formatter.applyPattern("#,##,###");
                                            String cover_data = formatter.format(longval);

                                            holder.cover.setText(cover_data);
                                        }catch (Exception e){
                                            holder.cover.setText(allsumdata);
                                        }

                                    }else {

                                        try{
                                            int datas= Integer.parseInt(String.valueOf(allsumdata));

                                            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(datas);


                                            int datass= Integer.parseInt(String.valueOf(train.getMember_mob_no()));

                                            String cover_datas = NumberFormat.getNumberInstance(Locale.US).format(datass);




                                            holder.cover.setText(cover_data+" (IPD) "+cover_datas+" (OPD)");
                                        }catch (Exception e){

                                             familicover=allsumdata;
                                            holder.cover.setText(allsumdata);
                                        }




                                    }





                                }
                            }





                    if(allPremiumdata.equalsIgnoreCase("0.0")
                            ||allPremiumdata.equalsIgnoreCase("0.00")){
                        holder.prem_hide.setVisibility(View.GONE);
                        holder.premium.setVisibility(View.GONE);
                        showprmium="no";
                        holder.cvv.setText("-");
                    }else {



                        if(train.getNumber_of_time_salary().equalsIgnoreCase("3.00")
                        &&
                                (!train.getPolicy_sub_type_id().equalsIgnoreCase("1"))){
                            holder.cvv.setText("-");
                            holder.prem_hide.setVisibility(View.GONE);
                            showprmium="no";
                            holder.premium.setVisibility(View.GONE);
                        }else {
                            showprmium="yes";
                            holder.prem_hide.setVisibility(View.VISIBLE);
                            holder.premium.setVisibility(View.VISIBLE);
                            try{
                                int datsa= Integer.parseInt(String.valueOf(allPremiumdata));
                                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(datsa);



                                double vailable_val = Double.parseDouble(cover_data);
                                vailable_val =Double.parseDouble(new DecimalFormat("##.####").format(vailable_val));

                                holder.cvv.setText(String.valueOf(datsa));
                            }catch (Exception e){
                              //  int datsa= Integer.parseInt(String.valueOf(allPremiumdata));
                                double vailable_val = Double.parseDouble(allPremiumdata);
                                vailable_val =Double.parseDouble(new DecimalFormat("##.####").format(vailable_val));

                                holder.cvv.setText(String.valueOf(vailable_val));
                                //holder.cvv.setText(allPremiumdata);
                            }

                        }




                    }






                        for (int i=0;i<jsonObj.length();i++){

                            JSONObject groupCoverMemberList = (JSONObject) jsonObj.get(i);

                            if(train.getSuminsured().equalsIgnoreCase(groupCoverMemberList.getString("policy_number")))
                            {
                                String name = (groupCoverMemberList.getString("policy_name"));
                                String first_name = (groupCoverMemberList.getString("first_name"));
                                String last_name = (groupCoverMemberList.getString("last_name"));
                                String suminsured = String.valueOf((groupCoverMemberList.getString("suminsured")));
                                String dob = (groupCoverMemberList.getString("dob"));
                                String gender = (groupCoverMemberList.getString("gender"));
                                String member_email = groupCoverMemberList.getString("member_email");
                                String member_mob_no="0";
                                if(groupCoverMemberList.getString("employee_premium").equalsIgnoreCase("0.0")
                                ||groupCoverMemberList.getString("employee_premium").equalsIgnoreCase("0.00")||
                                        groupCoverMemberList.getString("employee_premium").equalsIgnoreCase("null")){

                                    member_mob_no = groupCoverMemberList.getString("enhance_employee_premium");
                                }else {
                                    member_mob_no = groupCoverMemberList.getString("employee_premium");
                                }


                                String relation = groupCoverMemberList.getString("relation_name");


                                finalGroupCoverMemberList.add(new VoluntaryBenefit(first_name,first_name,last_name,suminsured,
                                        dob,gender,member_email,member_mob_no,relation,train.getNumber_of_time_salary(),
                                        show,
                                        showprmium,
                                        "",""));






                            }




                        }

                        GroupCoverMemberAdapter groupCoverMemberAdapter = new GroupCoverMemberAdapter(context, finalGroupCoverMemberList);

                        LinearLayoutManager layoutManagerGroupCoverMember = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

                        holder.additionalInfo.setLayoutManager(layoutManagerGroupCoverMember);

                        holder.additionalInfo.setAdapter(groupCoverMemberAdapter);

                } catch (Exception e) {
                    Log.e("myerror", e.toString());
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




        rq.add(mStringRequest);








  /*      if(position %2 == 0)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#f0eae9"));
        }*/
        holder.dropdownBtn.setImageResource(R.drawable.down_arrow);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.hide.getVisibility() == View.GONE){
                    holder.hide.setVisibility(View.VISIBLE);
                    holder.dropdownBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }else {
                    holder.hide.setVisibility(View.GONE);
                    holder.dropdownBtn.setImageResource(R.drawable.down_arrow);
                }

                if (holder.nomineehide.getVisibility() == View.GONE){



                        List<VoluntaryBenefit> finalGroupCoverMemberLists = new ArrayList<>();

                        RequestQueue rqs = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                        rqs.getCache().clear();


                        String urla = con.base_url + "/api/employee/get/nominee";
                        StringRequest smr = new StringRequest(Request.Method.POST, urla,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {


                                            JSONObject data= new JSONObject(response);

                                            Log.e("nominee", data.toString());

                                            statusa= String.valueOf(data.getString("status"));

                                            if(statusa.equalsIgnoreCase("true")){

                                                JSONArray jsonObj =data.getJSONArray("data");

                                                if(jsonObj.length()==0){
                                                    statusa="false";
                                                 holder.nomineehide.setVisibility(View.GONE);
                                                }else {
                                                    statusa="true";
                                                  holder.nomineehide.setVisibility(View.VISIBLE);
                                                }




                                                for (int i=0;i<jsonObj.length();i++){

                                                    JSONObject groupCoverMemberList = (JSONObject) jsonObj.get(i);

                                                    String name = (groupCoverMemberList.getString("nominee_relation"));
                                                    String first_name = (groupCoverMemberList.getString("nominee_fname"));
                                                    String last_name = (groupCoverMemberList.getString("nominee_lname"));
                                                    String suminsured = String.valueOf((groupCoverMemberList.getString("share_per")));
                                                    String dob = (groupCoverMemberList.getString("nominee_relation"));
                                                    String gender = (groupCoverMemberList.getString("guardian_fname"));
                                                    String member_email = groupCoverMemberList.getString("guardian_lname");
                                                    String member_mob_no = groupCoverMemberList.getString("guardian_relation");


                                                    String relation = groupCoverMemberList.getString("nominee_relation");


                                                    finalGroupCoverMemberLists.add(new VoluntaryBenefit(first_name,first_name,last_name,suminsured,
                                                            dob,gender,member_email,member_mob_no,relation));



                                                }

                                                GroupCoverNomineeAdapter groupCoverMemberAdapter = new GroupCoverNomineeAdapter(context, finalGroupCoverMemberLists);

                                                LinearLayoutManager layoutManagerGroupCoverMember = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

                                                holder.nomineeCoverAdditionalInfo.setLayoutManager(layoutManagerGroupCoverMember);

                                                holder.nomineeCoverAdditionalInfo.setAdapter(groupCoverMemberAdapter);

                                            }else {
                                                holder.nomineehide.setVisibility(View.GONE);
                                            }










                                        } catch (Exception e) {
                                            Log.e("term_errror", e.toString());
                                        }

                                    }
                                },  new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("term_errror", error.toString());

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

                        params.put("policy_id", train.getEmployee_premium());

                   Log.d("policy_id", train.getEmployee_premium());
                        smr.setParams(params);
                        rqs.add(smr);






                }else {

                    holder.nomineehide.setVisibility(View.GONE);

                }
            }
        });

        if(train.getPolicy_sub_type_id().equalsIgnoreCase("2")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("5")){
            holder.two_five.setVisibility(View.VISIBLE);
            holder.one_four.setVisibility(View.GONE);
            holder.three_six.setVisibility(View.GONE);


        }else if(train.getPolicy_sub_type_id().equalsIgnoreCase("1")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("4")){

            holder.two_five.setVisibility(View.GONE);
            holder.one_four.setVisibility(View.VISIBLE);
            holder.three_six.setVisibility(View.GONE);


        }else if(train.getPolicy_sub_type_id().equalsIgnoreCase("3")||
                train.getPolicy_sub_type_id().equalsIgnoreCase("6")){

            holder.two_five.setVisibility(View.GONE);
            holder.one_four.setVisibility(View.GONE);
            holder.three_six.setVisibility(View.VISIBLE);
        }



    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView flex_name,flex_discription,cover,final_amount,allocate,deduction_type,cvv,frogm,sumhead;
        public ImageView s_logo,dropdownBtn,two_five, one_four,three_six;
        TextView head,premium;
        RecyclerView additionalInfo,nomineeCoverAdditionalInfo;
        LinearLayout plus;
        LinearLayout hide,nomineehide,prem_hide;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            flex_name = (TextView) itemView.findViewById(R.id.flex_name);
            flex_discription = (TextView) itemView.findViewById(R.id.flex_discription);
            final_amount = (TextView) itemView.findViewById(R.id.final_amount);
            allocate = (TextView) itemView.findViewById(R.id.allocate);
            dropdownBtn = (ImageView) itemView.findViewById(R.id.dropdownBtn);
            deduction_type= (TextView) itemView.findViewById(R.id.deduction_type);
            additionalInfo= (RecyclerView) itemView.findViewById(R.id.groupCoverAdditionalInfo);
            nomineeCoverAdditionalInfo= (RecyclerView) itemView.findViewById(R.id.nomineeCoverAdditionalInfo);
            plus= (LinearLayout) itemView.findViewById(R.id.plus);
            cover=itemView.findViewById(R.id.cover);
            frogm=itemView.findViewById(R.id.frogm);
            prem_hide=itemView.findViewById(R.id.prem_hide);
            cvv=itemView.findViewById(R.id.cvv);
            hide=itemView.findViewById(R.id.hide);
            nomineehide=itemView.findViewById(R.id.nomineehide);
            head=itemView.findViewById(R.id.head);
            one_four=itemView.findViewById(R.id.one_four);
            three_six=itemView.findViewById(R.id.three_six);
            two_five = (ImageView) itemView.findViewById(R.id.two_five);
            sumhead= (TextView) itemView.findViewById(R.id.sumhead);
            premium= (TextView) itemView.findViewById(R.id.premium);
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
