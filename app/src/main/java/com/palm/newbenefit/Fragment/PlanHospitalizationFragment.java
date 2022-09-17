package com.palm.newbenefit.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
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
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import android.widget.AutoCompleteTextView;
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
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Adapter.ExampleAdapter;
import com.palm.newbenefit.ApiConfig.AppHelper;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.newbenefit.ApiConfig.VolleyMultiPartRequest;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.Module.SpinnerModalFamilyData;
import com.palm.newbenefit.R;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class PlanHospitalizationFragment extends Fragment {

Spinner policy_type_spin,policy_type_spin_no,patient_name_spin,state,city;

EditText insurar_name,tpa,employer_name,employee_name,sum_insured,
        relationdrop,email_id,contact
        ,doc_name,hos_date,discharge_date;

LinearLayout download_ecard;
    String user_id;
RelativeLayout adhaarBackLayout_cheque1,adhaarBackLayout_cheque1_two;
ImageView bankPreview1,bankPreview1_two;
    AutoCompleteTextView ailment;
    Constants con = null;
    Context context;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    String state_id="";
    ArrayList<String> bank_name = null;

    ArrayList<String> bank_cityc = null;
    ArrayList<SpinnerModalFamilyData> bank_cityListc = null;
    ArrayAdapter<SpinnerModalFamilyData> bank_cityAdapterc = null;

    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;
    String token;
    String emp_data;
    String employee_id;
    public PlanHospitalizationFragment() {
        // Required empty public constructor
    }
    String bank_name_value;
    private SimpleDateFormat dateFormatter;
    String[] days;
    ProgressDialog progressDialog = null;
    Calendar newCalendar;
    String claim_id = null;
    String mindate = null;
    String maxdate = null;
    String bank_city_value = "";
    int day_b ;
    int month_b;
    int year_b ;
    String displayName;

    ArrayList<String> bank_branch = null;
    ArrayList<SpinnerModal> bank_branchList = null;
    ArrayAdapter<SpinnerModal> bank_branchAdapter = null;

    AutoCompleteTextView hos_name;

    Bitmap myBitmap;
    String _year;
    String _month, _day;

    int day = 0;
    int month = 0;
    int year = 0;

    int day_a = 0;
    int month_a = 0;
    int year_a= 0;

    private ArrayList<String> mExampleList;
    private ArrayList<String> mExampleListA;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final int REQUEST_CODE_USER_PHOTO = 114;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1890;

    public static final int REQUEST_CODE_USER_PHOTO_two = 115;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_two = 1891;

    String selBankPath=null,selBankPath_cheque=null;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_plan_hospitalization, container, false);

        con=new Constants();
        requestMultiplePermissions();
        context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        policy_type_spin=v.findViewById(R.id.policy_type_spin);
        policy_type_spin_no=v.findViewById(R.id.policy_type_spin_no);
        patient_name_spin=v.findViewById(R.id.patient_name_spin);
        state=v.findViewById(R.id.state);
        city=v.findViewById(R.id.city);

        insurar_name=v.findViewById(R.id.insurar_name);
        tpa=v.findViewById(R.id.tpa);
        employer_name=v.findViewById(R.id.employer_name);
        employee_name=v.findViewById(R.id.employee_name);
        sum_insured=v.findViewById(R.id.sum_insured);
        relationdrop=v.findViewById(R.id.relationdrop);
        email_id=v.findViewById(R.id.email_id);
        contact=v.findViewById(R.id.contact);
        ailment=v.findViewById(R.id.ailment);
        hos_name=v.findViewById(R.id.hos_name);
        doc_name=v.findViewById(R.id.doc_name);
        hos_date=v.findViewById(R.id.hos_date);
        discharge_date=v.findViewById(R.id.discharge_date);
        submit=v.findViewById(R.id.submit);
        adhaarBackLayout_cheque1=v.findViewById(R.id.adhaarBackLayout_cheque1);
        adhaarBackLayout_cheque1_two=v.findViewById(R.id.adhaarBackLayout_cheque1_two);

        bankPreview1=v.findViewById(R.id.bankPreview1);
        bankPreview1_two=v.findViewById(R.id.bankPreview1_two);
        download_ecard=v.findViewById(R.id.download_ecard);
        ((MainActivity) getActivity()).setTitle("E-Cashless");

        GetEmployeeData();



        adhaarBackLayout_cheque1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialog1();
            }
        });

        adhaarBackLayout_cheque1_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialog_two();
            }
        });

        hos_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                Utils.desablePreDatePicker(getActivity(), new MyDateListener() {
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


                        day_b = selectedDay;
                        month_b =selectedMonth;
                        year_b = selectedYear;


                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        hos_date.setText(date);
                        discharge_date.setText("");


                        //travel_request_todateshow.setText("");
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

                        day_a = selectedDay;
                        month_a = selectedMonth;
                        year_a = selectedYear;

                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
                        discharge_date.setText(date);

                        //travel_request_todateshow.setText("");
                    }
                });
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dummy();
            }
        });
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) ;





                if (isNetworkAvailable()) {


                    createExampleListHospital();



                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }








            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) ;





                if (isNetworkAvailable()) {


                    getCity(bank_name_modal.getSelKey());



                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }








            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        policy_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) ;




                if (isNetworkAvailable()) {

                    getBankNamenumber(bank_name_modal.getSelKey());


                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }










            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        download_ecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    SpinnerModalFamilyData bank_state_modal = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();


                    if (bank_state_modal.getSelKey().trim().length() == 0) {


                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Select Patient to download E-card")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();
                        return;
                    }else {
                        DownloadManager downloadManager = null;
                        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(bank_state_modal.getFamily_gender());
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        Long reference = downloadManager.enqueue(request);
                    }

                }catch (Exception e){

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Select Patient to download E-card")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                    return;
                }



            }
        });

        policy_type_spin_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals(""))





                    if (isNetworkAvailable()) {




                        getBankCity(bank_name_modal.getSelKey(), bank_city_value);
                        getAllStates(bank_name_modal.getSelKey());

                        insurar_name.setText(bank_name_modal.getBank_id());
                        tpa.setText(bank_name_modal.getMinage());
                        employer_name.setText(bank_name_modal.getMinvalue());
//                        sum_insured.setText(bank_name_modal.getData());
                        employee_name.setText(bank_name_modal.getEmployename());
                        createExampleList();

                        GetSumInsured();

                    }else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error?")
                                .setMessage("Please Check Your Internet Connection")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }











            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        patient_name_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModalFamilyData bank_city_modal = (SpinnerModalFamilyData) adapterView.getSelectedItem();
                if (!bank_city_modal.selValue.equals("")) {



                    email_id.setText(bank_city_modal.getBank_id());
                    contact.setText(bank_city_modal.getFamily_dob());
                    relationdrop.setText(bank_city_modal.getAge());




                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        return v;
    }
    private void showPhotoPickerDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_option_image, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_option_image));


                if (values.get(which).toLowerCase().equalsIgnoreCase("gallery")) {
                    openGallery();
                    // galleryImage();
                } else if (values.get(which).toLowerCase().equalsIgnoreCase("camera")) {
                    openCamera();
                    // cameraImage();
                }


            }
        });
        builder.show();
    }

    private void showPhotoPickerDialog_two() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_option_image, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_option_image));


                if (values.get(which).toLowerCase().equalsIgnoreCase("gallery")) {
                    openGallery_two();
                    // galleryImage();
                } else if (values.get(which).toLowerCase().equalsIgnoreCase("camera")) {
                    openCamera_two();
                    // cameraImage();
                }


            }
        });
        builder.show();
    }
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_USER_PHOTO);
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    public void openGallery_two() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_USER_PHOTO_two);
    }

    public void openCamera_two() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_two);
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

    void dummy() {


        int count = 0;

        SpinnerModalFamilyData bankSel = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();
        SpinnerModal citySel = (SpinnerModal) policy_type_spin.getSelectedItem();
        SpinnerModal branchSel = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal city_hos = (SpinnerModal) city.getSelectedItem();
        SpinnerModal state_hos = (SpinnerModal) state.getSelectedItem();
        SpinnerModal policyno = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        if (state_hos.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select State")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (policyno.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Policy Name")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }
        if (city_hos.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select City")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }




        if (bankSel.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Member Name")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        } else if (citySel.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select policy Type")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (branchSel.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Policy Number")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (hos_name.getText().toString().trim().length() ==0) {
            ++count;
            hos_name.setError("Hospital Name is Required");
        } else {
            if (hos_name.getText().toString().trim().length()<2) {
                ++count;
                hos_name.setError("Minimum 2 character required");
            }else {
                hos_name.setError(null);
            }

        }

        if (doc_name.getText().toString().trim().length() ==0) {

        } else {
            if (doc_name.getText().toString().trim().length()<2) {
                ++count;
                doc_name.setError("Minimum 2 character required");
            }else {
                doc_name.setError(null);
            }

        }
        if (tpa.getText().toString().trim().length() == 0) {
            ++count;
            tpa.setError("Tpa is Required");
        } else {
            tpa.setError(null);
        }


        if (employer_name.getText().toString().trim().length() == 0) {
            ++count;
            employer_name.setError("Re-Enter Bank Account Number");
        } else {
            employer_name.setError(null);
        }

        if (employee_name.getText().toString().trim().length() == 0) {
            ++count;
            employee_name.setError("Employee Name is Required");
        } else {
            employee_name.setError(null);
        }


        if (sum_insured.getText().toString().trim().length() == 0) {
            ++count;
            sum_insured.setError("Balance Sum Insured is Required");
        } else {
            sum_insured.setError(null);
        }

            if (selBankPath == null) {
                ++count;
                new AlertDialog.Builder(getActivity())
                        .setTitle("No File Selected")
                        .setMessage("Please add  Id Proof file to upload")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();



        }


        if (selBankPath_cheque == null) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("No File Selected")
                    .setMessage("Please add Doctor Prescription to upload")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();



        }

        if (count == 0) {
            progressDialog = ProgressDialog.show(getActivity(), "",
                    "Saving. Please wait...", true);


            try {

                // uploadPDF(displayName,pdffile);

                callSaveBankDetailsApi();


            } catch (Exception e) {

            }


        }

    }
    private void callSaveBankDetailsApi() {

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/api/admin/create/claim/intimate";


        VolleyMultiPartRequest multiPartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e("Response ", new String(response.data));
                        try {

                            progressDialog.dismiss();
                            //Toast.makeText(BankDetailExample.this, new String(response.data), Toast.LENGTH_LONG).show();
                            JSONObject js = new JSONObject(new String(response.data));
                            String status = String.valueOf(js.getBoolean("status"));
                            String message = js.getString("message");
                            if (status.equals("true")) {

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

                            } else {



                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        }).show();


                               /* new AlertDialog.Builder(BankDetailExample.this)
                                        .setTitle("Error")
                                        .setMessage(new String(response.data))
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        }).show();*/
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
                SpinnerModal citySel = (SpinnerModal) policy_type_spin_no.getSelectedItem();
                SpinnerModal branchSel = (SpinnerModal) city.getSelectedItem();
                SpinnerModal state_hos = (SpinnerModal) state.getSelectedItem();
                SpinnerModalFamilyData member_name = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();


            params.put("city_name", branchSel.getSelValue());
                params.put("state_name", state_hos.getSelValue());
                params.put("tpa_member_id", member_name.getSelValue());
                params.put("tpa_member_name", member_name.getSelKey());
                params.put("tpa_emp_id", citySel.getEmployee_id());

                params.put("status", "3");
                params.put("claim_type", "e-cashless");
                params.put("planned_date", hos_date.getText().toString().trim());

                if(discharge_date.getText().toString().trim().isEmpty()){

                }else {
                    params.put("discharge_date", discharge_date.getText().toString().trim());
                }
                if(doc_name.getText().toString().trim().isEmpty()){

                }else {
                    params.put("doctor_name", doc_name.getText().toString().trim());
                }


                params.put("email", email_id.getText().toString().trim());

                params.put("hospital_name",hos_name.getText().toString().trim());
                params.put("member_id", member_name.getSelValue());
                params.put("mobile_no",contact.getText().toString().trim());

                params.put("policy_id", citySel.getSelKey());
                params.put("reason", ailment.getText().toString().trim());
                params.put("relation", relationdrop.getText().toString().trim());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                try {


                        Log.d("image","No");
                        params.put("id_proof", new DataPart(System.currentTimeMillis() + ".jpg", AppHelper.getFileDataFromDrawable(getActivity(), bankPreview1.getDrawable()), "image/jpeg"));



                } catch (Exception e) {

                }


                try {

                    Log.d("image","No");
                    params.put("doctor_prescription", new DataPart(System.currentTimeMillis() + ".jpg", AppHelper.getFileDataFromDrawable(getActivity(), bankPreview1_two.getDrawable()), "image/jpeg"));

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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {


                Bitmap photo = (Bitmap) data.getExtras().get("data");


                Uri tempUri = getImageUri(getActivity(), photo);


                File finalFile = new File(getRealPathFromURI(tempUri));




                bankPreview1.setImageBitmap(photo);
                selBankPath = finalFile.getAbsolutePath();





            }
        } else if (requestCode == REQUEST_CODE_USER_PHOTO) {

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


                bankPreview1.setVisibility(View.VISIBLE);
                bankPreview1.setImageBitmap(bitmap);
                selBankPath = cursor.getString(columnIndex);
                cursor.close();



            }

        } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_two) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");


                Uri tempUri = getImageUri(getActivity(), photo);


                File finalFile = new File(getRealPathFromURI(tempUri));

                bankPreview1_two.setImageBitmap(photo);
                selBankPath_cheque = finalFile.getAbsolutePath();


            }
        } else if (requestCode == REQUEST_CODE_USER_PHOTO_two) {

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

                bankPreview1_two.setImageBitmap(bitmap);
                selBankPath_cheque = cursor.getString(columnIndex);
                cursor.close();
            }


        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
                cursor.close();
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    private void createExampleList() {
        mExampleList = new ArrayList<>();


        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/employee/ailment-with-hospital-mapping";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject resp= new JSONObject(response);

                            Log.d("alignament",response);





                                JSONArray jsonObj =resp.getJSONArray("data");

                                 for (int j = 0; j < jsonObj.length(); j++) {
                                    JSONObject jo_area = (JSONObject) jsonObj.get(j);

                                    String id = jo_area.getString("id");
                                    String name = jo_area.getString("ailment_name");


                                    mExampleList.add(name);


                                 }

                            ailment.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mExampleList));




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


        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();






        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", bank_state_modal.getSelKey());

        smr.setParams(params);
        rq.add(smr);

    }


    private void createExampleListHospital() {
        mExampleListA = new ArrayList<>();
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();

        String url = con.base_url+"/api/admin/get/networkhospital/details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject resp= new JSONObject(response);

                            Log.d("hospital Name",response);





                            JSONArray jsonObj =resp.getJSONArray("data");

                            for (int j = 0; j < jsonObj.length(); j++) {
                                JSONObject jo_area = (JSONObject) jsonObj.get(j);

                                String id = jo_area.getString("id");
                                String name = jo_area.getString("hospital_name");


                                mExampleListA.add(name);


                            }

                            hos_name.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mExampleListA));




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


        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();
        SpinnerModal citys = (SpinnerModal) city.getSelectedItem();




        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", bank_state_modal.getSelKey());
        params.put("city_name", citys.getSelKey());
        if(ailment.getText().toString().trim().isEmpty()){

        }else {
            params.put("ailment_id", bank_state_modal.getSelKey());

        }

        smr.setParams(params);
        rq.add(smr);

    }

    void GetEmployeeData(){
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

                        emp_data = explrObject.getString("employee_id");




                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id=employer_id;

                            //GetEmployeeId();
                            getBankName(bank_name_value);

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

    void GetSumInsured(){
        String url =con.base_url+"/api/employee/get/balance/suminsured";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject data=new JSONObject(response);

                            if(data.getString("status").equalsIgnoreCase("false")){
                                sum_insured.setText("0");

                            }else {
                                JSONObject jsonArr = data.getJSONObject("data");
                                sum_insured.setText(jsonArr.getString("balance_ipd_suminsured_amount"));

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
        params.put("employee_id", emp_data);

        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();


        params.put("policy_id", bank_state_modal.getSelKey());

        smr.setParams(params);
        rq.add(smr);
    }

    private void getBankName(final String set_bank_name) {
        //String url =con.base_url+"/api/employee/policies";
        String url =con.base_url+"/api/admin/get/policy/subtype";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject data=new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");





                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);

                                if(jsonObj.getString("policy_sub_type_name").equalsIgnoreCase("Group Mediclaim")){
                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("policy_sub_type_id"),
                                            jsonObj.getString("policy_sub_type_name")));
                                    bank_name.add(jsonObj.getString("policy_sub_type_name"));
                                }
                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


                            policy_type_spin.setAdapter(bank_nameAdapter);
                            // policy_type_spin_no.setAdapter(bank_nameAdapter);

                            if (!set_bank_name.equals(""))
                                policy_type_spin.setSelection(bank_nameAdapter.getPosition(bank_nameList.get(bank_name.indexOf(set_bank_name))));


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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };



        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employee_id);

        smr.setParams(params);
        rq.add(smr);
    }


    private void getBankNamenumber(final String set_bank_name) {
        String url =con.base_url+"/api/admin/get/policyno";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("response",response);


                            JSONObject data=new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");






                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();




                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);

                                if(jsonObj.getString("e_cashless_allowed").equalsIgnoreCase("0")){

                                }else {

                                    bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                            jsonObj.getString("policy_no"),
                                            jsonObj.getString("insurer_name"),
                                            jsonObj.getString("tpa_name"),
                                            jsonObj.getString("employer_name"),
                                            jsonObj.getString("employee_name"),
                                            jsonObj.getString("employee_id"),

                                            jsonObj.getString("balance_cover")));

                                    bank_name.add(jsonObj.getString("policy_no"));

                                }





                            }





                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);



                            policy_type_spin_no.setAdapter(bank_nameAdapter);



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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };




        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employee_id);
        params.put("policy_sub_type_id", set_bank_name);
        params.put("user_type_name", "Employee");

        smr.setParams(params);
        rq.add(smr);
    }

    private void getCity(final String set_bank_name) {
        SpinnerModal bank_name_modal = (SpinnerModal) policy_type_spin.getSelectedItem();
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();

        String url = con.base_url+"/api/admin/get/networkhospital/city";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject data=new JSONObject(response);
                            String status = String.valueOf(data.getBoolean("status"));
                            bank_branch = new ArrayList<>();
                            bank_branchList = new ArrayList<>();
                            bank_branchList.add(new SpinnerModal("", "Select Hospital City"));
                            bank_branch.add("");

                            if(status.equalsIgnoreCase("false")){

                            }else {
                                JSONArray jsonArr = data.getJSONArray("data");


                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);




                                    bank_branchList.add(new SpinnerModal(String.valueOf(jsonObj.getString("CITY_NAME")),
                                            jsonObj.getString("CITY_NAME")));
                                    bank_branch.add(jsonObj.getString("CITY_NAME"));
                                }




                            }
                            bank_branchAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_branchList);
                            bank_branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            city.setAdapter(bank_branchAdapter);


                        } catch (Exception e) {

                        }

                    }
                },new Response.ErrorListener() {
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

        SpinnerModal bank_state_modal = (SpinnerModal) policy_type_spin_no.getSelectedItem();




        HashMap<String, String> params = new HashMap<>();
        params.put("policy_id", bank_state_modal.getSelKey());


        params.put("state_name", set_bank_name);

        smr.setParams(params);
        rq.add(smr);
    }


    public void getBankCity(final String bank_name, final String set_bank_city) {
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            bank_cityc = new ArrayList<>();
                            bank_cityListc = new ArrayList<>();
                            JSONObject objectd=new JSONObject(response);
                            Log.d("Patient_name",response);

                            JSONArray jsonArr = objectd.getJSONArray("data");

                            bank_cityListc.add(new SpinnerModalFamilyData("Select Patient Name", "0","",""
                                    ,"","",""));
                            bank_cityc.add("0");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);



                                bank_cityListc.add(new SpinnerModalFamilyData(jsonObj.getString("name"),
                                        String.valueOf(jsonObj.getInt("member_id")),
                                        String.valueOf(jsonObj.getLong("mobile")),
                                        jsonObj.getString("email"),
                                        jsonObj.getString("relation_name"),

                                        jsonObj.getString("ecard_url"),
                                        ""));
                                bank_cityc.add(jsonObj.getString("name"));
                            }
                            bank_cityAdapterc = new ArrayAdapter<SpinnerModalFamilyData>(getActivity(), R.layout.spinner_item, bank_cityListc);
                            bank_cityAdapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            patient_name_spin.setAdapter(bank_cityAdapterc);

                            if (!bank_name.equals(""))
                                patient_name_spin.setSelection(bank_cityAdapterc.getPosition(bank_cityListc.get(bank_cityc.indexOf(state_id))));



                        } catch (Exception e) {

                        }

                    }
                },new Response.ErrorListener() {
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
        params.put("employee_id", emp_data);

        params.put("policy_id", bank_name);
        smr.setParams(params);
        rq.add(smr);
    }




    private void getAllStates(String state_id) {
        String url =con.base_url+"/api/admin/get/networkhospital/state";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();

        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                    JSONObject data=new JSONObject(response);
                    JSONArray jsonArr = data.getJSONArray("data");


                    bank_city = new ArrayList<>();
                    bank_cityList = new ArrayList<>();
                    bank_cityList.add(new SpinnerModal("", "Select Hospital State"));
                    bank_city.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);


                        bank_cityList.add(new SpinnerModal(String.valueOf(jsonObj.getString("state_name")),
                                jsonObj.getString("state_name")));
                        bank_city.add(jsonObj.getString("state_name"));
                    }


                    bank_cityAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_cityList);
                    bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    state.setAdapter(bank_cityAdapter);



                    if (!state_id.equals(""))
                        state.setSelection(bank_cityAdapter.getPosition(bank_cityList.get(bank_city.indexOf(state_id))));

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
        params.put("policy_id", state_id);

        smr.setParams(params);
        rq.add(smr);

    }


    public void getBankbranch(final String bank_cityj) {

        String url = con.base_url+"/api/employee/get/relation";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);

                   // relation_type_spin.setAdapter(bank_nameAdapter);

                    if (!bank_cityj.equals("")){

                      //  int posi = bank_nameAdapter.getPosition(bank_nameList.get(bank_name.indexOf(bank_cityj)));


                       // relation_type_spin.setSelection(posi);

                        relationdrop.setText(bank_cityj);

                    }


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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        rq.add(mStringRequest);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
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