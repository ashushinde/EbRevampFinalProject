package com.palm.newbenefit.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.palm.newbenefit.Adapter.AnnouncementAdapter;
import com.palm.newbenefit.Adapter.HomeGroupAdapter;
import com.palm.newbenefit.Adapter.myNotifyAdapter;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterBenefit;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterGroup;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterGroupMyTerm;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterMediclaim;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterPersonal;
import com.palm.newbenefit.Adapter.myvoluntaryAdapterTerm;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;
import com.palm.newbenefit.Module.Group;
import com.palm.newbenefit.Module.Image;
import com.palm.newbenefit.Module.Notification;
import com.palm.newbenefit.Module.VoluntaryBenefit;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.squareup.picasso.Picasso;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class CoverDataFragment extends Fragment {
    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    List<Group> ob = null;
    HomeGroupAdapter adapter = null;
    myNotifyAdapter adaptere = null;

    List<Notification> notify = null;
    List<Image> announceob = null;
    AnnouncementAdapter adapterannounce = null;


    List<Group> oba = null;
    HomeGroupAdapter adaptera = null;

    RecyclerView recyclerView,voluntary;
    int amountrs = 500;
    String emp_id;

    String company_id;
    ImageView info_text,info_text_vol;
    Button group_cover,voluntary_cover;
    private RequestQueue requestQueue;
    TextView emp_name;
    TextView title,content,view_more;
    RecyclerView announce;
    LinearLayout noannounce;
    RelativeLayout notificationBadge;
    TextView tvBadgeNumber;
ImageView img,mylogo;
LinearLayout vouln;

LinearLayout summary;


    List<VoluntaryBenefit> policyob = null;
    myvoluntaryAdapterBenefit policySummaryAdapter = null;


    List<VoluntaryBenefit> policyob_term = null;
    myvoluntaryAdapterGroupMyTerm policySummaryAdapter_term = null;


    List<VoluntaryBenefit> policyob_Group = null;
    myvoluntaryAdapterGroup policySummaryAdapter_Group = null;

    List<VoluntaryBenefit> policyob_Personal = null;
    myvoluntaryAdapterPersonal policySummaryAdapter_Personal = null;



    List<VoluntaryBenefit> policyob_Medi = null;
    myvoluntaryAdapterMediclaim policySummaryAdapter_Medi = null;


    List<VoluntaryBenefit> policyob_top = null;
    myvoluntaryAdapterTerm policySummaryAdapter_top = null;

    public CoverDataFragment() {
        // Required empty public constructor
    }


    RecyclerView policy_cycle,policy_cycle_term,policy_cycle_Medi,policy_cycle_Group,policy_cycle_Personal
            ,policy_cycle_top;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cover_data, container, false);



      ((MainActivity) getActivity()).setTitle("Dashboard");
      con = new Constants();

       SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        recyclerView = v.findViewById(R.id.ghi_recycle);
        info_text= v.findViewById(R.id.info_text);
        emp_name= v.findViewById(R.id.emp_name);
        voluntary= v.findViewById(R.id.voluntary);
        announce= v.findViewById(R.id.announce);
        info_text_vol= v.findViewById(R.id.info_text_vol);
        view_more= v.findViewById(R.id.view_more);
        noannounce= v.findViewById(R.id.noannounce);
        mylogo= v.findViewById(R.id.mylogo);
        notificationBadge= v.findViewById(R.id.notificationBadge);
        vouln= v.findViewById(R.id.vouln);
        img= v.findViewById(R.id.img);
        summary= v.findViewById(R.id.summary);


        policy_cycle= v.findViewById(R.id.policy_cycle);
        policy_cycle_term= v.findViewById(R.id.policy_cycle_term);
        policy_cycle_Medi= v.findViewById(R.id.policy_cycle_Medi);

        policy_cycle_Group= v.findViewById(R.id.policy_cycle_Group);
        policy_cycle_Personal= v.findViewById(R.id.policy_cycle_Personal);
        policy_cycle_top= v.findViewById(R.id.policy_cycle_top);


