package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.palm.newbenefit.ApiConfig.AppHelper;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.VolleyMultiPartRequest;
import com.palm.newbenefit.DatabaseHelper.DBHelper;
import com.palm.newbenefit.Fragment.DashboardBenifitFragment;
import com.palm.newbenefit.Module.ImageData;
import com.palm.newbenefit.Module.SpinnerModal;
import com.palm.newbenefit.Module.SpinnerModalFamilyData;
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

public class UploadDeficiencyActivity extends AppCompatActivity {
EditText claim_id,raised_date,reason_def;
EditText doc_name;
    DBHelper db;
    byte[] inputData;
    Bitmap myBitmap;
    String selProfilePath = "null";
Constants con;
    String employee_id, emp_data;

    TextView pdf_document;
    Uri picUri;
    ProgressDialog progressDialog = null;
    Button save_doc, view_doc;
    TextView add_doc, claim_clear_btn;
    ImageView viewImage;
    LinearLayout hideh;
    RelativeLayout bf;
    String token,user_id;
    String claimid;
    private List<Drawable> Image_List = null;
    private List<String> docname_List = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_deficiency);
        requestMultiplePermissions();

        Log.d("data", "dfff");


        requestMultiplePermissions();
        claim_id=findViewById(R.id.claim_id);
        raised_date=findViewById(R.id.raised_date);
        reason_def=findViewById(R.id.reason_def);
        doc_name = findViewById(R.id.doc_name);
        hideh = findViewById(R.id.hideh);
        isStoragePermissionGrantedfg();
        isStoragePermissionGranted();

        claim_clear_btn = findViewById(R.id.claim_clear_btn);
        add_doc = findViewById(R.id.add_doc);
        save_doc = findViewById(R.id.save_doc);
        view_doc = findViewById(R.id.view_doc);

        Image_List = new ArrayList<>();
        docname_List= new ArrayList<>();
        SharedPreferences prefs =getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        user_id = prefs.getString("user_id", null);

        db = new DBHelper(UploadDeficiencyActivity.this);
        db.removeAll();
        pdf_document = (TextView)findViewById(R.id.pdf_document);
        bf = (RelativeLayout) findViewById(R.id.adhaarBackLayout_cheque);
        viewImage = (ImageView) findViewById(R.id.bankPreview);

        Intent intent = getIntent();



        claimid = intent.getStringExtra("claim_id");

        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isStoragePermissionGranted();
                showPhotoPickerDialog();


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


        view_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(UploadDeficiencyActivity.this, ViewDataActivty.class);
                startActivity(intent);


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
//                    new AlertDialog.Builder(UploadDeficiencyActivity.this)
//                            .setTitle("Alert")
//                            .setMessage("Please Submit User Form")
//                            .setIcon(android.R.drawable.btn_dialog)
//                            .setNegativeButton(android.R.string.ok, null).show();
//
//                } else {


                if (isNetworkAvailable()) {


                    if (selProfilePath.equalsIgnoreCase("null")) {
                        new AlertDialog.Builder(UploadDeficiencyActivity.this)
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
                    new AlertDialog.Builder(UploadDeficiencyActivity.this)
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
                        new AlertDialog.Builder(UploadDeficiencyActivity.this)
                                .setTitle("Error")
                                .setMessage("Kindly upload Document")
                                .setIcon(android.R.drawable.btn_dialog)
                                .setNegativeButton(android.R.string.ok, null).show();
                    } else {
                        // SendDataToServerFinal();

                        callSaveBankDetailsApi();
                    }


                } else {
                    new AlertDialog.Builder(UploadDeficiencyActivity.this)
                            .setTitle("Error")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


            }
        });


    }

    public boolean isStoragePermissionGrantedfg() {
        if (ContextCompat.checkSelfPermission(UploadDeficiencyActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(UploadDeficiencyActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
        }
        return true;
    }


    private void showPhotoPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDeficiencyActivity.this);
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_optionsa, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_options));


                    showFileChooser();

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
            if (UploadDeficiencyActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && UploadDeficiencyActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {
                ActivityCompat.requestPermissions(UploadDeficiencyActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }


    public boolean isStoragePermissionGrantedf() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (UploadDeficiencyActivity.this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && UploadDeficiencyActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                return true;


            } else {


                ActivityCompat.requestPermissions(UploadDeficiencyActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == UploadDeficiencyActivity.this.RESULT_OK) {
                  /*  Bundle extras = data.getExtras();
                    Bitmap photo = (Bitmap) extras.get("data");


                    Uri tempUri = getImageUri(context, photo);


                    File finalFile = new File(getRealPathFromURI(tempUri));*/


                    Bitmap photo = (Bitmap) data.getExtras().get("data");


                    Uri tempUri = getImageUri(UploadDeficiencyActivity.this, photo);


                    File finalFile = new File(getRealPathFromURI(tempUri));


                    hideh.setVisibility(View.GONE);
                    viewImage.setVisibility(View.VISIBLE);
                    viewImage.setImageBitmap(photo);
                    selProfilePath = finalFile.getAbsolutePath();


                    pdf_document.setVisibility(View.GONE);

                }

                break;
            case 1:
                if (resultCode == UploadDeficiencyActivity.this.RESULT_OK) {

                    Uri selectedImage = data.getData();
                    File myFile = new File(selectedImage.toString());
                    String file = myFile.getAbsolutePath();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = UploadDeficiencyActivity.this.getContentResolver().query(selectedImage,
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
                if (resultCode == UploadDeficiencyActivity.this.RESULT_OK) {

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
                            cursor = UploadDeficiencyActivity.this.getContentResolver().query(uri, null, null, null, null);
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

                                    iStream = UploadDeficiencyActivity.this.getContentResolver().openInputStream(uri);


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
        if (UploadDeficiencyActivity.this.getContentResolver() != null) {
            Cursor cursor = UploadDeficiencyActivity.this.getContentResolver().query(uri, null, null, null, null);
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

        RequestQueue rq = Volley.newRequestQueue(UploadDeficiencyActivity.this,
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
    public void SendHospitalDoc() {

        int count = 0;

        if (selProfilePath.equalsIgnoreCase("null")) {
            ++count;
            new AlertDialog.Builder(UploadDeficiencyActivity.this)
                    .setTitle("No File Selected")
                    .setMessage("Please Add File To Upload")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }

         /*   Log.d("size", String.valueOf(finlesize / 1024));
        }else if(finlesize / 1024 > 5000){
            ++count;
            new AlertDialog.Builder(UploadDeficiencyActivity.this)
                    .setTitle("No File Selected")
                    .setMessage("The document may not be greater than 5000 kilobytes.")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();

        }*/

        if (count == 0) {


            String data = String.valueOf(viewImage.getDrawable());


            //  boolean isInsertedbillComment = db.AddImage(new ImageData("anydata.jpg"));

            Image_List.add(viewImage.getDrawable());
            docname_List.add(doc_name.getText().toString().trim());
            boolean isInsertedbillComment = db.AddImage(new ImageData(selProfilePath));




            if (isInsertedbillComment == true) {
                doc_name.setText(null);
                add_doc.setText("Add More");
                save_doc.setVisibility(View.VISIBLE);

                new AlertDialog.Builder(UploadDeficiencyActivity.this)
                        .setTitle("Success")
                        .setMessage("Data Added Successfully !")
                        .setIcon(R.drawable.checkmark)
                        .setNegativeButton(android.R.string.ok, null).show();


            } else {

                new AlertDialog.Builder(UploadDeficiencyActivity.this)
                        .setTitle("Alert")
                        .setMessage("Data  Not Added !")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show();

            }


        }

    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDeficiencyActivity.this);
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

    void GetEmployeeId(){
        String url = con.base_url+"/api/admin/get-claim-data";


        RequestQueue mRequestQueue = Volley.newRequestQueue(UploadDeficiencyActivity.this,
                new HurlStack(null, getSocketFactory()));
        mRequestQueue.getCache().clear();
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject explrObjectd=new JSONObject(response);

                            JSONObject explrObject=explrObjectd.getJSONObject("data");
                            // JSONObject explrObject = jsonObj.getJSONObject(0);

//                    Log.d("mydata",response);
//                    JSONArray jsonObj=js.getJSONArray("data");
//
//                    for (int i = 0; i < jsonObj.length(); i++) {
//                        JSONObject explrObject = jsonObj.getJSONObject(0);
//



                            String tpa_claim_id = explrObject.getString("tpa_claim_id");
                            claim_id.setText(tpa_claim_id);
                            String employee_name = explrObject.getString("employee_name");
                            String member_email = explrObject.getString("member_email");
                            String member_mobile = explrObject.getString("member_mobile");
                            String member_name = explrObject.getString("member_name");
                            String member_relation = explrObject.getString("member_relation");
                            String ailment = explrObject.getString("ailment");
                            String policy_number = explrObject.getString("policy_number");
                            String policy_types = explrObject.getString("policy_type");
                            String policy_start_dates = explrObject.getString("policy_start_date");
                            String policy_end_dates = explrObject.getString("policy_end_date");

                            String member_start_date = explrObject.getString("member_start_date");
                            String company_name = explrObject.getString("company_name");
                            String sum_insured = explrObject.getString("sum_insured");
                            String claim_received_data_tpa = explrObject.getString("claim_received_data_tpa");
                            String hospitalization_date = explrObject.getString("hospitalization_date");
                            String discharge_date = explrObject.getString("discharge_date");
                            String claim_types = explrObject.getString("claim_type");
                            String hospital_name = explrObject.getString("hospital_name");
                            String hospital_add = explrObject.getString("hospital_add");
                            String hospital_city = explrObject.getString("hospital_city");
                            String hospital_state = explrObject.getString("hospital_state");
                            // String claim_amounts = explrObject.getString("claim_amount");

                            String hospital_pincode = explrObject.getString("hospital_pincode");
                            String claimed_amount = explrObject.getString("claimed_amount");
                            String billed_amount = explrObject.getString("billed_amount");
                            String claim_approved_amount = explrObject.getString("claim_approved_amount");
                            String claim_settled_amount = explrObject.getString("claim_settled_amount");
                            String claim_incurred_amount = explrObject.getString("claim_incurred_amount");
                            String claim_statuss = explrObject.getString("claim_status");
                            String claim_approval_date = explrObject.getString("claim_approval_date");
                            String claim_settled_date = explrObject.getString("claim_settled_date");
                            String pre_hospital_amount = explrObject.getString("pre_hospital_amount");
                            String post_hospital_amounts = explrObject.getString("post_hospital_amount");

                            String deficiency_reason = explrObject.getString("deficiency_reason");
                            String reject_season = explrObject.getString("reject_season");
                            String tpa_names = explrObject.getString("tpa_name");
                            String tpa_licence_no = explrObject.getString("tpa_licence_no");
                            String room_rent_chargess = explrObject.getString("room_rent_charges");
                            String consultation_chargess = explrObject.getString("consultation_charges");
                            String medicine_charges = explrObject.getString("medicine_charges");
                            String investigation_charges = explrObject.getString("investigation_charges");
                            String domiciliary_hospital_amount = explrObject.getString("domiciliary_hospital_amount");
                            String maternity_amounts = explrObject.getString("maternity_amount");
                            String maternity = explrObject.getString("maternity");

                            String daycare_amounts = explrObject.getString("daycare_amount");
                            String organ_donor_amount = explrObject.getString("organ_donor_amount");
                            String ancillary_service_amount = explrObject.getString("ancillary_service_amount");
                            String dental_amounts = explrObject.getString("dental_amount");
                            String out_patient_amounts= explrObject.getString("out_patient_amount");
                            String pa_amounts = explrObject.getString("pa_amount");
                            String ci_amounts = explrObject.getString("ci_amount");
                            String health_checkup_amounts = explrObject.getString("health_checkup_amount");
                            String ci_buffer_amounts = explrObject.getString("ci_buffer_amount");
                            String disallowed_amount = explrObject.getString("disallowed_amount");
                            String deficiency_raised_date = explrObject.getString("deficiency_raised_date");

                            String deficiency_first_reminder = explrObject.getString("deficiency_first_reminder");
                            String deficiency_closure_date = explrObject.getString("deficiency_closure_date");
                            String insurance_company_names = explrObject.getString("insurance_company_name");


                            // String balance_sum_insured = explrObject.getString("balance_sum_insured");
                            String room_category = explrObject.getString("room_category");
                            String claim_registered_tpa = explrObject.getString("claim_registered_tpa");
                            String provider_types = explrObject.getString("provider_type");
                            String rohini_code = explrObject.getString("rohini_code");
                            String tds_amounts = explrObject.getString("tds_amount");
                            String service_tax = explrObject.getString("service_tax");
                            String copayment_amounts = explrObject.getString("copayment_amount");

                            String insurance_claim_ids = explrObject.getString("insurance_claim_id");
                            String room_rent_amounts = explrObject.getString("room_rent_amount");
                            String icu_amount = explrObject.getString("icu_amount");
                            String icu_related_amount = explrObject.getString("icu_related_amount");
                            String nursing_amount = explrObject.getString("nursing_amount");
                            String disallowance_reason_1 = explrObject.getString("disallowance_reason_1");
                            String disallowance_reason_2 = explrObject.getString("disallowance_reason_2");
                            String disallowance_reason_3 = explrObject.getString("disallowance_reason_3");
                            String disallowance_reason_4 = explrObject.getString("disallowance_reason_4");
                            String disallowance_reason_5 = explrObject.getString("disallowance_reason_5");
                            String disallowance_reason_6 = explrObject.getString("disallowance_reason_6");




                            String report_amount = explrObject.getString("reported_amount");
                            String dudecutamount = explrObject.getString("deduction_amount");
                            String deduction_reason = explrObject.getString("deduction_reason");
                            String sub = explrObject.getString("claim_sub_status");
                            String documents = explrObject.getString("documents");



                            if (tpa_claim_id == "null" || tpa_claim_id.isEmpty()) {


                                claim_id.setText("-");

                            }else {
                                claim_id.setText(tpa_claim_id);
                            }



                            if (discharge_date == "null" || discharge_date.isEmpty()) {

                                raised_date.setText("-");

                            }else {
                                raised_date.setText(discharge_date);
                            }

                            if (deficiency_reason == "null" || deficiency_reason.isEmpty()) {

                                reason_def.setText("-");

                            }else {
                                reason_def.setText(deficiency_reason);
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


        params.put("claim_id", claimid);


        smr.setParams(params);




        mRequestQueue.add(smr);





    }

    void callSaveBankDetailsApi() {


        try {

            progressDialog = ProgressDialog.show(UploadDeficiencyActivity.this, "",
                    "Saving. Please wait...", true);
            RequestQueue rq = Volley.newRequestQueue(UploadDeficiencyActivity.this,
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


                                    new AlertDialog.Builder(UploadDeficiencyActivity.this)
                                            .setTitle("Success")
                                            .setMessage(message)
                                            .setIcon(R.drawable.checkmark)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {


                                                    DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                    UploadDeficiencyActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();


                                                }
                                            })
                                            .setNegativeButton(android.R.string.no, null).show();

                                } else {
                                    String message = jsonObject.getString("errors");
                                    new AlertDialog.Builder(UploadDeficiencyActivity.this)
                                            .setTitle("Error")
                                            .setMessage(message)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(android.R.string.ok, null).show();
                                }


                            } catch (Exception e) {
                                new AlertDialog.Builder(UploadDeficiencyActivity.this)
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
                    new AlertDialog.Builder(UploadDeficiencyActivity.this)
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




                    smr.put("relation", "relationdrop.getText().toString");
                    smr.put("step", "2");




                    return smr;
                }

                @Override
                protected Map<String, DataPart> getByteData() throws AuthFailureError {
                    Map<String, DataPart> params = new HashMap<>();

                    for (int i = 0; i < Image_List.size(); i++) {

                        long imageName2 = System.currentTimeMillis();
                        // params.put("images["+i+"]", new DataPart(imageName2 + ".jpg", getFileDataFromDrawable(Image_List.get(i))));

                        params.put("document_path["+i+"]", new DataPart(System.currentTimeMillis() + ".jpg",
                                AppHelper.getFileDataFromDrawable(UploadDeficiencyActivity.this,
                                        Image_List.get(i)),
                                "image/jpeg"));

                        params.put("filenames["+0+"]", new DataPart(System.currentTimeMillis() + ".jpg",
                                AppHelper.getFileDataFromDrawable(UploadDeficiencyActivity.this, Image_List.get(0)),
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
                        new AlertDialog.Builder(UploadDeficiencyActivity.this)
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
                = (ConnectivityManager)UploadDeficiencyActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void  requestMultiplePermissions(){
        Dexter.withActivity(UploadDeficiencyActivity.this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //   Toast.makeText(UploadDeficiencyActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
                        //  Toast.makeText(UploadDeficiencyActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}