package com.palm.newbenefit.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.AnalyticsApplication;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import com.squareup.picasso.Picasso;
import com.palm.newbenefit.ApiConfig.AppHelper;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.VolleyMultiPartRequest;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

public class BankDetailExample extends AppCompatActivity {

    Constants con;
    String user_id;
    Context context = null;
    String data;
    String mobileNumber = null;
    String token = null;


    String selBankPath = null;
    String selBankPath_cheque = null;


    Spinner bank_nameSpin = null;
    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;

    private Uri filePath;


    private Uri img;
    String mydefaultid;
     byte[] inputData;

    byte[] inputDataa;
    Uri pdffile, pdffiledata;
    String displayName, displanamepreview1;

    Spinner bank_citySpin = null;
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;

    Spinner bank_branchSpin = null;
    ArrayList<String> bank_branch = null;
    ArrayList<SpinnerModal> bank_branchList = null;
    ArrayAdapter<SpinnerModal> bank_branchAdapter = null;


    EditText ac_holder_name = null;
    EditText bank_ac_no = null;
    EditText ifsc_code = null;
    ImageView bankPreview = null;
    ImageView bankPreview1;
    TextView up;
    ImageView uc;
    ImageView bankPreview_cheque = null;
    Button saveBtn = null;


    private static final int PICK_FILE = 101;

    RelativeLayout adhaarBackLayout_cheque = null;
    int count = 0;
    ProgressDialog progressDialog = null;
    TextWatcher ifsc_code_watcher = null;
    public static final int REQUEST_CODE_USER_PHOTOc = 114;
    public static final int REQUEST_CODE_USER_PHOTOa = 115;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEc = 1890;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEa = 1891;
    String bank_name_value = "";
    String bank_city_value = "";
    String bank_branch_value = "";
    String passed;
    String gender = null;
    RadioButton male = null;
    RadioButton female = null;
    ImageView info_text;
    EditText bank_name_data, bank_city_data, bank_branch_data;
    Calendar myCalendar = Calendar.getInstance();

    private static final int PICK_FILE_REQUEST = 1;
    RelativeLayout adhaarBackLayout_cheque1;

    LinearLayout upload_document_image, upload_id_proof;

    EditText bank_ac_no_re;
    TextView pdf_document, text_pdf;
    ImageView next_id, next_document;
    String path_id, path_document;

    String first_doc = "0", first_id = "0";


    String internaldoc ="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail_java);
        requestMultiplePermissions();

        Log.d("data", "dfff");


        requestMultiplePermissions();
        con = new Constants();
       // AnalyticsApplication.getInstance().trackEvent("Bank Screen", "Showing Bank Detail", "Bank Detail");


        text_pdf = findViewById(R.id.text_pdf);
        pdf_document = findViewById(R.id.pdf_document);


        next_id = findViewById(R.id.next_id);
        next_document = findViewById(R.id.next_document);

        bank_nameSpin = findViewById(R.id.bank_name);
        bank_branchSpin = findViewById(R.id.bank_branch);
        bank_citySpin = findViewById(R.id.bank_city);
        bankPreview = findViewById(R.id.bankPreview);
        bankPreview1 = findViewById(R.id.bankPreview1);
        adhaarBackLayout_cheque = findViewById(R.id.adhaarBackLayout_cheque);
        adhaarBackLayout_cheque1 = findViewById(R.id.adhaarBackLayout_cheque1);
        upload_document_image = findViewById(R.id.upload_document_image);
        upload_id_proof = findViewById(R.id.upload_id_proof);

        ac_holder_name = findViewById(R.id.ac_holder_name);
        bank_ac_no_re = findViewById(R.id.bank_ac_no_re);
        bank_ac_no = findViewById(R.id.bank_ac_no);
        ifsc_code = findViewById(R.id.ifsc_code);


        bank_name_data = findViewById(R.id.bank_name_data);
        bank_city_data = findViewById(R.id.bank_city_data);
        bank_branch_data = findViewById(R.id.bank_branch_data);


        up = findViewById(R.id.up);
        uc = findViewById(R.id.uc);
        saveBtn = findViewById(R.id.saveBank);
        info_text = findViewById(R.id.info_text);


        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        Log.d("token", "token");

