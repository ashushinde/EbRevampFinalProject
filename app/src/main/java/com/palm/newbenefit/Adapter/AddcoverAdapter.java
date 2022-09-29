package com.palm.newbenefit.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.Module.AddCover;
import com.palm.newbenefit.Module.VoluntaryBenefit;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

import org.json.JSONArray;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AddcoverAdapter extends
        RecyclerView.Adapter<AddcoverAdapter.ViewHolder> {
    Constants con;
    private List<AddCover> mTrain;
    private Context context = null;
    String Flex_Wallet,toPay,wallet_utilization;

    String dtype_vol;
    String emp_id,policy_id;
    int payroll,walletFlex;

    // Pass in the contact array into the constructor
    public AddcoverAdapter(Context context, List<AddCover> train, String emp_id) {
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
        View trainingView = inflater.inflate(R.layout.add_cover_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, trainingView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        emp_id = prefs.getString("api_token", null);
        policy_id = prefs.getString("policy_id", null);

        // Get the data model based on position
        AddCover train = mTrain.get(position);
        con = new Constants();
        // Set item views based on your views and data model

        DatePickerDialog fromDatePickerDialog;


        if(train.getDescription().equalsIgnoreCase("null")){
            viewHolder.intro.setText(null);
        }else {
            viewHolder.intro.setText(train.getDescription());
        }


        SharedPreferences prefsd = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        String  shown = prefsd.getString("shown", null);

        viewHolder.name.setText(train.getPolicyname());
        if(shown.equalsIgnoreCase("all")){

            viewHolder.vol_btn_cancel.setVisibility(View.VISIBLE);
            viewHolder.vol_btn_cancel_reset.setVisibility(View.VISIBLE);
            viewHolder.hideall.setVisibility(VISIBLE);
            viewHolder.covers.setVisibility(GONE);
            viewHolder.sum_lin.setVisibility(GONE);
            viewHolder.pay.setVisibility(VISIBLE);
            viewHolder.pre_lin.setVisibility(GONE);

        }else {

            viewHolder.old_sum.setText(train.getOld_suminsured());
            viewHolder.covertype.setText("Floater");
            viewHolder.covers.setVisibility(VISIBLE);
            viewHolder.sum_lin.setVisibility(VISIBLE);
            viewHolder.pay.setVisibility(GONE);
            viewHolder.pre_lin.setVisibility(VISIBLE);
            viewHolder.vol_btn_cancel.setVisibility(GONE);
            viewHolder.hideall.setVisibility(GONE);
            viewHolder.vol_btn_cancel_reset.setVisibility(View.GONE);
        }








        String url = con.base_url+"/api/employee/get/balance/utilization?policy_id="+policy_id;

    RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);

                    JSONObject  jsonObj=js.getJSONObject("data");


                    //Premium



                    Flex_Wallet = String.valueOf(jsonObj.getInt("remaining_flex_bal"));
                    walletFlex = jsonObj.getInt("total_flex_amt");
                    toPay = String.valueOf(jsonObj.getString("to_pay"));
                    wallet_utilization = String.valueOf(jsonObj.getInt("flex_utilized_amt"));


                    if(toPay.equalsIgnoreCase("0")
                    ||toPay.equalsIgnoreCase("null")
                    ||toPay.equalsIgnoreCase(""))
                    {
                        viewHolder.pay.setVisibility(GONE);
                    }
                    else {


                        viewHolder.to_pay.setText(toPay);
                      /*  try{
                            int data= Integer.parseInt(String.valueOf(toPay));

                            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                            viewHolder.to_pay.setText(cover_data);
                        }catch (Exception e){
                            viewHolder.to_pay.setText(toPay);
                        }

*/

                        if(shown.equalsIgnoreCase("all")){

                                viewHolder.pay.setVisibility(VISIBLE);



                        }else {
                            viewHolder.pay.setVisibility(GONE);
                        }

                    }


                } catch (Exception e) {
                    Log.e("onErrorResponse", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + emp_id);

                return headers;
            }
        };
        mRequestQueue.add(mStringRequest);




        if (viewHolder.vol_wallet.isChecked()) {
            dtype_vol = "F";
        } else {
            dtype_vol = "S";
        }






