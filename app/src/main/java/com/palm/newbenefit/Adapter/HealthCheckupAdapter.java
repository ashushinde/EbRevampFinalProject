package com.palm.newbenefit.Adapter;

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
import com.palm.newbenefit.Activity.HealthCheckupActivity;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.HealthCheckup;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.google.android.material.snackbar.Snackbar;

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

public class HealthCheckupAdapter extends
        RecyclerView.Adapter<HealthCheckupAdapter.ViewHolder> {
Constants con;
    private List<HealthCheckup> mTrain;
    private Context context = null;

    // Pass in the contact array into the constructor
    public HealthCheckupAdapter(Context context, List<HealthCheckup> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
         con=new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.checkup_list_demo, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        HealthCheckup train = mTrain.get(position);
        con=new Constants();
        // Set item views based on your views and data model



        viewHolder.policy_number.setText(train.getPolicy_number());
        viewHolder.employee_code.setText(train.getEmployee_code());
        viewHolder.employee_no.setText(train.getEmployee_name());




        viewHolder.app_status.setText(train.getAppointment_status());


        if(train.getCheckup_type().equalsIgnoreCase("null")){
            viewHolder.checkup_type.setText("-");
        }else {
            viewHolder.checkup_type.setText(train.getCheckup_type());
        }



        viewHolder.relation.setText(train.getRelation_with_employee());

        if(train.getAlternate_appointment_request_date_time().equalsIgnoreCase("null")){
            viewHolder.alter_healthchekup_date.setText("-");
        }else if(train.getAlternate_appointment_request_date_time().equalsIgnoreCase("0000-00-00 00:00:00")){
            viewHolder.alter_healthchekup_date.setText("0000-00-00 00:00:00");
        } else {
            try {

                String strCurrentDate = train.getAlternate_appointment_request_date_time();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

                viewHolder.alter_healthchekup_date.setText(format.format(newDate));
            } catch (Exception e) {
                viewHolder.alter_healthchekup_date.setText(train.getAlternate_appointment_request_date_time());
            }
        }




        if(train.getAppointment_request_date_time().equalsIgnoreCase("null")){
            viewHolder.health_checkup_date.setText("-");
        }else if(train.getAppointment_request_date_time().equalsIgnoreCase("0000-00-00 00:00:00")){
            viewHolder.health_checkup_date.setText("0000-00-00 00:00:00");
        } else {

            try{

                String strCurrentDate = train.getAppointment_request_date_time();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

                viewHolder.health_checkup_date.setText(format.format(newDate));
            }catch (Exception e){
                viewHolder.health_checkup_date.setText(train.getAppointment_request_date_time());
            }

        }


        if(train.getAppointment_date_time().equalsIgnoreCase("null")){
            viewHolder.app_date.setText("-");
        }else if(train.getAppointment_date_time().equalsIgnoreCase("0000-00-00 00:00:00")){
            viewHolder.app_date.setText("0000-00-00 00:00:00");
        } else {

            try{

                String strCurrentDate = train.getAppointment_date_time();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

                viewHolder.app_date.setText(format.format(newDate));
            }catch (Exception e){
                viewHolder.app_date.setText(train.getAppointment_date_time());
            }
        }

        if(train.getAppointment_request_date_time().equalsIgnoreCase("null")){
            viewHolder.request_date.setText("-");
        }else if(train.getAppointment_request_date_time().equalsIgnoreCase("0000-00-00 00:00:00")){
            viewHolder.request_date.setText("0000-00-00 00:00:00");
        } else {

            try{

                String strCurrentDate = train.getAppointment_request_date_time();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

                viewHolder.request_date.setText(format.format(newDate));
            }catch (Exception e){
                viewHolder.request_date.setText(train.getAppointment_request_date_time());
            }
        }










        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                AlertDialog.Builder alerdialog = new AlertDialog.Builder(context);

                alerdialog.setTitle("Delete Member");
                alerdialog.setMessage("Are you sure you want to delete this member?");
                alerdialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alerdialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // RequestQueue rq = Volley.newRequestQueue(context);

                       RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
                        HealthCheckup train = mTrain.get(position);
                        String url = con.base_url+"/api/broker/delete-health-checkup";
                        StringRequest smr = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {



                                            JSONObject json=new JSONObject(response);
                                            String status= String.valueOf(json.getBoolean("status"));
                                            String message=json.getString("message");

                                            if(status.equalsIgnoreCase("true")){
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
                                                            }
                                                        }).show();

                                            }else {
                                                new AlertDialog.Builder(context)
                                                        .setTitle("Error")
                                                        .setMessage(message)
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
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

                              String  emp_id = prefs.getString("api_token", null);


                                headers.put("Authorization", "Bearer " + emp_id);
                                return headers;
                            }
                        };



                        HashMap<String, String> params = new HashMap<>();
                        params.put("record_id", train.getId());

                        smr.setParams(params);
                        rq.add(smr);

                    }
                }).show();

            }

        });



        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Context context = v.getContext();
                HealthCheckup train = mTrain.get(position);

                if(train.getAppointment_status().equalsIgnoreCase("Approved")){


                            Snackbar snackbar = Snackbar
                            .make(viewHolder.coordinatorLayout, "Health checkup already done!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {
                    Intent intent = new Intent(context, HealthCheckupActivity.class);

                    intent.putExtra("page_type", "edit");
                    intent.putExtra("member_name", train.getMember_name());
                    intent.putExtra("email", train.getEmail());
                    intent.putExtra("contact_no", train.getContact());
                    intent.putExtra("relations", train.getRelation_with_employee());
                    intent.putExtra("pincode", train.getPincode());
                    intent.putExtra("adress1", train.getAddress_line_1());
                    intent.putExtra("adress2", train.getAddress_line_2());
                    intent.putExtra("health_date", train.getAppointment_request_date_time());
                    intent.putExtra("alter_date", train.getAlternate_appointment_request_date_time());
                    intent.putExtra("id", train.getEmployee_member_mapping_id());
                    context.startActivity(intent);
                }



            }

        });

        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){


                    viewHolder.expand.setText("View Less");
                    viewHolder.hide.setVisibility(View.GONE);

                }
                else {

                    viewHolder.expand.setText("View More");
                    viewHolder.hide.setVisibility(View.VISIBLE);

                }



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
        public TextView  policy_number,employee_code,employee_no,app_date,request_date,health_checkup_date
                ,alter_healthchekup_date,app_status,checkup_type,expand,relation;

        public ImageView edit,delete;
        LinearLayout hide;
        LinearLayout coordinatorLayout;

        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            relation=  itemView.findViewById(R.id.relation);
            policy_number =  itemView.findViewById(R.id.policy_number);
            employee_code =  itemView.findViewById(R.id.employee_code);
            employee_no =  itemView.findViewById(R.id.employee_no);
            app_date =  itemView.findViewById(R.id.app_date);
            request_date =  itemView.findViewById(R.id.request_date);
            health_checkup_date =  itemView.findViewById(R.id.health_checkup_date);
            alter_healthchekup_date =  itemView.findViewById(R.id.alter_healthchekup_date);
            app_status =  itemView.findViewById(R.id.app_status);
            checkup_type =  itemView.findViewById(R.id.checkup_type);
            edit =  itemView.findViewById(R.id.edit);
            delete =  itemView.findViewById(R.id.delete);
            expand =  itemView.findViewById(R.id.expand);
            hide =  itemView.findViewById(R.id.hide);
            coordinatorLayout=  itemView.findViewById(R.id.coordinatorLayout);


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
