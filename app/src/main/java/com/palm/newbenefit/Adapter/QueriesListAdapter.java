package com.palm.newbenefit.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.Queries;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class QueriesListAdapter extends
        RecyclerView.Adapter<QueriesListAdapter.ViewHolder> {
    Constants con;
    private List<Queries> mTrain;
    private Context context = null;
    String emp_id,policy_no;

    // Pass in the contact array into the constructor
    public QueriesListAdapter(Context context, List<Queries> train, String emp_id) {
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
        View trainingView = inflater.inflate(R.layout.queries_list_demo, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        Queries train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model

        DatePickerDialog fromDatePickerDialog;


        try{

            String strCurrentDate = train.getRaised_on();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = format.parse(strCurrentDate);

            format = new SimpleDateFormat("dd-MM-yyyy");

            viewHolder.relation.setText(format.format(newDate));
        }catch (Exception e){
            viewHolder.relation.setText(train.getRaised_on());
        }

        viewHolder.first_name.setText(train.getQuery_type());

        viewHolder.gender.setText(train.getQuery_sub_type());
        viewHolder.dob.setText(train.getComments());

        if(train.getStatus().equalsIgnoreCase("0")){
            viewHolder.last_name.setText("Resolved");
            viewHolder.last_name.setTextColor(Color.GREEN);
        }else {
            viewHolder.last_name.setText("Open");
            viewHolder.last_name.setTextColor(Color.RED);
        }


       /* if(train.getResolved_on().equalsIgnoreCase("null")|train.getResolved_on().equalsIgnoreCase("")|train.getResolved_on().isEmpty())
        {
            viewHolder.resolved_on.setVisibility(View.GONE);
        }
*/

        if(train.getDocument().equalsIgnoreCase("null")|train.getResolved_on().equalsIgnoreCase("")|train.getResolved_on().isEmpty())
        {
            viewHolder.download.setVisibility(View.GONE);
        }

        Picasso.get().load(train.getDocument()).into( viewHolder.download);

//        viewHolder.download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//
//
//                Context context = v.getContext();
//                Queries train = mTrain.get(position);
//
//                final Dialog dialog = new Dialog(context);
//                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.queries_document);
//
//                ImageView image = dialog.findViewById(R.id.image);
//                ImageView ab = (ImageView) dialog.findViewById(R.id.ab);
//
//
//                ab.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.cancel();
//                    }
//                });
//
//                Picasso.get().load(train.getDocument()).into(image);
//
//
//
//
//
//
//        dialog.show();
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//
//
//
//            }
//
//        });


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




    }


    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView relation, first_name, last_name, gender, dob, age, age_type, fam_cover, premium,know;
        public ImageView edit, expand, delete, download;
        LinearLayout hide, prem,hidden,resolved_on,exapndd;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            relation = (TextView) itemView.findViewById(R.id.relation);
            resolved_on=(LinearLayout)itemView.findViewById(R.id.resolved_on);
            first_name = (TextView) itemView.findViewById(R.id.first_name);
            know= (TextView) itemView.findViewById(R.id.know);
            last_name = (TextView) itemView.findViewById(R.id.last_name);
            gender = (TextView) itemView.findViewById(R.id.gender);
            age_type = (TextView) itemView.findViewById(R.id.age_type);
            age = (TextView) itemView.findViewById(R.id.age);
            fam_cover = (TextView) itemView.findViewById(R.id.fam_cover);
            premium = (TextView) itemView.findViewById(R.id.premium);
            dob = (TextView) itemView.findViewById(R.id.dob);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            expand = (ImageView) itemView.findViewById(R.id.expand);
            exapndd= (LinearLayout) itemView.findViewById(R.id.exapndd);
            hide = (LinearLayout) itemView.findViewById(R.id.hide);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            download = (ImageView) itemView.findViewById(R.id.download);
            prem = (LinearLayout) itemView.findViewById(R.id.prem);
            hidden= (LinearLayout) itemView.findViewById(R.id.hidden);
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
