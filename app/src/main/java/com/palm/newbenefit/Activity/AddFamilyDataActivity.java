package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.newbenefit.Module.SpinnerModal;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class AddFamilyDataActivity extends AppCompatActivity {
    Spinner relation_type;
    EditText first_name,m_date, last_name, dob, email_id, contact, pincode, building, city, state;
    Toolbar settingtoolbar;
    LinearLayout marr_date;
    Constants con;
    Context context;
    TextView call;
    CheckBox check;
    Button edit, clear;
    String family_id;
    ArrayList<String> relation_Status = null;
    ArrayList<SpinnerModal> Relation_list = null;
    ArrayAdapter<SpinnerModal> RelationAdapter = null;
    DatePickerDialog.OnDateSetListener datePickerListener = null;
    ProgressDialog progressDialog = null;
    private DatePickerDialog fromDatePickerDialog;
    String emp_id,token;
    final Calendar myCalendar = Calendar.getInstance();
    String invalid="1";
    Calendar newCalendar;
    private SimpleDateFormat dateFormatter;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    ArrayList<String> bank_name = null;
    ImageView info_text;
    SwitchCompat switchCompat;
    String user_id;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_data);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try{
            AnalyticsApplication.getInstance().trackEvent("Add Family Page", "Add Family Page Displaying", "Add Family Page Displaying");

        }catch (Exception e){

        }

      newCalendar = Calendar.getInstance();
        con = new Constants();
        context = getApplicationContext();
        clear = findViewById(R.id.cancle);
       /* settingtoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        settingtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
      user_id = prefs.getString("user_id", null);

      check = findViewById(R.id.check);
        relation_type = findViewById(R.id.relation_type);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        dob = findViewById(R.id.dob);
        email_id = findViewById(R.id.email_id);
        contact = findViewById(R.id.contact);
        pincode = findViewById(R.id.pincode);
        building = findViewById(R.id.building);

        city = findViewById(R.id.city);
        state = findViewById(R.id.state);

        edit = findViewById(R.id.submit);
        info_text= findViewById(R.id.info_text);
        marr_date = findViewById(R.id.marr_date);
        m_date = findViewById(R.id.m_date);

      switchCompat = (SwitchCompat) findViewById(R.id.swOnOff);
      switchCompat.setOnCheckedChangeListener(onCheckedChanged());

        if (isNetworkAvailable()) {

            getBankbranch();
        }else {
            new AlertDialog.Builder(AddFamilyDataActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }



        relation_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();


                if (bank_name_modal.getSelKey().equals("2")) {
                    marr_date.setVisibility(View.VISIBLE);

                } else {
                    marr_date.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filterLongEnough()) {
                    //RequestQueue rq = Volley.newRequestQueue(AddFamilyDataActivity.this);
                 RequestQueue rq = Volley.newRequestQueue(AddFamilyDataActivity.this,
                         new HurlStack(null, getSocketFactory()));
                 rq.getCache().clear();
                    String url =con.base_url+"/api/employee/get/state-city";
                    StringRequest smr = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("pincode",response);


                                        JSONObject js=new JSONObject(response);



                                      //  String status=js.getString("status");
                                        String status = String.valueOf(js.getBoolean("status"));

                                            if(status.equalsIgnoreCase("false")){

                                                  invalid="1";

                                                city.setText("");
                                                state.setText("");
                                                new AlertDialog.Builder(AddFamilyDataActivity.this)
                                                        .setTitle("Invalid Pincode?")
                                                        .setMessage("Please Check the Pincode!")
                                                        .setIcon(android.R.drawable.btn_dialog)
                                                        .setNegativeButton(android.R.string.ok, null).show();

                                            } else  {

                                                invalid="0";
                                                Log.d("response",response);
                                                JSONArray  jsonObj=js.getJSONArray("data");

                                                for (int i = 0; i < jsonObj.length(); i++) {
                                                    JSONObject explrObject = jsonObj.getJSONObject(i);

                                                    city.setText(explrObject.getString("city_name"));
                                                    state.setText(explrObject.getString("state_name"));

                                                }

                                        }




                                    } catch (Exception e) {

                                        e.printStackTrace();
                                       /* new AlertDialog.Builder(FamilyDataActivity.this)
                                                .setTitle("Invalid Pincode?")
                                                .setMessage("Please Check the Pincode!")
                                                .setIcon(android.R.drawable.btn_dialog)
                                                .setNegativeButton(android.R.string.ok, null).show();*/
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

                    params.put("pincode", pincode.getText().toString().trim());
                    smr.setParams(params);
                    rq.add(smr);

                } else {
                    city.setText("");
                    state.setText("");
                }
            }

            private boolean filterLongEnough() {
                return pincode.getText().toString().trim().length() > 5;
            }
        };


        if (isNetworkAvailable()) {
            pincode.addTextChangedListener(fieldValidatorTextWatcher);
        }else {
            new AlertDialog.Builder(AddFamilyDataActivity.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }




        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {


                    SendData();
                }
                else {

                    new AlertDialog.Builder(AddFamilyDataActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();


                }

            }
        });



        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getBankbranch();

                first_name.setText("");
                last_name.setText("");
                email_id.setText("");
                dob.setText("");
                contact.setText("");
                pincode.setText("");
                building.setText("");
                city.setText("");
                state.setText("");
                m_date.setText("");




            }
        });


        if (check.isChecked()) {


            if (isNetworkAvailable()) {

                setProfileDet();
            }
            else {

                new AlertDialog.Builder(AddFamilyDataActivity.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();

            }

        } else {

            pincode.setText("");
            building.setText("");

            city.setText("");
            state.setText("");

        }


        m_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Utils.desablePostDatePicker(AddFamilyDataActivity.this, new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        m_date.setText(date);

                        //travel_request_todateshow.setText("");
                    }
                });





            }
        });


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.desablePostDatePicker(getActivity(), new MyDateListener() {
//                    @Override
//                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
//                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
//                        newCalendar.set(Calendar.MONTH, selectedMonth);
//                        newCalendar.set(Calendar.YEAR, selectedYear);
//                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
//                        dob.setText(date);
//
//
//                        //travel_request_todateshow.setText("");
//                    }
//                });

                SpinnerModal bank_branch_modal = (SpinnerModal) relation_type.getSelectedItem();



                if(bank_branch_modal.getSelValue().equalsIgnoreCase("Spouse")|| bank_branch_modal.getSelValue().equalsIgnoreCase("Mother") || bank_branch_modal.getSelValue().equalsIgnoreCase("Father")|| bank_branch_modal.getSelValue().equalsIgnoreCase("Mother-in-law")|| bank_branch_modal.getSelValue().equalsIgnoreCase("Father-in-law")){

                    final Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(
                            AddFamilyDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker,
                                              int selectedyear, int selectedmonth,
                                              int selectedday) {
                            selectedyear=selectedyear;
                            mcurrentDate.set(Calendar.YEAR, selectedyear);
                            mcurrentDate.set(Calendar.MONTH, selectedmonth);
                            mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                            dob.setText(sdf.format(mcurrentDate.getTime()));


                        }
                    }, mYear-18, mMonth, mDay);
                    mcurrentDate.set(mYear-18,mMonth,mDay);
                    long value=mcurrentDate.getTimeInMillis();
                    mDatePicker.getDatePicker().setMaxDate(value);
                    mDatePicker.show();

                }else {

                    Utils.desablePostDatePicker(AddFamilyDataActivity.this, new MyDateListener() {
                        @Override
                        public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                            newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                            newCalendar.set(Calendar.MONTH, selectedMonth);
                            newCalendar.set(Calendar.YEAR, selectedYear);
                            String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                            dob.setText(date);


                            //travel_request_todateshow.setText("");
                        }
                    });

                }

            }
        });



       /* dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/





        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable()) {

                    setProfileDet();
                }
                else {

                    new AlertDialog.Builder(AddFamilyDataActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();

                }





            }

        });


       /* contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = contact.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);


            }
        });
*/

    }

    private CompoundButton.OnCheckedChangeListener onCheckedChanged() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.swOnOff:
                        setButtonState(isChecked);
                        break;

                }
            }
        };
    }

    private void setButtonState(boolean statea) {
        if (statea) {
            if (isNetworkAvailable()) {

                setProfileDet();

            }else {
                new AlertDialog.Builder(AddFamilyDataActivity.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }

        } else {

            pincode.setText("");

            building.setText("");

            city.setText("");

            state.setText("");


        }
    }


    public void getBankbranch() {

        String url = con.base_url+"/api/employee/get/relation";
       // RequestQueue mRequestQueue = Volley.newRequestQueue(AddFamilyDataActivity.this);
        RequestQueue mRequestQueue = Volley.newRequestQueue(AddFamilyDataActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);
                    Log.d("response",response);
                    JSONArray  jsonArr=js.getJSONArray("data");
                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Family Relation"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        if (!jsonObj.getString("name").equalsIgnoreCase("self")) {
                            bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                            bank_name.add(jsonObj.getString("name"));
                        }
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(AddFamilyDataActivity.this, R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                    relation_type.setAdapter(bank_nameAdapter);


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
        mRequestQueue.add(mStringRequest);

    }

    public static boolean isValid(String s) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }



    public void SendData() {

        int count = 0;

        if (first_name.getText().toString().trim().length() == 0) {
            ++count;
            first_name.setError("First Name is Required");
        } else {
            first_name.setError(null);
        }
        if (contact.getText().toString().trim().isEmpty() || !isValid(contact.getText().toString().trim()) || contact.getText().toString().length() < 10 || contact.getText().toString().trim().length() > 13) {
            ++count;
            contact.setError("Invalid Mobile");
        } else {
            contact.setError(null);
        }

        if (last_name.getText().toString().trim().length() == 0) {
            ++count;
            last_name.setError("Last Name is Required");
        } else {
            last_name.setError(null);
        }



        if (email_id.getText().toString().trim().length() == 0) {
            ++count;
            email_id.setError("Enter Email Id");
        } else {
            email_id.setError(null);
        }

        if (dob.getText().toString().trim().length() == 0) {
            ++count;
            dob.setError("DOB is Required");
        } else {
            dob.setError(null);
        }


        if(Patterns.EMAIL_ADDRESS.matcher(email_id.getText().toString().trim()).matches()){
            email_id.setError(null);
        }else {
            email_id.setError("Invalid Email");
            ++count;
        }


        if(pincode.getText().toString().trim().length()==0){

        }else {
            if (pincode.getText().toString().trim().length()<6) {
                ++count;
                pincode.setError("Enter  Valid Pincode");
            } else {
                if(invalid.equalsIgnoreCase("1")){
                    ++count;
                    pincode.setError("Enter  Valid Pincode");
                }else {
                    pincode.setError(null);
                }



            }
        }

        SpinnerModal patient = (SpinnerModal) relation_type.getSelectedItem();
        if (patient.selValue.equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(AddFamilyDataActivity.this)
                    .setTitle("Error")
                    .setMessage("Please Select Relation")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }



        if (count == 0) {
            progressDialog = ProgressDialog.show(this, "",
                    "Saving. Please wait...", true);
            RequestQueue rq = Volley.newRequestQueue(AddFamilyDataActivity.this,
                    new HurlStack(null, getSocketFactory()));
            String url = con.base_url+"/api/employee/add/family-member";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();

                                Log.d("response",response);

                                AnalyticsApplication.getInstance().trackEvent("Add Family Data", response, "Add Family Data");


                                JSONObject jsonObject = new JSONObject(response);
                                String errorCode = jsonObject.getString("message");
                             //   String status = jsonObject.getString("status");
                                String status = String.valueOf(jsonObject.getBoolean("status"));

                                if(status.equalsIgnoreCase("true")){
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddFamilyDataActivity.this);
                                    alertDialog.setTitle("");
                                    alertDialog.setMessage(errorCode);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                }else {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddFamilyDataActivity.this);
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage(errorCode);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    alertDialog.show();
                                }




                            } catch (Exception e) {
                                progressDialog.dismiss();

                            }

                        }
                    },  new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", error.toString());
                    progressDialog.dismiss();
                    AnalyticsApplication.getInstance().trackEvent("Error in adding Family Data", error.toString(), "Error in adding family Data");

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };


            SpinnerModal bank_name_modal = (SpinnerModal) relation_type.getSelectedItem();



            HashMap<String, String> params = new HashMap<>();


            params.put("relation_id", bank_name_modal.getSelKey());
            params.put("first_name", first_name.getText().toString());
            params.put("last_name", last_name.getText().toString());
            params.put("dob", dob.getText().toString());
            params.put("member_mob_no", contact.getText().toString());
            params.put("member_email", email_id.getText().toString());


           if(pincode.getText().toString().length()==0){

            }else {
               params.put("pincode", pincode.getText().toString());
               params.put("city", city.getText().toString());
               params.put("state", state.getText().toString());
            }


            if(building.getText().toString().length()==0){

            }else {
                params.put("address ", building.getText().toString());
            }



            if (bank_name_modal.getSelKey().equals("2")) {
                params.put("marriage_date", m_date.getText().toString());

            } else {

            }

            smr.setParams(params);
            rq.add(smr);


        }


    }

    public void setProfileDet() {
        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(AddFamilyDataActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);



                        String pincode_input = explrObject.getString("pincode");
                        String building_input = explrObject.getString("address");
                        String city_input = explrObject.getString("city");
                        String state_input = explrObject.getString("state");


                        if (pincode_input != "null" && !pincode_input.isEmpty()) {
                            pincode.setText(pincode_input);
                        }

                        if (building_input != "null" && !building_input.isEmpty()) {
                            building.setText(building_input);
                        }

                        if (city_input != "null" && !city_input.isEmpty()) {
                            city.setText(city_input);
                        }



                        if (state_input != "null" && !state_input.isEmpty()) {
                            state.setText(state_input);
                        }



                    }



                } catch (JSONException e) {
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
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);

        mRequestQueue.add(mStringRequest);

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

