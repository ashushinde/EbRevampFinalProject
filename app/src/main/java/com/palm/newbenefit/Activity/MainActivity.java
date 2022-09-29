package com.palm.newbenefit.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
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
import com.palm.newbenefit.ApiConfig.AnalyticsApplication;
import com.palm.newbenefit.Fragment.ContactLogFragment;
import com.palm.newbenefit.Fragment.ContactUsMainFinalFragment;
import com.palm.newbenefit.Fragment.DashboardBenifitFragment;
import com.palm.newbenefit.Fragment.DownloadEcardFragment;
import com.palm.newbenefit.Fragment.FormcenterFourthFragment;
import com.palm.newbenefit.Fragment.InsuranceFlexbenifitFragment;
import com.palm.newbenefit.Fragment.MyTrackClaimFragment;
import com.palm.newbenefit.Fragment.NewDashBoardFragment;
import com.palm.newbenefit.Fragment.OverAllClaimFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.palm.newbenefit.Adapter.ExpandableListAdapter;
import com.palm.newbenefit.Adapter.myNotifyAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener;

import com.palm.newbenefit.Fragment.NewDashboardDesignFragment;
import com.palm.newbenefit.Fragment.HelpFragment;

import com.palm.newbenefit.Fragment.IntimateClaimFragment;
import com.palm.newbenefit.Fragment.MyClaimsFragment;
import com.palm.newbenefit.Fragment.MyEnrollMentCards;
import com.palm.newbenefit.Fragment.NetworkHospitalJava;
import com.palm.newbenefit.Fragment.PolicyTabFragment;
import com.palm.newbenefit.Fragment.ProfileViewFragment;
import com.palm.newbenefit.Fragment.SubmitClaimFragment;
import com.palm.newbenefit.Fragment.TrackClaimFragment;
import com.palm.newbenefit.Fragment.WellnessFragmentSecond;
import com.palm.newbenefit.Module.MenuModel;
import com.palm.newbenefit.Module.Notification;
import com.kmd.newbenefit.R;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    SharedPreferences prefs;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ConstraintLayout Linearlayoutdrawer;
    FragmentManager manager;
    Toolbar toolbar;
    String token;
    String user_id;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    LinearLayout linearLayout;
    LinearLayout claim, enrollment;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    Constants con;
    List<Notification> notify = null;
    String broker_id,employee_id,employer_id;
    String network_hospital="null",
            claims,SubmitClaim="null",
            downloadecard="null",
            IntimateClaim="null",TrackClaim="null",PortalClaims="null",OverallClaim="null",
            my_wellness="null",
            form_center="null",
            contact_us="null",
            help="null",
            enrollments="null",my_policy="null";

    ImageView GifImageView,logoclick;
    String flex="null";

    ProgressDialog progressDialog = null;
    LinearLayout lstloginhinde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         toolbar = findViewById(R.id.toolbar);
        GifImageView = findViewById(R.id.GifImageView);
        logoclick= findViewById(R.id.logoclick);
//        AnalyticsApplication.getInstance().trackEvent("Drawer Page", "Drawer  Page Displaying",
//                "Drawer Page Displaying");

        prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);



        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);



        Log.d("token",token);




        try{
            network_hospital = prefs.getString("network_hospital", null);
            help = prefs.getString("help", null);
            enrollments = prefs.getString("enrollment", null);
            claims = prefs.getString("claim", null);
            contact_us = prefs.getString("contact_us", null);
            my_policy = prefs.getString("my_policy", null);

            form_center = prefs.getString("form_center", null);
            my_wellness = prefs.getString("my_wellness", null);


            SubmitClaim = prefs.getString("SubmitClaim", null);
            IntimateClaim = prefs.getString("IntimateClaim", null);
            TrackClaim = prefs.getString("TrackClaim", null);

            PortalClaims = prefs.getString("PortalClaims", null);
            OverallClaim = prefs.getString("OverallClaim", null);
            flex= prefs.getString("flex", null);
            downloadecard= prefs.getString("downloadecard", null);

        }catch (Exception e){

        }










        isStoragePermissionGrantedfg();
        isStoragePermissionGranted();

        Linearlayoutdrawer = findViewById(R.id.relativelayout_for_fragement);
        setSupportActionBar(toolbar);
        con=new Constants();

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);



       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.howden_launch);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
