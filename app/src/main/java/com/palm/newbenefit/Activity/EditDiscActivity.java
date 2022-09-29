package com.palm.newbenefit.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
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
import com.palm.newbenefit.ApiConfig.AppHelper;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.ApiConfig.VolleyMultiPartRequest;
import com.palm.newbenefit.Fragment.DashboardBenifitFragment;
import com.kmd.newbenefit.R;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class EditDiscActivity extends AppCompatActivity {
    EditText insurar_name;



    RelativeLayout adhaarBackLayout_cheque1,adhaarBackLayout_cheque1_two,adhaarBackLayout_cheque1_three;
    ImageView bankPreview1,bankPreview1_two,bankPreview1_three;

    Constants con = null;
    Context context;
    String user_id;


    String token;

    ProgressDialog progressDialog = null;


    Bitmap myBitmap;




    public static final int REQUEST_CODE_USER_PHOTO = 114;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1890;

    public static final int REQUEST_CODE_USER_PHOTO_two = 115;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_two = 1891;

    public static final int REQUEST_CODE_USER_PHOTO_three = 116;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_three = 1892;

    String selBankPath=null,selBankPath_cheque=null;
    Button submit;
    String family_id,disc_id;
    ImageView info_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_disc);

        con=new Constants();
        requestMultiplePermissions();
        context = EditDiscActivity.this;
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);


        insurar_name=findViewById(R.id.insurar_name);
        info_text=findViewById(R.id.info_text);
        submit=findViewById(R.id.submit);
        adhaarBackLayout_cheque1=findViewById(R.id.adhaarBackLayout_cheque1);
        adhaarBackLayout_cheque1_two=findViewById(R.id.adhaarBackLayout_cheque1_two);
        adhaarBackLayout_cheque1_three=findViewById(R.id.adhaarBackLayout_cheque1_three);

        bankPreview1=findViewById(R.id.bankPreview1);
        bankPreview1_two=findViewById(R.id.bankPreview1_two);
        bankPreview1_three=findViewById(R.id.bankPreview1_three);


        Intent intent = getIntent();



        family_id = intent.getStringExtra("pol");
        disc_id= intent.getStringExtra("disc_id");


        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


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

        adhaarBackLayout_cheque1_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialog_three();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dummy();
            }
        });






    }
    private void showPhotoPickerDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditDiscActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditDiscActivity.this);
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

    private void showPhotoPickerDialog_three() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditDiscActivity.this);
        builder.setTitle("Select");
        builder.setItems(R.array.photo_picker_option_image, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> values = Arrays.asList(getResources().getStringArray(R.array.photo_picker_option_image));


                if (values.get(which).toLowerCase().equalsIgnoreCase("gallery")) {
                    openGallery_three();
                    // galleryImage();
                } else if (values.get(which).toLowerCase().equalsIgnoreCase("camera")) {
                    openCamera_three();
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

    public void openGallery_three() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_USER_PHOTO_three);
    }

    public void openCamera_three() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,
                CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_three);
    }
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(EditDiscActivity.this.getContentResolver(), inImage, "Title", null);
        if (path != null) {

        } else {
            path = "dfgfdg";
        }
        return Uri.parse(path);
    }

    void dummy() {


        int count = 0;


        if (selBankPath == null) {
            ++count;
            new AlertDialog.Builder(EditDiscActivity.this)
                    .setTitle("No File Selected")
                    .setMessage("Please add Bank Id Proof file to upload")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();



        }


        if (selBankPath_cheque == null) {
            ++count;
            new AlertDialog.Builder(EditDiscActivity.this)
                    .setTitle("No File Selected")
                    .setMessage("Please add Bank Id Proof file to upload")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();



        }

        if (count == 0) {
            progressDialog = ProgressDialog.show(EditDiscActivity.this, "",
                    "Saving. Please wait...", true);


            try {

                // uploadPDF(displayName,pdffile);

                callSaveBankDetailsApi();


            } catch (Exception e) {

            }


        }

    }
    private void callSaveBankDetailsApi() {

        RequestQueue rq = Volley.newRequestQueue(EditDiscActivity.this,
                new HurlStack(null, getSocketFactory()));
        rq.getCache().clear();
        String url = con.base_url + "/api/broker/claim-deficiency-flow";


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

                                new AlertDialog.Builder(EditDiscActivity.this)
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                EditDiscActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

                                            }
                                        }).show();

                            } else {



                                new AlertDialog.Builder(EditDiscActivity.this)
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

                params.put("type","2");
                params.put("response",insurar_name.getText().toString().trim());
                params.put("claim_request_id",family_id);
                params.put("log_id",disc_id);


                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                try {


                    Log.d("image","No");
                    params.put("id_proof", new DataPart(System.currentTimeMillis() + ".jpg", AppHelper.getFileDataFromDrawable(EditDiscActivity.this, bankPreview1.getDrawable()), "image/jpeg"));



                } catch (Exception e) {

                }


                try {

                    Log.d("image","No");
                    params.put("doctor_prescription", new DataPart(System.currentTimeMillis() + ".jpg", AppHelper.getFileDataFromDrawable(EditDiscActivity.this, bankPreview1_two.getDrawable()), "image/jpeg"));

                } catch (Exception e) {

                }


                try {

                    Log.d("image","No");
                    params.put("other_document", new DataPart(System.currentTimeMillis() + ".jpg", AppHelper.getFileDataFromDrawable(EditDiscActivity.this, bankPreview1_three.getDrawable()), "image/jpeg"));

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
        if (EditDiscActivity.this.getContentResolver() != null) {
            Cursor cursor = EditDiscActivity.this.getContentResolver().query(uri, null, null, null, null);
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


                Uri tempUri = getImageUri(EditDiscActivity.this, photo);


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

                Cursor cursor = EditDiscActivity.this.getContentResolver().query(selectedImage,
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


                Uri tempUri = getImageUri(EditDiscActivity.this, photo);


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

                Cursor cursor = EditDiscActivity.this.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();



                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)EditDiscActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void  requestMultiplePermissions(){
        Dexter.withActivity(EditDiscActivity.this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(EditDiscActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditDiscActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
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



}