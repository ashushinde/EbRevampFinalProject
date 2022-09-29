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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

;

public class myvoluntaryAdapterBenefitVOL extends RecyclerView.Adapter<myvoluntaryAdapterBenefitVOL.ViewHolder> {
    Constants con;
    private List<VoluntaryBenefit> mTrain;
    private Context context = null;
    String token,policy_id;
    // Pass in the contact array into the constructor
    public myvoluntaryAdapterBenefitVOL(Context context, List<VoluntaryBenefit> train) {
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




        if(mTrain.get(0).getSuminsured().equalsIgnoreCase("0")){
            holder.cover.setText("-");

        }else {
            holder.cover.setText(mTrain.get(0).getSuminsured());


        }

        if(mTrain.get(0).getMember_mob_no().equalsIgnoreCase("0.0")
                ||mTrain.get(0).getMember_mob_no().equalsIgnoreCase("0.00")){
            holder.cvv.setText("-");
        }else {
            holder.cvv.setText(mTrain.get(0).getMember_mob_no());
        }

        holder.frogm.setText(mTrain.get(0).getName());

        List<VoluntaryBenefit> finalGroupCoverMemberList = new ArrayList<>();


        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        policy_id = prefs.getString("policy_id", null);



        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
       // RequestQueue rq = Volley.newRequestQueue(context);

       RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));





                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Voluntary Mediclaim Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);

                    double employee_premium=0.0;
                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            try{
                                employee_premium= jo_areag.getDouble("employee_premium");

                            }catch (Exception e){
                                employee_premium= 0.0;

                            }
                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);







                        for (int i=0;i<jsonObj.length();i++){

                            JSONObject groupCoverMemberList = (JSONObject) jsonObj.get(i);

                            String name = (groupCoverMemberList.getString("policy_name"));
                            String first_name = (groupCoverMemberList.getString("first_name"));
                            String last_name = (groupCoverMemberList.getString("last_name"));
                            String suminsured = String.valueOf((groupCoverMemberList.getString("suminsured")));
                            String dob = (groupCoverMemberList.getString("dob"));
                            String gender = (groupCoverMemberList.getString("gender"));
                            String member_email = groupCoverMemberList.getString("member_email");
                            String member_mob_no = groupCoverMemberList.getString("employee_premium");
                            String relation = groupCoverMemberList.getString("relation_name");


                            finalGroupCoverMemberList.add(new VoluntaryBenefit(first_name,first_name,last_name,suminsured,
                                    dob,gender,member_email,member_mob_no,relation));



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
        public TextView flex_name,flex_discription,cover,final_amount,allocate,deduction_type,cvv,frogm;
        public ImageView s_logo,dropdownBtn;
        RecyclerView additionalInfo;
        LinearLayout plus;
        LinearLayout hide;



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
            plus= (LinearLayout) itemView.findViewById(R.id.plus);
            cover=itemView.findViewById(R.id.cover);
            frogm=itemView.findViewById(R.id.frogm);
            cvv=itemView.findViewById(R.id.cvv);
            hide=itemView.findViewById(R.id.hide);
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