*/

        toggle.syncState();





        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setTitle(" ");









        navigationView.setItemIconTintList(null);



        navigationView.setNavigationItemSelectedListener(this);


        linearLayout = (LinearLayout) navigationView.getHeaderView(0);

        // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        View hView = navigationView.getHeaderView(0);

        TextView nav_user = hView.findViewById(R.id.fullName);
        TextView nav_email = hView.findViewById(R.id.email);
        ImageView sync = hView.findViewById(R.id.log);
        ImageView userImage = hView.findViewById(R.id.userImage);
        LinearLayout navcolor = hView.findViewById(R.id.navcolor);
        LinearLayout clickoption = hView.findViewById(R.id.clickoption);
        TextView companyname= hView.findViewById(R.id.companyname);
        TextView lastlogin = hView.findViewById(R.id.lastlogin);
        LinearLayout lstloginhinde = hView.findViewById(R.id.lstloginhinde);




        logoclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag(employer_id);
            }
        });

        clickoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                ProfileViewFragment WellnessFragment = new ProfileViewFragment();
                transaction.replace(R.id.relativelayout_for_fragement, WellnessFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

//        GradientDrawable nonound = (GradientDrawable)navcolor.getBackground();
//        nonound.setColor(Color.parseColor("#636161"));

        progressDialog = ProgressDialog.show(MainActivity.this, "",
                "Please wait...", true);

        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this);
       /* RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this,
                new HurlStack(null, getSocketFactory()));*/
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);
                        String first_name_input = explrObject.getString("name");
                        String company_name = explrObject.getString("company_name");
                        String email_input = explrObject.getString("email");

                        String gendera = explrObject.getString("gender");
                        String id = explrObject.getString("id");
                        String last_login_at = explrObject.getString("last_login_at");
                        employer_id = explrObject.getString("employer_id");

                        frag(employer_id);

                        try{

                            String strCurrentDate = last_login_at;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date newDate = format.parse(strCurrentDate);

                            format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                            lastlogin.setText(format.format(newDate));
                        }catch (Exception e){

                            if(last_login_at.equalsIgnoreCase("null")){
                                lstloginhinde.setVisibility(View.GONE);
                            }else {
                                lstloginhinde.setVisibility(View.VISIBLE);
                            }

                            lastlogin.setText(last_login_at);
                        }



                         broker_id = explrObject.getString("broker_id");

                        employee_id= explrObject.getString("employee_id");
                        Log.d("gendera",gendera);





                        if (first_name_input != "null" && !first_name_input.isEmpty()) {



                            nav_email.setText(first_name_input.toUpperCase());
                        }

                        if (company_name != "null" && !company_name.isEmpty()||
                        !company_name.equalsIgnoreCase("null")) {

                            companyname.setText(company_name);
                        }




                        if (email_input != "null" && !email_input.isEmpty()) {
                            nav_user.setText(email_input);
                        }


                        String logo = explrObject.getString("branding");










                        if (logo != "null" && !logo.isEmpty()) {

                            Picasso.get().load(logo).into(GifImageView);
                        }



                        if (gendera.equalsIgnoreCase("Female")) {
                            userImage.setBackgroundResource(R.drawable.user_female);
                        } else {
                            userImage.setBackgroundResource(R.drawable.user_male);
                        }



                    }



                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Are you sure,You want to logout ? ");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        logout();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });



        navigationView.setNavigationItemSelectedListener(this);





       /* linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent = new Intent(getApplicationContext(), ProfileFragment.class);//jump to next activity
                //startActivity(intent);
                //finish();
                ProfileViewFragment travel = new ProfileViewFragment();
                manager.beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
*/

        //*************************************************************dashboard main layout***********************************//


        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        claim = findViewById(R.id.claim);

     //   fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33691E")));

        enrollment = findViewById(R.id.enrollment);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        claim.setOnClickListener(this);
        enrollment.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
    }


    void frag(String employer_id){


        String urls = con.base_url+ "/api/broker/get/dashboard-card-mapping?employer_id="+employer_id;
        RequestQueue rqs = Volley.newRequestQueue(MainActivity.this,
                new HurlStack(null, getSocketFactory()));

        rqs.getCache().clear();
        StringRequest mStringRequests = new StringRequest(Request.Method.GET, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{


                    progressDialog.dismiss();

                    JSONObject resp= new JSONObject(response);



                    JSONArray jsonObj =resp.getJSONArray("data");


                    if(jsonObj.toString().isEmpty()||
                            jsonObj.toString().equalsIgnoreCase("[]")){

                        progressDialog.dismiss();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        NewDashBoardFragment profileFragment = new NewDashBoardFragment();
                        transaction.replace(R.id.relativelayout_for_fragement, profileFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }else {
                        progressDialog.dismiss();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        NewDashboardDesignFragment profileFragment = new NewDashboardDesignFragment();
                        transaction.replace(R.id.relativelayout_for_fragement, profileFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }




                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.e("onErrorResponse", e.toString());
                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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

        progressDialog.dismiss();


        HashMap<String, String> paramss = new HashMap<>();
        paramss.put("employer_id", employer_id);

        rqs.add(mStringRequests);
    }

    //android:orderInCategory="50"
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_navigation, menu);


        final View menu_main_setting = menu.findItem(R.id.menu_main_setting).getActionView();
        ImageView myicon= menu_main_setting.findViewById(R.id.icon);

        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this);
       /* RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this,
                new HurlStack(null, getSocketFactory()));*/
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("mydata",response);
                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);





                        String last_login_at = explrObject.getString("last_login_at");







                        String logo = explrObject.getString("branding");










                        if (logo != "null" && !logo.isEmpty()) {

                            Picasso.get().load(logo).into(myicon);
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
        HashMap<String, String> paramss = new HashMap<>();
        paramss.put("user_id", user_id);
        paramss.put("master_user_type_id", "5");


        mStringRequest.setParams(paramss);
        mRequestQueue.add(mStringRequest);




        final View notificaitons = menu.findItem(R.id.action_notification).getActionView();
        TextView  tvBadgeNumber= notificaitons.findViewById(R.id.tvBadgeNumber);
        RelativeLayout count= notificaitons.findViewById(R.id.notificationBadge);
        RelativeLayout badgeLayout= notificaitons.findViewById(R.id.badgeLayout);
        Button bell= notificaitons.findViewById(R.id.bell);

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parental_dailog();

            }
        });

        tvBadgeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parental_dailog();

            }
        });
        badgeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parental_dailog();

            }
        });
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parental_dailog();

            }
        });

        tvBadgeNumber.setVisibility(View.GONE);
        String urla = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueues = Volley.newRequestQueue(MainActivity.this);
       /* RequestQueue mRequestQueues = Volley.newRequestQueue(MainActivity.this,
                new HurlStack(null, getSocketFactory()));*/
        mRequestQueues.getCache().clear();
        StringRequest mStringRequests = new StringRequest(Request.Method.GET, urla, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);


                    JSONArray jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);





                        broker_id = explrObject.getString("broker_id");

                        employee_id= explrObject.getString("employee_id");






                        String url = con.base_url+"/api/admin/get/user-wise-notification";
                        RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this);
                      /*  RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this,
                                new HurlStack(null, getSocketFactory()));*/
                        mRequestQueue.getCache().clear();
                        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(String response) {
                                notify = new ArrayList<>();
                                try {
                                    int count = 0;

                                    if (response.equalsIgnoreCase("")) {
                                        tvBadgeNumber.setVisibility(View.GONE);
                                    } else {
                                        try {


                                            JSONObject js = new JSONObject(response);


                                            Log.d("notifiction", response);
                                            JSONArray jsonObj = js.getJSONArray("data");

                                            for (int i = 0; i < jsonObj.length(); i++) {
                                                JSONObject explrObject = jsonObj.getJSONObject(i);


                                                String id = String.valueOf(explrObject.getInt("id"));

                                                String notification_id = String.valueOf(explrObject.getString("notification_id"));
                                                String dynamic_content = String.valueOf(explrObject.getString("dynamic_content"));
                                                String notification_type_id = String.valueOf(explrObject.getString("notification_type_id"));
                                                String action_type_id = String.valueOf(explrObject.getString("action_type_id"));
                                                String is_read = String.valueOf(explrObject.getString("is_read"));
                                                String title = String.valueOf(explrObject.getString("title"));
                                                String content = String.valueOf(explrObject.getString("content"));
                                                String action_url = String.valueOf(explrObject.getString("action_url"));
                                                String link = String.valueOf(explrObject.getString("link"));
                                                String start_date = String.valueOf(explrObject.getString("start_date"));
                                                String end_dates = String.valueOf(explrObject.getString("end_date"));
                                                String end_date = employee_id;
                                                String updated_at = String.valueOf(explrObject.getString("updated_at"));






                                                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                                                Date todayDate = new Date();
                                                String thisDate = currentDate.format(todayDate);



                                                int result=0;
                                                try{

                                                    String strCurrentDate = updated_at;
                                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                                                    Date newDate = format.parse(strCurrentDate);

                                                    format = new SimpleDateFormat("dd/MM/yyyy");


                                                    Date date1 = format.parse(updated_at);
                                                    Date date2 = format.parse(thisDate);

                                                     result = date1.compareTo(date2);

                                                }catch (Exception e){

                                                }




                                                Log.e("result", String.valueOf(result <0));
                                                if ((result >0)||(notification_type_id.equalsIgnoreCase("1") && (!dynamic_content.equalsIgnoreCase("null")))) {


                                                        notify.add(new Notification(id, notification_id, dynamic_content,
                                                                notification_type_id, action_type_id, is_read, title, content, action_url,
                                                                link, start_date, end_date, updated_at));




                                                }


                                                if ((result < 0)||(notification_type_id.equalsIgnoreCase("1") && (!dynamic_content.equalsIgnoreCase("null")))) {

                                                    if (is_read.equalsIgnoreCase("0")) {
                                                        count++;

                                                    }


                                                }

                                            }



                                            String countt = String.valueOf(count);

                                            tvBadgeNumber.setText(countt);
                                            Log.e("countt", broker_id);

                                            if (tvBadgeNumber.getText().toString().equalsIgnoreCase("0")) {
                                                tvBadgeNumber.setVisibility(View.GONE);
                                            }


                                        } catch (Exception e) {
                                            tvBadgeNumber.setVisibility(View.GONE);
                                            Log.e("onErrorResponse", e.toString());
                                        }


                                        if(notify.isEmpty()){
                                            tvBadgeNumber.setVisibility(View.GONE);
                                        }


                                    }





                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("onErrorResponse", e.toString());
                                    tvBadgeNumber.setVisibility(View.GONE);
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("onErrorResponse", error.toString());
                                tvBadgeNumber.setVisibility(View.GONE);
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



                } catch (JSONException e) {
                    e.printStackTrace();
                    tvBadgeNumber.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
                tvBadgeNumber.setVisibility(View.GONE);
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


        mStringRequests.setParams(params);
        mRequestQueues.add(mStringRequests);










        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {



            case R.id.menu_main_setting:

                String url = con.base_url+ "/api/broker/get/dashboard-card-mapping?employer_id="+employer_id;
                RequestQueue rq = Volley.newRequestQueue(MainActivity.this,
                        new HurlStack(null, getSocketFactory()));

                rq.getCache().clear();
                StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try{




                            JSONObject resp= new JSONObject(response);



                            JSONArray jsonObj =resp.getJSONArray("data");


                            if(jsonObj.toString().isEmpty()||
                                    jsonObj.toString().equalsIgnoreCase("[]")){


                                NewDashBoardFragment travel = new NewDashBoardFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                        travel, travel.getTag()).addToBackStack("back").commit();


                            }else {
                                NewDashboardDesignFragment travel = new NewDashboardDesignFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement,
                                        travel, travel.getTag()).addToBackStack("back").commit();
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
                params.put("employer_id", employer_id);

                rq.add(mStringRequest);

                return true;

            case R.id.action_notification:

                return true;



            default:
                break;
        }

        return false;
    }
    void parental_dailog() {
        myNotifyAdapter adaptere = null;
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notification_list);

        LinearLayout s_logo = (LinearLayout) dialog.findViewById(R.id.s_logo);
        RecyclerView ghi_recycle = (RecyclerView) dialog.findViewById(R.id.ghi_recycle);
        ImageView no_data= (ImageView) dialog.findViewById(R.id.no_data);


        if(notify.isEmpty()||notify.size()==0){
            no_data.setVisibility(View.VISIBLE);
            ghi_recycle.setVisibility(View.GONE);
        }else {
            no_data.setVisibility(View.GONE);
            ghi_recycle.setVisibility(View.VISIBLE);
        }


        adaptere = new myNotifyAdapter(MainActivity.this, notify);
        ghi_recycle.setAdapter(adaptere);
        adaptere.notifyDataSetChanged();

        s_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.dismiss();




            }
        });

        int numberOfColumns = 1;
        ghi_recycle.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));

        ghi_recycle.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, ghi_recycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.VERTICAL, false);
        ghi_recycle.setLayoutManager(manager);


        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);




    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:


                MyEnrollMentCards home = new MyEnrollMentCards();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relativelayout_for_fragement, home, home.getTag()).commit();

                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                isFabOpen = false;
                claim.setVisibility(View.GONE);
                enrollment.setVisibility(View.GONE);

                break;
            case R.id.fab2:

                SubmitClaimFragment homed = new SubmitClaimFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relativelayout_for_fragement, homed, homed.getTag()).commit();

                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                isFabOpen = false;
                claim.setVisibility(View.GONE);
                enrollment.setVisibility(View.GONE);

                break;
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            claim.setVisibility(View.GONE);
            enrollment.setVisibility(View.GONE);


        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            claim.setVisibility(View.VISIBLE);
            enrollment.setVisibility(View.VISIBLE);
            isFabOpen = true;


        }


    }
    public void back() {

        NewDashboardDesignFragment travel = new NewDashboardDesignFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.setCheckedItem(R.id.nav_view);
    }

