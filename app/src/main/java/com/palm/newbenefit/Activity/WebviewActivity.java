package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class WebviewActivity extends AppCompatActivity {

    String url=null;
    String pol_id;
    Constants con;
    ProgressDialog progressDialog = null;
    String user_id = null;
    private static final String TAG = WebviewActivity.class.getSimpleName();
    public static String SAMPLE_FILE = "android_tutorial.pdf";

    Integer pageNumber = 0;
    String pdfFileName;
    ImageView info_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        con=new Constants();
        WebView mywebview = (WebView) findViewById(R.id.webView);
        info_text=findViewById(R.id.info_text);

        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });

        Intent intent = getIntent();
        pol_id = intent.getStringExtra("pol_id");

        //String number = pol_id.replaceAll("/", "_");



        url = pol_id;



        Log.d("pol_id",pol_id);






        //url = "http://eb.benefitz.in/application/resources/uploads/policy_documents/0202/0202.pdf";

        //  url =  "https://www.tutorialspoint.com/java/java_tutorial.pdf";


        if(url != null) {


            if (isNetworkAvailable()) {





                if (url.matches("pdf")) {

                    new RetrievePDFStream().execute(url);

                    mywebview.setVisibility(View.GONE);
                }else{

                    mywebview.setVisibility(View.VISIBLE);
                    mywebview.clearCache(true);
                    mywebview.clearHistory();
                    mywebview.getSettings().setJavaScriptEnabled(true);
                    mywebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

                    mywebview.clearSslPreferences();
//Those other methods I tried out of despair just in case
                    mywebview.clearFormData();
                    mywebview.clearCache(true);
                    mywebview.clearHistory();
                    mywebview.clearMatches();

                    mywebview.getSettings().setDomStorageEnabled(true); // Add this
                    mywebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


                    mywebview.setWebViewClient(new WebViewClient(){
                        @Override
                        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(WebviewActivity.this);
                            String message = "SSL Certificate error.";
                            switch (error.getPrimaryError()) {
                                case SslError.SSL_UNTRUSTED:
                                    message = "The certificate authority is not trusted.";
                                    break;
                                case SslError.SSL_EXPIRED:
                                    message = "The certificate has expired.";
                                    break;
                                case SslError.SSL_IDMISMATCH:
                                    message = "The certificate Hostname mismatch.";
                                    break;
                                case SslError.SSL_NOTYETVALID:
                                    message = "The certificate is not yet valid.";
                                    break;
                            }
                            message += " Do you want to continue anyway?";

                            builder.setTitle("SSL Certificate Error");
                            builder.setMessage(message);
                            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    handler.proceed();
                                }
                            });
                            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    handler.cancel();
                                }
                            });
                            final AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon)
                        {
                            progressDialog = ProgressDialog.show(WebviewActivity.this, "",
                                    "Saving. Please wait...", true);


                            super.onPageStarted(view, url, favicon);
                        }

                        @Override
                        public void onPageFinished(WebView view, String url)
                        {
                            progressDialog.dismiss();
                            super.onPageFinished(view, url);
                        }


                    });




                    mywebview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url);


                }



            }else {
                new AlertDialog.Builder(WebviewActivity.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }













        /*    String filename = getFilesDir().getAbsolutePath()+"/"+url;

            pdfView.fromFile(new File(url)).load();*/

        }

























    }




    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> { //add byte[] instead of Inputstream

        @Override
        protected void onPostExecute(InputStream inputStream) { //add byte
            // progressDialog.dismiss();

            //pdfView.fromStream(inputStream).load();


        }

        @Override
        protected InputStream doInBackground(String... strings) { //return type byte[]
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

                return inputStream; //IOUtils.toByteArray(inputStream);
            } catch (Exception e) {
                return null;
            }
        }
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

