package com.palm.newbenefit.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.EditNomineeActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.NomineeEnroll;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;



public class NomineeEnrollAdapter extends RecyclerView.Adapter<NomineeEnrollAdapter.ViewHolder> {
    Constants con;
    private List<NomineeEnroll> mTrain;
    private Context context = null;
    String emp_id,policy_no;

    // Pass in the contact array into the constructor
    public NomineeEnrollAdapter(Context context, List<NomineeEnroll> train, String emp_id) {
        this.context = context;
        mTrain = train;
        emp_id = emp_id;
        con=new Constants();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.nominee_enroll_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        NomineeEnroll train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model


        viewHolder.relation.setText(train.getFr_id());
        viewHolder.first_name.setText(train.getNominee_fname());

        if(!train.getNominee_lname().equalsIgnoreCase("null")){
            viewHolder.last_name.setText(train.getNominee_lname());
        }

        viewHolder.gender.setText(train.getShare_percentile());

        viewHolder.fam_cover.setText(train.getStatus());
        viewHolder.premium.setText(train.getConfirmed_date());


        viewHolder.first_name_g.setText(train.getGuardian_fname());


        if(!train.getGuardian_lname().equalsIgnoreCase("null")){
            viewHolder.last_name_g.setText(train.getGuardian_lname());
        }



        viewHolder.relation_g.setText(train.getGuardian_relation());



        try{

            String strCurrentDate = train.getGuardian_dob();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            viewHolder.dob_g.setText(format.format(newDate));
        }catch (Exception e){
            viewHolder.dob_g.setText(train.getGuardian_dob());
        }

        try{

            String strCurrentDate = train.getNominee_dob();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            viewHolder.dob.setText(format.format(newDate));
        }catch (Exception e){
            viewHolder.dob.setText(train.getNominee_dob());
        }

        if (!train.getGuardian_fname().equalsIgnoreCase("null")) {
            viewHolder.guar.setVisibility(View.VISIBLE);
            viewHolder.guar_detail.setVisibility(View.VISIBLE);
        } else {
            viewHolder.guar.setVisibility(View.GONE);
            viewHolder.guar_detail.setVisibility(View.GONE);
        }


//        RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
//
//        String url = con.base_url+"/get_profile_per_details";
//        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//
//
//                            JSONObject jsonObjh = new JSONObject(response);
//
//
//                            JSONObject jsonObj = jsonObjh.getJSONObject("flag");
//
//
//                            String benifit_all_data=jsonObj.getString("benifit_all_data");
//                            String nominee_data=jsonObj.getString("nominee_data");
//                            String subQuery1=jsonObj.getString("subQuery1");
//
//
//                            String enrollment_flag=jsonObj.getString("enrollment_flag");
//
//                            String can_edit=jsonObjh.getString("can_edit");
//
//                            if(((!(benifit_all_data.equalsIgnoreCase(null)) && (!(nominee_data.equalsIgnoreCase(null)) && (subQuery1 .equalsIgnoreCase("Y")))||(enrollment_flag.equalsIgnoreCase("Y"))))){
//                                viewHolder.delete.setVisibility(View.GONE);
//                                viewHolder.edit.setVisibility(View.GONE);
//                                viewHolder.download.setVisibility(View.GONE);
//                            }
//                            else if((!(benifit_all_data.equalsIgnoreCase("null")) || (subQuery1 .equalsIgnoreCase("Y"))||(enrollment_flag.equalsIgnoreCase("Y")))){
//                                viewHolder.delete.setVisibility(View.GONE);
//                                viewHolder.edit.setVisibility(View.GONE);
//                                viewHolder.download.setVisibility(View.GONE);
//                            }
//                            else {
//
//                                Log.d("a","3");
//                                if (can_edit.equalsIgnoreCase("0")) {
//                                    viewHolder.delete.setVisibility(View.GONE);
//                                    viewHolder.edit.setVisibility(View.GONE);
//                                    // viewHolder.download.setVisibility(View.VISIBLE);
//                                    viewHolder.download.setVisibility(View.GONE);
//                                    Log.d("b","a");
//
//                                }else {
//                                    Log.d("b","b");
//                                    viewHolder.delete.setVisibility(View.VISIBLE);
//                                    viewHolder.edit.setVisibility(View.VISIBLE);
//                                    // viewHolder.download.setVisibility(View.VISIBLE);
//                                    viewHolder.download.setVisibility(View.GONE);
//                                }
//
//
//                            }
//
//
//
//
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
//        policy_no = prefs.getString("policy_no", null);
//        emp_id = prefs.getString("user_id", null);
//
//
//        smr.addStringParam("emp_id", emp_id);
//        smr.addStringParam("policy_no",policy_no);
//
//        /*smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
//                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
//        mRequestQueue.add(smr);*/
//
//        rq.add(smr);
//
//
//
//
//
//


            SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

            String   shown = prefs.getString("shown", null);


            if(shown.equalsIgnoreCase("all")){
                viewHolder.delete.setVisibility(View.VISIBLE);
                viewHolder.edit.setVisibility(View.VISIBLE);
            }else {
                viewHolder.delete.setVisibility(View.GONE);
                viewHolder.edit.setVisibility(View.GONE);
            }







        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {




                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setTitle("Delete Member");
                alertDialog.setMessage("Are you sure you want to delete nominee?");
                alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {




                         NomineeEnroll train = mTrain.get(position);
                        RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                        rq.getCache().clear();
                        String url = con.base_url+"/api/employee/delete/nominee"+"/"+train.getNominee_id();
                        StringRequest postRequest = new StringRequest(Request.Method.DELETE, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                      Log.d("delete Response",response);


                                            JSONObject js=new JSONObject(response);

                                            String status= String.valueOf(js.getBoolean("status"));
                                            String message=js.getString("message");
                                            if(status.equalsIgnoreCase("true")) {

                                                new AlertDialog.Builder(context)
                                                        .setTitle("Success")
                                                        .setMessage(message)
                                                        .setIcon(R.drawable.checkmark)
                                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                                            public void onClick(DialogInterface dialog, int whichButton) {


                                                                int newPosition = viewHolder.getAdapterPosition();
                                                                mTrain.remove(newPosition);
                                                                notifyItemRemoved(newPosition);
                                                                notifyItemRangeChanged(newPosition, mTrain.size());
//

                                                            }
                                                        }).show();

                                            }else {
                                                new AlertDialog.Builder(context)
                                                        .setTitle("Error?")
                                                        .setMessage(message)
                                                        .setIcon(android.R.drawable.btn_dialog)
                                                        .setNegativeButton(android.R.string.ok, null).show();


                                            }

                                                        } catch (Exception e) {
                                                            //progressDialog.dismiss();

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
                                SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                                emp_id = prefs.getString("api_token", null);

                                headers.put("Authorization", "Bearer " + emp_id);
                                return headers;
                            }
                        };