if(train.getPremium_type_id().equalsIgnoreCase("5")||
        train.getPremium_type_id().equalsIgnoreCase("6")||
        train.getPremium_type_id().equalsIgnoreCase("7")){


   // ........newservice..............//



    List<String> myList = new ArrayList<String>(Arrays.asList(train.getSuminsured().split(",")));

    final RadioButton[] rb = new RadioButton[myList.size()];
//                                                  RadioGroup rg = new RadioGroup(context); //create the RadioGroup
    viewHolder.vol_rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL


    Drawable img = context.getResources().getDrawable( R.drawable.rupee );
    img.setBounds( 0, 0, 30, 30 );

    for (int i = 0; i < myList.size(); i++) {
        rb[i] = new RadioButton(context);
        if (myList.get(i).toString().isEmpty()||
                myList.get(i).toString().equalsIgnoreCase("null")||
                myList.get(i).toString().equalsIgnoreCase("0"))
        {
            rb[i].setText((myList.get(i).toString()));
        }else {
            try {
             /*   int data= Integer.parseInt(myList.get(i).toString());

                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                rb[i].setText(cover_data);*/

                 DecimalFormat formatter = new DecimalFormat("#,###,###");
                String yourFormattedString = formatter.format(myList.get(i).toString());
                rb[i].setText(yourFormattedString);

            }catch (Exception e){
                rb[i].setText(myList.get(i).toString());
            }
        }

        rb[i].setCompoundDrawables( img, null, null, null );

        rb[i].setId(Integer.parseInt(myList.get(i).toString()));
        viewHolder.vol_rg.addView(rb[i]);
    }


    List<String> myListg = new ArrayList<String>(Arrays.asList(train.getPremium().split(",")));

    final RadioButton[] rbg = new RadioButton[myListg.size()];
//                                                  RadioGroup rg = new RadioGroup(context); //create the RadioGroup
    viewHolder.vol_rg_rb.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
    for (int i = 0; i < myListg.size(); i++) {
        rbg[i] = new RadioButton(context);
        rbg[i].setText((myListg.get(i).toString()));
        rbg[i].setId((int) Float.parseFloat(myListg.get(i).toString()));
        viewHolder.vol_rg_rb.addView(rbg[i]);
    }


    try {

        if (train.getOld_suminsured().equalsIgnoreCase("0")) {

        } else {
            int selectedRadioButtonID = Integer.parseInt(train.getOld_suminsured());
            int radioButtonID = selectedRadioButtonID;
            View radioButton = viewHolder.vol_rg.findViewById(radioButtonID);
            int idx = viewHolder.vol_rg.indexOfChild(radioButton);
            ((RadioButton) viewHolder.vol_rg.getChildAt(idx)).setChecked(true);
            ((RadioButton) viewHolder.vol_rg_rb.getChildAt(idx)).setChecked(true);

            int selectedRadioButtonIDf= viewHolder.vol_rg_rb.getCheckedRadioButtonId();




            if (String.valueOf(selectedRadioButtonIDf).isEmpty()||
                    String.valueOf(selectedRadioButtonIDf).equalsIgnoreCase("null")||
                    String.valueOf(selectedRadioButtonIDf).equalsIgnoreCase("0"))
            {
                viewHolder.vol_pre.setText(String.valueOf(selectedRadioButtonIDf));
            }else {
               /* int data= Integer.parseInt(String.valueOf(selectedRadioButtonIDf));

                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                viewHolder.vol_pre.setText(cover_data);*/

                viewHolder.vol_pre.setText(String.valueOf(selectedRadioButtonIDf));
            }






        }
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }





    //////endservice////..............

        }else {

    List<String> myList = new ArrayList<String>(Arrays.asList(train.getSuminsured().split(",")));

    final RadioButton[] rb = new RadioButton[myList.size()];
//                                                  RadioGroup rg = new RadioGroup(context); //create the RadioGroup
    viewHolder.vol_rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL


    Drawable img = context.getResources().getDrawable( R.drawable.rupee );
    img.setBounds( 0, 0, 30, 30 );

    for (int i = 0; i < myList.size(); i++) {
        rb[i] = new RadioButton(context);
        if (myList.get(i).toString().isEmpty()||
                myList.get(i).toString().equalsIgnoreCase("null")||
                myList.get(i).toString().equalsIgnoreCase("0"))
        {
            rb[i].setText((myList.get(i).toString()));
        }else {
            try {
                int data= Integer.parseInt(myList.get(i).toString());

                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                rb[i].setText(cover_data);
            }catch (Exception e){
                rb[i].setText(myList.get(i).toString());
            }
        }

        rb[i].setCompoundDrawables( img, null, null, null );

        rb[i].setId(Integer.parseInt(myList.get(i).toString()));
        viewHolder.vol_rg.addView(rb[i]);
    }


    List<String> myListg = new ArrayList<String>(Arrays.asList(train.getPremium().split(",")));

    final RadioButton[] rbg = new RadioButton[myListg.size()];
//                                                  RadioGroup rg = new RadioGroup(context); //create the RadioGroup
    viewHolder.vol_rg_rb.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
    for (int i = 0; i < myListg.size(); i++) {
        rbg[i] = new RadioButton(context);
        try {
            int data= Integer.parseInt(myListg.get(i).toString());

            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

            rbg[i].setText(cover_data);
        }catch (Exception e){
            rbg[i].setText(myListg.get(i).toString());
        }


        rbg[i].setCompoundDrawables( img, null, null, null );

        try{
            rbg[i].setId(Integer.parseInt(myListg.get(i).toString()));
        }catch (Exception e){
            rbg[i].setId((int) Double.parseDouble(myListg.get(i).toString()));
        }


        viewHolder.vol_rg_rb.addView(rbg[i]);
    }


    try{
        int selectedRadioButtonID = Integer.parseInt(train.getOld_suminsured());
        int radioButtonID = selectedRadioButtonID;
        View radioButton = viewHolder.vol_rg.findViewById(radioButtonID);
        int idx = viewHolder.vol_rg.indexOfChild(radioButton);
        ((RadioButton) viewHolder.vol_rg.getChildAt(idx)).setChecked(true);
        ((RadioButton) viewHolder.vol_rg_rb.getChildAt(idx)).setChecked(true);

        int selectedRadioButtonIDf= viewHolder.vol_rg_rb.getCheckedRadioButtonId();




        if (String.valueOf(selectedRadioButtonIDf).isEmpty()||
                String.valueOf(selectedRadioButtonIDf).equalsIgnoreCase("null")||
                String.valueOf(selectedRadioButtonIDf).equalsIgnoreCase("0"))
        {
            viewHolder.vol_pre.setText(String.valueOf(selectedRadioButtonIDf));
        }else {
            int data= Integer.parseInt(String.valueOf(selectedRadioButtonIDf));

            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

            viewHolder.vol_pre.setText(cover_data);
        }







    }
    catch(Exception e)
    {
        e.printStackTrace();
    }



}





        if(train.getHas_flex().equalsIgnoreCase("1") &&
                train.getHas_payroll().equalsIgnoreCase("1")){


            viewHolder.vol_payroll.setChecked(true);


            viewHolder.vol_wallet.setVisibility(VISIBLE);
            viewHolder.vol_payroll.setVisibility(VISIBLE);


            if (viewHolder.vol_wallet.isChecked()) {
                dtype_vol="F";
            } else {
                dtype_vol = "S";
            }




        }else{
            if (train.getHas_flex().equalsIgnoreCase("1")) {
                viewHolder.vol_wallet.setChecked(true);
                viewHolder.vol_payroll.setChecked(false);


                viewHolder.vol_wallet.setVisibility(VISIBLE);
                viewHolder.vol_payroll.setVisibility(GONE);
                dtype_vol="F";
            }
            if (train.getHas_payroll().equalsIgnoreCase("1")) {


                viewHolder.vol_wallet.setVisibility(GONE);
                viewHolder.vol_payroll.setVisibility(VISIBLE);

                viewHolder. vol_wallet.setChecked(false);
                viewHolder.vol_payroll.setChecked(true);
                dtype_vol="S";
            }
        }








        List<String> myList=new ArrayList<>();
        String urla = con.base_url+"/api/employee/get/cover/details?policy_id="+policy_id;

        RequestQueue mRequestQueues = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        mRequestQueues.getCache().clear();
        StringRequest mStringRequestss = new StringRequest(Request.Method.GET, urla, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);

                    JSONArray  jsonObj=js.getJSONArray("data");


                    JSONObject objectone=jsonObj.getJSONObject(0);

                    try{
                        String top_up_premium=objectone.getString("top_up_premium");
                        viewHolder.vol_pre.setText(top_up_premium);
                    }catch (Exception e){
                        String top_up_premium=objectone.getString("premium");
                        viewHolder.vol_pre.setText(top_up_premium);

                    }








                    JSONArray  installment=objectone.getJSONArray("installment");

                    for (int j = 0; j < installment.length(); j++) {

                        JSONObject jo_area = (JSONObject) installment.get(j);

                        String installments=jo_area.getString("installment");

                        myList.add(installments);



                        //JSONObject jo_areaa = (JSONObject) jsonObj.get(0);


                    }


                        //Premium



                   // List<String> myList = new ArrayList<String>(Arrays.asList(installment.split(",")));

                    final RadioButton[] rb = new RadioButton[myList.size()];