        next_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id;
                if (first_doc.equals("1")) {
                    id = "1";
                } else {
                    id = "0";
                }

                if(internaldoc.equalsIgnoreCase("0")){
                    Log.d("pdf",text_pdf.getText().toString().trim());


                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(text_pdf.getText().toString().trim()));
                    startActivity(browserIntent);

                }else {
                    showPhotoPickerDialog();
                }



             /*   Intent intent = new Intent(getApplicationContext(), PdfActivityjava.class);
                intent.putExtra("pol", path_id);
                intent.putExtra("id", id);
                startActivity(intent);*/

            }
        });

        next_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String id;
                if (first_doc.equals("1")) {
                    id = "1";
                } else {
                    id = "0";
                }


                if(internaldoc.equalsIgnoreCase("0")){
                    Log.d("pdf",pdf_document.getText().toString().trim());

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_document.getText().toString().trim()));
                    startActivity(browserIntent);
                }else {
                    showPhotoPickerDialog();
                }



           /*     Intent intent = new Intent(getApplicationContext(), PdfActivityjava.class);
                intent.putExtra("pol", path_document);
                intent.putExtra("id", id);
                startActivity(intent);*/


            }
        });


        text_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String id;
                if (first_doc.equals("1")) {
                    id = "1";
                } else {
                    id = "0";
                }

               /* Intent intent = new Intent(getApplicationContext(), PdfActivityjava.class);
                intent.putExtra("pol", path_id);
                intent.putExtra("id", id);
                startActivity(intent);
*/
                if(internaldoc.equalsIgnoreCase("0")){
                    Log.d("pdf",text_pdf.getText().toString().trim());

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(text_pdf.getText().toString().trim()));
                    startActivity(browserIntent);
                }else {
                    showPhotoPickerDialoga();
                }



            }
        });


        pdf_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                startActivity(browserIntent);

