package com.palm.newbenefit.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.AppHelper;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.VolleyMultiPartRequest;
import com.kmd.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

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
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class UploadActivity extends AppCompatActivity {
    Constants con;

    String token = null;



    String selBankPath_cheque = null;



    private Uri filePath;


    private Uri img;
    String mydefaultid;





    ImageView bankPreview1;

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
String memberid;
    Calendar myCalendar = Calendar.getInstance();

    private static final int PICK_FILE_REQUEST = 1;
    RelativeLayout adhaarBackLayout_cheque1;

    LinearLayout  upload_id_proof;


    TextView  text_pdf;
    ImageView next_id;

    String first_doc = "0", first_id = "0";


    String relations,tpaid;
    String internaldoc ="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        requestMultiplePermissions();


        requestMultiplePermissions();
        con = new Constants();

        text_pdf = findViewById(R.id.text_pdf);


        next_id = findViewById(R.id.next_id);


        bankPreview1 = findViewById(R.id.bankPreview1);
        
        adhaarBackLayout_cheque1 = findViewById(R.id.adhaarBackLayout_cheque1);

        upload_id_proof = findViewById(R.id.upload_id_proof);







        saveBtn = findViewById(R.id.saveBank);
        info_text = findViewById(R.id.info_text);


        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);

        Intent intent = getIntent();



        relations = intent.getStringExtra("relation");
        tpaid = intent.getStringExtra("tpa");
        memberid= intent.getStringExtra("memberid");



        if(( relations.equalsIgnoreCase("Self"))
                || relations.equalsIgnoreCase("Spouse/Partner")){


            if(tpaid.equalsIgnoreCase("Male")){
                bankPreview1.setBackgroundResource(R.drawable.man);
            }else {
                bankPreview1.setBackgroundResource(R.drawable.woman);
            }


        }
        if(( relations.equalsIgnoreCase("Mother"))
                ||( relations.equalsIgnoreCase("Mother-In-Law"))){
            bankPreview1.setBackgroundResource(R.drawable.grandmother);
        }

        if( relations.equalsIgnoreCase("Father")
        ){
            bankPreview1.setBackgroundResource(R.drawable.grandfather);
        }

        if( relations.equalsIgnoreCase("Father-in-law")
        ){
            bankPreview1.setBackgroundResource(R.drawable.grandfather);
        }

        if( relations.equalsIgnoreCase("Mother")
        ){
            bankPreview1.setBackgroundResource(R.drawable.grandmother);
        }

        if( relations.equalsIgnoreCase("Mother-in-law")
        ){
            bankPreview1.setBackgroundResource(R.drawable.grandmother);
        }

        if( relations.equalsIgnoreCase("Daughter")
        ){
            bankPreview1.setBackgroundResource(R.drawable.girl);
        }
        if( relations.equalsIgnoreCase("Son")
        ){
            bankPreview1.setBackgroundResource(R.drawable.boy);
        }





        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isNetworkAvailable()) {


                    dummy();
                } else {
                    new AlertDialog.Builder(UploadActivity.this)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                }


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





    }

    void dummy() {


        int count = 0;


        if (bankPreview1.getVisibility() == View.VISIBLE) {
            if (selBankPath_cheque == null) {
                ++count;
                new AlertDialog.Builder(UploadActivity.this)
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

        RequestQueue rq = Volley.newRequestQueue(UploadActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url + "/api/employee/update/employee-member-details";


        VolleyMultiPartRequest multiPartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e("Response ", new String(response.data));
                        try {

                            progressDialog.dismiss();
                            //Toast.makeText(UploadActivity.this, new String(response.data), Toast.LENGTH_LONG).show();
                            JSONObject js = new JSONObject(new String(response.data));

                            String status = String.valueOf(js.getBoolean("status"));
                            if (status.equals("true")) {
                                String message = js.getString("message");



                                new AlertDialog.Builder(UploadActivity.this)
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(R.drawable.checkmark)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                finish();
                                            }
                                        }).show();

                            } else {


                                new AlertDialog.Builder(UploadActivity.this)
                                        .setTitle("Error")
                                        .setMessage("The document may not be greater than 5 mb.")
                                        .setIcon(android.R.drawable.btn_dialog)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        }).show();


                               /* new AlertDialog.Builder(UploadActivity.this)
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
                            new AlertDialog.Builder(UploadActivity.this)
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
                new AlertDialog.Builder(UploadActivity.this)
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

                params.put("member_id", memberid);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,DataPart> params = new HashMap<>();
                try {


                        Log.d("image","No");
                        params.put("image", new DataPart(System.currentTimeMillis() + ".jpg",
                                AppHelper.getFileDataFromDrawable(getBaseContext(), bankPreview1.getDrawable()),
                                "image/jpeg"));



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








    private void showPhotoPickerDialoga() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_option_image, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_option_image));


                if (values.get(which).toLowerCase().equalsIgnoreCase("gallery")) {
                    openGalleryc();
                    // galleryImage();
                } else if (values.get(which).toLowerCase().equalsIgnoreCase("camera")) {
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



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEc) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");


                Uri tempUri = getImageUri(this, photo);


                File finalFile = new File(getRealPathFromURI(tempUri));
                img=img;

                first_id="1";
                text_pdf.setVisibility(View.GONE);
                next_id.setVisibility(View.GONE);
                mydefaultid="2";
                bankPreview1.setBackgroundResource(0);
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
                bankPreview1.setBackgroundResource(0);
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                text_pdf.setVisibility(View.GONE);
                next_id.setVisibility(View.GONE);
                bankPreview1.setImageBitmap(bitmap);
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


