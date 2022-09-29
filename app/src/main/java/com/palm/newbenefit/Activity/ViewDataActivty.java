package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palm.newbenefit.Adapter.ViewImageAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.ImageData;
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
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ViewDataActivty extends AppCompatActivity {
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    RecyclerView course_history_recycle;
    int gg;
    SearchView mSearcha;
    LinearLayout ll_main_data;
    RelativeLayout allempnotdatafound;
    // List<BillAllData> ob;

    private List<ImageData> ob ;
    ViewImageAdapter adapter = null;
    RecyclerView recyclerView = null;
    TextView tv_data_not_found;
    DBHelper db;
    ImageData bill;
    ImageView info_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_activty_doc);
        con = new Constants();
        recyclerView = findViewById(R.id.policy_recycle);
        // bill=new BillAllData();
        ob =new ArrayList<ImageData>();
        db=new DBHelper(this);
        info_text=findViewById(R.id.info_text);

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });

        ob=  db.getImage();



        adapter = new ViewImageAdapter(this, ob);



        RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(ViewDataActivty.this);
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);







       /* SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        mobileNumber = prefs.getString("mobileNumber", null);
        token = prefs.getString("token", null);
        user_id = prefs.getString("user_id", null);



        // Data();


        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                *//* FamilyData train = ob.get(position);*//*

         *//*Intent intent = new Intent(getApplicationContext(), FamilyDataActivity.class);

                intent.putExtra("first_name", train.getFirst_name());
                intent.putExtra("last_name", train.getLast_name());
                intent.putExtra("address", train.getAddress());
                intent.putExtra("number", train.getContact_number());
                intent.putExtra("dob", train.getDate_of_birth());
                intent.putExtra("relation", train.getRelation());
                intent.putExtra("location", train.getLocation());
                intent.putExtra("pincode", train.getPincode());
                intent.putExtra("city", train.getCity());
                intent.putExtra("state", train.getState());
                startActivity(intent);*//*


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

*/
    }








   /* private void setBankDet() {

        ob=db.getAllBanks();

        //bill_no_list = db.getBillNo();


        adapter.notifyDataSetChanged();


    }*/







    public void Data(){


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private SSLSocketFactory getSocketFactory() {

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getResources().openRawResource(R.raw.cert);
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