*/

                String id;
                if (first_doc.equals("1")) {
                    id = "1";
                } else {
                    id = "0";
                }

                if(internaldoc.equalsIgnoreCase("0")){
                    Log.d("pdf",pdf_document.getText().toString().trim());
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_document.getText().toString().trim()));
                    startActivity(browserIntent);

                }else {
                    showPhotoPickerDialoga();
                }


       /*         Intent intent = new Intent(getApplicationContext(), PdfActivityjava.class);
                intent.putExtra("pol", path_document);
                intent.putExtra("id", id);
                startActivity(intent);*/


            }
        });


        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });

        bank_nameSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")) {
                    try {


                        if (isNetworkAvailable()) {

                            getBankCity(bank_name_modal.getSelKey(), bank_city_value);
                        } else {
                            new AlertDialog.Builder(BankDetailExample.this)
                                    .setTitle("Error?")
                                    .setMessage("Please Check Your Internet Connection")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();
                        }


                    } catch (Exception e) {

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bank_citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_city_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_city_modal.selValue.equals(""))

                    try {


                        if (isNetworkAvailable()) {

                            getBankbranch(bank_city_modal.getSelKey(), bank_branch_value);
                        } else {
                            new AlertDialog.Builder(BankDetailExample.this)
                                    .setTitle("Error?")
                                    .setMessage("Please Check Your Internet Connection")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();
                        }


                    } catch (Exception e) {

                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bank_branchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_branch_modal = (SpinnerModal) adapterView.getSelectedItem();

                ifsc_code.removeTextChangedListener(ifsc_code_watcher);
                if (!bank_branch_modal.selValue.equals("")) {

                    if (isNetworkAvailable()) {

                        getBankbranchIfsc(bank_branch_modal.getSelKey(), bank_branch_value);
                    } else {
                        new AlertDialog.Builder(BankDetailExample.this)
                                .setTitle("Error?")
                                .setMessage("Please Check Your Internet Connection")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }
                }
                //ifsc_code.setText(bank_branch_modal.selValue.split("\\|")[1]);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ifsc_code_watcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filterLongEnough()) {


                    if (isNetworkAvailable()) {

                        getIfscCode(ifsc_code.getText().toString());
                    } else {
                        new AlertDialog.Builder(BankDetailExample.this)
                                .setTitle("Error?")
                                .setMessage("Please Check Your Internet Connection")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    }


                }
            }

            private boolean filterLongEnough() {
                return ifsc_code.getText().toString().trim().length() > 10;
            }
        };


        if (isNetworkAvailable()) {

            ifsc_code.addTextChangedListener(ifsc_code_watcher);
        } else {
            new AlertDialog.Builder(BankDetailExample.this)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }


        try {
            if (isNetworkAvailable()) {

                getBankName(bank_name_value);

                callGetBankDetailsApi();
            } else {
                new AlertDialog.Builder(BankDetailExample.this)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();
            }


        } catch (Exception e) {

        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isNetworkAvailable()) {


                    dummy();
                } else {
                    new AlertDialog.Builder(BankDetailExample.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });

        adhaarBackLayout_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialog();
            }
        });


        upload_document_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialog();
            }
        });

        adhaarBackLayout_cheque1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialoga();
            }
        });


        upload_id_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialoga();
            }
        });


     /*   deletebtn_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selBankPath_cheque = null;
                bankPreview_cheque.setImageBitmap(null);

            }
        });*/


    }

    void dummy() {


        int count = 0;

        SpinnerModal bankSel = (SpinnerModal) bank_nameSpin.getSelectedItem();
        SpinnerModal citySel = (SpinnerModal) bank_citySpin.getSelectedItem();
        SpinnerModal branchSel = (SpinnerModal) bank_branchSpin.getSelectedItem();

        if (bankSel.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(BankDetailExample.this)
                    .setTitle("Error")
                    .setMessage("Please Select Bank Name")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        } else if (citySel.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(BankDetailExample.this)
                    .setTitle("Error")
                    .setMessage("Please Select City Of Bank")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (branchSel.getSelKey().trim().length() == 0) {
            ++count;
            new AlertDialog.Builder(BankDetailExample.this)
                    .setTitle("Error")
                    .setMessage("Please Select Branch Of Bank")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
            return;
        }

        if (ac_holder_name.getText().toString().trim().length() == 0) {
            ++count;
            ac_holder_name.setError("A/c Holder Name is Required");
        } else {
            ac_holder_name.setError(null);
        }


        if (bank_ac_no_re.getText().toString().trim().length() == 0) {
            ++count;
            bank_ac_no_re.setError("Re-Enter Bank Account Number");
        } else {
            bank_ac_no_re.setError(null);
        }


        if (bank_ac_no_re.getText().toString().trim().equalsIgnoreCase(bank_ac_no.getText().toString().trim())) {
            bank_ac_no_re.setError(null);
        } else {
            ++count;
            bank_ac_no_re.setError("Re-Enter Correct Bank Account Number");

        }

        if (ifsc_code.getText().toString().trim().length() == 0 || ifsc_code.getText().toString().trim().length() < 11) {
            ++count;
            ifsc_code.setError("IFSC Code is Required");
        } else {
            ifsc_code.setError(null);
        }

        if (bank_ac_no.getText().toString().trim().length() == 0) {
            ++count;
            bank_ac_no.setError("Bank Account Number is Required");
        } else {
            bank_ac_no.setError(null);
        }

        if (bankPreview.getVisibility() == View.VISIBLE) {
            if (selBankPath == null) {
                ++count;
                new AlertDialog.Builder(BankDetailExample.this)
                        .setTitle("No File Selected")
                        .setMessage("Please add Bank Id Proof file to upload")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();


            }
        }


        if (bankPreview1.getVisibility() == View.VISIBLE) {
            if (selBankPath_cheque == null) {
                ++count;
                new AlertDialog.Builder(BankDetailExample.this)
                        .setTitle("No File Selected")
                        .setMessage("Please add Bank Id Proof file to upload")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();


            }
        }


        if (count == 0) {
            progressDialog = ProgressDialog.show(this, "",
                    "Saving. Please wait...", true);


            try {

                // uploadPDF(displayName,pdffile);

                callSaveBankDetailsApi();


            } catch (Exception e) {

            }


        }

    }


    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
        if (path != null) {

        } else {
            path = "dfgfdg";
        }
        return Uri.parse(path);
    }


    private String getRealPathFromURI(Uri uri) {
        String path = "";
        if (this.getContentResolver() != null) {
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void callSaveBankDetailsApi() {

        RequestQueue rq = Volley.newRequestQueue(BankDetailExample.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url + "/api/employee/store/bank/details";


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
                            if (status.equals("true")) {
                                String message = js.getString("message");



                                new AlertDialog.Builder(BankDetailExample.this)
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(R.drawable.checkmark)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                finish();
                                            }
                                        }).show();

                            } else {


                                new AlertDialog.Builder(BankDetailExample.this)
                                        .setTitle("Error")
                                        .setMessage("The document may not be greater than 5 mb.")
                                        .setIcon(android.R.drawable.btn_dialog)
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
                            new AlertDialog.Builder(BankDetailExample.this)
                                    .setTitle("Error")
                                    .setMessage("The document may not be greater than 5 mb.")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {

                                        }
                                    }).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorResponse", error.toString());
                progressDialog.dismiss();
                new AlertDialog.Builder(BankDetailExample.this)
                        .setTitle("Error")
                        .setMessage("The document may not be greater than 5 mb.")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SpinnerModal bankSel = (SpinnerModal) bank_nameSpin.getSelectedItem();
                SpinnerModal citySel = (SpinnerModal) bank_citySpin.getSelectedItem();
                SpinnerModal branchSel = (SpinnerModal) bank_branchSpin.getSelectedItem();
                params.put("branch_id", branchSel.getSelKey());
                params.put("account_holder_name", ac_holder_name.getText().toString());
                params.put("account_no", bank_ac_no.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,DataPart> params = new HashMap<>();
                try {

                    if (text_pdf.getVisibility() == View.VISIBLE) {
                        /***
                         * 1) if user selects pdf than it will fail.
                         */

                        Log.d("image","yes");
                        params.put("image", new DataPart(path_id ,
                                inputDataa,
                                "application/pdf"));
                    //  params.put("image", new DataPart(System.currentTimeMillis() + ".pdf",
                        //  AppHelper.getFileInBytes(path_id), "application/pdf"));

                    } else {
                        Log.d("image","No");
                        params.put("image", new DataPart(System.currentTimeMillis() + ".jpg",
                                AppHelper.getFileDataFromDrawable(getBaseContext(), bankPreview1.getDrawable()),
                                "image/jpeg"));
                    }


                } catch (Exception e) {

                }


                try {

                    if (path_document.startsWith("https") || path_document.startsWith("http")) {
                        Log.d("document","yes");
                      //  params.put("document", new DataPart(System.currentTimeMillis() + ".pdf", getByteArrayFromUrl(new URL(path_document)), "pdf"));

                    } else {
                        Log.d("document","No");
                        params.put("document", new DataPart(path_document ,inputData,"application/pdf"));
                      // params.put("document", new DataPart(System.currentTimeMillis() + ".pdf", AppHelper.convertFilePathToByteArray(path_document), "application/pdf"));

                    }
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


    private void callGetBankDetailsApi() {

        String url = con.base_url + "/api/employee/get/employee/bank-details";

        RequestQueue mRequestQueue = Volley.newRequestQueue(BankDetailExample.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();

        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);

                    Log.d("FetchModule", response);
                    JSONObject explrObject = js.getJSONObject("data");


                    String ac_holder_name_input = explrObject.getString("account_holder_name");
                    String bank_ac_no_input = explrObject.getString("account_no");
                    String ifsc_code_input = explrObject.getString("ifsc_code");
                    String bankDocument = explrObject.getString("document");
                    String bankIdProof = explrObject.getString("image");


                    path_document = bankDocument;
                    bankPreview.setVisibility(View.GONE);
                    pdf_document.setVisibility(View.VISIBLE);
                    next_document.setVisibility(View.VISIBLE);
                    pdf_document.setText(bankDocument);


                    if (bankIdProof.contains("pdf") || bankIdProof.contains("PDF")) {

                        path_id = bankIdProof;

                        //path_id = FilePath.getPath(getApplicationContext(), Uri.parse(bankIdProof));

                        mydefaultid = "1";
                        selBankPath_cheque = bankIdProof;

                        bankPreview1.setVisibility(View.GONE);
                        text_pdf.setVisibility(View.VISIBLE);
                        next_id.setVisibility(View.VISIBLE);
                        text_pdf.setText(bankIdProof);

                    } else {


                        bankPreview1.setVisibility(View.VISIBLE);
                        text_pdf.setVisibility(View.GONE);
                        next_id.setVisibility(View.GONE);
                        if (!bankIdProof.equals("null") && !bankIdProof.isEmpty()) {
                            mydefaultid = "1";
                            selBankPath_cheque = bankIdProof;


                            bankPreview1.setVisibility(View.VISIBLE);


                            String finalJsonStr = bankIdProof.replace("/\\n", "");
                            Log.d("finalJsonStr", finalJsonStr);


                            Picasso.get().load(finalJsonStr).into(bankPreview1);


                        }
                    }


                    bankPreview.setVisibility(View.GONE);
                    pdf_document.setVisibility(View.VISIBLE);
                    next_document.setVisibility(View.VISIBLE);
                    if (!bankDocument.equals("null") && !bankDocument.isEmpty()) {

                        selBankPath = bankDocument;

                        pdf_document.setText(selBankPath);


                        bankPreview.setVisibility(View.GONE);


                    }


                    if (ac_holder_name_input != "null" && !ac_holder_name_input.isEmpty()) {
                        ac_holder_name.setText(ac_holder_name_input);

                    }

                    if (bank_ac_no_input != "null" && !bank_ac_no_input.isEmpty()) {
                        bank_ac_no.setText(bank_ac_no_input);
                        bank_ac_no_re.setText(bank_ac_no_input);
                    }


                    if (ifsc_code_input != "null" && !ifsc_code_input.isEmpty()) {

                        ifsc_code.setText(ifsc_code_input);
                        getIfscCode(ifsc_code.getText().toString().trim());

                    }


//                                if (bank_name_input != "null" && !bank_name_input.isEmpty()) {
//                                    getBankName(bank_name_input);
//                                    bank_name_data.setText(bank_name_input);
//                                }
//
//                                if (bank_id != "null" && !bank_id.isEmpty()) {
//                                    getBankCity(bank_id, bank_city_input);
//                                    bank_city_data.setText(bank_city_input);
//                                }
//
//                                if (bank_branch_input != "null" && !bank_branch_input.isEmpty()) {
//                                    getBankbranch(bank_city_input, bank_branch_input);
//                                    bank_branch_data.setText(bank_branch_input);
//                                }


                } catch (Exception e) {
                    setProfileDet();
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

    public void setProfileDet() {

        String url = con.base_url+"/api/admin/user";
        RequestQueue mRequestQueue = Volley.newRequestQueue(BankDetailExample.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js=new JSONObject(response);

                    Log.d("response",response);
                    JSONArray  jsonObj=js.getJSONArray("data");

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject explrObject = jsonObj.getJSONObject(i);








                        String emaild = explrObject.getString("name");
                        //ac_holder_name.setText(emaild);


                       // bank_ac_no.setText("..........");



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
        params.put("user_id", user_id);
        params.put("master_user_type_id", "5");


        mStringRequest.setParams(params);
        mRequestQueue.add(mStringRequest);
    }



    private void showPhotoPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_optionsa, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_optionsa));

                showFileChooser();
            }
        });
        builder.show();
    }


    private void showFileChooser() {




      /*  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File"), PICK_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }

*/

/*
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent,PICK_FILE);*/


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_FILE);


    }


    private void showFileChooser_id() {




      /*  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File"), PICK_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }

*/


//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        startActivityForResult(intent,PICK_FILE_REQUEST);


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 1);


    }

    private void showPhotoPickerDialoga() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_options));


                if (values.get(which).toLowerCase().equalsIgnoreCase("gallery")) {
                    openGalleryc();
                    // galleryImage();
                } else if (values.get(which).toLowerCase().equalsIgnoreCase("camera")) {
                    openCamerac();
                    // cameraImage();
                } else {
                    showFileChooser_id();
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


    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_USER_PHOTOa);
    }

    public void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEa);






    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEa) {
            if (resultCode == Activity.RESULT_OK) {


                Bitmap photo = (Bitmap) data.getExtras().get("data");


                Uri tempUri = getImageUri(this.getApplicationContext(), photo);


                File finalFile = new File(getRealPathFromURI(tempUri));

                first_doc="1";

                pdf_document.setVisibility(View.GONE);
                next_document.setVisibility(View.GONE);
                bankPreview.setImageBitmap(photo);
                selBankPath = finalFile.getAbsolutePath();





            }
        } else if (requestCode == REQUEST_CODE_USER_PHOTOa) {

            if (resultCode == Activity.RESULT_OK) {


                Uri selectedImage = data.getData();
                File myFile = new File(selectedImage.toString());
                String file = myFile.getAbsolutePath();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = this.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

                first_doc="1";
                pdf_document.setVisibility(View.GONE);
                next_document.setVisibility(View.GONE);
                bankPreview.setVisibility(View.VISIBLE);
                bankPreview.setImageBitmap(bitmap);
                selBankPath = cursor.getString(columnIndex);
                cursor.close();



            }

        } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEc) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");


                Uri tempUri = getImageUri(this, photo);


                File finalFile = new File(getRealPathFromURI(tempUri));
                img=img;

                first_id="1";
                text_pdf.setVisibility(View.GONE);
                next_id.setVisibility(View.GONE);
                mydefaultid="2";
                bankPreview1.setImageBitmap(photo);
                selBankPath_cheque = finalFile.getAbsolutePath();


            }
        } else if (requestCode == REQUEST_CODE_USER_PHOTOc) {

            if (resultCode == Activity.RESULT_OK) {

                first_id="1";

                Uri selectedImage = data.getData();


                File myFile = new File(selectedImage.toString());
                String file = myFile.getAbsolutePath();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                img=selectedImage;

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                text_pdf.setVisibility(View.GONE);
                next_id.setVisibility(View.GONE);
                bankPreview1.setImageBitmap(bitmap);
                selBankPath_cheque = cursor.getString(columnIndex);
                cursor.close();
            }

        }else if (requestCode == PICK_FILE) {

            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();


            String displayName = null;

            path_document = path;

            internaldoc="1";


          //  Log.d("Path",path_document);

            pdf_document.setVisibility(View.VISIBLE);
            next_document.setVisibility(View.VISIBLE);
            bankPreview.setVisibility(View.GONE);



            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            first_doc="1";
            displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                      /*  int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        path_document= cursor.getString(columnIndex);*/

                        // uploadPDF(displayName,uri);


                        pdf_document.setText(displayName);
                        pdf_document.setVisibility(View.VISIBLE);
                        next_document.setVisibility(View.VISIBLE);
                        bankPreview.setVisibility(View.GONE);


                        path_document=displayName;

                        InputStream iStream = null;
                        try {

                            iStream = getContentResolver().openInputStream(uri);
                            inputData = getBytes(iStream);
                        }catch (Exception e){

                        }



                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }
        }else  if (requestCode == PICK_FILE_REQUEST) {



            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(data.getData().getPath());
            String path = myFile.getAbsolutePath();


            String displayName = null;

            path_id = path;






            internaldoc="1";

            first_id="1";



            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);





                        text_pdf.setText(displayName);
                        text_pdf.setVisibility(View.VISIBLE);
                        next_id.setVisibility(View.VISIBLE);
                        bankPreview1.setVisibility(View.GONE);


                        path_id=displayName;

                        InputStream iStream = null;
                        try {

                            iStream = getContentResolver().openInputStream(uri);
                            inputDataa = getBytes(iStream);
                        }catch (Exception e){

                        }




                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
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


    private void getBankName(final String set_bank_name) {

        String url = con.base_url + "/api/employee/get/bank/name";

        RequestQueue mRequestQueue = Volley.newRequestQueue(BankDetailExample.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.d("banknameModule", response);
                    JSONObject js = new JSONObject(response);
                    JSONArray jsonArr = js.getJSONArray("data");


                    bank_name = new ArrayList<>();
                    bank_nameList = new ArrayList<>();
                    bank_nameList.add(new SpinnerModal("", "Select Bank"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                        bank_name.add(jsonObj.getString("name"));
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(BankDetailExample.this, R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bank_nameSpin.setAdapter(bank_nameAdapter);


                    if (!set_bank_name.equals(""))
                        bank_nameSpin.setSelection(bank_nameAdapter.getPosition(bank_nameList.get(bank_name.indexOf(set_bank_name))));


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


        mRequestQueue.add(mStringRequest);
    }


    public void getBankCity(final String bank_name, final String set_bank_city) {

        RequestQueue rq = Volley.newRequestQueue(BankDetailExample.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url + "/api/employee/get/bank-city";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            Log.d("cityModule", response);


                            JSONObject js = new JSONObject(response);
                            JSONArray jsonArr = js.getJSONArray("data");


                            bank_city = new ArrayList<>();
                            bank_cityList = new ArrayList<>();
                            bank_cityList.add(new SpinnerModal("", "Select City"));
                            bank_city.add("");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);

                                bank_cityList.add(new SpinnerModal(jsonObj.getString("bank_city"),
                                        jsonObj.getString("bank_city")));
                                bank_city.add(jsonObj.getString("bank_city"));
                            }
                            bank_cityAdapter = new ArrayAdapter<SpinnerModal>(BankDetailExample.this, R.layout.spinner_item, bank_cityList);
                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            bank_citySpin.setAdapter(bank_cityAdapter);

                            if (!set_bank_city.equals(""))
                                bank_citySpin.setSelection(bank_cityAdapter.getPosition(bank_cityList.get(bank_city.indexOf(set_bank_city))));


                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());

//                bank_city = new ArrayList<>();
//                bank_cityList = new ArrayList<>();
//                bank_cityList.add(new SpinnerModal("", "Select City"));
//                bank_city.add("");
//
//                bank_cityAdapter = new ArrayAdapter<SpinnerModal>(BankDetailExample.this, R.layout.spinner_item, bank_cityList);
//                bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                bank_citySpin.setAdapter(bank_cityAdapter);


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

        SpinnerModal bank_name_modala = (SpinnerModal) bank_nameSpin.getSelectedItem();
        params.put("bank_id", bank_name);
        Log.d("bank_id", bank_name);

        smr.setParams(params);
        rq.add(smr);


    }


    public void getBankbranch(final String bank_city, final String set_bank_branch) {

        RequestQueue rq = Volley.newRequestQueue(BankDetailExample.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url + "/api/employee/get/bank-branch";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            Log.d("bankBranchModule", response);
                            JSONObject js = new JSONObject(response);
                            JSONArray jsonArr = js.getJSONArray("data");

                            bank_branch = new ArrayList<>();
                            bank_branchList = new ArrayList<>();
                            bank_branchList.add(new SpinnerModal("", "Select Branch"));
                            bank_branch.add("");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);


                                bank_branchList.add(new SpinnerModal(jsonObj.getString("branch_id"), jsonObj.getString("bank_branch")));
                                bank_branch.add(jsonObj.getString("bank_branch"));
                            }


                            bank_branchAdapter = new ArrayAdapter<SpinnerModal>(BankDetailExample.this, R.layout.spinner_item, bank_branchList);
                            bank_branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            bank_branchSpin.setAdapter(bank_branchAdapter);

                            if (!set_bank_branch.equals(""))
                                bank_branchSpin.setSelection(bank_branchAdapter.getPosition(bank_branchList.get(bank_branch.indexOf(set_bank_branch))));

                        } catch (Exception e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
                bank_branch = new ArrayList<>();
                bank_branchList = new ArrayList<>();
                bank_branchList.add(new SpinnerModal("", "Select Branch"));
                bank_branch.add("");

                bank_branchAdapter = new ArrayAdapter<SpinnerModal>(BankDetailExample.this, R.layout.spinner_item, bank_branchList);
                bank_branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bank_branchSpin.setAdapter(bank_branchAdapter);
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

        SpinnerModal bank_name_modala = (SpinnerModal) bank_citySpin.getSelectedItem();



        SpinnerModal bank_name_modal = (SpinnerModal) bank_nameSpin.getSelectedItem();

        params.put("bank_id", bank_name_modal.getSelKey());
        params.put("bank_city", bank_name_modala.getSelValue());
        Log.d("bank_city", bank_name_modala.getSelValue());
        Log.d("bank_id", bank_name_modal.getSelKey());

        smr.setParams(params);


        rq.add(smr);
    }


    public void getBankbranchIfsc(final String bank_city, final String set_bank_branch) {

        RequestQueue rq = Volley.newRequestQueue(BankDetailExample.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        //RequestQueue rq = Volley.newRequestQueue(BankDetailExample.this, new HurlStack(null, getSocketFactory()));
        SpinnerModal bank_name_modal = (SpinnerModal) bank_nameSpin.getSelectedItem();

        String url = con.base_url + "/api/employee/get/bank-ifsc-city";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("ifsccodeModule", response);

                            JSONObject js = new JSONObject(response);

                            String status = String.valueOf(js.getBoolean("status"));

                            if (status.equalsIgnoreCase("true")) {
                                JSONArray jsonArr = js.getJSONArray("data");
                                for (int i = 0; i < jsonArr.length(); i++) {

                                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                                    ifsc_code.setText(jsonObj.getString("ifsc_code"));

                                    // ifsc_code.addTextChangedListener(ifsc_code_watcher);

                                }
                            } else {

                                new AlertDialog.Builder(BankDetailExample.this)
                                        .setTitle("Error")
                                        .setMessage("No IFSC Code For this Branch")
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, null).show();

                                ifsc_code.setText(null);
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


        SpinnerModal bankname = (SpinnerModal) bank_nameSpin.getSelectedItem();
        SpinnerModal bankcitya = (SpinnerModal) bank_citySpin.getSelectedItem();
        SpinnerModal bankbranch = (SpinnerModal) bank_branchSpin.getSelectedItem();



        HashMap<String, String> params = new HashMap<>();


        params.put("bank_id", bankname.getSelKey());
        params.put("bank_branch", bankbranch.getSelValue());


        smr.setParams(params);
        rq.add(smr);

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
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                return true;


            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }

    public void getIfscCode(String code) {

        RequestQueue rq = Volley.newRequestQueue(BankDetailExample.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
       // RequestQueue rq = Volley.newRequestQueue(BankDetailExample.this, new HurlStack(null, getSocketFactory()));
        SpinnerModal bank_name_modal = (SpinnerModal) bank_nameSpin.getSelectedItem();
        SpinnerModal bank_city_modal = (SpinnerModal) bank_nameSpin.getSelectedItem();
        SpinnerModal bank_branch_modal = (SpinnerModal) bank_nameSpin.getSelectedItem();

        String url = con.base_url + "/api/employee/get/bank/details";
       
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("IfscModule", response);
                            JSONObject jsonobj = new JSONObject(response);

                            JSONArray array = jsonobj.getJSONArray("data");


                            for (int i = 0; i < array.length(); i++) {
                                JSONObject explrObject = array.getJSONObject(i);

                                bank_name_value = explrObject.getString("bank_name");
                                bank_city_value = explrObject.getString("city_name");
                                bank_branch_value = explrObject.getString("branch_name");
                            }


                            getBankName(bank_name_value);
                            getBankCity(bank_name_modal.getSelKey(), bank_city_value);
                            getBankbranch(bank_city_value, bank_branch_value);

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

        params.put("ifsc_code", ifsc_code.getText().toString().trim());
        smr.setParams(params);
        rq.add(smr);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public SSLSocketFactory getSocketFactory() {

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

        return null;
    }


    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           // Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
    public  byte[] getByteArrayFromUrl(URL url) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(getSocketFactory());
            // Since you get a URLConnection, use it to get the InputStream
            InputStream in = connection.getInputStream();
            // Now that the InputStream is open, get the content length
            int contentLength = connection.getContentLength();

            // To avoid having to resize the array over and over and over as
            // bytes are written to the array, provide an accurate estimate of
            // the ultimate size of the byte array
            ByteArrayOutputStream tmpOut;
            if (contentLength != -1) {
                tmpOut = new ByteArrayOutputStream(contentLength);
            } else {
                tmpOut = new ByteArrayOutputStream(16384); // Pick some appropriate size
            }

            byte[] buf = new byte[512];
            while (true) {
                int len = in.read(buf);
                if (len == -1) {
                    break;
                }
                tmpOut.write(buf, 0, len);
            }
            in.close();
            tmpOut.close();

            byte[] array = tmpOut.toByteArray();
            return array;
        }
//            URL url = new URL(url);
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//
//            try (InputStream inputStream = url.openStream()) {
//                int n = 0;
//                byte [] buffer = new byte[ 1024 ];
//                while (-1 != (n = inputStream.read(buffer))) {
//                    output.write(buffer, 0, n);
//                }
//            }
//
//            return output.toByteArray();
//        }
        catch (Exception e) {
            Log.e("Error", e.toString());

        }
        byte[] bytes = new byte[0];
        return bytes;

    }


}


