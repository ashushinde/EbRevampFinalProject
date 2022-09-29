package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.Feature;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.squareup.picasso.Picasso;
import com.palm.newbenefit.Activity.HomePoliciesActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Group;
import com.kmd.newbenefit.R;

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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class HomeGroupAdapter extends RecyclerView.Adapter<HomeGroupAdapter.ViewHolder> {
    Constants con;
    private List<Group> mTrain;
    private Context context = null;
    List<Feature> oba = null;
    MyFeatureAdapter adaptera = null;
    RecyclerView recyclerView;
    TextView desc;
    ImageView info_text;
    public HomeGroupAdapter(Context context, List<Group> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.ghi_list_new, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Group train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;
        TextView cover = viewHolder.cov;
        TextView member = viewHolder.m_cov;
        TextView covera = viewHolder.cover;


        if(train.getCover().equalsIgnoreCase("null")||train.getCover().isEmpty()){
            viewHolder.covera.setText(train.getCover());

        }else {
            try{
                int data= Integer.parseInt(train.getCover());

                String cover_data =NumberFormat.getNumberInstance(Locale.US).format(data);

                viewHolder.covera.setText(cover_data);
            }catch (Exception e){
                viewHolder.covera.setText(train.getCover());

            }

        }



        if(train.getPol_sub_type_id().equalsIgnoreCase("2")||
                train.getPol_sub_type_id().equalsIgnoreCase("5")){
            viewHolder.two_five.setVisibility(View.VISIBLE);
            viewHolder.one_four.setVisibility(View.GONE);
            viewHolder.three_six.setVisibility(View.GONE);


        }else if(train.getPol_sub_type_id().equalsIgnoreCase("1")||
                train.getPol_sub_type_id().equalsIgnoreCase("4")){

            viewHolder.two_five.setVisibility(View.GONE);
            viewHolder.one_four.setVisibility(View.VISIBLE);
            viewHolder.three_six.setVisibility(View.GONE);


        }else if(train.getPol_sub_type_id().equalsIgnoreCase("3")||
                train.getPol_sub_type_id().equalsIgnoreCase("6")){

            viewHolder.two_five.setVisibility(View.GONE);
            viewHolder.one_four.setVisibility(View.GONE);
            viewHolder.three_six.setVisibility(View.VISIBLE);
        }





        if(train.getOpd_suminsured().equalsIgnoreCase("null")||train.getOpd_suminsured().isEmpty()||train.getOpd_suminsured().equalsIgnoreCase("0")){
            viewHolder.opd_cover.setText(train.getCover());
            viewHolder.opd.setVisibility(View.GONE);
        }else {
            int data= Integer.parseInt(train.getOpd_suminsured());
            viewHolder.opd.setVisibility(View.VISIBLE);

            String cover_data =NumberFormat.getNumberInstance(Locale.US).format(data);

            viewHolder.opd_cover.setText(cover_data);

        }

        //  viewHolder.policyname.setText(train.getPolicy_type_id());
        if(train.getCover_balancea().equalsIgnoreCase("null")||train.getCover().isEmpty()){
            viewHolder.premium.setText(train.getCover_balancea());
        }else {
            int data= Integer.parseInt(train.getCover_balancea());

            String cover_data =NumberFormat.getNumberInstance(Locale.US).format(data);

            viewHolder.premium.setText(cover_data);
        }

//        if(train.getOpd().equalsIgnoreCase("null")||train.getCover().isEmpty()||train.getOpd().equalsIgnoreCase("")||train.getOpd().equalsIgnoreCase("0")){
//            viewHolder.opd.setVisibility(View.GONE);
//        }else {
//            viewHolder.opd.setVisibility(View.VISIBLE);
//            int data= Integer.parseInt(train.getOpd());
//
//            String cover_data =NumberFormat.getNumberInstance(Locale.US).format(data);
//
//            viewHolder.opd_cover.setText(cover_data);
//        }



//        if(train.getPol_sum_premmium().equalsIgnoreCase("null")||train.getPol_sum_premmium().isEmpty()){
//            viewHolder.premium_data.setText(train.getPol_sum_premmium());
//        }else {
//
//            try{
//                int data= Integer.parseInt(train.getPol_sum_premmium());
//                String cover_data =NumberFormat.getNumberInstance(Locale.US).format(data);
//
//                viewHolder.premium_data.setText(cover_data);
//            }catch(Exception e)
//            {
//                viewHolder.premium_data.setText(train.getPol_sum_premmium());
//            }
//
//
//        }









//        if (train.getPol_sub_type_id().equalsIgnoreCase("1")) {
//            viewHolder.balance.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.balance.setVisibility(View.GONE);
//        }

//        if (train.getPolicy_type_id().equalsIgnoreCase("2") || train.getPolicy_type_id().equalsIgnoreCase("3")) {
//            viewHolder.pre.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.pre.setVisibility(View.GONE);
//        }
        viewHolder.frogm.setText(train.getPol_sub_type_name());


        if(train.getPol_mem_insured().equalsIgnoreCase("null")||train.getPol_mem_insured().isEmpty()){
            covera.setText(train.getPol_mem_insured());
        }else {
            int data= Integer.parseInt(train.getPol_mem_insured());

            String cover_data =NumberFormat.getNumberInstance(Locale.US).format(data);

            covera.setText(cover_data);
        }



        if(train.getPol_id().equalsIgnoreCase("null")||
                train.getPol_id().isEmpty()||
                (train.getPol_id().equalsIgnoreCase("0")||
                        train.getPol_id().equalsIgnoreCase("0.0"))){
            viewHolder.premium_data.setText(train.getPol_id());
            viewHolder.pre.setVisibility(View.GONE);
        }else {
            double data= Double.parseDouble(train.getPol_id());

            String cover_data =NumberFormat.getNumberInstance(Locale.US).format(data);

            viewHolder.premium_data.setText(cover_data);
            viewHolder.pre.setVisibility(View.VISIBLE);
        }

        if(train.getEnhnace_cover().equalsIgnoreCase("0")||
        train.getEnhnace_cover().equalsIgnoreCase("null")||
        train.getEnhnace_cover().equalsIgnoreCase("")){
            viewHolder.enhance_value.setVisibility(View.GONE);
            viewHolder.enhance.setText(null);

        }else {
            viewHolder.enhance_head.setText("IPD Sum Insured + Enhance");
            viewHolder.enhance_value.setVisibility(View.VISIBLE);
            viewHolder.enhance.setText(train.getEnhnace_cover());
        }



        // type.setText(train.getPol_sub_type_name());
        // cover.setText(String.valueOf(train.getCover()));
        member.setText(train.getMem_count());


        //new DownloadImageFromInternet(viewHolder.s_logo).execute(train.getInsure_com_img_path());

        Picasso.get().load(train.getPol_sub_type_img_path()).into(viewHolder.s_logo);



//        try{
//            Picasso.get().load(train.getPol_sub_type_img_path()).into(viewHolder.p_img);
//        }catch (Exception e){
//
//        }

        if(train.getCover_balancea().equalsIgnoreCase(train.getCover())){
            viewHolder.balance.setVisibility(View.GONE);
        }


        viewHolder.see_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Dialog myDialog;
                SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                con = new Constants();
                String token = prefs.getString("api_token", null);

                myDialog = new Dialog(context);

                myDialog.setContentView(R.layout.product_features);

                recyclerView = myDialog.findViewById(R.id.ghi_recycle);
                desc= myDialog.findViewById(R.id.desc);
                info_text= myDialog.findViewById(R.id.info_text);
                 TextView name= myDialog.findViewById(R.id.name);
                name.setText(train.getPol_sub_type_name());
                LinearLayout dec_hide= myDialog.findViewById(R.id.dec_hide);
                if(train.getInsure_com_img_path().equalsIgnoreCase("null")){
                    dec_hide.setVisibility(View.GONE);
                }else {
                    desc.setText(train.getInsure_com_img_path());
                    dec_hide.setVisibility(View.VISIBLE);
                }

                oba = new ArrayList<>();
                adaptera = new MyFeatureAdapter(context, oba);
                recyclerView.setAdapter(adaptera);

                int numberOfColumnsv = 1;
                recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumnsv));

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));


                GridLayoutManager managerv = new GridLayoutManager(context, 1,
                        GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(managerv);



                String url = con.base_url+"/api/employee/get/dashboard";
                RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));

                mRequestQueue.getCache().clear();

                StringRequest mStringRequest = new StringRequest(Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                               try {
                                   JSONObject resp= new JSONObject(response);

                                   String statusa= String.valueOf(resp.getBoolean("status"));


                                   if(statusa.equalsIgnoreCase("false") ||
                                     response.equalsIgnoreCase("{\"status\":true,\"data\":[]}"))
                                   {
                                        info_text.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);

                                    } else {

                                        info_text.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);

                                        JSONArray  jsonObj=resp.getJSONArray("data");



                                        for (int j = 0; j < jsonObj.length(); j++) {
                                            JSONObject jo_areas = (JSONObject) jsonObj.get(j);

                                            String ids = (jo_areas.getString("policy_id"));


                                            if(ids.equalsIgnoreCase(train.getPo_id())){
                                                JSONArray features=jo_areas.getJSONArray("product_features");

                                                for (int k = 0; k < features.length(); k++) {
                                                    JSONObject jo_area = (JSONObject) features.get(k);


                                                    String id = (jo_area.getString("id"));
                                                    String policy_id = jo_area.getString("policy_id");
                                                    String policy_name = jo_area.getString("content");
                                                    String suminsured = (jo_area.getString("suminsured"));
                                                    String no_of_times_of_salary = (jo_area.getString("no_of_times_of_salary"));
                                                    String title = (jo_area.getString("title"));
                                                    String content = (jo_area.getString("content"));
                                                    String image = (jo_area.getString("image"));

                                                    oba.add(new Feature(id,policy_id
                                                            ,policy_name,suminsured,no_of_times_of_salary,title
                                                            ,content,image));


                                                }
                                            }

                                        }




                                        adaptera.notifyDataSetChanged();

                                        Log.e("onErrorResponse", String.valueOf(adaptera.getItemCount()));
                                        Log.e("oba", oba.toString());



                                    }


                                } catch (Exception e) {
                                    Log.e("onErrorResponse", e.toString());
                                    info_text.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }

                            }
                        },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", error.toString());
                        info_text.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };



