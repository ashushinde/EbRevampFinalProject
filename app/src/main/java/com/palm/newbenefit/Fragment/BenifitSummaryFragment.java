package com.palm.newbenefit.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Adapter.NoteAdapter;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterGroupMyTermSumm;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.Note;
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


public class BenifitSummaryFragment extends Fragment {

    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String user_id = null;
    List<String> policy_no = null;

    List<Note> obnote = null;
    NoteAdapter noteadapter = null;




    List<VoluntaryBenefit> policyob_term = null;

    myvoluntaryAdapterGroupMyTermSumm policySummaryAdapter_term = null;





    String nominee_requirement;

    String status = "1";
    TextView flex_wallet,flex_utilisation,to_pay;


    TextView payto;
    int datas=0,dataa=0;
    String token,policy_id;
    ImageView info_text;
    private boolean doInOnAttach = false;
    TextView month_hide;
    RecyclerView policy_cycle,policy_cycle_term,policy_cycle_Medi,policy_cycle_Group,policy_cycle_Personal
            ,policy_cycle_top,policy_cycle_vol_med,policy_cycle_group_health;
    TextView note;
    RecyclerView contentrecyle;
    TextView submit;
    CheckBox check;
    public BenifitSummaryFragment() {
        // Required empty public constructor
    }


    TextView premium;
    TextView month;
    LinearLayout status_prem;
            TextView gst;
    LinearLayout summary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_benifit_summary, container, false);
        con = new Constants();
        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        policy_id = /*prefs.getString("policy_id", null);*/"6495";

        summary= v.findViewById(R.id.summary);



        policy_cycle_term= v.findViewById(R.id.policy_cycle_term);

        gst=v.findViewById(R.id.gst);

        premium= v.findViewById(R.id.premium);
        month= v.findViewById(R.id.month);
        month_hide= v.findViewById(R.id.month_hide);
        contentrecyle= v.findViewById(R.id.contentrecyle);
        submit= v.findViewById(R.id.submitAll);
        check= v.findViewById(R.id.check);
        int numberOfColumns = 1;
















        int numberOfColumnsn = 1;
        contentrecyle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsn));

        contentrecyle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), contentrecyle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managern = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        contentrecyle.setLayoutManager(managern);











        policy_cycle_term.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsn));

        policy_cycle_term.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_term, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager policy_cycle_termterm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_term.setLayoutManager(policy_cycle_termterm);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status.equalsIgnoreCase("0")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Do You Want To Proceed?");
                    alertDialog.setMessage("If You Click Yes, The Data Submitted By You Will Be Considered Final");
                    alertDialog.setNegativeButton(R.string.no, null);
                    alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                            if (isNetworkAvailable()) {

                                if(nominee_requirement.equalsIgnoreCase("1")){
                                    if(datas==100)
                                    {
                                        Data();
                                    }else {
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Error?")
                                                .setMessage("Total share for nominee should be 100")
                                                .setIcon(android.R.drawable.btn_dialog)
                                                .setNegativeButton(android.R.string.ok, null).show();
                                    }
                                }else {
                                    Data();
                                }


                            }else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error?")
                                        .setMessage("Please Check Your Internet Connection")
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();
                            }











                        }
                    });

                    alertDialog.show();


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Agree Above Information")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }

            }
        });




        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    status = "0";
                }
                else{
                    status="1";
                }






            }
        });