//                                                  RadioGroup rg = new RadioGroup(context); //create the RadioGroup
                    viewHolder.premium_installment.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL


                    Drawable img = context.getResources().getDrawable( R.drawable.rupee );
                    img.setBounds( 0, 0, 30, 30 );

                    for (int i = 0; i < myList.size(); i++) {
                        rb[i] = new RadioButton(context);
                        if (myList.get(i).toString().isEmpty() ||
                                myList.get(i).toString().equalsIgnoreCase("null") ||
                                myList.get(i).toString().equalsIgnoreCase("0")) {
                            rb[i].setText((myList.get(i).toString() +" "+ "month"));
                        } else {
                           /* try {
                                int data = Integer.parseInt(myList.get(i).toString());

                                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                                rb[i].setText(cover_data);
                            } catch (Exception e) {
                                rb[i].setText(myList.get(i).toString());
                            }*/

                            rb[i].setText(myList.get(i).toString()+" "+ "month");
                        }

                      /*  rb[i].setCompoundDrawables(img, null, null, null);

                        rb[i].setId(Integer.parseInt(myList.get(i).toString()));*/
                        viewHolder.premium_installment.addView(rb[i]);
                    }






                } catch (Exception e) {
                    Log.e("onErrorResponse", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + emp_id);

                return headers;
            }
        };
        mRequestQueues.add(mStringRequestss);












        viewHolder.vol_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {




                if(train.getPremium_type_id().equalsIgnoreCase("5")||
                        train.getPremium_type_id().equalsIgnoreCase("6")||
                        train.getPremium_type_id().equalsIgnoreCase("7")){



//                    List<String> myList = new ArrayList<String>(Arrays.asList(train.getSuminsured().split(",")));
//
//                    final RadioButton[] rb = new RadioButton[myList.size()];
////                                                  RadioGroup rg = new RadioGroup(context); //create the RadioGroup
//                    viewHolder.vol_rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
//
//
//                    Drawable img = context.getResources().getDrawable( R.drawable.rupee );
//                    img.setBounds( 0, 0, 30, 30 );
//
//                    for (int i = 0; i < myList.size(); i++) {
//                        rb[i] = new RadioButton(context);
//                        if (myList.get(i).toString().isEmpty()||
//                                myList.get(i).toString().equalsIgnoreCase("null")||
//                                myList.get(i).toString().equalsIgnoreCase("0"))
//                        {
//                            rb[i].setText((myList.get(i).toString()));
//                        }else {
//                            try {
//                                int data= Integer.parseInt(myList.get(i).toString());
//
//                                String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);
//
//                                rb[i].setText(cover_data);
//                            }catch (Exception e){
//                                rb[i].setText(myList.get(i).toString());
//                            }
//                        }
//
//                        rb[i].setCompoundDrawables( img, null, null, null );
//
//                        rb[i].setId(Integer.parseInt(myList.get(i).toString()));
//                        viewHolder.vol_rg.addView(rb[i]);
//                    }



                    String urla = con.base_url+"/api/employee/get/permilly-premium";

                   RequestQueue mRequestQueuea = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
                    mRequestQueuea.getCache().clear();
                    StringRequest mStringRequesta = new StringRequest(Request.Method.POST, urla, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject js=new JSONObject(response);

                                Log.e("mydata", response);

                                String Status= String.valueOf(js.getBoolean("status"));


                                if(Status.equalsIgnoreCase("false")){

                                    String message = js.getString("message");
                                    new AlertDialog.Builder(context)
                                            .setTitle("Error")
                                            .setMessage(message).setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }else {
                                    String premium= js.getString("premium");







                                }







                            } catch (Exception e) {
                                Log.e("error", response);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("onErrorResponse", error.toString());
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();


                            headers.put("Authorization", "Bearer " + emp_id);
                            return headers;
                        }
                    };
                    int selectedRadioButtonID = viewHolder.vol_rg.getCheckedRadioButtonId();




                    HashMap<String, String> params = new HashMap<>();
                    params.put("policy_id",train.getPolicy_id());
                    params.put("suminsured_amt", String.valueOf(selectedRadioButtonID));
                    params.put("premium_type_id",train.getPremium_type_id());

                    mStringRequesta.setParams(params);
                    mRequestQueuea.add(mStringRequesta);



                }else {

                    try {
                        int selectedRadioButtonID = viewHolder.vol_rg.getCheckedRadioButtonId();

                        int radioButtonID = viewHolder.vol_rg.getCheckedRadioButtonId();
                        View radioButton = viewHolder.vol_rg.findViewById(radioButtonID);
                        int idx = viewHolder.vol_rg.indexOfChild(radioButton);
                        ((RadioButton) viewHolder.vol_rg_rb.getChildAt(idx)).setChecked(true);
                        int selectedRadioButtonIDf = viewHolder.vol_rg_rb.getCheckedRadioButtonId();


                        if (String.valueOf(selectedRadioButtonIDf).isEmpty() ||
                                String.valueOf(selectedRadioButtonIDf).equalsIgnoreCase("null") ||
                                String.valueOf(selectedRadioButtonIDf).equalsIgnoreCase("0")) {
                            viewHolder.vol_pre.setText(String.valueOf(selectedRadioButtonIDf));
                        } else {
                            int data = Integer.parseInt(String.valueOf(selectedRadioButtonIDf));

                            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                            viewHolder.vol_pre.setText(cover_data);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });


        viewHolder.vol_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( viewHolder.vol_rg.getCheckedRadioButtonId() != -1) {


                    if (isNetworkAvailable()) {



                        int selectedRadioButtonIDg =  viewHolder.vol_rg_rb.getCheckedRadioButtonId();

                       /* if (group_wallet.isChecked()) {
                            dtype_group="F";
                        } else {
                            dtype_group = "S";
                        }
*/

                        if( viewHolder.vol_wallet.isChecked()){
                            if(walletFlex==0){
                                new AlertDialog.Builder(context)
                                        .setTitle("Error")
                                        .setMessage("Flex Balance Not Enough")

                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();
                            }else {
                                payroll=selectedRadioButtonIDg-walletFlex;

                                if (selectedRadioButtonIDg > walletFlex) {

                                    String data = "Premium deduction from , Wallet : " + " "+walletFlex +" & "+ "Payroll : " + " "+payroll +" , "+ "Would you like to continue?";

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                    alertDialogBuilder.setMessage(data);
                                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            ///////////////..SendData_Flex.../////////////////

                                           // RequestQueue rq = Volley.newRequestQueue(context);

                                               RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
                                                String url = con.base_url+"/api/employee/submit/toup";
                                            StringRequest smr = new StringRequest(Request.Method.POST, url,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {


                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    String errorCode = jsonObject.getString("message");
                                                                    String status = String.valueOf(jsonObject.getBoolean("status"));



                                                                    if(status.equalsIgnoreCase("false")){
                                                                        new AlertDialog.Builder(context)
                                                                                .setTitle("Error")
                                                                                .setMessage(errorCode)
                                                                                .setIcon(android.R.drawable.btn_dialog)
                                                                                .setNegativeButton(android.R.string.ok, null).show();
                                                                    }else {

                                                                        viewHolder.vol_btn_cancel_reset.setVisibility(VISIBLE);
                                                                        viewHolder.vol_btn_cancel.setVisibility(GONE);
                                                                        GetAllFlex();
                                                                        new AlertDialog.Builder(context)
                                                                                .setTitle("Success")
                                                                                .setMessage(errorCode)
                                                                                .setIcon(R.drawable.checkmark)
                                                                                .setNegativeButton(android.R.string.ok, null).show();
                                                                    }

                                                                } catch (Exception e) {


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
                                                        headers.put("Authorization", "Bearer " + emp_id);
                                                        return headers;
                                                    }
                                                };


                                                int selectedRadioButtonID = viewHolder.vol_rg.getCheckedRadioButtonId();
                                                //((RadioButton)med_rg_rb.getChildAt(selectedRadioButtonID)).setChecked(true);


                                                int selectedRadioButtonIDg = viewHolder.vol_rg_rb.getCheckedRadioButtonId();







                                                if (viewHolder.vol_wallet.isChecked()) {
                                                    dtype_vol = "F";
                                                } else {
                                                    dtype_vol = "S";
                                                }






                                            HashMap<String, String> params = new HashMap<>();


                                            params.put("policy_id", train.getPolicy_id());

                                            params.put("flexi_benefit_id", train.getFlexi_benefit_id());




                                            params.put("flex_amount",Flex_Wallet);




                                            int payroll=selectedRadioButtonIDg-walletFlex;

                                            int final_amt=walletFlex+payroll;

                                            params.put("flex_amount", String.valueOf(walletFlex));
                                            params.put("pay_amount", String.valueOf(payroll));
                                            params.put("final_amount", String.valueOf(final_amt));
                                            params.put("sum_insured", String.valueOf(selectedRadioButtonID));
                                            params.put("deduction_type", "F,S");

                                            smr.setParams(params);
                                            rq.add(smr);





                                            /////////////////////////////////////



                                        }
                                    });
                                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {



                                        }
                                    });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();


                                } else {

                                  RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
                                    String url = con.base_url+"/api/employee/submit/toup";
                                    StringRequest smr = new StringRequest(Request.Method.POST, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {

                                                        Log.d("response",response);


                                                        JSONObject jsonObject = new JSONObject(response);
                                                        String errorCode = jsonObject.getString("message");

                                                        String status = String.valueOf(jsonObject.getBoolean("status"));



                                                        if(status.equalsIgnoreCase("false")){
                                                            new AlertDialog.Builder(context)
                                                                    .setTitle("Error")
                                                                    .setMessage(errorCode)
                                                                    .setIcon(android.R.drawable.btn_dialog)
                                                                    .setNegativeButton(android.R.string.ok, null).show();
                                                        }else {
                                                            viewHolder.vol_btn_cancel_reset.setVisibility(VISIBLE);
                                                            viewHolder.vol_btn_cancel.setVisibility(GONE);
                                                            GetAllFlex();
                                                            new AlertDialog.Builder(context)
                                                                    .setTitle("Success")
                                                                    .setMessage(errorCode)
                                                                    .setIcon(R.drawable.checkmark)
                                                                    .setNegativeButton(android.R.string.ok, null).show();
                                                        }

                                                    } catch (Exception e) {


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
                                            headers.put("Authorization", "Bearer " + emp_id);
                                            return headers;
                                        }
                                    };

                                    int selectedRadioButtonID = viewHolder.vol_rg.getCheckedRadioButtonId();



                                    int selectedRadioButtonIDga = viewHolder.vol_rg_rb.getCheckedRadioButtonId();







                                    if (viewHolder.vol_wallet.isChecked()) {
                                        dtype_vol = "F";
                                    } else {
                                        dtype_vol = "S";
                                    }












                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("policy_id", train.getPolicy_id());
                                    params.put("flexi_benefit_id", train.getFlexi_benefit_id());
                                    params.put("flex_amount", String.valueOf(selectedRadioButtonIDga));
                                    params.put("final_amount",String.valueOf(selectedRadioButtonIDga));
                                    params.put("sum_insured", String.valueOf(selectedRadioButtonID));
                                    params.put("deduction_type", dtype_vol);
                                    params.put("pay_amount", "0");

                                    Log.d("flexi_benefit_id", train.getFlexi_benefit_id());
                                    Log.d("flex_amount", String.valueOf(selectedRadioButtonIDga));
                                    Log.d("final_amount",String.valueOf(selectedRadioButtonIDga));
                                    Log.d("sum_insured", String.valueOf(selectedRadioButtonID));
                                    Log.d("deduction_type", dtype_vol);
                                    Log.d("pay_amount", "0");

                                    smr.setParams(params);
                                    rq.add(smr);

                                }

                            }

                        }else {
                           // RequestQueue rq = Volley.newRequestQueue(context);
                     RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
                            String url = con.base_url+"/api/employee/submit/toup";
                            StringRequest smr = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {

                                                Log.d("response",response);


                                                JSONObject jsonObject = new JSONObject(response);
                                                String errorCode = jsonObject.getString("message");

                                                String status = String.valueOf(jsonObject.getBoolean("status"));



                                                if(status.equalsIgnoreCase("false")){
                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Error")
                                                            .setMessage(errorCode)
                                                            .setIcon(android.R.drawable.btn_dialog)
                                                            .setNegativeButton(android.R.string.ok, null).show();
                                                }else {
                                                    viewHolder.vol_btn_cancel_reset.setVisibility(VISIBLE);
                                                    viewHolder.vol_btn_cancel.setVisibility(GONE);
                                                    GetAllFlex();
                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Success")
                                                            .setMessage(errorCode)
                                                            .setIcon(R.drawable.checkmark)
                                                            .setNegativeButton(android.R.string.ok, null).show();
                                                }

                                            } catch (Exception e) {


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
                                    headers.put("Authorization", "Bearer " + emp_id);
                                    return headers;
                                }
                            };

                            int selectedRadioButtonID = viewHolder.vol_rg.getCheckedRadioButtonId();



                            int selectedRadioButtonIDga = viewHolder.vol_rg_rb.getCheckedRadioButtonId();







                            if (viewHolder.vol_wallet.isChecked()) {
                                dtype_vol = "F";
                            } else {
                                dtype_vol = "S";
                            }









                            HashMap<String, String> params = new HashMap<>();




                            params.put("policy_id", train.getPolicy_id());
                            params.put("flexi_benefit_id", train.getFlexi_benefit_id());
                            params.put("flex_amount", "0");
                            params.put("final_amount",String.valueOf(selectedRadioButtonIDga));
                            params.put("sum_insured", String.valueOf(selectedRadioButtonID));
                            params.put("deduction_type", dtype_vol);
                            params.put("pay_amount", String.valueOf(selectedRadioButtonIDga));

                            smr.setParams(params);
                            rq.add(smr);

                        }








                    }else {
                        new AlertDialog.Builder(context)
                                .setTitle("Error?")
                                .setMessage("Please Check Your Internet Connection")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }

                } else {


                    new AlertDialog.Builder(context)
                            .setTitle("Error !")
                            .setMessage("Select Atleast One Amount")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
            }
        });


        viewHolder.vol_btn_cancel_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isNetworkAvailable()) {

                    viewHolder.vol_btn_cancel.setVisibility(VISIBLE);
                  //  RequestQueue rq = Volley.newRequestQueue(context);
                   RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
rq.getCache().clear();
                    String url = con.base_url+"/api/employee/reset/flex";
                    StringRequest smr = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {


                              JSONObject jsonObject = new JSONObject(response);
                            String errorCode = jsonObject.getString("message");
                                        String status = String.valueOf(jsonObject.getBoolean("status"));

                                        if(status.equalsIgnoreCase("true")){
                                            GetAllFlex();
                                            viewHolder.vol_rg.clearCheck();
                                            viewHolder.vol_rg_rb.clearCheck();
                                            viewHolder.vol_pre.setText("0");
                                            // vol_pre.getText().toString();

                                            new AlertDialog.Builder(context)
                                                    .setTitle("Success")
                                                    .setMessage(errorCode)
                                                    .setIcon(R.drawable.checkmark)
                                                    .setNegativeButton(android.R.string.ok, null).show();
                                        }else {
                                            new AlertDialog.Builder(context)
                                                    .setTitle("Error !")
                                                    .setMessage(errorCode)
                                                    .setIcon(android.R.drawable.btn_dialog)
                                                    .setNegativeButton(android.R.string.ok, null).show();
                                        }

                                    } catch (Exception e) {


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
                            headers.put("Authorization", "Bearer " + emp_id);
                            return headers;
                        }
                    };








                    HashMap<String, String> params = new HashMap<>();
                    params.put("policy_id", train.getPolicy_id());
                    params.put("flexi_benefit_id", train.getFlexi_benefit_id());

                    smr.setParams(params);
                    rq.add(smr);


                }else {
                    new AlertDialog.Builder(context)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }






            }
        });





        List<VoluntaryBenefit> finalGroupCoverMemberList = new ArrayList<>();



