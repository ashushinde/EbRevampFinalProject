package com.palm.newbenefit.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.AppHelper;
import com.palm.newbenefit.ApiConfig.VolleyMultiPartRequest;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.palm.newbenefit.Activity.MyPoliciesActivity;
import com.palm.newbenefit.Adapter.myPolicyAdapter;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.newbenefit.Module.Policy;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.R;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import java.util.Arrays;
import java.util.Calendar;
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
public class RenuewalFragment extends Fragment {

    int gg;
    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    List<Policy> ob = null;
    myPolicyAdapter adapter = null;
    RecyclerView recyclerView = null;
    int amountrs = 500;
    String emp_id;

    String company_id;
    ImageView info_text;
    Button group_cover,voluntary_cover;
    private RequestQueue requestQueue;

    Spinner policy_type_spin;
    EditText dte_of_adminsiion,discharge_date,policy_name;
    EditText company_name,sum_insured,premium;
    Button send;
    ProgressDialog progressDialog = null;
    LinearLayout my_insurance_cover_form;
    ImageView bankPreview1;
    String selBankPath_cheque = null;


    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;

    ArrayList<String> bank_named = null;
    ArrayList<SpinnerModal> bank_nameListd = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapterd = null;

    int day = 0;
    int month = 0;
    int year = 0;
    String _year;
    String _month, _day;
    Calendar newCalendar = Calendar.getInstance();
    RelativeLayout adhaarBackLayout_cheque1;
    LinearLayout hide;

