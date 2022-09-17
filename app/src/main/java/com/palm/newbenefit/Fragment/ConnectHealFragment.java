package com.palm.newbenefit.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.R;

import java.util.HashMap;
import java.util.Map;


public class ConnectHealFragment extends Fragment {


    public ConnectHealFragment() {
        // Required empty public constructor
    }


    WebView webview;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_connect_heal, container, false);
        webview = v.findViewById(R.id.webView);
        progressBar = v.findViewById(R.id.progressBar);

        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");

                webview.setWebViewClient(new WebViewClient());

        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);

        webview.getSettings().setSupportMultipleWindows(false);
        webview.getSettings().setSupportZoom(false);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);


        String number="https://wellness.connectandheal.com/api/auth/selfSign?session=rAr6/e7sxD3ZxGqSl3EWraI1iDsZoIzb75gTGLIOrrE";

        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("X-CNH-PARTNER","FYNTUNE");
        webview.loadUrl(number, extraHeaders);
        return v;
    }


    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.GONE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            progressBar.setVisibility(View.GONE);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}