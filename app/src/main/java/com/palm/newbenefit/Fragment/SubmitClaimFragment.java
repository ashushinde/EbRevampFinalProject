package com.palm.newbenefit.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.AppHelper;
import com.palm.newbenefit.ApiConfig.VolleyMultiPartRequest;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.palm.newbenefit.Activity.EditDataActivity;
import com.palm.newbenefit.Activity.MainActivity;
import com.palm.newbenefit.Activity.ViewDataActivty;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.DateTimeFormater;
import com.palm.newbenefit.ApiConfig.MyDateListener;
import com.palm.newbenefit.ApiConfig.Utils;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Module.BillAllData;
import com.palm.newbenefit.Module.BillData;
import com.palm.newbenefit.Module.BillDate;
import com.palm.newbenefit.Module.Claim_amount;
import com.palm.newbenefit.Module.Cost;
import com.palm.newbenefit.Module.ImageData;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.Module.SpinnerModalFamilyData;
import com.palm.newbenefit.Module.comment;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitClaimFragment extends Fragment {

    String bank_name_value, state_balue, disease_value = "";
    LinearLayout user_detail, hospital_detail, claim_detail;
    TextView cost_summary, emp_name, clear_user_btn, add_user_btn;
    EditText email_id, contact, hos_date, disease_name, discharge_date, hos_address, hos_reason, bill_no, bill_date, bill_amount, comment, type;
    Button hos_add_button;
    AutoCompleteTextView hos_name;
    Spinner policy_type_spin, policy_type_spin_no, patient_name_spin, relation_type_spin, city, state;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    EditText relationdrop;
    ArrayList<String> bank_name = null;
    String user_id;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    ArrayList<SpinnerModal> bank_nameList_city = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter_city = null;
    ArrayList<String> bank_name_city = null;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEa = 1891;
    public static final int REQUEST_CODE_USER_PHOTOc = 114;
    public static final int REQUEST_CODE_USER_PHOTOa = 115;
    ArrayList<SpinnerModal> bank_nameList_state = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter_state = null;
    ArrayList<String> bank_name_state = null;
    private List<ImageData> ob;
    Constants con = null;
    Context context;
    ArrayList<String> relation_Status = null;
    ArrayList<SpinnerModal> Relation_list = null;
    ArrayAdapter<SpinnerModal> RelationAdapter = null;
    ArrayList<String> bank_cityc = null;
    ArrayList<SpinnerModalFamilyData> bank_cityListc = null;
    ArrayAdapter<SpinnerModalFamilyData> bank_cityAdapterc = null;
    String emp_data;
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;

    String bank_city_value = "";
    RelativeLayout a, b, c;
    String selProfilePath;
    String token, name;
    byte[] inputData;
    String emailPattern = "[a-zA-Z0-9\\\\+\\\\.\\\\_\\\\%\\\\-\\\\+]{1,256}\" + \"\\\\@\"\n" +
            "                    + \"[a-zA-Z0-9][a-zA-Z0-9\\\\-]{0,64}\" + \"(\" + \"\\\\.\"\n" +
            "                    + \"[a-zA-Z0-9][a-zA-Z0-9\\\\-]{0,25}\" + \")+";


    LinearLayout sudexo_next, hospital_details, claim_submit;


    public SubmitClaimFragment() {
        // Required empty public constructor
    }

    private List<Drawable> Image_List = null;

    ProgressDialog progressDialog = null;

    String claim_id = null;
    String mindate = null;
    String maxdate = null;
    Calendar newCalendar;
    int day_b;
    int month_b;
    int year_b;
    String _year;
    String _month, _day;
    int day = 0;
    int month = 0;
    int year = 0;

    int day_a = 0;
    int month_a = 0;
    int year_a = 0;
    String displayName;
    private SimpleDateFormat dateFormatter;
    private Uri filePath;
    private List<BillData> bill_no_list;
    private List<BillDate> bill_date_list = new ArrayList<>();
    private List<Claim_amount> bill_amount_list = new ArrayList<>();
    private List<Cost> bill_type_list = new ArrayList<>();
    private List<com.palm.newbenefit.Module.comment> bill_comment_list = new ArrayList<>();
    DBHelper db;
    Button send, view_data;

    BillData Billno;
    BillDate Billdate;
    Cost typedata;
    comment comdata;
    Claim_amount claimamt;
    String data_override = "NO";
    String id = null;
    TextView bankBtn_cheque;
    ImageView bankPreview_cheque;



    ArrayList<String> bank_branch = null;
    ArrayList<SpinnerModal> bank_branchList = null;
    ArrayAdapter<SpinnerModal> bank_branchAdapter = null;

    Bitmap myBitmap;

    Uri picUri;
    Button save_doc, view_doc;
    TextView add_doc, claim_clear_btn;
    ImageView viewImage;
    LinearLayout hideh;
    RelativeLayout bf;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private Date ght;
    View claimLine1, claimLine2;
    private static final int PICK_FILE = 101;
    TextView pdf_document;
    String employee_id, myempid;
    Button group_cover, voluntary_cover;
    String Ipd = "1", Opd = "1";
    String state_id = "";
    LinearLayout dis_date_layout;
    TextView hos_date_head;
    EditText doc_name;
    long finlesize;
    String employer_id;
    Spinner claim_type_spin;
    ArrayList<String> BankName = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_submit_claim, container, false);
        isStoragePermissionGrantedfg();
        isStoragePermissionGranted();

        context = getActivity();

        Image_List = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();
        db = new DBHelper(getActivity());

        typedata = new Cost();
        Billdate = new BillDate();
        claimamt = new Claim_amount();
        comdata = new comment();
        Billno = new BillData();

        con = new Constants();
        setHasOptionsMenu(true);
        group_cover = v.findViewById(R.id.group_cover);
        voluntary_cover = v.findViewById(R.id.voluntary_cover);
        claim_type_spin = v.findViewById(R.id.claim_type_spin);
        con = new Constants();
        ((MainActivity) getActivity()).setTitle("");
        db.removeAll();

        pdf_document = (TextView) v.findViewById(R.id.pdf_document);
        bf = (RelativeLayout) v.findViewById(R.id.adhaarBackLayout_cheque);
        viewImage = (ImageView) v.findViewById(R.id.bankPreview);
        hideh = (LinearLayout) v.findViewById(R.id.hideh);
        claimLine1 = v.findViewById(R.id.claimLine1);
        claimLine2 = v.findViewById(R.id.claimLine2);
        hos_date_head = v.findViewById(R.id.hos_date_head);
        dis_date_layout = v.findViewById(R.id.dis_date_layout);
        sudexo_next = v.findViewById(R.id.sudexo_next);
        hospital_details = v.findViewById(R.id.hospital_details);
        claim_submit = v.findViewById(R.id.claim_submit);
        doc_name = v.findViewById(R.id.doc_name);


        Log.d("api_token",token);


        group_cover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                group_cover.setBackgroundResource(R.drawable.nav_back_tab);
                voluntary_cover.setBackgroundResource(R.drawable.tab_curve);
                voluntary_cover.setTextColor(Color.BLACK);
                group_cover.setTextColor(Color.WHITE);

                Ipd = "0";

                hos_date_head.setText("Hospitalization Date");
                dis_date_layout.setVisibility(View.VISIBLE);
            }
        });


        voluntary_cover.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                voluntary_cover.setBackgroundResource(R.drawable.nav_back_tab);

                group_cover.setBackgroundResource(R.drawable.tab_curve);
                group_cover.setTextColor(Color.BLACK);
                voluntary_cover.setTextColor(Color.WHITE);

                Opd = "0";

                hos_date_head.setText("Opd Date");
                dis_date_layout.setVisibility(View.GONE);

            }
        });


        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isStoragePermissionGranted();
                showPhotoPickerDialog();


            }
        });



      /*  bankBtn_cheque = v.findViewById(R.id.bankBtn_cheque);
        bankPreview_cheque = v.findViewById(R.id.bankPreview_cheque);
*/
        claim_clear_btn = v.findViewById(R.id.claim_clear_btn);
        add_doc = v.findViewById(R.id.add_doc);
        save_doc = v.findViewById(R.id.save_doc);
        view_doc = v.findViewById(R.id.view_doc);
       /* bankBtn_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStoragePermissionGrantedf();
            isStoragePermissionGranted();
                selectImage(getActivity());
            }
        });*/

        user_detail = (LinearLayout) v.findViewById(R.id.user_detail);
        hospital_detail = (LinearLayout) v.findViewById(R.id.hospital_detail);
        claim_detail = (LinearLayout) v.findViewById(R.id.claim_detail);

        emp_name = v.findViewById(R.id.emp_name);

        emp_name.setText(name);


        send = (Button) v.findViewById(R.id.send);
        email_id = (EditText) v.findViewById(R.id.email_id);
        contact = (EditText) v.findViewById(R.id.contact);

        hos_date = (EditText) v.findViewById(R.id.hos_date);
        discharge_date = (EditText) v.findViewById(R.id.discharge_date);
        hos_name = (AutoCompleteTextView) v.findViewById(R.id.hos_name);
        hos_address = (EditText) v.findViewById(R.id.hos_address);
        hos_reason = (EditText) v.findViewById(R.id.hos_reason);
        bill_no = (EditText) v.findViewById(R.id.bill_no);
        bill_date = (EditText) v.findViewById(R.id.bill_date);
        bill_amount = (EditText) v.findViewById(R.id.bill_amount);
        comment = (EditText) v.findViewById(R.id.comment);
        type = (EditText) v.findViewById(R.id.type);


        clear_user_btn = v.findViewById(R.id.clear_user_btn);
        add_user_btn = v.findViewById(R.id.add_user_btn);

        hos_add_button = (Button) v.findViewById(R.id.hos_add_button);
        view_data = (Button) v.findViewById(R.id.view_data);
        policy_type_spin = (Spinner) v.findViewById(R.id.policy_type_spin);
        policy_type_spin_no = (Spinner) v.findViewById(R.id.policy_type_spin_no);
        patient_name_spin = (Spinner) v.findViewById(R.id.patient_name_spin);
        relation_type_spin = (Spinner) v.findViewById(R.id.relation_type_spin);
        relationdrop = (EditText) v.findViewById(R.id.relationdrop);
        city = (Spinner) v.findViewById(R.id.city);
        state = (Spinner) v.findViewById(R.id.state);
        disease_name = (EditText) v.findViewById(R.id.disease_name);


        a = (RelativeLayout) v.findViewById(R.id.a);
        b = (RelativeLayout) v.findViewById(R.id.b);
        c = (RelativeLayout) v.findViewById(R.id.c);

        GetEmployeeIdData();

        EmployeeDetail();
        GetEmployeeData();
        getClaimType();


        clear_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact.setText("");
                hos_date.setText("");
                email_id.setText("");
                discharge_date.setText("");


                if (isNetworkAvailable()) {


                    getBankName(bank_name_value);
                    getBankNamenumber(bank_name_value);

                    SpinnerModal bank_branch_modal = (SpinnerModal) policy_type_spin.getSelectedItem();


                    getBankCity(bank_branch_modal.getSelValue(), "Select Patient Name");

                    SpinnerModalFamilyData bank_branch_modalf = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();
                    getBankbranch(bank_branch_modalf.getSelValue());

                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }

        });


        claim_clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  selProfilePath = null;
                viewImage.setImageBitmap(null);*/
                hideh.setVisibility(View.VISIBLE);
                viewImage.setVisibility(View.GONE);
                pdf_document.setVisibility(View.GONE);


            }
        });


        if (isNetworkAvailable()) {


            getAllStates(state_id);


        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }


        //getCity(bank_name_value);

        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), EditDataActivity.class);
                startActivity(intent);


            }
        });


        view_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), ViewDataActivty.class);
                startActivity(intent);


            }
        });


        hos_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.desablePostDatePicker(getActivity(), new MyDateListener() {
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
                        month_b = selectedMonth;
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


                Utils.maxdateAdult(getActivity(), day, month, year, new MyDateListener() {
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


        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new
                DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd-MM-yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        bill_date.setText(sdf.format(myCalendar.getTime()));
                    }

                };
//        bill_date.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                    new DatePickerDialog(getActivity(), date, myCalendar
//                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                }
//            }
//        });


        bill_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


//                Utils.maxdateAdultBill(getActivity(), day_b,day_a, month_b,month_a, year_b,year_a, new MyDateListener() {
//                    @Override
//                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
//                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
//                        newCalendar.set(Calendar.MONTH, selectedMonth);
//                        newCalendar.set(Calendar.YEAR, selectedYear);
//
//                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
//                        bill_date.setText(date);
//
//                        //travel_request_todateshow.setText("");
//                    }
//                });


//                Utils.normal(getActivity(), new MyDateListener() {
//                    @Override
//                    public void onDateSelect(int selectedYear, int selectedMonth, int selectedDay) {
//                        newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
//                        newCalendar.set(Calendar.MONTH, selectedMonth);
//                        newCalendar.set(Calendar.YEAR, selectedYear);
//
//
//                        String date = DateTimeFormater.getFormatedDate(newCalendar.getTime());
//                        bill_date.setText(date);
//
//                        //travel_request_todateshow.setText("");
//                    }
//                });
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


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Date d = null;
                    try {
                        d = sdf.parse(bill_date.getText().toString());
                        Date startDate = sdf.parse(hos_date.getText().toString());
                        Date endDate = sdf.parse(discharge_date.getText().toString());

                        if ((d.after(startDate) && (d.before(endDate))) ||
                                (bill_date.getText().toString().equals(sdf.format(startDate)) ||
                                        bill_date.getText().toString().equals(sdf.format(endDate)))) {
                            type.setText("Hospitalization");
                        } else if (bill_date.getText().toString().compareTo(discharge_date.getText().toString()) > 0) {
                            Log.i("app", "Date1 is after Date2");
                            type.setText("Post-Hospitalization");
                        } else if (bill_date.getText().toString().compareTo(hos_date.getText().toString()) < 0) {
                            type.setText("Pre-Hospitalization");
                        } else if (bill_date.getText().toString().compareTo(discharge_date.getText().toString()) == 0) {
                            type.setText("Hospitalization");
                        } else if (bill_date.getText().toString().compareTo(hos_date.getText().toString()) == 0) {
                            type.setText("Hospitalization");
                        } else {
                            type.setText("opd");

                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.i("date crash", e.toString());
                        type.setText("opd");
                    }


                }
            }

            private boolean filterLongEnough() {
                return bill_date.getText().toString().trim().length() > 0;
            }
        };


        if (isNetworkAvailable()) {


            bill_date.addTextChangedListener(fieldValidatorTextWatcher);


        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }


        user_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.setVisibility(View.VISIBLE);
                b.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                claimLine1.setBackgroundResource(R.drawable.dash_line_);
                claimLine2.setBackgroundResource(R.drawable.dash_line_);

                sudexo_next.setBackgroundResource(R.drawable.circle_green);
                hospital_details.setBackgroundResource(R.drawable.circle_normal);
                claim_submit.setBackgroundResource(R.drawable.circle_normal);
               /* user_detail.setBackgroundResource(R.drawable.sudexo_cardview);
                hospital_detail.setBackgroundResource(R.drawable.edit_back);
                claim_detail.setBackgroundResource(R.drawable.edit_back);*/
            }
        });


        add_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable()) {


                    SendData();


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });


        add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               /* Log.d("claim_id",claim_id);
                Log.d("id",id);
*/
//                if (claim_id==null && id==null) {
//
//                    new AlertDialog.Builder(getActivity())
//                            .setTitle("Alert")
//                            .setMessage("Please Submit User Form")
//                            .setIcon(android.R.drawable.btn_dialog)
//                            .setNegativeButton(android.R.string.ok, null).show();
//
//                } else {


                if (isNetworkAvailable()) {


                    if (selProfilePath.equalsIgnoreCase("null")) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Kindly Upload Document")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    } else {
                        SendHospitalDoc();


                        add_doc.setText("Add More");
                        save_doc.setVisibility(View.VISIBLE);
                        view_doc.setVisibility(View.VISIBLE);
                    }


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });


        hos_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               /* if (claim_id == null) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Alert")
                            .setMessage("Please Submit User Form")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();

                } else {
*/


                if (isNetworkAvailable()) {


                    SendHospital();


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if (claim_id == null) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Alert")
                            .setMessage("Please Submit User Form")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();

                } else {

*/

                if (isNetworkAvailable()) {


                    SendDataToServer();


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });


        save_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isNetworkAvailable()) {

                    if (selProfilePath.equalsIgnoreCase("null")) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Kindly upload Document")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    } else {
                        // SendDataToServerFinal();

                        callSaveBankDetailsApi();
                    }


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });


        hospital_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setVisibility(View.VISIBLE);
                a.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                claimLine1.setBackgroundResource(R.drawable.dash_line_active);
                claimLine2.setBackgroundResource(R.drawable.dash_line_);

                hospital_details.setBackgroundResource(R.drawable.circle_green);
                sudexo_next.setBackgroundResource(R.drawable.circle_normal);
                claim_submit.setBackgroundResource(R.drawable.circle_normal);


             /*   hospital_detail.setBackgroundResource(R.drawable.sudexo_cardview);
                user_detail.setBackgroundResource(R.drawable.edit_back);
                claim_detail.setBackgroundResource(R.drawable.edit_back);*/
            }
        });


        claim_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setVisibility(View.VISIBLE);
                b.setVisibility(View.GONE);
                a.setVisibility(View.GONE);

                claimLine1.setBackgroundResource(R.drawable.dash_line_);
                claimLine2.setBackgroundResource(R.drawable.dash_line_active);


                claim_submit.setBackgroundResource(R.drawable.circle_green);
                sudexo_next.setBackgroundResource(R.drawable.circle_normal);
                hospital_details.setBackgroundResource(R.drawable.circle_normal);
               /* claim_detail.setBackgroundResource(R.drawable.sudexo_cardview);
                user_detail.setBackgroundResource(R.drawable.edit_back);
                hospital_detail.setBackgroundResource(R.drawable.edit_back);*/
            }
        });


        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) ;


                if (isNetworkAvailable()) {


                    getCity(bank_name_modal.getSelKey());


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) ;


                if (isNetworkAvailable()) {


                    getHospitalName();


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
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


                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        policy_type_spin_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals(""))


                    if (isNetworkAvailable()) {


                        getBankCity(bank_name_modal.getSelKey(), bank_city_value);

                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
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


                    if (bank_city_modal.getBank_id().equalsIgnoreCase("null")) {
                        email_id.setText("");
                    } else {
                        email_id.setText(bank_city_modal.getBank_id());
                    }

                    if (bank_city_modal.getFamily_dob().equalsIgnoreCase("0")
                            || bank_city_modal.getFamily_dob().equalsIgnoreCase("null")) {
                        contact.setText("");
                    } else {
                        contact.setText(bank_city_modal.getFamily_dob());
                    }


                    relationdrop.setText(bank_city_modal.getAge());


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        relation_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_city_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_city_modal.selValue.equals("")) ;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        GetEmployeeId();
        return v;


    }

    public boolean isStoragePermissionGrantedfg() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
        }
        return true;
    }


    private void showPhotoPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_options));

                if (values.get(which).toLowerCase().equalsIgnoreCase("gallery")) {
                    openGallery();
                    // galleryImage();
                } else if (values.get(which).toLowerCase().equalsIgnoreCase("camera")) {
                    openCamera();
                    // cameraImage();
                } else {
                    showFileChooser();
                }
            }
        });
        builder.show();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 2);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }


    public boolean isStoragePermissionGrantedf() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                return true;


            } else {


                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }

    public void openGallery() {
        try{
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
        }catch (Exception e){
            isStoragePermissionGrantedf();
            isStoragePermissionGrantedfg();
        }

    }

    public void openCamera() {



        try{
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, 0);
        }catch (Exception e){
            isStoragePermissionGrantedf();
            isStoragePermissionGrantedfg();
        }


    }

    @SuppressLint({"NewApi", "Range"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == getActivity().RESULT_OK) {
                  /*  Bundle extras = data.getExtras();
                    Bitmap photo = (Bitmap) extras.get("data");


                    Uri tempUri = getImageUri(context, photo);


                    File finalFile = new File(getRealPathFromURI(tempUri));*/


                    Bitmap photo = (Bitmap) data.getExtras().get("data");


                    Uri tempUri = getImageUri(getActivity(), photo);


                    File finalFile = new File(getRealPathFromURI(tempUri));


                    hideh.setVisibility(View.GONE);
                    viewImage.setVisibility(View.VISIBLE);
                    viewImage.setImageBitmap(photo);
                    selProfilePath = finalFile.getAbsolutePath();


                    pdf_document.setVisibility(View.GONE);

                }

                break;
            case 1:
                if (resultCode == getActivity().RESULT_OK) {

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


                    pdf_document.setVisibility(View.GONE);
                    hideh.setVisibility(View.GONE);
                    viewImage.setVisibility(View.VISIBLE);

                    viewImage.setImageBitmap(bitmap);

                    selProfilePath = cursor.getString(columnIndex);

                    cursor.close();


                }
                break;


            case 2:
                if (resultCode == getActivity().RESULT_OK) {

                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();


                    String displayName = null;

                    pdf_document.setVisibility(View.VISIBLE);
                    hideh.setVisibility(View.GONE);
                    viewImage.setVisibility(View.GONE);


                    String[] filePathColumn = {MediaStore.Images.Media.DATA};


                    displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.d("nameeeee>>>>  ", displayName);

                      /*  int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        path_document= cursor.getString(columnIndex);*/

                                // uploadPDF(displayName,uri);


                                pdf_document.setText(displayName);
                                pdf_document.setVisibility(View.VISIBLE);
                                hideh.setVisibility(View.GONE);
                                viewImage.setVisibility(View.GONE);
                                //selBankPath=uri;


                                InputStream iStream = null;
                                try {

                                    iStream = getActivity().getContentResolver().openInputStream(uri);


                                    inputData = getBytes(iStream);
                                } catch (Exception e) {

                                }
                                selProfilePath = displayName;


                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        Log.d("nameeeee>>>>  ", displayName);
                    }


                }
                break;
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

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        if (path == null) {
            path = "";
        } else {

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

        if (path == null) {
            path = "";
        } else {

        }
        return path;
    }


    void GetEmployeeData() {
        String url = con.base_url + "/api/admin/user";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);

                    Log.d("mydata", response);
                    JSONArray jsonObj = js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(0);


                        String employer_id = explrObject.getString("employer_id");

                        emp_data = explrObject.getString("employee_id");


                        if (employer_id != "null" && !employer_id.isEmpty()) {

                            employee_id = employer_id;

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


    private void getBankName(final String set_bank_name) {
        //String url =con.base_url+"/api/employee/policies";
        String url = con.base_url + "/api/admin/get/policy/subtype";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject data = new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");


                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);

                                if (jsonObj.getString("policy_sub_type_name").equalsIgnoreCase("Group Mediclaim")) {
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


    private void getClaimType() {

                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();

        bank_nameList.add(new SpinnerModal("Pre Hospitalization",
                "Pre Hospitalization"));
        bank_name.add("Pre Hospitalization");

        bank_nameList.add(new SpinnerModal("Post Hospitalization",
                "Post Hospitalization"));
        bank_name.add("Post Hospitalization");

        bank_nameList.add(new SpinnerModal("Hospitalization",
                "Hospitalization"));
        bank_name.add("Hospitalization");


                            bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                            bank_nameAdapter.setDropDownViewResource(R.layout.spinner_item);


                            claim_type_spin.setAdapter(bank_nameAdapter);
                            // policy_type_spin_no.setAdapter(bank_nameAdapter);


    }


    void GetSubType() {

        //String url =con.base_url+"/api/employee/policies";
        String url = con.base_url + "/api/admin/get/policy/subtype";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject data = new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");


                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);

                                String has_opd = jsonObj.getString("has_opd");
                                if (has_opd.equalsIgnoreCase("0")) {
                                    voluntary_cover.setVisibility(View.GONE);
                                } else {

                                    voluntary_cover.setVisibility(View.VISIBLE);

                                }

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


        HashMap<String, String> params = new HashMap<>();
        params.put("employer_id", employer_id);
        params.put("has_opd", "0");

        smr.setParams(params);
        rq.add(smr);


    }


    void GetEmployeeIdData(){
        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
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






                        employer_id= explrObject.getString("employer_id");

                        GetSubType();

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

    void GetEmployeeId() {
        String url = con.base_url + "/api/admin/get/existing-claim-data";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject js = new JSONObject(response);


                            JSONObject jsonObj = js.getJSONObject("data");


                            String status = String.valueOf(js.getBoolean("status"));


                            if (status.equalsIgnoreCase("true")) {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setMessage("Found saved claim, do you want to continue with it ? ");
                                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        GetExistingData();
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



                } catch (JSONException e) {
                    Log.d("MyClaimError", e.toString());
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
        rq.add(smr);

    }


    void GetExistingData() {
        String url = con.base_url + "/api/admin/get/existing-claim-data";

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject js = new JSONObject(response);


                            JSONObject jsonObj = js.getJSONObject("data");


                            String status = String.valueOf(js.getBoolean("status"));


                            if (status.equalsIgnoreCase("true")) {




                                String claim_submitted_data = jsonObj.getString("claim_submitted_data");


                                String str[] = claim_submitted_data.split(",");
                                List<String> al = new ArrayList<String>();
                                al = Arrays.asList(str);
                                for (String s : al) {
                                    System.out.println(s);
                                }


                                Log.d("mydata", al.toString());
                                JSONArray array = new JSONArray(al.toString());

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject data = null;
                                    try {
                                        data = array.getJSONObject(i);

                                        try{
                                            String step = data.getString("step");

                                            String email = data.getString("email_id");
                                            String mobile_no = data.getString("mobile_no");
                                            String relation = data.getString("relation");
                                            String admit_date = data.getString("admit_date");
                                            String discharge_datse = data.getString("discharge_date");
                                            String policyId = data.getString("policy_id");
                                            String member_name = data.getString("member_name");

                                            email_id.setText(email);
                                            contact.setText(mobile_no);
                                            hos_date.setText(relation);
                                            relationdrop.setText(relation);


                                            hos_date.setText(admit_date);

                                            discharge_date.setText(discharge_datse);
                                            relationdrop.setText(relation);

                                            getBankName(policyId);


                                            //getBankNamenumber(policyId);


                                            getBankCity(member_name, bank_city_value);
                                        }catch (Exception e){








                                            String email = data.getString("email");
                                            String mobile_no = data.getString("mobile_no");
                                            String relation = data.getString("relation");
                                            String admit_date = data.getString("admit_date");
                                            String discharge_datse = data.getString("discharge_date");

                                            String state_name = data.getString("state_name");
                                            String city_name = data.getString("city_name");
                                            String doctor_name = data.getString("doctor_name");
                                            String state_id = data.getString("state_id");
                                            String city_id = data.getString("city_id");
                                            String hospital_name = data.getString("hospital_name");
                                            String hospital_addres = data.getString("hospital_addres");
                                            String reason = data.getString("reason");
                                            String disease = data.getString("disease");
                                            String employerId = data.getString("employerId");
                                            String policyType = data.getString("policyType");
                                            String employeeId = data.getString("employeeId");
                                            String policyId = data.getString("policyId");
                                            String brokerId = data.getString("brokerId");

                                            String member_name = data.getString("member_name");
                                            hos_address.setText(hospital_addres);
                                            disease_name.setText(disease);

                                            //JSONArray filenames=data.getJSONArray("filenames");

                                            JSONObject tableBill = data.getJSONObject("tableBill");


                                            JSONArray bill_amt = tableBill.getJSONArray("bill_amt");
                                            JSONArray bill_date = tableBill.getJSONArray("bill_date");
                                            JSONArray comment = tableBill.getJSONArray("comment");
                                            JSONArray reimburment_type = tableBill.getJSONArray("reimburment_type");
                                            JSONArray bill_no = tableBill.getJSONArray("bill_no");


                                            for (int z = 0; z < comment.length(); z++) {
                                                boolean isInsertedbillNo = db.AddBillNo(new BillData(comment.getString(z)));
                                                boolean isd = db.AddAllData(new BillAllData(bill_no.getString(z),
                                                        bill_amt.getString(z),
                                                        bill_date.getString(z),
                                                        reimburment_type.getString(z),
                                                        comment.getString(z)));

                                            }

                                            for (int z = 0; z < bill_no.length(); z++) {
                                                boolean isInsertedbillNo = db.AddBillNo(new BillData(bill_no.getString(z)));
                                            }

                                            for (int z = 0; z < bill_amt.length(); z++) {
                                                boolean isInsertedbillNo = db.AddBillNo(new BillData(bill_amt.getString(z)));
                                            }
                                            for (int z = 0; z < bill_date.length(); z++) {
                                                boolean isInsertedbillNo = db.AddBillNo(new BillData(bill_date.getString(z)));
                                            }

                                            for (int z = 0; z < bill_no.length(); z++) {
                                                boolean isInsertedbillNo = db.AddBillNo(new BillData(reimburment_type.getString(z)));
                                            }

                                            getAllStates(state_id);

                                            email_id.setText(email);
                                            contact.setText(mobile_no);
                                            hos_date.setText(relation);
                                            relationdrop.setText(relation);

                                            hos_address.setText(hospital_addres);
                                            hos_date.setText(admit_date);
                                            disease_name.setText(disease);
                                            discharge_date.setText(discharge_datse);
                                            relationdrop.setText(relation);

                                            getBankName(policyId);


                                            //getBankNamenumber(policyId);


                                            getBankCity(member_name, bank_city_value);

                                        }


                                    } catch (JSONException jsonException) {
                                        Log.d("ClaimError", jsonException.toString());
                                    }


                                }


                            }


                        } catch (JSONException e) {
                            Log.d("MyClaimError", e.toString());
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
        rq.add(smr);

    }


    private void getBankNamenumber(final String set_bank_name) {
        String url = con.base_url + "/api/admin/get/policyno";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("response", response);


                            JSONObject data = new JSONObject(response);
                            JSONArray jsonArr = data.getJSONArray("data");


                            bank_name = new ArrayList<>();
                            bank_nameList = new ArrayList<>();

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);


                                bank_nameList.add(new SpinnerModal(jsonObj.getString("id"),
                                        jsonObj.getString("policy_no")));
                                bank_name.add(jsonObj.getString("policy_no"));

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
        String url = con.base_url + "/api/admin/get/city";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject data = new JSONObject(response);
                            String status = String.valueOf(data.getBoolean("status"));
                            bank_branch = new ArrayList<>();
                            bank_branchList = new ArrayList<>();
                            bank_branchList.add(new SpinnerModal("", "Select Hospital City"));
                            bank_branch.add("");

                            if (status.equalsIgnoreCase("false")) {

                            } else {
                                JSONArray jsonArr = data.getJSONArray("data");


                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);


                                    bank_branchList.add(new SpinnerModal(String.valueOf(jsonObj.getInt("id")), jsonObj.getString("city_name")));
                                    bank_branch.add(jsonObj.getString("city_name"));
                                }


                            }
                            bank_branchAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_branchList);
                            bank_branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            city.setAdapter(bank_branchAdapter);


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
        params.put("state_id", set_bank_name);

        smr.setParams(params);
        rq.add(smr);

    }



    private void getHospitalName() {
        BankName=new ArrayList<>();
        SpinnerModal bank_name_modal = (SpinnerModal) city.getSelectedItem();
        SpinnerModal bank_name_modals= (SpinnerModal) policy_type_spin_no.getSelectedItem();
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/api/admin/get/networkhospital/details";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject data = new JSONObject(response);
                            String status = String.valueOf(data.getBoolean("status"));

                            if (status.equalsIgnoreCase("false")) {

                            } else {
                                JSONArray jsonArr = data.getJSONArray("data");


                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);


                                    BankName.add(jsonObj.getString("hospital_name"));
                                }


                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getActivity(),
                                            android.R.layout.select_dialog_item, BankName);
                            //Getting the instance of AutoCompleteTextView

                            hos_name.setThreshold(1);//will start working from first character
                            hos_name.setAdapter(adapter);

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
        params.put("city_name", bank_name_modal.getSelValue());
        params.put("policy_id", bank_name_modals.getSelKey());

        smr.setParams(params);
        rq.add(smr);

    }


    public void getBankCity(final String bank_name, final String set_bank_city) {
        // RequestQueue rq = Volley.newRequestQueue(getActivity(), new HurlStack(null, getSocketFactory()));
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/api/admin/get/emp_member";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            bank_cityc = new ArrayList<>();
                            bank_cityListc = new ArrayList<>();
                            JSONObject objectd = new JSONObject(response);
                            Log.d("Patient_name", response);

                            JSONArray jsonArr = objectd.getJSONArray("data");

                            bank_cityListc.add(new SpinnerModalFamilyData("Select Patient Name", "0", "", ""
                                    , "", "", ""));
                            bank_cityc.add("0");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);


                                bank_cityListc.add(new SpinnerModalFamilyData(jsonObj.getString("name"),
                                        String.valueOf(jsonObj.getInt("member_id")),
                                        String.valueOf(jsonObj.getLong("mobile")),
                                        jsonObj.getString("email"),
                                        jsonObj.getString("relation_name"),

                                        "",
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

        params.put("policy_id", bank_name);

        smr.setParams(params);
        rq.add(smr);
    }


    private void getAllStates(String state_id) {
        String url = con.base_url + "/api/admin/get/state";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject data = new JSONObject(response);
                    JSONArray jsonArr = data.getJSONArray("data");


                    bank_city = new ArrayList<>();
                    bank_cityList = new ArrayList<>();
                    bank_cityList.add(new SpinnerModal("", "Select Hospital State"));
                    bank_city.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);


                        bank_cityList.add(new SpinnerModal(String.valueOf(jsonObj.getInt("id")),
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


    public void getBankbranch(final String bank_cityj) {

        String url = con.base_url + "/api/employee/get/relation";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);

                    Log.d("response", response);
                    JSONArray jsonArr = js.getJSONArray("data");
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

                    relation_type_spin.setAdapter(bank_nameAdapter);

                    if (!bank_cityj.equals("")) {

                        int posi = bank_nameAdapter.getPosition(bank_nameList.get(bank_name.indexOf(bank_cityj)));


                        relation_type_spin.setSelection(posi);

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


        SpinnerModalFamilyData patient = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();


        if (patient.getFamily_dob().trim().equalsIgnoreCase("")) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Patient Name")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (relationdrop.getText().toString().trim().length() == 0) {
            ++count;
            relationdrop.setError("Relationship  is Required");
        } else {
            relationdrop.setError(null);
        }


        if (email_id.getText().toString().trim().length() == 0) {
            ++count;
            email_id.setError("Email Id is Required");
        } else {
            email_id.setError(null);
        }


        if (Patterns.EMAIL_ADDRESS.matcher(email_id.getText().toString().trim()).matches()) {
            email_id.setError(null);
        } else {
            email_id.setError("Invalid Email");
            ++count;
        }


        if (contact.getText().toString().trim().isEmpty() || !isValid(contact.getText().toString().trim()) || contact.getText().toString().length() < 10 || contact.getText().toString().trim().length() > 13) {
            ++count;
            contact.setError("Invalid Mobile");
        } else {
            contact.setError(null);
        }


        if (dis_date_layout.getVisibility() == View.VISIBLE) {
            if (discharge_date.getText().toString().trim().length() == 0) {
                ++count;
                discharge_date.setError("Enter Discharge Date");
            } else {
                discharge_date.setError(null);
            }


            if (hos_date.getText().toString().trim().length() == 0) {
                ++count;
                hos_date.setError("OPD  Date is Required");
            } else {
                hos_date.setError(null);
            }
        } else {
            if (hos_date.getText().toString().trim().length() == 0) {
                ++count;
                hos_date.setError("Hospitalization  Date is Required");
            } else {
                hos_date.setError(null);
            }
        }


        if (count == 0) {

            progressDialog = ProgressDialog.show(getActivity(), "",
                    "Saving. Please wait...", true);
            RequestQueue rq = Volley.newRequestQueue(getActivity(),
                    new HurlStack(null, getSocketFactory()));

            rq.getCache().clear();
            String url = con.base_url + "/api/admin/submit/claim/step";
            StringRequest smr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();


                                JSONObject jsonObject = new JSONObject(response);

                                String status = String.valueOf(jsonObject.getBoolean("status"));


                                if (status.equalsIgnoreCase("false")) {
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Alert !")
                                            .setMessage("You Have Already Submited Claim On a Same Date Range")
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                } else {

//                                    if (status.equals("2")) {
//                                        new AlertDialog.Builder(getActivity())
//                                                .setTitle("Error")
//                                                .setMessage("You Have Already Submited Claim On a Same Date Range")
//                                                .setIcon(android.R.drawable.btn_dialog)
//                                                .setNegativeButton(android.R.string.ok, null).show();
//                                    } else if (status.equals("3")) {
//
//                                        new AlertDialog.Builder(getActivity())
//                                                .setTitle("Are You Sure?")
//                                                .setMessage("You Have Already Submiited Do U Want To New Claim!")
//                                                .setIcon(android.R.drawable.btn_dialog)
//                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                                                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                                                        datashow();
//                                                    }
//                                                })
//                                                .setNegativeButton(android.R.string.no, null).show();



                                    /*new AlertDialog.Builder(getActivity())
                                            .setTitle("Success")
                                            .setMessage("Your Claim Submitted Successfully")
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
*/

//                                        claim_id = jsonObject.getString("claim_id");
//                                        mindate = jsonObject.getString("min_date");
//                                        maxdate = jsonObject.getString("max_date");
//
//                                        String [] dateParts = maxdate.split("-");
//                                        year_a= Integer.parseInt(dateParts[0]);
//                                        month_a = Integer.parseInt(dateParts[1]);
//                                        day_a = Integer.parseInt(dateParts[2]);
//
//
//                                        String [] datePartss = mindate.split("-");
//                                        year_b = Integer.parseInt(datePartss[0]);
//                                        month_b = Integer.parseInt(datePartss[1]);
//                                        day_b = Integer.parseInt(datePartss[2]);

                                    b.setVisibility(View.VISIBLE);
                                    a.setVisibility(View.GONE);
                                    c.setVisibility(View.GONE);

                                    claimLine1.setBackgroundResource(R.drawable.dash_line_active);
                                    claimLine2.setBackgroundResource(R.drawable.dash_line_);

                                    hospital_details.setBackgroundResource(R.drawable.circle_green);
                                    sudexo_next.setBackgroundResource(R.drawable.circle_normal);
                                    claim_submit.setBackgroundResource(R.drawable.circle_normal);
                                       /* hospital_detail.setBackgroundResource(R.drawable.sudexo_cardview);
                                        user_detail.setBackgroundResource(R.drawable.edit_back);
                                        claim_detail.setBackgroundResource(R.drawable.edit_back);*/


                                }


                            } catch (Exception e) {
                                progressDialog.dismiss();


                            }

                        }
                    }, new Response.ErrorListener() {
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


            SpinnerModal bank_name_modal = (SpinnerModal) policy_type_spin.getSelectedItem();
            SpinnerModal bank_name_modald = (SpinnerModal) relation_type_spin.getSelectedItem();
            SpinnerModalFamilyData bank_name_modalpatient = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();


            HashMap<String, String> params = new HashMap<>();
            params.put("member_id", bank_name_modalpatient.getSelValue());
            params.put("admit_date", hos_date.getText().toString());
            params.put("discharge_date", discharge_date.getText().toString());
            params.put("policy_id", bank_name_modal.getSelKey());
            params.put("member_name", bank_name_modalpatient.getSelValue());

            params.put("email_id", email_id.getText().toString());
            params.put("mobile_no", contact.getText().toString());

            params.put("relation", relationdrop.getText().toString());
            params.put("step", "1");

            smr.setParams(params);
            rq.add(smr);


        }


    }


    public void datashow() {
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/claims_save_api";
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();


                            JSONObject jsonObject = new JSONObject(response);
                            String errorCode = jsonObject.getString("success");


                            claim_id = jsonObject.getString("claim_id");
                            mindate = jsonObject.getString("min_date");
                            maxdate = jsonObject.getString("max_date");


                            String[] dateParts = maxdate.split("-");
                            year_a = Integer.parseInt(dateParts[0]);
                            month_a = Integer.parseInt(dateParts[1]);
                            day_a = Integer.parseInt(dateParts[2]);


                            String[] datePartss = mindate.split("-");
                            year_b = Integer.parseInt(datePartss[0]);
                            month_b = Integer.parseInt(datePartss[1]);
                            day_b = Integer.parseInt(datePartss[2]);


                            b.setVisibility(View.VISIBLE);
                            a.setVisibility(View.GONE);
                            c.setVisibility(View.GONE);


                            hospital_details.setBackgroundResource(R.drawable.circle_green);
                            sudexo_next.setBackgroundResource(R.drawable.circle_normal);
                            claim_submit.setBackgroundResource(R.drawable.circle_normal);
                         /*   hospital_detail.setBackgroundResource(R.drawable.sudexo_cardview);
                            user_detail.setBackgroundResource(R.drawable.edit_back);
                            claim_detail.setBackgroundResource(R.drawable.edit_back);*/


                        } catch (Exception e) {
                            progressDialog.dismiss();

                        }

                    }
                }, new Response.ErrorListener() {
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


        SpinnerModal bank_name_modal = (SpinnerModal) policy_type_spin.getSelectedItem();
        SpinnerModal bank_name_modalpatient = (SpinnerModal) policy_type_spin.getSelectedItem();

        smr.addStringParam("patient_name", bank_name_modalpatient.getSelValue());
        smr.addStringParam("relationship_status", "0");
        smr.addStringParam("hospitalization_date", hos_date.getText().toString());
        smr.addStringParam("mobile_no", contact.getText().toString());
        smr.addStringParam("email_id", email_id.getText().toString());
        smr.addStringParam("discharge_date", discharge_date.getText().toString());

        smr.addStringParam("policy_numbers", bank_name_modal.getSelValue());
        smr.addStringParam("data_override", "YES");
        smr.addStringParam("claim_reimb_id", "");


        smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);


    }


    public void SendDataToServer() {

        int count = 0;


        claimLine1.setBackgroundResource(R.drawable.dash_line_);
        claimLine2.setBackgroundResource(R.drawable.dash_line_active);

        claim_submit.setBackgroundResource(R.drawable.circle_green);
        sudexo_next.setBackgroundResource(R.drawable.circle_normal);
        hospital_details.setBackgroundResource(R.drawable.circle_normal);

        send.setVisibility(View.GONE);


        c.setVisibility(View.VISIBLE);
        b.setVisibility(View.GONE);
        a.setVisibility(View.GONE);


    }


    public void EmployeeDetail() {
        String url = con.base_url + "/api/admin/user";
        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);

                    Log.d("response", response);
                    JSONArray jsonObj = js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);


                        String first_name_input = explrObject.getString("name");


                        if (first_name_input != "null" && !first_name_input.isEmpty()) {
                            emp_name.setText(first_name_input);
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


    public void SendHospital() {

        int count = 0;


        SpinnerModal patient = (SpinnerModal) city.getSelectedItem();
        SpinnerModal patienta = (SpinnerModal) state.getSelectedItem();


        if (patienta.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Hospital State")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (patient.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Select Hospital City")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }


        if (hos_name.getText().toString().trim().length() <2) {
            ++count;
            hos_name.setError("Hospital Name is Required(min 2 charachtor required)");
        } else {
            hos_name.setError(null);
        }


        if (hos_address.getText().toString().trim().length() <2) {
            ++count;
            hos_address.setError("Enter Hospital Address (min 2 charachtor required)");
        } else {
            hos_address.setError(null);
        }
        if (disease_name.getText().toString().trim().length()<2) {
            ++count;
            disease_name.setError("Disease Name is Required(min 2 charachtor required)");
        } else {
            disease_name.setError(null);
        }


      /*  if (hos_reason.getText().toString().trim().length() == 0) {
            ++count;
            hos_reason.setError("Enter Hospital Reason");
        } else {
            hos_reason.setError(null);
        }
*/

       /* if (hos_reason.getText().toString().trim().length() == 0) {
            ++count;
            hos_reason.setError("Enter Hospital Reason");
        } else {
            hos_reason.setError(null);
        }*/


        if (bill_no.getText().toString().trim().length() == 0) {
            ++count;
            bill_no.setError("Enter Bill No");
        } else {
            bill_no.setError(null);
        }


        if (bill_date.getText().toString().trim().length() == 0) {
            ++count;
            bill_date.setError("Enter Bill date");
        } else {
            bill_date.setError(null);
        }


        String x = bill_amount.getText().toString().trim();


        if (bill_amount.getText().toString().trim().length() == 0) {
            ++count;
            bill_amount.setError("Enter Bill Amount");
        } else {
            if (bill_amount.getText().toString().trim().startsWith("0")) {
                ++count;
                bill_amount.setError("Enter Valid Bill Amount");
            } else {
                bill_amount.setError(null);
            }
        }

        if (comment.getText().toString().trim().length() == 0) {
            ++count;
            comment.setError("Enter Comment");
        } else {
            comment.setError(null);
        }


        if (type.getText().toString().trim().length() == 0) {
            ++count;
            type.setError("Enter Reimbursement Type");

        } else {
            type.setError(null);
        }


        if (count == 0) {


            boolean isd = db.AddAllData(new BillAllData(bill_no.getText().toString().trim(), bill_amount.getText().toString()
                    , bill_date.getText().toString().trim(), type.getText().toString().trim(), comment.getText().toString()));

            boolean isInsertedbillamount = db.AddBillAmount(new Claim_amount(bill_amount.getText().toString()));
            boolean isInsertedbillDate = db.AddBillDate(new BillDate(bill_date.getText().toString()));


            boolean isInsertedbillNo = db.AddBillNo(new BillData(bill_no.getText().toString().trim()));
            boolean isInsertedbillType = db.AddBillCost(new Cost(type.getText().toString().trim()));
            boolean isInsertedbillComment = db.AddBillComment(new comment(comment.getText().toString()));


            if (isInsertedbillamount == true && isInsertedbillDate && isd == true && isInsertedbillNo == true && isInsertedbillType == true && isInsertedbillComment == true) {

                new AlertDialog.Builder(context)
                        .setTitle("Success")
                        .setMessage("Data Added Successfully !")
                        .setIcon(R.drawable.checkmark)
                        .setNegativeButton(android.R.string.ok, null).show();


                hos_add_button.setText("Add More");
                send.setVisibility(View.VISIBLE);
                view_data.setVisibility(View.VISIBLE);

                bill_date.setText("");
                bill_amount.setText("");
                bill_no.setText("");
                comment.setText("");
                type.setText("");


            } else {

                new AlertDialog.Builder(context)
                        .setTitle("Alert")
                        .setMessage("Please cannot enter double bill no !")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();

            }


        }


    }

    public void SendHospitalDoc() {

        int count = 0;

        if (selProfilePath.equalsIgnoreCase("null")) {
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("No File Selected")
                    .setMessage("Please Add File To Upload")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }

         /*   Log.d("size", String.valueOf(finlesize / 1024));
        }else if(finlesize / 1024 > 5000){
            ++count;
            new AlertDialog.Builder(getActivity())
                    .setTitle("No File Selected")
                    .setMessage("The document may not be greater than 5000 kilobytes.")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();

        }*/

        if (count == 0) {


            String data = String.valueOf(viewImage.getDrawable());


             //  boolean isInsertedbillComment = db.AddImage(new ImageData("anydata.jpg"));

              Image_List.add(viewImage.getDrawable());

             boolean isInsertedbillComment = db.AddImage(new ImageData(selProfilePath));


            if (isInsertedbillComment == true) {
                add_doc.setText("Add More");
                save_doc.setVisibility(View.VISIBLE);

                new AlertDialog.Builder(context)
                        .setTitle("Success")
                        .setMessage("Data Added Successfully !")
                        .setIcon(R.drawable.checkmark)
                        .setNegativeButton(android.R.string.ok, null).show();


            } else {

                new AlertDialog.Builder(context)
                        .setTitle("Alert")
                        .setMessage("Data  Not Added !")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();

            }


        }

    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public void SendDataToServerFinal() {

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);
        String url = con.base_url + "/api/admin/create/claim/submitclaim";
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();


                            JSONObject jsonObject = new JSONObject(response);
                            String errorCode = String.valueOf(jsonObject.getBoolean("status"));


                            if (errorCode.equalsIgnoreCase("true")) {


                                String message = jsonObject.getString("message");

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(R.drawable.checkmark)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                claim_id = null;
                                                id = null;

                                                DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();


                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();

                            } else {
                                String message = jsonObject.getString("errors");
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();
                            }


                        } catch (Exception e) {

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Error")
                                    .setMessage("Please Enter All The Details")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();
                            progressDialog.dismiss();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Please Enter All The Details")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
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


        try {
            bill_no_list = db.getBillNo();


            String date = bill_no_list.toString();
            String number = date.replaceAll("BillData", "");
            String numberData = number.replaceAll("bill_no=", "");
            String numberDataAray = numberData.replaceAll("[{}]", "");
            String BillNOData = numberDataAray.replaceAll("'", "\"");


            bill_amount_list = db.getBillAmount();
            String dateamt = bill_amount_list.toString();
            String numberamt = dateamt.replaceAll("Claim_amount", "");
            String numberDataamt = numberamt.replaceAll("claim_amount=", "");
            String numberDataArayamt = numberDataamt.replaceAll("[{}]", "");
            String BillAmountData = numberDataArayamt.replaceAll("'", "\"");


            bill_comment_list = db.getbillComment();
            String dateamtc = bill_comment_list.toString();
            String numberamtc = dateamtc.replaceAll("comment", "");
            String numberDataamtc = numberamtc.replaceAll("comment_arr=", "");
            String numberDataArayamtc = numberDataamtc.replaceAll("[{}]", "");
            String BillCommentData = numberDataArayamtc.replaceAll("'", "\"");
            String BillCommentDatad = BillCommentData.replaceAll("_arr=", "");


            bill_date_list = db.getbillDate();
            String dateamtcdt = bill_date_list.toString();
            String numberamtcdt = dateamtcdt.replaceAll("BillDate", "");
            String numberDataamtcdt = numberamtcdt.replaceAll("bill_date=", "");
            String numberDataArayamtcdt = numberDataamtcdt.replaceAll("[{}]", "");
            String BillDateData = numberDataArayamtcdt.replaceAll("'", "\"");


            bill_type_list = db.getbillCost();
            String dateamtcdtt = bill_type_list.toString();
            String numberamtcdtt = dateamtcdtt.replaceAll("Cost", "");
            String numberDataamtcdtt = numberamtcdtt.replaceAll("cost_arr=", "");
            String numberDataArayamtcdtt = numberDataamtcdtt.replaceAll("[{}]", "");
            String BillTypeData = numberDataArayamtcdtt.replaceAll("'", "\"");


            SpinnerModal bank_name_modalf = (SpinnerModal) policy_type_spin.getSelectedItem();

            SpinnerModalFamilyData bank_name_modalpatientf = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();


            SpinnerModal bank_name_modal = (SpinnerModal) policy_type_spin.getSelectedItem();
            SpinnerModal bank_name_modalpatient = (SpinnerModal) policy_type_spin.getSelectedItem();


            SpinnerModal bank_name_modalpatientd = (SpinnerModal) policy_type_spin_no.getSelectedItem();


            smr.addStringParam("member_id", bank_name_modalpatientf.getSelValue());
            smr.addStringParam("admit_date", hos_date.getText().toString());
            smr.addStringParam("discharge_date", discharge_date.getText().toString());
            smr.addStringParam("policy_id", bank_name_modalpatientd.getSelKey());
            smr.addStringParam("member_name", bank_name_modalpatient.getSelValue());

            smr.addStringParam("email", email_id.getText().toString());
            smr.addStringParam("mobile_no", contact.getText().toString());

            smr.addStringParam("relation", relationdrop.getText().toString());
            smr.addStringParam("step", "2");


            SpinnerModal bank_name_modalc = (SpinnerModal) city.getSelectedItem();
            SpinnerModal bank_name_modalpatientc = (SpinnerModal) state.getSelectedItem();


            JSONObject jsonObject = new JSONObject();

            try {


                jsonObject.put("bill_no", BillNOData);
                jsonObject.put("bill_date", BillDateData);
                jsonObject.put("comment", BillCommentDatad);
                jsonObject.put("reimburment_type", BillTypeData);
                jsonObject.put("bill_amt", BillAmountData);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //smr.addStringParam("tableBill", jsonObject.toString());

            smr.addStringParam("bill_no", BillNOData);
            smr.addStringParam("bill_date", BillDateData);
            smr.addStringParam("comment", BillCommentDatad);
            smr.addStringParam("reimburment_type", BillTypeData);
            smr.addStringParam("bill_amt", BillAmountData);
            smr.addStringParam("claim_hospitalization_type", BillTypeData);


            smr.addStringParam("hospital_addres", hos_address.getText().toString());
            smr.addStringParam("doctor_name", hos_name.getText().toString());

            smr.addStringParam("hospital_name", hos_name.getText().toString());

            if (hos_reason.getText().toString().trim().isEmpty()) {
                smr.addStringParam("reason", "NA");
            } else if (hos_reason.getText().toString().trim().length() == 0) {
                smr.addStringParam("reason", "NA");

            } else {
                smr.addStringParam("reason", hos_reason.getText().toString());
            }

            smr.addStringParam("disease", disease_name.getText().toString());
            smr.addStringParam("state_id", bank_name_modalpatientc.getSelKey());
            smr.addStringParam("city_id", bank_name_modalc.getSelKey());

            smr.addStringParam("state_name", bank_name_modalpatientc.getSelValue());
            smr.addStringParam("city_name", bank_name_modalc.getSelValue());
            smr.addStringParam("status", "1");
            smr.addStringParam("emp_id", emp_data);
            smr.addStringParam("agent", "mobile");

            if (Ipd.equalsIgnoreCase("0")) {
                smr.addStringParam("claim_type", "1");

            } else {
                smr.addStringParam("claim_type", "0");

            }

            ob = db.getImage();
            ArrayList<String> n_First_name = null;

            n_First_name = new ArrayList<>();
            for (ImageData background1 : ob) {
                n_First_name.add(background1.getImage());
            }


            // smr.addFile("filenames" , String.valueOf(ob));


            for (int i = 0; i < n_First_name.size(); i++) {

                smr.addStringParam("filenames", String.valueOf(n_First_name));
                smr.addStringParam("document_name", String.valueOf(n_First_name));

            }

            rq.add(smr);

        } catch (Exception e) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("Please Enter All The Details")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            progressDialog.dismiss();
        }


    }


    void callSaveBankDetailsApi() {


        try {

            progressDialog = ProgressDialog.show(getActivity(), "",
                    "Saving. Please wait...", true);
            RequestQueue rq = Volley.newRequestQueue(getActivity(),
                    new HurlStack(null, getSocketFactory()));

            rq.getCache().clear();
            String url = con.base_url + "/api/admin/create/claim/submitclaim";

            VolleyMultiPartRequest multiPartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {

                            try {
                                progressDialog.dismiss();


                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                String errorCode = String.valueOf(jsonObject.getBoolean("status"));


                                if (errorCode.equalsIgnoreCase("true")) {


                                    String message = jsonObject.getString("message");


                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Success")
                                            .setMessage(message)
                                            .setIcon(R.drawable.checkmark)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                    claim_id = null;
                                                    id = null;

                                                    DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();


                                                }
                                            })
                                            .setNegativeButton(android.R.string.no, null).show();

                                } else {
                                    String message = jsonObject.getString("errors");
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage(message)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }


                            } catch (Exception e) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error")
                                        .setMessage("Please Enter All The Details")
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();
                                progressDialog.dismiss();

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ErrorResponse", error.toString());
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Enter All The Details")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                    progressDialog.dismiss();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> smr = new HashMap<>();

                    bill_no_list = db.getBillNo();


                    String date = bill_no_list.toString();
                    String number = date.replaceAll("BillData", "");
                    String numberData = number.replaceAll("bill_no=", "");
                    String numberDataAray = numberData.replaceAll("[{}]", "");
                    String BillNOData = numberDataAray.replaceAll("'", "\"");


                    bill_amount_list = db.getBillAmount();
                    String dateamt = bill_amount_list.toString();
                    String numberamt = dateamt.replaceAll("Claim_amount", "");
                    String numberDataamt = numberamt.replaceAll("claim_amount=", "");
                    String numberDataArayamt = numberDataamt.replaceAll("[{}]", "");
                    String BillAmountData = numberDataArayamt.replaceAll("'", "\"");


                    bill_comment_list = db.getbillComment();
                    String dateamtc = bill_comment_list.toString();
                    String numberamtc = dateamtc.replaceAll("comment", "");
                    String numberDataamtc = numberamtc.replaceAll("comment_arr=", "");
                    String numberDataArayamtc = numberDataamtc.replaceAll("[{}]", "");
                    String BillCommentData = numberDataArayamtc.replaceAll("'", "\"");
                    String BillCommentDatad = BillCommentData.replaceAll("_arr=", "");


                    bill_date_list = db.getbillDate();
                    String dateamtcdt = bill_date_list.toString();
                    String numberamtcdt = dateamtcdt.replaceAll("BillDate", "");
                    String numberDataamtcdt = numberamtcdt.replaceAll("bill_date=", "");
                    String numberDataArayamtcdt = numberDataamtcdt.replaceAll("[{}]", "");
                    String BillDateData = numberDataArayamtcdt.replaceAll("'", "\"");


                    bill_type_list = db.getbillCost();
                    String dateamtcdtt = bill_type_list.toString();
                    String numberamtcdtt = dateamtcdtt.replaceAll("Cost", "");
                    String numberDataamtcdtt = numberamtcdtt.replaceAll("cost_arr=", "");
                    String numberDataArayamtcdtt = numberDataamtcdtt.replaceAll("[{}]", "");
                    String BillTypeData = numberDataArayamtcdtt.replaceAll("'", "\"");


                    SpinnerModal bank_name_modalf = (SpinnerModal) policy_type_spin.getSelectedItem();

                    SpinnerModalFamilyData bank_name_modalpatientf = (SpinnerModalFamilyData) patient_name_spin.getSelectedItem();


                    SpinnerModal bank_name_modal = (SpinnerModal) policy_type_spin.getSelectedItem();
                    SpinnerModal bank_name_modalpatient = (SpinnerModal) policy_type_spin.getSelectedItem();


                    SpinnerModal bank_name_modalpatientd = (SpinnerModal) policy_type_spin_no.getSelectedItem();


                    smr.put("member_id", bank_name_modalpatientf.getSelValue());
                    smr.put("admit_date", hos_date.getText().toString());
                    smr.put("discharge_date", discharge_date.getText().toString());
                    smr.put("policy_id", bank_name_modalpatientd.getSelKey());
                    smr.put("member_name", bank_name_modalpatient.getSelValue());

                    smr.put("email", email_id.getText().toString());
                    smr.put("mobile_no", contact.getText().toString());

                    smr.put("relation", relationdrop.getText().toString());
                    smr.put("step", "2");


                    SpinnerModal bank_name_modalc = (SpinnerModal) city.getSelectedItem();
                    SpinnerModal bank_name_modalpatientc = (SpinnerModal) state.getSelectedItem();


                    JSONObject jsonObject = new JSONObject();

                    try {


                        jsonObject.put("bill_no", BillNOData);
                        jsonObject.put("bill_date", BillDateData);
                        jsonObject.put("comment", BillCommentDatad);
                        jsonObject.put("reimburment_type", BillTypeData);
                        jsonObject.put("bill_amt", BillAmountData);


                    } catch (JSONException e) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Please Enter All The Details")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }


                    //smr.addStringParam("tableBill", jsonObject.toString());

                    smr.put("bill_no", BillNOData);
                    smr.put("bill_date", BillDateData);
                    smr.put("comment", BillCommentDatad);
                    smr.put("reimburment_type", BillTypeData);
                    smr.put("bill_amt", BillAmountData);


                    smr.put("hospital_addres", hos_address.getText().toString());
                    smr.put("doctor_name", hos_name.getText().toString());

                    smr.put("hospital_name", hos_name.getText().toString());
                    if (hos_reason.getText().toString().trim().isEmpty()) {
                        smr.put("reason", "NA");
                    } else if (hos_reason.getText().toString().trim().length() == 0) {
                        smr.put("reason", "NA");

                    } else {
                        smr.put("reason", hos_reason.getText().toString());
                    }

                    smr.put("disease", disease_name.getText().toString());
                    smr.put("state_id", bank_name_modalpatientc.getSelKey());
                    smr.put("city_id", bank_name_modalc.getSelKey());

                    smr.put("state_name", bank_name_modalpatientc.getSelValue());
                    smr.put("city_name", bank_name_modalc.getSelValue());
                    smr.put("status", "1");
                    smr.put("emp_id", emp_data);
                    smr.put("agent", "mobile");

                    if (Ipd.equalsIgnoreCase("0")) {
                        smr.put("claim_type", "1");

                    } else {
                        smr.put("claim_type", "0");

                    }

                    ob = db.getImage();
                    ArrayList<String> n_First_name = null;

                    n_First_name = new ArrayList<>();
                    for (ImageData background1 : ob) {
                        n_First_name.add(background1.getImage());
                    }


                    // smr.addFile("filenames" , String.valueOf(ob));


                    for (int i = 0; i < n_First_name.size(); i++) {

                        smr.put("filenames", String.valueOf(n_First_name));
                        smr.put("document_name", String.valueOf(n_First_name));

                    }

                    return smr;
                }

                @Override
                protected Map<String, DataPart> getByteData() throws AuthFailureError {
                    Map<String, DataPart> params = new HashMap<>();

                    for (int i = 0; i < Image_List.size(); i++) {

                        long imageName2 = System.currentTimeMillis();
                       // params.put("images["+i+"]", new DataPart(imageName2 + ".jpg", getFileDataFromDrawable(Image_List.get(i))));

                        params.put("document_path["+i+"]", new DataPart(System.currentTimeMillis() + ".jpg",
                                AppHelper.getFileDataFromDrawable(getContext(), Image_List.get(i)),
                                "image/jpeg"));

                        params.put("filenames["+0+"]", new DataPart(System.currentTimeMillis() + ".jpg",
                                AppHelper.getFileDataFromDrawable(getContext(), Image_List.get(0)),
                                "image/jpeg"));


                    }
                    return params;
                }

                /*@Override
                protected Map<String, DataPart> getByteData() throws AuthFailureError {
                    Map<String, DataPart> params = new HashMap<>();
                    try {


                        params.put("filenames[]", new DataPart(System.currentTimeMillis() + ".jpg",
                                AppHelper.getFileDataFromDrawable(getContext(), Image_List.get(0)),
                                "image/jpeg"));

                        ob = db.getImage();

                        Log.d("list", String.valueOf(Image_List));
                        
                        for (int i = 1; i < Image_List.size(); i++) {


                            params.put("document_path[]", new DataPart(
                                    AppHelper.getFileDataFromDrawable(Image_List.get(i))
                            ));

                        }




                        ArrayList<String> n_First_name = null;

                        n_First_name = new ArrayList<>();
                        for (ImageData background1 : ob) {
                            n_First_name.add(background1.getImage());


                        }





                    } catch (Exception e) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("Please Enter All The Details")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }


                    return params;
                }
*/
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };


            rq.add(multiPartRequest);

        } catch (Exception e) {
            Log.d("thiserror", e.toString());
        }


    }


    public byte[] getFileDataFromDrawableData(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        bitmap.recycle();
        return byteArray;
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
                         //   Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
                      //  Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}
