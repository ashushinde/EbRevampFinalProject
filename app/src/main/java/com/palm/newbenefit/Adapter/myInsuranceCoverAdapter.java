package com.palm.newbenefit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.InsuranceCover;
import com.palm.newbenefit.R;
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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;;

public class myInsuranceCoverAdapter extends RecyclerView.Adapter<myInsuranceCoverAdapter.ViewHolder> {
    Constants con;
    private List<InsuranceCover> mTrain;
    private Context context = null;
    String token;
    List<InsuranceCover> obab = null;
    Feature_flex_adapter adapterab = null;
    TextView desc=null;

    // Pass in the contact array into the constructor
    public myInsuranceCoverAdapter(Context context, List<InsuranceCover> train) {
        this.context = context;
        mTrain = train;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        con = new Constants();
        // Inflate the custom layout
        View trainingView = inflater.inflate(R.layout.in_flex_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        // Get the data model based on position
        InsuranceCover train = mTrain.get(position);
        con = new Constants();


        viewHolder.plan_name.setText(train.getPlan_name());
        viewHolder.description.setText(train.getBenefit_description());
        viewHolder.flex_name.setText(train.getPlan_name());












    /*    if(train.getBenefit_description().equalsIgnoreCase("2")||
                train.getBenefit_description().equalsIgnoreCase("5")){
            viewHolder.two_five.setVisibility(View.VISIBLE);
            viewHolder.one_four.setVisibility(View.GONE);
            viewHolder.three_six.setVisibility(View.GONE);


        }else if(train.getBenefit_description().equalsIgnoreCase("1")||
                train.getBenefit_description().equalsIgnoreCase("4")){

            viewHolder.two_five.setVisibility(View.GONE);
            viewHolder.one_four.setVisibility(View.VISIBLE);
            viewHolder.three_six.setVisibility(View.GONE);


        }else if(train.getBenefit_description().equalsIgnoreCase("3")||
                train.getBenefit_description().equalsIgnoreCase("6")){

            viewHolder.two_five.setVisibility(View.GONE);
            viewHolder.one_four.setVisibility(View.GONE);
            viewHolder.three_six.setVisibility(View.VISIBLE);
        }

*/



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(viewHolder.hide.getVisibility() == View.VISIBLE){

                    viewHolder.hide.setVisibility(View.GONE);
                    viewHolder.expand.setImageResource(R.drawable.ic_add_black_24dp);

                }
                else {
                    viewHolder.hide.setVisibility(View.VISIBLE);

                    viewHolder.expand.setImageResource(R.drawable.ic_remove_black_24dp);

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
        public TextView plan_name,description,flex_name;
        ImageView expand,one_four,three_six,two_five;
        LinearLayout hide;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            plan_name = (TextView) itemView.findViewById(R.id.plan_name);
            expand=  itemView.findViewById(R.id.expand);
            hide=  itemView.findViewById(R.id.hide);
            description=  itemView.findViewById(R.id.description);
            description=  itemView.findViewById(R.id.description);
            flex_name=  itemView.findViewById(R.id.flex_name);
            hide=  itemView.findViewById(R.id.hide);

            one_four=itemView.findViewById(R.id.one_four);
            three_six=itemView.findViewById(R.id.three_six);
            two_five = (ImageView) itemView.findViewById(R.id.two_five);
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
