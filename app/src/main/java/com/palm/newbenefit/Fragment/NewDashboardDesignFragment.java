package com.palm.newbenefit.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.MyDashboardAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.MyDashboard;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static android.view.View.VISIBLE;


public class NewDashboardDesignFragment extends Fragment {
    String user_id;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;



    List<MyDashboard> ob = null;
    MyDashboardAdapter adapter = null;
    RecyclerView recyclerView;
    ImageView info_text;

    public NewDashboardDesignFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    String employee_id;


    SharedPreferences prefs;



    TextView title,content;
    RelativeLayout announcement;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_new_dashboard_design, container, false);
        con = new Constants();

        con=new Constants();
        prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        title=v.findViewById(R.id.title);
        content=v.findViewById(R.id.content);
        announcement=v.findViewById(R.id.announcement);

        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        recyclerView = v.findViewById(R.id.ghi_recycle);
        info_text= v.findViewById(R.id.info_text);


        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle("");



        if (isNetworkAvailable()) {

            ob = new ArrayList<>();
            adapter = new MyDashboardAdapter(getActivity(), ob);
            recyclerView.setAdapter(adapter);



            GetEmployeeId();
Announcement();




            //Data();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }


        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                MyDashboard train = ob.get(position);

                if(train.getRedirect_url().equalsIgnoreCase("/employee/enrollment")){

                    MyEnrollMentCards travel = new MyEnrollMentCards();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                 else if(train.getRedirect_url().equalsIgnoreCase("/employee/flex-benefit")){

                    InsuranceFlexbenifitFragment travel = new InsuranceFlexbenifitFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();


                }
                else  if(train.getRedirect_url().equalsIgnoreCase("/employee/polices")){

                    PolicyTabFragment travel = new PolicyTabFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else if(train.getRedirect_url().equalsIgnoreCase("/employee/submit-claim")){

                    SubmitClaimFragment travel = new SubmitClaimFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else if(train.getRedirect_url().equalsIgnoreCase("/employee/track-claim")){

                    MyTrackClaimFragment travel = new MyTrackClaimFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else if(train.getRedirect_url().equalsIgnoreCase("/employee/intimate-claim")){

                    IntimateClaimFragment travel = new IntimateClaimFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }

                else if(train.getRedirect_url().equalsIgnoreCase("/employee/contact-us")){

                    String url = con.base_url+"/api/employee/get/contact-logs";
                    RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
                    //RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this, new HurlStack(null, getSocketFactory()));
                    mRequestQueue.getCache().clear();
                    StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            try {
                                JSONObject  js = new JSONObject(response);

                                Log.d("response",response);


                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                                JSONArray data =js.getJSONArray("data");

                                JSONObject  one=data.getJSONObject(0);
                                JSONArray dataone =one.getJSONArray("data");
                                JSONObject  two=data.getJSONObject(1);
                                JSONArray datatwo =two.getJSONArray("data");
                                JSONObject  three=data.getJSONObject(2);
                                JSONArray datathree =three.getJSONArray("data");
                                JSONObject  four=data.getJSONObject(3);
                                JSONArray datafour =four.getJSONArray("data");

                                if((dataone.length()==0) && (datatwo.length()==0) &&
                                        (datathree.length()==0) &&  (datafour.length()==0)){


                                    ContactUsMainFinalFragment ContactUsFragmentNew = new ContactUsMainFinalFragment();
                                    transaction.replace(R.id.relativelayout_for_fragement, ContactUsFragmentNew);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }else {
                                    ContactLogFragment ContactUsFragmentNew = new ContactLogFragment();
                                    transaction.replace(R.id.relativelayout_for_fragement, ContactUsFragmentNew);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }





                            }  catch (JSONException ex) {
                                ex.printStackTrace();
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

                else if(train.getRedirect_url().equalsIgnoreCase("/employee/my-wellness")){

                    WellnessFragmentSecond travel = new WellnessFragmentSecond();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }


                else  if(train.getRedirect_url().equalsIgnoreCase("/employee/my-policy")){

                    PolicyTabFragment travel = new PolicyTabFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }


                else  if(train.getRedirect_url().equalsIgnoreCase("/employee/flex-benefits")){

                    SubmitClaimFragment travel = new SubmitClaimFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else  if(train.getRedirect_url().equalsIgnoreCase("/employee/wellness")){

                    SubmitClaimFragment travel = new SubmitClaimFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }


                else if(train.getRedirect_url().equalsIgnoreCase("/employee/portal-claim")){

                    MyClaimsFragment travel = new MyClaimsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                if(train.getRedirect_url().equalsIgnoreCase("/employee/overall-claim")){

                    OverAllClaimFragment travel = new OverAllClaimFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else if(train.getRedirect_url().equalsIgnoreCase("/employee/claims")){

                    SubmitClaimFragment travel = new SubmitClaimFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else  if(train.getRedirect_url().equalsIgnoreCase("/employee/help")){

                    HelpFragment travel = new HelpFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else  if(train.getRedirect_url().equalsIgnoreCase("/employee/home")){

                    DashboardBenifitFragment travel = new DashboardBenifitFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }

                else  if(train.getRedirect_url().equalsIgnoreCase("/employee/dashboard")){

                    DashboardBenifitFragment travel = new DashboardBenifitFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else  if(train.getRedirect_url().equalsIgnoreCase("/employee/form-center")){

                    FormcenterFourthFragment travel = new FormcenterFourthFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
                else if(train.getRedirect_url().equalsIgnoreCase("/employee/network-hospital")){

                    NetworkHospitalJava travel = new NetworkHospitalJava();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                            travel, travel.getTag()).addToBackStack("back").commit();

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);





        return  v;
    }




    private void setBankDet() {


        String url = con.base_url+ "/api/broker/get/dashboard-card-mapping?employer_id="+employee_id;
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{


                       Log.d("wellness",response);



                            JSONObject resp= new JSONObject(response);

                            String statusa= String.valueOf(resp.getBoolean("status"));



                            if (statusa.equalsIgnoreCase("false")) {
                                info_text.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                info_text.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                JSONArray jsonObj =resp.getJSONArray("data");


                                for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);


                                    String card_id = jo_area.getString("card_id");

                                    JSONObject card_details=jo_area.getJSONObject("card_details");
                                    JSONObject theme_json=card_details.getJSONObject("theme_json");



                                    String heading = card_details.getString("heading");
                                    String sub_heading = (card_details.getString("sub_heading"));
                                    String description = (card_details.getString("description"));
                                    String cardBackground = (theme_json.getString("cardBackground"));
                                    String cardColor = theme_json.getString("cardColor");
                                    String textColor = theme_json.getString("textColor");
                                    String redirect_url = (card_details.getString("redirect_url"));
                                    String has_icon = (card_details.getString("has_icon"));
                                    String image = (card_details.getString("image"));
                                    String show_card = (jo_area.getString("show_card"));





if(show_card.equalsIgnoreCase("1")){
    ob.add(new MyDashboard( heading,  sub_heading,
            description,  cardBackground,
            cardColor,  textColor,  redirect_url,
            image,  has_icon,  show_card));
}




                                }





                                adapter.notifyDataSetChanged();


                            }


                        } catch (Exception e) {
                            Log.e("onErrorResponse", e.toString());
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
        params.put("employer_id", employee_id);

        rq.add(mStringRequest);


    }

    private void Announcement() {




        String url = con.base_url+"/api/admin/get/annoucement/module-wise?user_type_name=Employee";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();

        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONObject data= new JSONObject(response);



                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {

                        announcement.setVisibility(View.GONE);
                    } else {
                        JSONArray array = data.getJSONArray("data");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject explrObject = array.getJSONObject(i);

                            JSONArray module = explrObject.getJSONArray("module");

                            for (int j = 0; j < module.length(); j++) {
                                JSONObject explrObjects = module.getJSONObject(j);

                                String modulname=explrObjects.getString("module_name");
                                if(modulname.equalsIgnoreCase("Home")){
                                    String titles=explrObject.getString("title");
                                    String contents=explrObject.getString("content");
                                    title.setText(titles);
                                    content.setText(contents);
                                    announcement.setVisibility(VISIBLE);
                                }
                            }


                        }


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
        mRequestQueue.add(mStringRequest);
    }


    void GetEmployeeId(){
        String url = con.base_url+"/api/admin/user";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);





                        String employer_id = explrObject.getString("employer_id");





                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id=employer_id;

                            setBankDet();

                        }





                    }



                } catch (JSONException e) {
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
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
        rq.add(mStringRequest);

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