//                HashMap<String, String> params = new HashMap<>();
//
//                params.put("policy_id", train.getPo_id());
//                params.put("suminsured", train.getPol_mem_insured());
//
//
//                smr.setParams(params);
                mRequestQueue.add(mStringRequest);







                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(myDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                myDialog.getWindow().setAttributes(layoutParams);
                myDialog.show();
            }

        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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


                            Intent intent = new Intent(context, HomePoliciesActivity.class);
                            intent.putExtra("coverName",train.getPol_sub_type_name());
                            intent.putExtra("coverID",train.getPolicy_type_id());
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

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView p_type,opd_cover,policyname, covera,m_cov,enhance,
                enhance_head,cov, cover, premium, frogm, premium_data;
        public ImageView two_five, one_four,three_six,s_logo;
        LinearLayout balance, pre,opd,enhance_value;
        LinearLayout see_details;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            one_four=itemView.findViewById(R.id.one_four);
            three_six=itemView.findViewById(R.id.three_six);
            two_five = (ImageView) itemView.findViewById(R.id.two_five);
            //    policyname= (TextView) itemView.findViewById(R.id.policyname);
            frogm = (TextView) itemView.findViewById(R.id.frogm);
            premium = (TextView) itemView.findViewById(R.id.premium);
            m_cov = (TextView) itemView.findViewById(R.id.m_cov);
            cov = (TextView) itemView.findViewById(R.id.cov);
            enhance_head= (TextView) itemView.findViewById(R.id.enhance_head);
            s_logo = (ImageView) itemView.findViewById(R.id.s_logo);
            cover = (TextView) itemView.findViewById(R.id.cover);
            premium_data = (TextView) itemView.findViewById(R.id.premium_data);
            balance = (LinearLayout) itemView.findViewById(R.id.balance);
            pre = (LinearLayout) itemView.findViewById(R.id.pre);
            opd= (LinearLayout) itemView.findViewById(R.id.opd);
            opd_cover= (TextView) itemView.findViewById(R.id.opd_cover);
            covera= (TextView) itemView.findViewById(R.id.covera);
            enhance= (TextView) itemView.findViewById(R.id.enhance);
            see_details= (LinearLayout) itemView.findViewById(R.id.see_details);
            enhance_value= (LinearLayout) itemView.findViewById(R.id.enhance_value);
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