//    @Override
//    public void onBackPressed() {
//        manager = getSupportFragmentManager();
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        }
//
//        if (toolbar.getTitle().equals("Employee Dashboard")) {
//
//            new AlertDialog.Builder(this)
//                    .setTitle("Really Exit?")
//                    .setMessage("Are you sure you want to exit?")
//                    .setNegativeButton(android.R.string.no, null)
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            // MainActivity.super.onBackPressed();
//                            //finish();
//                            moveTaskToBack(true);
//                        }
//                    }).create().show();
//        } else {
//            back();
//        }
//
//
//    }






    private void prepareMenuData() {
        MenuModel menuModel;

        menuModel = new MenuModel("Home", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        menuModel = new MenuModel("Dashboard", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        menuModel = new MenuModel("Profile", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        try{
            if(enrollments.equalsIgnoreCase("yes")){

                menuModel = new MenuModel("Enrolment", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                headerList.add(menuModel);
            }

            try {

                if (flex.equalsIgnoreCase("yes")) {

                    menuModel = new MenuModel("Flex Benefit", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                    headerList.add(menuModel);
                }

            }catch (Exception e){

            }

            try {

                if (downloadecard.equalsIgnoreCase("yes")) {

                    menuModel = new MenuModel("Download E-Card", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                    headerList.add(menuModel);
                }

            }catch (Exception e){

            }




            if(network_hospital.equalsIgnoreCase("yes")){

                menuModel = new MenuModel("Network Hospital", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                headerList.add(menuModel);
            }


            if(my_wellness.equalsIgnoreCase("yes")){

                menuModel = new MenuModel("My Wellness", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                headerList.add(menuModel);
            }


//        menuModel = new MenuModel("HEAPS", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
//        headerList.add(menuModel);

            if(form_center.equalsIgnoreCase("yes")){

                menuModel = new MenuModel("Form Center", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                headerList.add(menuModel);
            }


            if(contact_us.equalsIgnoreCase("yes")){

                menuModel = new MenuModel("Contact Us", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                headerList.add(menuModel);
            }
            if(help.equalsIgnoreCase("yes")){

                menuModel = new MenuModel("Help", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                headerList.add(menuModel);
            }
            if(my_policy.equalsIgnoreCase("yes")){

                menuModel = new MenuModel("My Policy", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
                headerList.add(menuModel);
            }



        }catch (Exception e){

            Log.d("errornav",e.toString());

        }



      /*  menuModel = new MenuModel("E-Cashless", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);*/

      /*  menuModel = new MenuModel("Health Checkup", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);*/


        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        try{
            if(claims.equalsIgnoreCase("yes")){
                menuModel = new MenuModel("Claim", true, true, ""); //Menu of Java Tutorials
                headerList.add(menuModel);

            }
        }catch (Exception e )
        {
            Log.d("errornav",e.toString());
        }










        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel;

        if(IntimateClaim.equalsIgnoreCase("yes")){

             childModel = new MenuModel("Intimate Claim", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
            childModelsList.add(childModel);
        }

        if(SubmitClaim.equalsIgnoreCase("yes")){

            childModel = new MenuModel("Submit Claim", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
            childModelsList.add(childModel);
        }

        if(PortalClaims.equalsIgnoreCase("yes")){

            childModel = new MenuModel("Portal Claim", false, false, "https://www.journaldev.com/19115/java-filereader");
            childModelsList.add(childModel);

        }


        if(OverallClaim.equalsIgnoreCase("yes")){

            childModel = new MenuModel("Overall Claim", false, false, "https://www.journaldev.com/19115/java-filereader");
            childModelsList.add(childModel);

        }

        if(TrackClaim.equalsIgnoreCase("yes")){

            childModel = new MenuModel("Track Claim", false, false, "https://www.journaldev.com/19115/java-filereader");
            childModelsList.add(childModel);
        }












//        childModel = new MenuModel("E-Cashless", false, false, "https://www.journaldev.com/19115/java-filereader");
//        childModelsList.add(childModel);




        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

       /* childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Python Tutorials", true, true, ""); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Python AST – Abstract Syntax Tree", false, false, "https://www.journaldev.com/19243/python-ast-abstract-syntax-tree");
        childModelsList.add(childModel);

        childModel = new MenuModel("Python Fractions", false, false, "https://www.journaldev.com/19226/python-fractions");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }*/
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {



                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        Fragment newFragment;
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        if(headerList.get(groupPosition).getMenuName().equalsIgnoreCase("Enrolment")){
                            MyEnrollMentCards EnrollMentFragmentJava = new MyEnrollMentCards();
                            transaction.replace(R.id.relativelayout_for_fragement, EnrollMentFragmentJava);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Network Hospital")) {
                            NetworkHospitalJava NetworkHospitalFragmentJ = new NetworkHospitalJava();
                            transaction.replace(R.id.relativelayout_for_fragement, NetworkHospitalFragmentJ);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase
                                ("Download E-Card")) {
                            DownloadEcardFragment NetworkHospitalFragmentJ = new DownloadEcardFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, NetworkHospitalFragmentJ);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("My Wellness")) {
                            WellnessFragmentSecond WellnessFragment = new WellnessFragmentSecond();
                            transaction.replace(R.id.relativelayout_for_fragement, WellnessFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Form Center")) {
                            FormcenterFourthFragment FormCenterFragmentDemo = new FormcenterFourthFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, FormCenterFragmentDemo);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Flex Benefit")) {
                            InsuranceFlexbenifitFragment FormCenterFragmentDemo = new InsuranceFlexbenifitFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, FormCenterFragmentDemo);
                            transaction.addToBackStack(null);
                            transaction.commit();

                           // InsuranceFlexFragment  InsuranceFlexbenifitFragment
                        }


                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Contact Us")) {


                            String url = con.base_url+"/api/employee/get/contact-logs";
                            RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this);
                            //RequestQueue mRequestQueue = Volley.newRequestQueue(MainActivity.this, new HurlStack(null, getSocketFactory()));
                            mRequestQueue.getCache().clear();
                            StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {



                                        try {
                                            JSONObject  js = new JSONObject(response);

                                            Log.d("response",response);


                                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

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
                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Help")) {
                            HelpFragment HelpFragment = new HelpFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, HelpFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("My Policy")) {
                            PolicyTabFragment PolicyTabFragment = new PolicyTabFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, PolicyTabFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Logout")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setMessage("Are you sure,You want to logout ?");
                            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    logout();
                                }
                            });
                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }

                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Home")) {


                            String url = con.base_url+ "/api/broker/get/dashboard-card-mapping?employer_id="+employer_id;
                            RequestQueue rq = Volley.newRequestQueue(MainActivity.this,
                                    new HurlStack(null, getSocketFactory()));

                            rq.getCache().clear();
                            StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                    try{




                                        JSONObject resp= new JSONObject(response);



                                            JSONArray jsonObj =resp.getJSONArray("data");


                                            if(jsonObj.toString().isEmpty()||
                                                    jsonObj.toString().equalsIgnoreCase("[]")){


                                                NewDashBoardFragment profileFragment2 = new NewDashBoardFragment();
                                                transaction.replace(R.id.relativelayout_for_fragement, profileFragment2);
                                                transaction.addToBackStack(null);
                                                transaction.commit();


                                            }else {
                                                NewDashboardDesignFragment profileFragment2 = new NewDashboardDesignFragment();
                                                transaction.replace(R.id.relativelayout_for_fragement, profileFragment2);
                                                transaction.addToBackStack(null);
                                                transaction.commit();
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
                            params.put("employer_id", employer_id);

                            rq.add(mStringRequest);

                        }

                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Dashboard")) {
                            DashboardBenifitFragment profileFragment2 = new DashboardBenifitFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, profileFragment2);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        else    if(headerList.get(groupPosition).menuName.equalsIgnoreCase("Profile")) {
                            ProfileViewFragment profileFragment1 = new ProfileViewFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, profileFragment1);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }


                       /* switch (groupPosition) {





                            case 0:
                                NewDashboardDesignFragment profileFragment = new NewDashboardDesignFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, profileFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case 1:
                                DashboardBenifitFragment profileFragment2 = new DashboardBenifitFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, profileFragment2);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

                            case 2:
                                ProfileViewFragment profileFragment1 = new ProfileViewFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, profileFragment1);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;



                            case 3:


                                    MyEnrollMentCards EnrollMentFragmentJava = new MyEnrollMentCards();
                                    transaction.replace(R.id.relativelayout_for_fragement, EnrollMentFragmentJava);
                                    transaction.addToBackStack(null);
                                    transaction.commit();


                                break;

                           *//* case 4:
                                FlexiBenefitFragment FlexiBenefitFragment = new FlexiBenefitFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, FlexiBenefitFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;*//*
                            case 4:
                                NetworkHospitalJava NetworkHospitalFragmentJ = new NetworkHospitalJava();
                                transaction.replace(R.id.relativelayout_for_fragement, NetworkHospitalFragmentJ);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case 5:
                                WellnessFragmentSecond WellnessFragment = new WellnessFragmentSecond();
                                transaction.replace(R.id.relativelayout_for_fragement, WellnessFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

//                            case 7:
//
////                                RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
////
////                                //  RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
////
////                                String url = con.base_url+"/api/admin/heaps/member-sync";
////                                SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
////                                        new Response.Listener<String>() {
////                                            @Override
////                                            public void onResponse(String response) {
////
////
////
////                                                try {
////
////                                                    Log.d("response_data",response);
////                                                    JSONObject js=new JSONObject(response);
////
////
////
////
////
////                                                    JSONObject jsonObj=js.getJSONObject("data");
////
////
////
////
////                                                    String url = jsonObj.getString("url");
////
////
////                                                    String urlString = url;
////                                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
////                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                                    intent.setPackage("com.android.chrome");
////                                                    try {
////                                                        startActivity(intent);
////                                                    } catch (ActivityNotFoundException ex) {
////                                                        // Chrome browser presumably not installed so allow user to choose instead
////                                                        intent.setPackage(null);
////                                                        startActivity(intent);
////                                                    }
////
////
////
////
////                                                } catch (JSONException e) {
////                                                    e.printStackTrace();
////                                                }
////
////
////                                            }
////                                        }, new Response.ErrorListener() {
////                                    @Override
////                                    public void onErrorResponse(VolleyError error) {
////                                        Log.e("onErrorResponse", error.toString());
////                                    }
////                                }) {
////                                    @Override
////                                    public Map<String, String> getHeaders() throws AuthFailureError {
////                                        Map<String, String> headers = new HashMap<>();
////                                        headers.put("Authorization", "Bearer " + token);
////                                        return headers;
////                                    }
////                                };
////
////                                smr.addStringParam("wellness_partner_id", "1");
////                                smr.addStringParam("user_type_name", "Employee");
////
////
////
////
////
////                                rq.add(smr);
////
//
////                                HeapsFragment FormCenterFragmentDemos = new HeapsFragment();
////                                transaction.replace(R.id.relativelayout_for_fragement, FormCenterFragmentDemos);
////                                transaction.addToBackStack(null);
////                                transaction.commit();
////
////                                break;
                            case 6:
                                FormCenterFragmentDemo FormCenterFragmentDemo = new FormCenterFragmentDemo();
                                transaction.replace(R.id.relativelayout_for_fragement, FormCenterFragmentDemo);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case 7:
                                ContactUsMainFinalFragment ContactUsFragmentNew = new ContactUsMainFinalFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, ContactUsFragmentNew);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case 8:
                                HelpFragment HelpFragment = new HelpFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, HelpFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case 9:
                                PolicyTabFragment PolicyTabFragment = new PolicyTabFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, PolicyTabFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

                            *//*case 11:
                                ECashlessFragment PolicyTabFragments = new ECashlessFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, PolicyTabFragments);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case 12:
                                HealthChekupFragment HealthChekupFragments = new HealthChekupFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, HealthChekupFragments);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;*//*

                            case 10:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setMessage("Are you sure,You want to logout ?");
                                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        logout();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                break;

                        }
*/
                      /*  WebView webView = findViewById(R.id.webView);
                        webView.loadUrl(headerList.get(groupPosition).url);*/
                       onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.url.length() > 0) {
                       /* WebView webView = findViewById(R.id.webView);
                        webView.loadUrl(model.url);*/
                         onBackPressed();
                        Fragment newFragment;
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


                        if(childList.get(headerList.get(groupPosition)).get(childPosition).getMenuName().equalsIgnoreCase("Intimate Claim")){
                            IntimateClaimFragment IntimateClaimFragment = new IntimateClaimFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, IntimateClaimFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }else
                        if(childList.get(headerList.get(groupPosition)).get(childPosition).getMenuName().equalsIgnoreCase("Submit Claim")){
                            SubmitClaimFragment SubmitClaimFragment = new SubmitClaimFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, SubmitClaimFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        else
                        if(childList.get(headerList.get(groupPosition)).get(childPosition).getMenuName().equalsIgnoreCase("Portal Claim")){
                            MyClaimsFragment MyClaimsFragment = new MyClaimsFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, MyClaimsFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        else
                        if(childList.get(headerList.get(groupPosition)).get(childPosition).getMenuName().equalsIgnoreCase("Overall Claim")){
                            OverAllClaimFragment MyClaimsFragments = new OverAllClaimFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, MyClaimsFragments);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        else
                        if(childList.get(headerList.get(groupPosition)).get(childPosition).getMenuName().equalsIgnoreCase("Track Claim")){
                            MyTrackClaimFragment track_claim = new MyTrackClaimFragment();
                            transaction.replace(R.id.relativelayout_for_fragement, track_claim);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }



                       /* switch (childPosition) {
                            case 0:

                                break;

                            case 1:
                                SubmitClaimFragment SubmitClaimFragment = new SubmitClaimFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, SubmitClaimFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

                            case 2:
                                MyClaimsFragment MyClaimsFragment = new MyClaimsFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, MyClaimsFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

                            case 3:
                                OverAllClaimFragment MyClaimsFragments = new OverAllClaimFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, MyClaimsFragments);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

                            case 4:
                                TrackClaimFragment track_claim = new TrackClaimFragment();
                                transaction.replace(R.id.relativelayout_for_fragement, track_claim);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
//
//                            case 4:
//                                PlanHospitalizationFragment track_claims = new PlanHospitalizationFragment();
//                                transaction.replace(R.id.relativelayout_for_fragement, track_claims);
//                                transaction.addToBackStack(null);
//                                transaction.commit();
//                                break;

                        }
*/
                      /*  WebView webView = findViewById(R.id.webView);
                        webView.loadUrl(headerList.get(groupPosition).url);*/
                        onBackPressed();

                    }
                }

                return false;
            }
        });
    }

    public void logout() {


        SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().apply();
        editor.apply();


        Intent intent = new Intent(MainActivity.this, DemoActivty.class);
        startActivity(intent);
        finish();




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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public boolean isStoragePermissionGrantedfg() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
        return true;
    }
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {

                return true;


            } else {


                ActivityCompat.requestPermissions(this,new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }
}
