package com.palm.newbenefit.ApiConfig;

import com.android.volley.RequestQueue;

public class Constants {

    //https://mybenefits.policyanchor.com/home
  //  apiuatmybenefits.policyanchor.com

    public   final String domain = "apilifekaplanuat-eb.tmibasl.in";
    public final String base_url = "https://apilifekaplanuat-eb.tmibasl.in";//http://eb.fynity.in
    //http://heroapi.fynity.in/api/
//https://uat-herocareapi.heroinsurance.com
    //https://herocareapi.heroinsurance.com:8443

//uat-herocareapi.heroinsurance.com
    private RequestQueue requestQueue;

    public String generteToken()
    {
        return Double.toString(Math.random());
    }


    public boolean isValidEmail(String target) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return  target.matches(emailPattern);
    }





}