//        GradientDrawable bgShape = (GradientDrawable)img.getBackground();
//        bgShape.setColor(Color.BLACK);



//
//        Drawable myIcon = getResources().getDrawable( R.drawable.home_back );
//        ColorFilter filter = new LightingColorFilter( Color.BLACK, Color.BLACK);
//        myIcon.setColorFilter(filter);


        EmployeeDetail();
       // Notification();
    //  Announcement();
        tvBadgeNumber= v.findViewById(R.id.tvBadgeNumber);
        tvBadgeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parental_dailog();

            }
        });

        notificationBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parental_dailog();

            }
        });



//        group_cover.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                group_cover.setBackgroundResource(R.drawable.nav_back_tab);
//                voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
//                voluntary_cover.setTextColor(Color.BLACK);
//                group_cover.setTextColor(Color.WHITE);
//
//                ob = new ArrayList<>();
//                adapter = new HomeGroupAdapter(getActivity(), ob);
//                recyclerView.setAdapter(adapter);
//                // demo();
//                setBankDet("group");
//            }
//        });
//
//
//        voluntary_cover.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                voluntary_cover.setBackgroundResource(R.drawable.nav_back_tab);
//
//                group_cover.setBackgroundResource(R.drawable.tab_curve);
//                group_cover.setTextColor(Color.BLACK);
//                voluntary_cover.setTextColor(Color.WHITE);
//
//                ob = new ArrayList<>();
//                adapter = new HomeGroupAdapter(getActivity(), ob);
//                recyclerView.setAdapter(adapter);
//                // demo();
//                setBankDet("voluntary");
//
//
//            }
//        });


        if (isNetworkAvailable()) {

            ob = new ArrayList<>();
            adapter = new HomeGroupAdapter(getActivity(), ob);
            recyclerView.setAdapter(adapter);
            // demo();
            setBankDet("group");



            announceob = new ArrayList<>();
            adapterannounce = new AnnouncementAdapter(getActivity(), announceob);
            announce.setAdapter(adapterannounce);
            // demo();
            Announcement();



            oba = new ArrayList<>();
            adaptera = new HomeGroupAdapter(getActivity(), oba);
            voluntary.setAdapter(adaptera);


            setBankDetVoluntary("voluntary");





            getAllVol();



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


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);









        int numberOfColumnsv = 1;
        voluntary.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsv));

        voluntary.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), voluntary, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerv = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        voluntary.setLayoutManager(managerv);



        int numberOfColumnsa = 1;
        announce.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsa));

        announce.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), announce, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        GridLayoutManager managera = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        announce.setLayoutManager(managera);








        policy_cycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        policy_cycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerd = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle.setLayoutManager(managerd);




        policy_cycle_top.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        policy_cycle_top.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_top, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager policy_cycle_topmanager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_top.setLayoutManager(policy_cycle_topmanager);






        policy_cycle_Medi.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        policy_cycle_Medi.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_Medi, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managerdd = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_Medi.setLayoutManager(managerdd);



        policy_cycle_Group.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        policy_cycle_Group.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_Group, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager managefrdd = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_Group.setLayoutManager(managefrdd);



        policy_cycle_Personal.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        policy_cycle_Personal.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), policy_cycle_Personal, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager fhg = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        policy_cycle_Personal.setLayoutManager(fhg);












        policy_cycle_term.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

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






        return v;

    }

    void parental_dailog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notification_list);

        ImageView s_logo = (ImageView) dialog.findViewById(R.id.s_logo);
        RecyclerView ghi_recycle = (RecyclerView) dialog.findViewById(R.id.ghi_recycle);


        adaptere.notifyDataSetChanged();
        adaptere = new myNotifyAdapter(getActivity(), notify);
        ghi_recycle.setAdapter(adaptere);

        s_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.dismiss();

            }
        });

        int numberOfColumns = 1;
        ghi_recycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        ghi_recycle.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), ghi_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        ghi_recycle.setLayoutManager(manager);


        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    private void setBankDet(String fg) {

        String url = con.base_url+"/api/employee/get/dashboard";

         RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(),
                 new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);




                   String  status= String.valueOf(js.getBoolean("status"));

                   if(status.equalsIgnoreCase("false")){
                       info_text.setVisibility(View.VISIBLE);
                       recyclerView.setVisibility(View.GONE);
                   }else {
                       JSONArray  jsonObj=js.getJSONArray("data");

                       if (jsonObj.length() == 0){
                           info_text.setVisibility(View.VISIBLE);
                           recyclerView.setVisibility(View.GONE);
                       }else {
                           info_text.setVisibility(View.GONE);
                           recyclerView.setVisibility(View.VISIBLE);




                           for (int j = 0; j < jsonObj.length(); j++) {

                               JSONObject jo_area = (JSONObject) jsonObj.get(j);
                               String po_id = String.valueOf(jo_area.getInt("policy_id"));
                               String id = String.valueOf(jo_area.getString("premium"));
                               String pol_id = jo_area.getString("policy_number");
                               String in_co_name = jo_area.getString("insurer_name");
                               String pol_mem_insured = String.valueOf((jo_area.getInt("suminsured")));
                               String cover_balance = String.valueOf((jo_area.getInt("suminsured")));
                               //String pol_mem_pre = (jo_area.getString("policy_mem_sum_premium"));
                               String pol_sub_type_name = (jo_area.getString("policy_name"));
                               String policy_sub_type_image_path = (jo_area.getString("insurer_logo"));
                               String insurer_companies_img_path = (jo_area.getString("insurer_logo"));
                               String policy_sub_type_id = (jo_area.getString("policy_sub_type_id"));
                               int member_count = (jo_area.getInt("total_member"));

//                                String opd = (jo_area.getString("Opd_sum_insure"));
//                                // String opd = "5000";
//
//                                String member = String.valueOf(member_count);
                               String cover_balancea = String.valueOf((jo_area.getInt("cover_balance")));
                               String opd_suminsured = String.valueOf((jo_area.getInt("opd_suminsured")));


                               String members = (jo_area.getString("members"));


                               members= members.replace("[","");
                               members=members.replace("]","");
                               members=members.replace("\"","");






                               if(fg.equalsIgnoreCase("Group")){

                                   if(String.valueOf(jo_area.getString("premium")).equalsIgnoreCase("0")){
                                       ob.add(new Group(po_id,id, pol_id, in_co_name, pol_mem_insured, "", pol_sub_type_name, policy_sub_type_image_path, insurer_companies_img_path, policy_sub_type_id, members, cover_balance, amountrs, "",cover_balancea,opd_suminsured));

                                   }else {

                                   }
                               }else {
//                                   if(pol_sub_type_name.contains("Group")){
//
//                                   }else {
//                                       ob.add(new Group(id, pol_id, in_co_name, pol_mem_insured, "", pol_sub_type_name, policy_sub_type_image_path, insurer_companies_img_path, "", members, cover_balance, amountrs, "",cover_balancea,opd_suminsured));
//
//                                   }

                               }



                           }

                           if(ob.isEmpty()){
                               info_text.setVisibility(View.VISIBLE);
                               recyclerView.setVisibility(View.GONE);
                           }else {
                               info_text.setVisibility(View.GONE);
                               recyclerView.setVisibility(View.VISIBLE);
                           }
                           adapter.notifyDataSetChanged();
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
        mRequestQueue.add(mStringRequest);

    }


    private void getAllVol() {


        policyob= new ArrayList<>();
        policySummaryAdapter = new myvoluntaryAdapterBenefit(getActivity(), policyob);
        policy_cycle.setAdapter(policySummaryAdapter);


        policyob_term= new ArrayList<>();
        policySummaryAdapter_term = new myvoluntaryAdapterGroupMyTerm(getActivity(), policyob_term);
        policy_cycle_term.setAdapter(policySummaryAdapter_term);

        policyob_Medi= new ArrayList<>();
        policySummaryAdapter_Medi = new myvoluntaryAdapterMediclaim(getActivity(), policyob_Medi);
        policy_cycle_Medi.setAdapter(policySummaryAdapter_Medi);


        policyob_Group= new ArrayList<>();
        policySummaryAdapter_Group = new myvoluntaryAdapterGroup(getActivity(), policyob_Group);
        policy_cycle_Group.setAdapter(policySummaryAdapter_Group);


        policyob_Personal= new ArrayList<>();
        policySummaryAdapter_Personal = new myvoluntaryAdapterPersonal(getActivity(), policyob_Personal);
        policy_cycle_Personal.setAdapter(policySummaryAdapter_Personal);



        policyob_top= new ArrayList<>();
        policySummaryAdapter_top= new myvoluntaryAdapterTerm(getActivity(), policyob_top);
        policy_cycle_top.setAdapter(policySummaryAdapter_top);

        String url = con.base_url+"/api/employee/get/dashboard";

         RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);




                    String  status= String.valueOf(js.getBoolean("status"));

                    if(status.equalsIgnoreCase("false")){
                        summary.setVisibility(View.GONE);
                    }else {
                        JSONArray  jsonObj=js.getJSONArray("data");

                        if (jsonObj.length() == 0){
                            summary.setVisibility(View.GONE);
                        }else {
                            summary.setVisibility(View.GONE);




                            for (int j = 0; j < jsonObj.length(); j++) {

                                JSONObject jo_area = (JSONObject) jsonObj.get(j);
                                String po_id = String.valueOf(jo_area.getInt("policy_id"));
                                String policy_sub_type_name = String.valueOf(jo_area.getString("policy_sub_type_name"));



                                if(policy_sub_type_name.equalsIgnoreCase("Group Term Life")){
                                    setBankDetTerm(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Personal Accident Top Up")){
                                    setBankDetPersonal(po_id);
                                }


                                if(policy_sub_type_name.equalsIgnoreCase("Term Life Top Up")){
                                    setBankDetTermTop(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Group Personal Accident")){
                                    setBankDetGroup(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Mediclaim Top Up")){
                                    setBankDetMediclaim(po_id);
                                }

                                if(policy_sub_type_name.equalsIgnoreCase("Group Mediclaim")){
                                    setBankDetVoluntarys(po_id);
                                }










                            }

                            if(ob.isEmpty()){
                                info_text.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else {
                                info_text.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
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
        mRequestQueue.add(mStringRequest);

    }


    private void setBankDetVoluntarys(String policy_id) {


        policyob= new ArrayList<>();
        policySummaryAdapter = new myvoluntaryAdapterBenefit(getActivity(), policyob);
        policy_cycle.setAdapter(policySummaryAdapter);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;

         RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("groupmedi_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle.setVisibility(View.GONE);

                    } else {
                        policy_cycle.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Group Mediclaim");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));
                        double employee_premium=0.0;
                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");

                            try{
                                employee_premium= jo_areag.getDouble("employee_premium");

                            }catch (Exception e){
                                employee_premium= 0.0;

                            }

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");

                        policyob.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata));





                        policySummaryAdapter.notifyDataSetChanged();

                        Log.e("policyob", policyob.toString());
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
    private void setBankDetVoluntary(String fg) {

        String url = con.base_url+"/api/employee/get/dashboard";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);

                    String  status= String.valueOf(js.getBoolean("status"));

                    if(status.equalsIgnoreCase("false")){
                        info_text_vol.setVisibility(View.VISIBLE);
                        voluntary.setVisibility(View.GONE);
                    }else {


                        JSONArray  jsonObj=js.getJSONArray("data");

                        if (jsonObj.length() == 0){
                            info_text_vol.setVisibility(View.VISIBLE);
                            voluntary.setVisibility(View.GONE);
                            vouln.setVisibility(View.GONE);
                        }else {
                            info_text_vol.setVisibility(View.GONE);
                            voluntary.setVisibility(View.VISIBLE);
                            vouln.setVisibility(View.VISIBLE);



                            for (int j = 0; j < jsonObj.length(); j++) {

                                JSONObject jo_area = (JSONObject) jsonObj.get(j);









                                String po_id = String.valueOf(jo_area.getInt("policy_id"));

                                String id = String.valueOf(jo_area.getString("premium"));
                                String pol_id = jo_area.getString("policy_number");
                                String in_co_name = jo_area.getString("insurer_name");
                                String pol_mem_insured = String.valueOf((jo_area.getInt("suminsured")));
                                String cover_balance = String.valueOf((jo_area.getInt("suminsured")));
                                //String pol_mem_pre = (jo_area.getString("policy_mem_sum_premium"));
                                String pol_sub_type_name = (jo_area.getString("policy_name"));
                                String policy_sub_type_image_path = (jo_area.getString("insurer_logo"));
                                String insurer_companies_img_path = (jo_area.getString("insurer_logo"));
                                String policy_sub_type_id = (jo_area.getString("policy_sub_type_id"));
                                int member_count = (jo_area.getInt("total_member"));



                                String members = (jo_area.getString("members"));


                                members= members.replace("[","");
                                members=members.replace("]","");
                                members=members.replace("\"","");



//                                String opd = (jo_area.getString("Opd_sum_insure"));
//                                // String opd = "5000";
//
//                                String member = String.valueOf(member_count);



                                String cover_balancea = String.valueOf((jo_area.getInt("cover_balance")));
                                String opd_suminsured = String.valueOf((jo_area.getInt("opd_suminsured")));

                                if(fg.equalsIgnoreCase("voluntary")){

                                    if(String.valueOf(jo_area.getString("premium")).equalsIgnoreCase("0")){

                                    }else {
                                        oba.add(new Group(po_id,id, pol_id, in_co_name, pol_mem_insured, "", pol_sub_type_name, policy_sub_type_image_path, insurer_companies_img_path, policy_sub_type_id, members, cover_balance, amountrs, "",cover_balancea,opd_suminsured));

                                    }
                                }else {
//                                    if(pol_sub_type_name.contains("Voluntary")){
//
//                                    }else {
//                                        oba.add(new Group(id, pol_id, in_co_name, pol_mem_insured, "", pol_sub_type_name, policy_sub_type_image_path, insurer_companies_img_path, "", members, cover_balance, amountrs, "",cover_balancea,opd_suminsured));
//
//                                    }

                                }



                            }

                            if(oba.isEmpty()){
                                info_text_vol.setVisibility(View.VISIBLE);
                                voluntary.setVisibility(View.GONE);
                                vouln.setVisibility(View.GONE);

                            }else {
                                info_text_vol.setVisibility(View.GONE);
                                voluntary.setVisibility(View.VISIBLE);
                                vouln.setVisibility(View.VISIBLE);

                            }
                            adaptera.notifyDataSetChanged();


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
        mRequestQueue.add(mStringRequest);

    }





    public  void EmployeeDetail(){
        String url = con.base_url+"/api/admin/user";
       RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);





                        String first_name_input = explrObject.getString("name");
                        String logo = explrObject.getString("branding");








                        if (first_name_input != "null" && !first_name_input.isEmpty()) {
                            emp_name.setText(first_name_input);
                        }

                        if (logo != "null" && !logo.isEmpty()) {

                            Picasso.get().load(logo).into(mylogo);
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
        mRequestQueue.add(mStringRequest);
    }




    public  void Notification(){
        String url = con.base_url+"/api/admin/get/user-wise-notification";
          RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(),
                  new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                notify = new ArrayList<>();
                try {
                    int count=0;
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);



                        String id= String.valueOf(explrObject.getInt("id"));

                        String notification_id= String.valueOf(explrObject.getString("notification_id"));
                        String dynamic_content= String.valueOf(explrObject.getString("dynamic_content"));
                        String notification_type_id= String.valueOf(explrObject.getString("notification_type_id"));
                        String action_type_id= String.valueOf(explrObject.getString("action_type_id"));
                        String is_read= String.valueOf(explrObject.getString("is_read"));
                        String title= String.valueOf(explrObject.getString("title"));
                        String content= String.valueOf(explrObject.getString("content"));
                        String action_url= String.valueOf(explrObject.getString("action_url"));
                        String link= String.valueOf(explrObject.getString("link"));
                        String start_date= String.valueOf(explrObject.getString("start_date"));
                        String end_date= String.valueOf(explrObject.getString("end_date"));
                        String updated_at= String.valueOf(explrObject.getString("updated_at"));





                        notify.add(new Notification(id,notification_id,dynamic_content,
                                notification_type_id,action_type_id,is_read,title,content,action_url,
                                link,start_date,end_date,updated_at));
                        if(is_read.equalsIgnoreCase("0")){
                            count++;
                        }

                    }
                    String countt= String.valueOf(count);

                    tvBadgeNumber.setText(countt);

                    if(tvBadgeNumber.getText().toString().equalsIgnoreCase("0")){
                        tvBadgeNumber.setVisibility(View.GONE);
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


        mRequestQueue.add(mStringRequest);
    }








    private void setBankDetTerm(String policy_id) {



        policyob_term= new ArrayList<>();
        policySummaryAdapter_term = new myvoluntaryAdapterGroupMyTerm(getActivity(), policyob_term);
        policy_cycle_term.setAdapter(policySummaryAdapter_term);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
     //   RequestQueue rq = Volley.newRequestQueue(getActivity());

        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_term.setVisibility(View.GONE);

                    } else {
                        policy_cycle_term.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Group Term Life");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");

                        policyob_term.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata));



                        policy_cycle_term.setAdapter(policySummaryAdapter_term);


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


    private void setBankDetTermTop(String policy_id) {



        policyob_top= new ArrayList<>();
        policySummaryAdapter_top= new myvoluntaryAdapterTerm(getActivity(), policyob_top);
        policy_cycle_top.setAdapter(policySummaryAdapter_top);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
        //RequestQueue rq = Volley.newRequestQueue(getActivity());

         RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_top.setVisibility(View.GONE);

                    } else {
                        policy_cycle_top.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Term Life Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");

                        policyob_top.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata));



                        policy_cycle_top.setAdapter(policySummaryAdapter_top);


                        policySummaryAdapter_top.notifyDataSetChanged();
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

    private void setBankDetGroup(String policy_id) {


        policyob_Group= new ArrayList<>();
        policySummaryAdapter_Group = new myvoluntaryAdapterGroup(getActivity(), policyob_Group);
        policy_cycle_Group.setAdapter(policySummaryAdapter_Group);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
       // RequestQueue rq = Volley.newRequestQueue(getActivity());

        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_Group.setVisibility(View.GONE);

                    } else {
                        policy_cycle_Group.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Group Personal Accident");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");

                        policyob_Group.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata));





                        policySummaryAdapter_Group.notifyDataSetChanged();
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



    private void setBankDetPersonal(String policy_id) {


        policyob_Personal= new ArrayList<>();
        policySummaryAdapter_Personal= new myvoluntaryAdapterPersonal(getActivity(), policyob_Personal);
        policy_cycle_Personal.setAdapter(policySummaryAdapter_Personal);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
       // RequestQueue rq = Volley.newRequestQueue(getActivity());
      RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_Personal.setVisibility(View.GONE);

                    } else {
                        policy_cycle_Personal.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Personal Accident Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");

                        policyob_Personal.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata));





                        policySummaryAdapter_Personal.notifyDataSetChanged();
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



    private void setBankDetMediclaim(String policy_id) {


        policyob_Medi= new ArrayList<>();
        policySummaryAdapter_Medi = new myvoluntaryAdapterMediclaim(getActivity(), policyob_Medi);
        policy_cycle_term.setAdapter(policySummaryAdapter_Medi);


        String url = con.base_url+"/api/employee/get/all-policy-member?policy_id="+policy_id;
       // RequestQueue rq = Volley.newRequestQueue(getActivity());

        RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int allsum = 0;
                Double allPremium=0.0;
                try {

                    JSONObject data= new JSONObject(response);

                    Log.e("group_response", data.toString());

                    String statusa= String.valueOf(data.getBoolean("status"));



                    if (statusa.equalsIgnoreCase("false")) {
                        policy_cycle_Medi.setVisibility(View.GONE);

                    } else {
                        policy_cycle_Medi.setVisibility(View.VISIBLE);


                        JSONObject jsonObja =data.getJSONObject("data");


                        JSONArray jsonObj =jsonObja.getJSONArray("Mediclaim Top Up");





                        JSONObject jo_area = (JSONObject) jsonObj.get(0);


                        String policy_name = (jo_area.getString("policy_name"));

                        for (int k = 0;k < jsonObj.length(); k++) {

                            JSONObject jo_areag = (JSONObject) jsonObj.get(k);
                            int suminsured = jo_areag.getInt("suminsured");
                            double employee_premium= jo_areag.getDouble("employee_premium");

                            allsum=suminsured+allsum;
                            allPremium=employee_premium+allPremium;



                        }


                        String allsumdata=String.valueOf(allsum);
                        String allPremiumdata= String.valueOf(allPremium);



                        JSONObject jo_areaf = (JSONObject) jsonObj.get(0);

                        String name = (jo_areaf.getString("policy_name"));
                        String first_name = (jo_areaf.getString("first_name"));
                        String last_name = (jo_areaf.getString("last_name"));
                        String suminsured = String.valueOf((jo_areaf.getString("suminsured")));
                        String dob = (jo_areaf.getString("dob"));
                        String gender = (jo_areaf.getString("gender"));
                        String member_email = jo_areaf.getString("member_email");
                        String member_mob_no = jo_areaf.getString("member_mob_no");

                        policyob_Medi.add(new VoluntaryBenefit(policy_name,first_name,last_name,allsumdata,
                                dob,gender,member_email,allPremiumdata));





                        policySummaryAdapter_Medi.notifyDataSetChanged();
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




    public  void Announcement(){
        String url = con.base_url+"/api/admin/get/annoucement/module-wise";
       // RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

       RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity(),
               new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("announcement",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);




                        String type_name = explrObject.getString("type_name");







                        JSONArray  module=explrObject.getJSONArray("module");


                        for (int j = 0; j < module.length(); j++) {
                            JSONObject explrObjectj = module.getJSONObject(j);

                            String module_name = explrObjectj.getString("module_name");



                            if(module_name.equalsIgnoreCase("Home")){

                                announce.setVisibility(View.VISIBLE);
                                noannounce.setVisibility(View.GONE);




                                if(type_name.equalsIgnoreCase("Caraousel")){



                                    JSONArray  media=explrObject.getJSONArray("media");

                                    for (int k = 0; k < media.length(); k++) {

                                        JSONObject mediaa = media.getJSONObject(k);
                                        String contenta= explrObject.getString("content");
                                        String term_condition = explrObject.getString("term_condition");
                                        String titlea = explrObject.getString("title");


                                        String image= mediaa.getString("image");

                                        announceob.add(new Image(image,titlea,contenta,term_condition));
                                    }

                                    adapterannounce.notifyDataSetChanged();

                                }else if(type_name.equalsIgnoreCase("Banner")){
                                   // JSONArray  media=explrObject.getJSONArray("media");


                                        String contenta= explrObject.getString("content");
                                        String term_condition = explrObject.getString("term_condition");
                                        String titlea = explrObject.getString("title");


                                        String image= explrObject.getString("media");

                                        announceob.add(new Image(image,titlea,contenta,term_condition));


                                    adapterannounce.notifyDataSetChanged();
                                }




                            }



                        }



                        }

Log.d("list",announceob.size()+announceob.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("e", e.toString());
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
        mRequestQueue.add(mStringRequest);
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