                                       rq.add(postRequest);










                    }
                }).show();

















            }

        });


        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                NomineeEnroll train = mTrain.get(position);
                Intent intent = new Intent(context, EditNomineeActivity.class);
                intent.putExtra("id", train.getNominee_id());
                context.startActivity(intent);
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

                 //   viewHolder.view_data.setLayoutParams(new LinearLayout.LayoutParams(3, 200));

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



        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_edit_data);


                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
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

        });


    }


    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView relation, first_name,
                first_name_g, last_name_g, dob_g, relation_g,
                last_name, gender, dob, age, age_type, fam_cover, premium;
        public ImageView edit, expand, delete, download;
        LinearLayout hide;
        LinearLayout guar, guar_detail;
        TextView know;
        View view_data;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            dob_g = (TextView) itemView.findViewById(R.id.dob_g);
            know= (TextView) itemView.findViewById(R.id.know);
            relation = (TextView) itemView.findViewById(R.id.relation);
            first_name_g = (TextView) itemView.findViewById(R.id.first_name_g);
            last_name_g = (TextView) itemView.findViewById(R.id.last_name_g);
            relation_g = (TextView) itemView.findViewById(R.id.relation_g);
            first_name = (TextView) itemView.findViewById(R.id.first_name);
            last_name = (TextView) itemView.findViewById(R.id.last_name);
            gender = (TextView) itemView.findViewById(R.id.gender);

            fam_cover = (TextView) itemView.findViewById(R.id.fam_cover);
            premium = (TextView) itemView.findViewById(R.id.premium);
            dob = (TextView) itemView.findViewById(R.id.dob);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            expand = (ImageView) itemView.findViewById(R.id.expand);
            hide = (LinearLayout) itemView.findViewById(R.id.hide);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            download = (ImageView) itemView.findViewById(R.id.download);
            guar = (LinearLayout) itemView.findViewById(R.id.guar);
            guar_detail = (LinearLayout) itemView.findViewById(R.id.guar_detail);
            view_data= (View) itemView.findViewById(R.id.view_data);
        }
    }


    void getprofile(){

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