getAllVol();

        return v;

    }

    private void getAllVol() {



        String url = con.base_url+"/api/employee/get/dashboard";
        //RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

         RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);


                       double allPremium=0.0;

                       String  status= String.valueOf(js.getBoolean("status"));


                        JSONArray  jsonObj=js.getJSONArray("data");




                            for (int k = 0; k < jsonObj.length(); k++) {
                                JSONObject jo_aread = (JSONObject) jsonObj.get(k);
                                int po_idd = jo_aread.getInt("premium");

                                allPremium= (po_idd+allPremium);

                            }






                        try{
                            double data= allPremium;

                            String cover_data = NumberFormat.getNumberInstance(Locale.US).format(data);

                            premium.setText(cover_data);
                        }catch (Exception e){
                            premium.setText(String.valueOf(allPremium));
                        }



                        if(premium.getText().toString().trim().equalsIgnoreCase("0")){
                            status_prem.setVisibility(View.GONE);
                            gst.setVisibility(View.GONE);
                        }else {
                            status_prem.setVisibility(View.VISIBLE);
                            gst.setVisibility(View.VISIBLE);
                        }







                } catch (Exception e) {
                    e.printStackTrace();
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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        mRequestQueue.add(mStringRequest);

    }


    public void Data() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.getCache().clear();
        String url =con.base_url+ "/api/employee/get/enroll/confirmation";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {





                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("response",response);



                            String datato= String.valueOf(jsonObject.getBoolean("status"));
                            String message=jsonObject.getString("message");

                            if(datato.equalsIgnoreCase("true")){
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();
                                submit.setVisibility(View.GONE);
                            }else {

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };




        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", policy_id);
        params.put("confirmation_flag", "1");

        smr.setParams(params);
        rq.add(smr);

        //Application.getInstance().addToRequestQueue(smr);

    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        context = getActivity();
        if (isVisibleToUser && (getActivity() != null)) {



            if (isNetworkAvailable()) {






                policyob_term= new ArrayList<>();
                policySummaryAdapter_term = new myvoluntaryAdapterGroupMyTermSumm(getActivity(), policyob_term);
                policy_cycle_term.setAdapter(policySummaryAdapter_term);


                obnote= new ArrayList<>();
                noteadapter = new NoteAdapter(getActivity(), obnote);
                contentrecyle.setAdapter(noteadapter);



                getAllVol();
                setBankDetTerm();
                nominee();
                NoteData();
                NominneConfig();


            }else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }












            //  getActivity();



        }


    }


    private void setBankDetTerm() {


        policyob_term= new ArrayList<>();
        policy_no= new ArrayList<>();
        policySummaryAdapter_term = new myvoluntaryAdapterGroupMyTermSumm(getActivity(), policyob_term);
        policy_cycle_term.setAdapter(policySummaryAdapter_term);


        String url = con.base_url+"/api/employee/get/all-policy-member-app";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                int allsum = 0;
                        Double allPremium=0.0;
                        try {

                            JSONObject data= new JSONObject(response);

                            Log.e("group_response", data.toString());

                            String statusa= String.valueOf(data.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                                policy_cycle_term.setVisibility(View.GONE);

                            } else {
                                policy_cycle_term.setVisibility(View.VISIBLE);


                                JSONArray jsonObja =data.getJSONArray("data");







                       /* for (int k = 0;k < jsonObja.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObja.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;

                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);*/




                                for (int k = 0;k < jsonObja.length(); k++) {

                                    JSONObject jo_areaf = (JSONObject) jsonObja.get(k);

                                    String name = (jo_areaf.getString("policy_name"));
                                    String first_name = (jo_areaf.getString("first_name"));
                                    String last_name = (jo_areaf.getString("last_name"));
                                    // String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                                    String dob = (jo_areaf.getString("dob"));
                                    String gender = (jo_areaf.getString("gender"));
                                    String member_email = jo_areaf.getString("member_email");
                                    String member_mob_no = jo_areaf.getString("member_mob_no");
                                    String suminsurued_type_id = jo_areaf.getString("suminsurued_type_id");
                                    String policy_id = jo_areaf.getString("policy_id");
                                    String policy_number = jo_areaf.getString("policy_number");
                                    int suminsured = jo_areaf.getInt("suminsured");
                                    double employee_premium= jo_areaf.getDouble("employee_premium");

                                    String opd_suminsured = jo_areaf.getString("opd_suminsured");

                                    String installment = jo_areaf.getString("installment");




                                    if(installment.equalsIgnoreCase("null")){
                                        month_hide.setVisibility(View.GONE);
                                        month.setVisibility(View.GONE);
                                    }else {
                                        month_hide.setVisibility(View.VISIBLE);
                                        month.setVisibility(View.VISIBLE);
                                    }

                                    month.setText(installment);



                         /*   Log.d("12345","----");
                            Log.d("12345","policy_number "+policy_number);
                            Log.d("12345","suminsured "+suminsured);
                            Log.d("12345","allPremium "+allPremium);*/
                                    if(!policy_no.contains(policy_number)){
                                        policy_no.add(policy_number);
                                        policyob_term.add(new VoluntaryBenefit(name, first_name, last_name,policy_number,
                                                dob, gender, member_email,
                                                opd_suminsured, policy_id, suminsurued_type_id));

                                    }/*else{
                                VoluntaryBenefit voluntaryBenefit = policyob_term.get(policyob_term.size()-1);
                                int newSum = Integer.parseInt(voluntaryBenefit.getAllsum()) + suminsured;
                                voluntaryBenefit.setAllsum(newSum+"");
                                policyob_term.remove(policyob_term.size()-1);
                                policyob_term.add(voluntaryBenefit);

                            }*/






                                }

                                policySummaryAdapter_term.notifyDataSetChanged();

                            }
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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        rq.add(mStringRequest);
    }












    void NominneConfig(){

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.getCache().clear();

        //  RequestQueue rq = Volley.newRequestQueue(getActivity());

        String url = "https://eb.fynity.in/api/broker/get/nominee-config?configurable_type=policy&configurable_id="+policy_id;
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("response_dependent",response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array =jsonObject.getJSONArray("data");

                            JSONObject object = array.getJSONObject(0);

                            nominee_requirement =object.getString("nominee_requirement");







                        } catch (Exception e) {

                            e.printStackTrace();
                            Log.d("nominee_config", e.toString());
                            nominee_requirement="1";

                        }

                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
                nominee_requirement="1";

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };





        HashMap<String, String> params = new HashMap<>();

        params.put("configurable_type", "policy");
        params.put("configurable_id", policy_id);
        smr.setParams(params);
        rq.add(smr);
    }



    private void NoteData() {


        obnote= new ArrayList<>();
        noteadapter = new NoteAdapter(getActivity(), obnote);
        contentrecyle.setAdapter(noteadapter);

        String url = con.base_url+"/api/employee/get/confirmation";

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);







                    JSONArray jsonObj=js.getJSONArray("data");

                    if (jsonObj.length() == 0){


                        contentrecyle.setVisibility(View.GONE);
                    }else {

                        contentrecyle.setVisibility(View.VISIBLE);



                        for (int j = 0; j < jsonObj.length(); j++) {



                            JSONObject jo_arear = (JSONObject) jsonObj.get(j);


                            String notea = jo_arear.getString("note");


                            JSONArray jo_area = jo_arear.getJSONArray("content");
                            List<String> sellItems = Arrays.asList(jo_area.toString().split(","));




                            for (int h = 0; h < sellItems.size(); h++) {
                                obnote.add(new Note(jo_area.getString(h)));


                            }


                            noteadapter = new NoteAdapter(getActivity(), obnote);
                            contentrecyle.setAdapter(noteadapter);
                            Log.d("notelist",obnote.toString());
                            noteadapter.notifyDataSetChanged();





                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };


        HashMap<String, String> params = new HashMap<>();
        params.put("policy_sub_type_id","1");

        mStringRequest.setParams(params);


        mRequestQueue.add(mStringRequest);

    }























    private void nominee() {



        String url = con.base_url+"/api/employee/get/nominee";

         RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject data= new JSONObject(response);

                    Log.d("nominee",response);

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {

                    } else {

                        JSONArray jsonObj =data.getJSONArray("data");
                        ArrayList<Integer> arrlist = new ArrayList<Integer>();

                        for (int j = 0; j < jsonObj.length(); j++) {

                            JSONObject jo_area = (JSONObject) jsonObj.get(j);


                            String fr_name = jo_area.getString("nominee_relation");
                            String nominee_id = jo_area.getString("id");
                            String emp_id = jo_area.getString("emp_id");
                            String nominee_fname = (jo_area.getString("nominee_fname"));
                            String nominee_lname = (jo_area.getString("nominee_lname"));
                            String fr_id = (jo_area.getString("nominee_relation"));
                            String guardian_relation = (jo_area.getString("guardian_relation"));
                            String guardian_fname = (jo_area.getString("guardian_fname"));
                            String guardian_lname = jo_area.getString("guardian_lname");
                            String guardian_dob = jo_area.getString("guardian_dob");
                            String share_percentile = jo_area.getString("share_per");
                            String nominee_dob = (jo_area.getString("nominee_dob"));
                            String confirmed_flag = "";
                            String confirmed_date = "";
                            String status = (jo_area.getString("is_confirm"));
                            String created_at = "";
                            datas += Integer.valueOf(share_percentile);

                        }

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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };



        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", policy_id);

        mStringRequest.setParams(params);
        rq.add(mStringRequest);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {


            return;
        }


        if (isNetworkAvailable()) {

            getAllVol();

            setBankDetTerm();


            nominee();


            NoteData();
            NominneConfig();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }









    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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

            SSLContext context = SSLContext.getInstance("TLSv1.2");
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