    public static final int REQUEST_CODE_USER_PHOTOc = 114;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEc = 1890;
    LinearLayout vehical_linear,travel_linear;
    EditText reg_no,reg_date,location;
    Spinner dummy_spin;
    Button btn_policy;
    public RenuewalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_renuewal, container, false);
        requestMultiplePermissions();


        con = new Constants();


        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        recyclerView = v.findViewById(R.id.ghi_recycle);
        info_text= v.findViewById(R.id.info_text);
        btn_policy= v.findViewById(R.id.btn_policy);
        send= v.findViewById(R.id.send);
        my_insurance_cover_form= v.findViewById(R.id.my_insurance_cover_form);


        policy_type_spin= v.findViewById(R.id.policy_type_spin);
        dte_of_adminsiion= v.findViewById(R.id.dte_of_adminsiion);
        discharge_date= v.findViewById(R.id.discharge_date);
        company_name= v.findViewById(R.id.company_name);
        sum_insured= v.findViewById(R.id.sum_insured);
        premium= v.findViewById(R.id.premium);
        adhaarBackLayout_cheque1= v.findViewById(R.id.adhaarBackLayout_cheque1);
        hide= v.findViewById(R.id.hide);
        bankPreview1= v.findViewById(R.id.bankPreview1);
        policy_name= v.findViewById(R.id.policy_name);
        vehical_linear= v.findViewById(R.id.vehical_linear);
        reg_no= v.findViewById(R.id.reg_no);
        reg_date= v.findViewById(R.id.reg_date);
        location= v.findViewById(R.id.location);
        dummy_spin= v.findViewById(R.id.dummy_spin);
        travel_linear= v.findViewById(R.id.travel_linear);


        policy_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_city_modal = (SpinnerModal) adapterView.getSelectedItem();




                if(bank_city_modal.getSelKey().equalsIgnoreCase("1")||
                        bank_city_modal.getSelKey().equalsIgnoreCase("2")){

                    vehical_linear.setVisibility(View.VISIBLE);
                    travel_linear.setVisibility(View.GONE);

                }else if(bank_city_modal.getSelKey().equalsIgnoreCase("3")){
                    travel_linear.setVisibility(View.VISIBLE);
                    vehical_linear.setVisibility(View.GONE);

                }else {
                    vehical_linear.setVisibility(View.GONE);
                    travel_linear.setVisibility(View.GONE);
                }









            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        dummy_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




                dte_of_adminsiion.setText("");
                discharge_date.setText("");





            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getActivity(), MyPoliciesActivity.class);
                startActivity(intent);
            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count =0;
                SpinnerModal sp_query_typea = (SpinnerModal) policy_type_spin.getSelectedItem();

                if (sp_query_typea.getSelKey().equalsIgnoreCase("")) {
                    ++count;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Select Policy Type")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                    return;
                }


                if (sum_insured.getText().toString().trim().length() == 0) {
                    ++count;
                    sum_insured.setError("Add Sum Insurance");
                } else {
                    sum_insured.setError(null);
                }

                if (premium.getText().toString().trim().length() == 0) {
                    ++count;
                    premium.setError("Add Premium");
                } else {
                    premium.setError(null);
                }

                if (premium.getText().toString().trim().length() == 0) {
                    ++count;
                    premium.setError("Add Premium");
                } else {
                    premium.setError(null);
                }




                if(vehical_linear.getVisibility()==View.VISIBLE){
                    if (reg_no.getText().toString().trim().length() == 0) {
                        ++count;
                        reg_no.setError("Add Vehical Reg No.");
                    } else {
                        reg_no.setError(null);
                    }
                    if (reg_date.getText().toString().trim().length() == 0) {
                        ++count;
                        reg_date.setError("Select Reg Date");
                    } else {
                        reg_date.setError(null);
                    }

                }else {
                    reg_date.setError(null);
                    reg_no.setError(null);
                }


                if(travel_linear.getVisibility()==View.VISIBLE){
                    if (location.getText().toString().trim().length() == 0) {
                        ++count;
                        location.setError("Add Travel Location");
                    } else {
                        location.setError(null);
                    }
                    SpinnerModal dummy = (SpinnerModal) dummy_spin.getSelectedItem();

                    if (dummy.getSelKey().equalsIgnoreCase("")) {
                        ++count;
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Please Select Travel Trip Type")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();
                        return;
                    }

                }


                if(company_name.getText().toString().startsWith("0")){
                    ++count;
                    company_name.setError("Company Name Should not be start with 0");
                }else {
                    company_name.setError(null);
                }
                if(sum_insured.getText().toString().startsWith("0")){
                    ++count;
                    sum_insured.setError("Enter correct sum insured");
                }else {
                    sum_insured.setError(null);
                }
                if(premium.getText().toString().startsWith("0")){
                    ++count;
                    premium.setError("Enter correct premium");
                }else {
                    premium.setError(null);
                }

                if(bankPreview1.getVisibility()==View.VISIBLE){
                    if (selBankPath_cheque == null) {
                        ++count;
                        new AlertDialog.Builder(getActivity())
                                .setTitle("No File Selected")
                                .setMessage("Please Add Document")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                }).show();


                    }
                }

                if(count==0){
                    // saveBankDet();
                    callSaveBankDetailsApi();
                }


            }
        });



        adhaarBackLayout_cheque1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialoga();

            }
        });





        dte_of_adminsiion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {












                Utils.normal(getActivity(), new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        day = selectedDay;
                        month = selectedMonth;
                        year = selectedYear;

                        _day = String.valueOf(selectedDay);
                        _month = String.valueOf(selectedMonth);
                        _year = String.valueOf(selectedYear);


                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        dte_of_adminsiion.setText(date);


                     //   SpinnerModal bank_city_modal =(SpinnerModal) dummy_spin.getSelectedItem();




//                        if(bank_city_modal.getSelKey().equalsIgnoreCase("2")&&!(dte_of_adminsiion.getText().toString().equalsIgnoreCase("")))
//                        {
//
//
//                            DateFormat newDate = new SimpleDateFormat("dd-MM-yyyy");
//
//                            Calendar cal = Calendar.getInstance();
//
//                            cal.set(Calendar.DAY_OF_MONTH, selectedDay);
//                            cal.set(Calendar.MONTH, selectedMonth);
//                            cal.set(Calendar.YEAR, selectedYear);
//
//                            cal.add(cal.YEAR, 1); // to get previous year add -1
//                            Date nextYear = cal.getTime();
//                            String startDate = newDate.format(nextYear);
//
//
//
//
//
//                            discharge_date.setText(startDate);
//                            discharge_date.setEnabled(false);
//                            discharge_date.setFocusable(false);
//
//
//                        }else{
//                            discharge_date.setEnabled(true);
//                            discharge_date.setFocusable(true);
//                            discharge_date.setText("");
//
//                        }



                    }
                });

            }
        });

        discharge_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.maxdate(getActivity(), day, month, year, new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        discharge_date.setText(date);

                        //travel_request_todateshow.setText("");








                    }
                });

            }
        });



        reg_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.desablePostDatePicker(getActivity(), new MyDateListener() {
                    @Override
                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        newCalendar.set(Calendar.MONTH, selectedMonth);
                        newCalendar.set(Calendar.YEAR, selectedYear);

                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        reg_date.setText(date);

                        //travel_request_todateshow.setText("");
                    }
                });

            }
        });





        if (isNetworkAvailable()) {


            getBankName();
            defaultSpinner();

        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }








        return v;

    }

    private void showPhotoPickerDialoga() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_option_image, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_option_image));



                if (values.get(which).toLowerCase().equalsIgnoreCase("gallery")) {
                    openGalleryc();
                    // galleryImage();
                } else if(values.get(which).toLowerCase().equalsIgnoreCase("camera")){
                    openCamerac();
                    // cameraImage();
                }



            }
        });
        builder.show();
    }

    public void openGalleryc() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_USER_PHOTOc);
    }

    public void openCamerac() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEc);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEc) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");


                Uri tempUri = getImageUri(getActivity(), photo);


                File finalFile = new File(getRealPathFromURI(tempUri));


                hide.setVisibility(View.GONE);
                bankPreview1.setVisibility(View.VISIBLE);
                bankPreview1.setImageBitmap(photo);
                selBankPath_cheque = finalFile.getAbsolutePath();


            }
        } else if (requestCode == REQUEST_CODE_USER_PHOTOc) {

            if (resultCode == Activity.RESULT_OK) {



                Uri selectedImage = data.getData();
                File myFile = new File(selectedImage.toString());
                String file = myFile.getAbsolutePath();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                hide.setVisibility(View.GONE);
                bankPreview1.setImageBitmap(bitmap);
                bankPreview1.setVisibility(View.VISIBLE);
                selBankPath_cheque = cursor.getString(columnIndex);
                cursor.close();
            }


        }
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        if (path != null) {

        } else {
            path = "dfgfdg";
        }
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void getBankName() {

        String url = con.base_url+"/api/employee/get/insurer-type";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.d("queries",response);
                    JSONObject js=new JSONObject(response);
                    JSONArray jsonArr = js.getJSONArray("data");


                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Policy Type"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                        bank_name.add(jsonObj.getString("name"));
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    policy_type_spin.setAdapter(bank_nameAdapter);







                } catch (Exception e) {

                }

            }
        },   new Response.ErrorListener() {
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



    private void defaultSpinner() {


        bank_named = new ArrayList<>();
        bank_nameListd = new ArrayList<>();
        bank_nameListd.add(new SpinnerModal("", "Select Travel Trip Type"));
        bank_named.add("");

        bank_nameListd.add(new SpinnerModal("1", "Single Trip"));
        bank_named.add("");

        bank_nameListd.add(new SpinnerModal("2", "Annual Multi-Trip"));
        bank_named.add("");


        bank_nameAdapterd = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameListd);
        bank_nameAdapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dummy_spin.setAdapter(bank_nameAdapterd);







    }




    private void saveBankDet() {
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);


        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/api/employee/add/mypolicy";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            Log.d("save data",response);

                            progressDialog.dismiss();


                            JSONObject js=new JSONObject(response);

                            String status= String.valueOf(js.getBoolean("status"));




                            if (status.equals("true")) {
                                String message=js.getString("message");
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

                                            }
                                        }).show();

                            }else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error")
                                        .setMessage("The vehicle reg no format is invalid.")
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        }).show();
                            }
                        } catch (Exception e) {
                            Log.e("my catche", e.toString());
                            progressDialog.dismiss();

                        }
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };


        SpinnerModal bankSel = (SpinnerModal) policy_type_spin.getSelectedItem();
        SpinnerModal dummy = (SpinnerModal) dummy_spin.getSelectedItem();










        HashMap<String, String> params = new HashMap<>();
        params.put("insurer_type_id", bankSel.getSelKey());
        params.put("policy_no", bankSel.getSelKey());
        params.put("company_name", company_name.getText().toString());
        params.put("premium", premium.getText().toString());
        params.put("suminsured",sum_insured.getText().toString());
        params.put("start_date",dte_of_adminsiion.getText().toString());
        params.put("end_date",discharge_date.getText().toString());
        params.put("status", "1");
        params.put("emp_id", "154");


        params.put("image", selBankPath_cheque);

        Log.d("myimage", selBankPath_cheque);



        if(vehical_linear.getVisibility()==View.VISIBLE){
            params.put("vehicle_reg_no",reg_no.getText().toString().trim());
            params.put("vehicle_reg_date", reg_date.getText().toString().trim());



        }


        if(travel_linear.getVisibility()==View.VISIBLE){
            params.put("travel_location",location.getText().toString());
            params.put("travel_trip_type", dummy.getSelValue());

        }



        smr.setParams(params);
        rq.add(smr);
    }



    private void callSaveBankDetailsApi() {

        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);


        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/api/employee/add/mypolicy";

        VolleyMultiPartRequest multiPartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e("Response ", new String(response.data));
                        try {

                            progressDialog.dismiss();
                            //Toast.makeText(BankDetailExample.this, new String(response.data), Toast.LENGTH_LONG).show();
                            JSONObject js = new JSONObject(new String(response.data));


                            String status= String.valueOf(js.getBoolean("status"));




                            if (status.equals("true")) {
                                String message=js.getString("message");
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

                                            }
                                        }).show();

                            }else {



                                if(dte_of_adminsiion.getText().toString().trim().
                                        equalsIgnoreCase(discharge_date.getText().toString().trim())){

                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage("The end date must be a date after start date.")
                                            .setIcon(android.R.drawable.ic_dialog_info)
                                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                }
                                            }).show();

                                }else {
                                    JSONObject errors= js.getJSONObject("errors");

                                    JSONArray aaray=errors.getJSONArray("vehicle_reg_no");







                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage(aaray.getString(0))
                                            .setIcon(android.R.drawable.ic_dialog_info)
                                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                }
                                            }).show();
                                }

                            }
                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorResponse", error.toString());
                progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SpinnerModal bankSel = (SpinnerModal) policy_type_spin.getSelectedItem();
                SpinnerModal dummy = (SpinnerModal) dummy_spin.getSelectedItem();

                params.put("insurer_type_id", bankSel.getSelKey());
                params.put("policy_no", bankSel.getSelKey());
                params.put("company_name", company_name.getText().toString());
                params.put("premium", premium.getText().toString());
                params.put("suminsured",sum_insured.getText().toString());
                params.put("start_date",dte_of_adminsiion.getText().toString());
                params.put("end_date",discharge_date.getText().toString());
                params.put("status", "1");
                params.put("emp_id", "154");


//                params.put("image", selBankPath_cheque);
//
//                Log.d("myimage", selBankPath_cheque);



                if(vehical_linear.getVisibility()==View.VISIBLE){
                    params.put("vehicle_reg_no",reg_no.getText().toString().trim().replaceAll("\\s+", ""));
                    params.put("vehicle_reg_date", reg_date.getText().toString());



                }


                if(travel_linear.getVisibility()==View.VISIBLE){
                    params.put("travel_location",location.getText().toString());
                    params.put("travel_trip_type", dummy.getSelValue());

                }
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                try {



                    params.put("image", new DataPart(System.currentTimeMillis() + ".jpg", AppHelper.getFileDataFromDrawable(getContext(), bankPreview1.getDrawable()), "image/jpeg"));


                } catch (Exception e) {

                }




                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };


        rq.add(multiPartRequest);
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
    private void  requestMultiplePermissions(){
        Dexter.withActivity(getActivity())
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}

