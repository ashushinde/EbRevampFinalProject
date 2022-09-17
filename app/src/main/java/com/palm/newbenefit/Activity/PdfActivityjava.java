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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.R;

public class PdfActivityjava extends AppCompatActivity   {

    String url=null;
    String pol_id,id;
    Constants con;
    ProgressDialog progressDialog = null;

    private static final String TAG = WebviewActivity.class.getSimpleName();
    public static String SAMPLE_FILE = "android_tutorial.pdf";

    Integer pageNumber = 0;
    String pdfFileName;
    ImageView info_text;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_activityjava);
         con = new Constants();
         webView=findViewById(R.id.webView);


        info_text = findViewById(R.id.info_text);
        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });

        Intent intent = getIntent();
        pol_id = intent.getStringExtra("pol");

      //  id = intent.getStringExtra("id");





        url = pol_id;


        Log.d("pol", pol_id);




        if (url != null) {


            if (isNetworkAvailable()) {
                webView.setVisibility(View.VISIBLE);
                webView.clearCache(true);
                webView.clearHistory();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webView.clearSslPreferences();
                webView.clearFormData();
                webView.clearCache(true);
                webView.clearHistory();
                webView.clearMatches();
                webView.getSettings().setDomStorageEnabled(true);
                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PdfActivityjava.this);
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


//                        try{
//                            handler.proceed();
//                            progressDialog.dismiss();
//                        }catch (Exception e){
//
//                        }
                        message += " Do you want to continue anyway?";

                        builder.setTitle("SSL Certificate Error");
                        builder.setMessage(message);
                        builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                handler.proceed();
                                progressDialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                handler.cancel();
                                progressDialog.dismiss();
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    }


                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon)
                    {
                        progressDialog = ProgressDialog.show(PdfActivityjava.this, "",
                                "Saving. Please wait...", true);


                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url)
                    {

                        try{
                        progressDialog.dismiss();
                        }catch (Exception e){

                        }

                        super.onPageFinished(view, url);
                    }


                });

                if(url.contains(".pdf")){
                    webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
                }else {
                    webView.loadUrl(url);
                }



            } else {
                new AlertDialog.Builder(PdfActivityjava.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }



        /*    String filename = getFilesDir().getAbsolutePath()+"/"+url;

            pdfView.fromFile(new File(url)).load();*/

        }


    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}