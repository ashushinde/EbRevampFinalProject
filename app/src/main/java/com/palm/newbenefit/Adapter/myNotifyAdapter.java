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
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;

import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Notification;
import com.palm.newbenefit.R;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class myNotifyAdapter extends RecyclerView.Adapter<myNotifyAdapter.ViewHolder> {
    Constants con;
    private List<Notification> mTrain;
    private Context context = null;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    // Pass in the contact array into the constructor
    public myNotifyAdapter(Context context, List<Notification> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.notify_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Notification train = mTrain.get(position);

        con = new Constants();
        // Set item views based on your views and data model
        //TextView type = viewHolder.p_type;


        viewHolder.premium.setText(train.getTitle());
        viewHolder.textdata.setText(train.getDynamic_content());


        if(train.getIs_read().equalsIgnoreCase("0")){

         viewHolder.linear.setBackgroundResource(R.drawable.card_light_blue);
        }else {
            viewHolder.linear.setBackgroundResource(R.drawable.card_back);
        }



//        String deliveryDate=train.getUpdated_at();
//        SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
//        Date d = null;
//        try {
//            d = dateFormatprev.parse(deliveryDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try{
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
//            String changedDate = dateFormat.format(d);
//            viewHolder.datetime.setText(changedDate);
//        }catch (Exception e){
//            viewHolder.datetime.setText(deliveryDate);
//        }


        try{

            String strCurrentDate = train.getUpdated_at();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("EEE dd MMM , yyyy  hh:mm aaa");

            viewHolder.datetime.setText(format.format(newDate));

        }catch (Exception e){
            viewHolder.datetime.setText(train.getUpdated_at());
        }




        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Notification train = mTrain.get(position);
              ///  RequestQueue rq = Volley.newRequestQueue(context);


                RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                rq.getCache().clear();
                String url = con.base_url + "/api/admin/updtae/user-wise-notification";
                SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.e("my response", response);


                                    final Dialog dialog = new Dialog(context);
                                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.detail_notification);

                                    ImageView ab = dialog.findViewById(R.id.ab);
                                    TextView lead_creation_date = dialog.findViewById(R.id.lead_creation_date);
                                    TextView data_start_date = dialog.findViewById(R.id.train_start_date);
                                    TextView data_completion_date = dialog.findViewById(R.id.train_completion_date);
                                    TextView exam_date = dialog.findViewById(R.id.exam_date);
                                    TextView cert_date = dialog.findViewById(R.id.cert_date);
                                    TextView stage = dialog.findViewById(R.id.stage);

                                    String leadDate = train.getEnd_date();
                                    String startDate = train.getStart_date();
                                    String endDate = train.getEnd_date();
                                    String examDate = train.getLink();
                                    String certificateDate = train.getContent();
                                    String stageText = train.getContent();

                                    if (leadDate.isEmpty() || leadDate.equals("null") || leadDate.equals("Not Found")){
                                        lead_creation_date.setText(R.string.NotFound);
                                    }else {
                                        String[] LeadDate = leadDate.split("\\s+");
                                        lead_creation_date.setText(LeadDate[0]);
                                    }

                                    if (startDate.isEmpty() || startDate.equals("null") || startDate.equals("Not Found")){
                                        data_start_date.setText(R.string.NotFound);
                                    }else {
                                        String[] StartDate = startDate.split("\\s+");
                                        data_start_date.setText(StartDate[0]);
                                    }

                                    if (endDate.isEmpty() || endDate.equals("null") || endDate.equals("Not Found")){
                                        data_completion_date.setText(R.string.NotFound);
                                    }else {
                                        String[] EndDate = endDate.split("\\s+");
                                        data_completion_date.setText(EndDate[0]);
                                    }

                                    if (examDate.isEmpty() || examDate.equals("null") || examDate.equals("Not Found")){
                                        exam_date.setText(R.string.NotFound);
                                    }else {
                                        exam_date.setText(examDate);
                                    }
                                    if (certificateDate.isEmpty() || certificateDate.equals("null") || certificateDate.equals("Not Found")){
                                        cert_date.setText(R.string.NotFound);
                                    }else {

                                        cert_date.setText(certificateDate);
                                    }

                                    stage.setText(stageText);

                                    ab.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.cancel();
                                        }
                                    });
                                    Button btn_cancel = dialog.findViewById(R.id.btn_cancel);


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



                                } catch (Exception e) {
                                    Log.e("my catche", e.toString());


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

                        String token = prefs.getString("api_token", null);
                        headers.put("Authorization", "Bearer " + token);
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }
                };



                smr.addStringParam("notification_id", train.getId());
                smr.addStringParam("is_read","1");







                rq.add(smr);












            }

        });



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




                        Notification train = mTrain.get(position);
                        RequestQueue rq = Volley.newRequestQueue(context);

                        //  RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                        String url = con.base_url+"/api/admin/clear/notification";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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
                                String emp_id = prefs.getString("api_token", null);

                                headers.put("Authorization", "Bearer " + emp_id);
                                return headers;
                            }
                        };

                        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
                        String emp_id = prefs.getString("api_token", null);

                        HashMap<String, String> params = new HashMap<>();
                        params.put("id",train.getId());
                        params.put("notification_id", train.getNotification_id());
                        params.put("employee_id",train.getEnd_date());

                        postRequest.setParams(params);

                        rq.add(postRequest);










                    }
                }).show();

















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
        public TextView  premium,textdata,datetime;
        public ImageView s_logo,delete;
        LinearLayout linear;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            premium = (TextView) itemView.findViewById(R.id.premium);
            datetime= (TextView) itemView.findViewById(R.id.datetime);
            s_logo = (ImageView) itemView.findViewById(R.id.s_logo);
            textdata = (TextView) itemView.findViewById(R.id.textdata);
            delete= (ImageView) itemView.findViewById(R.id.delete);
            linear=itemView.findViewById(R.id.linear);
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
}