Log.d("policyid",policy_id);

        String urls = con.base_url+"/api/employee/get/enroll/members?policy_id="+train.getPolicy_id();
          //RequestQueue rq = Volley.newRequestQueue(context);

     RequestQueue rq = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequests = new StringRequest(Request.Method.GET, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));





                    JSONArray jsonObj =data.getJSONArray("data");









                    for (int k = 0;k < jsonObj.length(); k++) {

                        JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                        int suminsured = jo_areag.getInt("suminsured");
                        double employee_premium= jo_areag.getDouble("employee_premium");

                        Log.d("suminsured", String.valueOf(allsum));
                        Log.d("premium", String.valueOf(employee_premium));


                        allsum=suminsured+allsum;
                        allPremium=employee_premium+allPremium;



                    }

                    Log.d("suminsured", String.valueOf(allsum));
                    Log.d("premium", String.valueOf(allPremium));


                    String allsumdata=String.valueOf(allsum);
                    String allPremiumdata= String.valueOf(allPremium);







                    for (int i=0;i<jsonObj.length();i++){

                        JSONObject groupCoverMemberList = (JSONObject) jsonObj.get(i);

                        String id = (groupCoverMemberList.getString("id"));
                        String marriage_date = (groupCoverMemberList.getString("marriage_date"));

                        String relation_name = (groupCoverMemberList.getString("relation_name"));
                        String relation_id = (groupCoverMemberList.getString("relation_id"));


                        String first_name = (groupCoverMemberList.getString("first_name"));
                        String last_name = (groupCoverMemberList.getString("last_name"));
                        String suminsured = String.valueOf((groupCoverMemberList.getString("suminsured")));
                        String dob = (groupCoverMemberList.getString("dob"));
                        String gender = (groupCoverMemberList.getString("gender"));
                        String member_email = groupCoverMemberList.getString("email");
                        String member_mob_no = groupCoverMemberList.getString("mobile_no");
                        String employee_premium = groupCoverMemberList.getString("employee_premium");
                        String age = groupCoverMemberList.getString("age");
                        String age_type = groupCoverMemberList.getString("age_type");


                        finalGroupCoverMemberList.add(new VoluntaryBenefit(first_name,first_name,last_name,suminsured,
                                dob,gender,member_email,member_mob_no,employee_premium,allsumdata,
                                allPremiumdata,age,age_type,id,marriage_date,relation_name,relation_id));






                    }

                    GroupCoverMemberAdapterTopup groupCoverMemberAdapter = new GroupCoverMemberAdapterTopup(context, finalGroupCoverMemberList);

                    LinearLayoutManager layoutManagerGroupCoverMember = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                    viewHolder.additionalInfo.setLayoutManager(layoutManagerGroupCoverMember);

                    viewHolder.additionalInfo.setAdapter(groupCoverMemberAdapter);

                } catch (Exception e) {
                    Log.e("myerror", e.toString());
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

                headers.put("Authorization", "Bearer " + emp_id);
                return headers;
            }
        };




        rq.add(mStringRequests);













    }




    public void GetAllFlex() {
//        String url = /*con.base_url + "/flexi_benifit/get_utilised_data"*/"http://eb.benefitz.in/flexi_details";
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        emp_id = prefs.getString("api_token", null);


        String url = con.base_url+"/api/employee/get/balance/utilization";
       // RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONObject  jsonObj=js.getJSONObject("data");


                    //Premium



                    Flex_Wallet = String.valueOf(jsonObj.getInt("remaining_flex_bal"));
                    walletFlex = jsonObj.getInt("remaining_flex_bal");
                    toPay = String.valueOf(jsonObj.getInt("to_pay"));
                    wallet_utilization = String.valueOf(jsonObj.getInt("flex_utilized_amt"));




                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + emp_id);
                return headers;
            }
        };
        mRequestQueue.add(mStringRequest);
    }

    @Override
    public int getItemCount() {
        return mTrain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView name,intro,vol_pre,to_pay,old_sum,covertype;
        LinearLayout pay;

        RadioGroup vol_rg,vol_rg_rb,premium_installment;
        Button vol_btn_cancel, vol_btn_cancel_reset;
        RadioButton vol_wallet,vol_payroll;
        RecyclerView additionalInfo;
        LinearLayout hideall,sum_lin,covers,pre_lin;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            covertype= itemView.findViewById(R.id.covertype);
            old_sum= itemView.findViewById(R.id.old_sum);
            additionalInfo = itemView.findViewById(R.id.groupCoverAdditionalInfo);
            name = (TextView) itemView.findViewById(R.id.name);
            vol_pre = (TextView) itemView.findViewById(R.id.vol_pre);
            intro = (TextView) itemView.findViewById(R.id.intro);
            covers= itemView.findViewById(R.id.covers);
            pre_lin= itemView.findViewById(R.id.pre_lin);
            vol_rg= (RadioGroup) itemView.findViewById(R.id.vol_rg);
            vol_rg_rb = (RadioGroup) itemView.findViewById(R.id.vol_rg_rb);
            premium_installment = (RadioGroup) itemView.findViewById(R.id.premium_installment);
            vol_btn_cancel = (Button) itemView.findViewById(R.id.vol_btn_cancel);
            vol_btn_cancel_reset = (Button) itemView.findViewById(R.id.vol_btn_cancel_reset);
            vol_wallet = (RadioButton) itemView.findViewById(R.id.vol_wallet);
            vol_payroll = (RadioButton) itemView.findViewById(R.id.vol_payroll);
            to_pay=(TextView) itemView.findViewById(R.id.to_pay);
            pay=(LinearLayout) itemView.findViewById(R.id.pay);
            hideall=(LinearLayout) itemView.findViewById(R.id.hideall);
            sum_lin=(LinearLayout) itemView.findViewById(R.id.sum_lin);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
