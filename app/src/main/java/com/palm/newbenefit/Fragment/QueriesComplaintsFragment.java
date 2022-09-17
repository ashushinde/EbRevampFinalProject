package com.palm.newbenefit.Fragment;


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
import com.palm.newbenefit.Activity.QueriesListActivity;
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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueriesComplaintsFragment extends Fragment {
Spinner sp_query_type,sp_sub_type;
EditText edt_comment;
String token;
Constants con;
    ProgressDialog progressDialog = null;
    ArrayList<String> bank_name = null;
    ArrayList<SpinnerModal> bank_nameList = null;
    ArrayAdapter<SpinnerModal> bank_nameAdapter = null;
    String bank_city_value = "";
    ArrayList<String> bank_city = null;
    ArrayList<SpinnerModal> bank_cityList = null;
    ArrayAdapter<SpinnerModal> bank_cityAdapter = null;
    ImageView bankPreview1;
    RelativeLayout adhaarBackLayout_cheque1;
    TextView pdf_document,pdf_id;
    ImageView next_id,next_document;
    String path_id,path_document;
    public static final int REQUEST_CODE_USER_PHOTOc = 114;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEc = 1890;
    LinearLayout upload_id_proof;
    String selBankPath_cheque = null;
    Button btn_can,btn_sub;
    public QueriesComplaintsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_queries_complaints, container, false);

        SharedPreferences prefs =getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        con=new Constants();
        sp_query_type=v.findViewById(R.id.sp_query_type);
        sp_sub_type=v.findViewById(R.id.sp_sub_type);
        edt_comment=v.findViewById(R.id.edt_comment);

        bankPreview1 =v. findViewById(R.id.bankPreview1);

        adhaarBackLayout_cheque1= v.findViewById(R.id.adhaarBackLayout_cheque1);

        upload_id_proof= v.findViewById(R.id.upload_id_proof);

        btn_can= v.findViewById(R.id.btn_can);
        btn_sub= v.findViewById(R.id.btn_sub);

        if(isNetworkAvailable()){
            getBankName();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show();
        }

        upload_id_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPhotoPickerDialoga();
            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(getActivity(), QueriesListActivity.class);
               startActivity(intent);
            }
        });

        btn_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count =0;
                SpinnerModal sp_query_typea = (SpinnerModal) sp_query_type.getSelectedItem();
                SpinnerModal sp_sub_typea = (SpinnerModal) sp_sub_type.getSelectedItem();


                if (sp_query_typea.getSelKey().equalsIgnoreCase("")) {
                    ++count;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Select Query Type")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                    return;
                }


                if (edt_comment.getText().toString().trim().length() <3) {
                    ++count;
                    edt_comment.setError("Add Your Comments");
                } else {
                    edt_comment.setError(null);
                }

                if (sp_sub_typea.getSelKey().equalsIgnoreCase("")) {
                    ++count;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Select Sub Type")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show();
                    return;
                }
//
//                if(bankPreview1.getVisibility()==View.VISIBLE){
//                    if (selBankPath_cheque == null) {
//                        ++count;
//                        new AlertDialog.Builder(getActivity())
//                                .setTitle("No File Selected")
//                                .setMessage("Please Add Document")
//                                .setIcon(android.R.drawable.ic_dialog_info)
//                                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                                    }
//                                }).show();
//
//
//                    }
//                }

                if(count==0){
                    //saveBankDet();
                    callSaveBankDetailsApi();
                }


            }
        });




        sp_query_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerModal bank_name_modal = (SpinnerModal) adapterView.getSelectedItem();
                if (!bank_name_modal.selValue.equals("")){
                    try{


                        if (isNetworkAvailable()) {

                            getBankCity(bank_name_modal.getSelKey(), bank_city_value);
                        }else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Error?")
                                    .setMessage("Please Check Your Internet Connection")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(android.R.string.ok, null).show();
                        }


                    }catch (Exception e)
                    {

                    }
                }







            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

                bankPreview1.setImageBitmap(bitmap);
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

        String url = con.base_url+"/api/admin/get/masterQueryType";


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
                    bank_nameList.add(new SpinnerModal("", "Select Query Type"));
                    bank_name.add("");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        bank_nameList.add(new SpinnerModal(jsonObj.getString("id"), jsonObj.getString("name")));
                        bank_name.add(jsonObj.getString("name"));
                    }
                    bank_nameAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_nameList);
                    bank_nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_query_type.setAdapter(bank_nameAdapter);







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

    public void getBankCity(final String bank_name, final String set_bank_city) {

        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url+"/api/admin/get/subQueryType";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            Log.d("sub query",response);







                            JSONObject js=new JSONObject(response);
                            JSONArray jsonArr = js.getJSONArray("data");



                            bank_city = new ArrayList<>();
                            bank_cityList = new ArrayList<>();
                            bank_cityList.add(new SpinnerModal("", "Select Sub Query "));
                            bank_city.add("");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);

                                bank_cityList.add(new SpinnerModal(jsonObj.getString("id"),
                                        jsonObj.getString("name")));
                                bank_city.add(jsonObj.getString("name"));
                            }
                            bank_cityAdapter = new ArrayAdapter<SpinnerModal>(getActivity(), R.layout.spinner_item, bank_cityList);
                            bank_cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_sub_type.setAdapter(bank_cityAdapter);





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
        SpinnerModal bank_name_modala = (SpinnerModal) sp_query_type.getSelectedItem();



        HashMap<String, String> params = new HashMap<>();
        params.put("query_id", bank_name);
        Log.d("query_id", bank_name);


        smr.setParams(params);
        rq.add(smr);
    }


    private void callSaveBankDetailsApi() {

        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);


        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/api/employer/create/queries";

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
                            String message=js.getString("message");



                            if (status.equals("true")) {

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Success")
                                        .setMessage(message)
                                        .setIcon(R.drawable.checkmark)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                DashboardBenifitFragment travel = new DashboardBenifitFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout_for_fragement, travel, travel.getTag()).addToBackStack("back").commit();

                                            }
                                        }).show();

                            }else {

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error")
                                        .setMessage(message)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                            }
                                        }).show();
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
                SpinnerModal bankSel = (SpinnerModal) sp_query_type.getSelectedItem();
                SpinnerModal citySel = (SpinnerModal) sp_sub_type.getSelectedItem();







                params.put("query_type_id", bankSel.getSelKey());
                params.put("comments", edt_comment.getText().toString());
                params.put("query_sub_type_id", citySel.getSelKey());

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                try {



                  //  params.put("document", new DataPart(System.currentTimeMillis() + ".jpg", AppHelper.getFileDataFromDrawable(getContext(), bankPreview1.getDrawable()), "image/jpeg"));


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



    private void saveBankDet() {
        progressDialog = ProgressDialog.show(getActivity(), "",
                "Saving. Please wait...", true);


        RequestQueue rq = Volley.newRequestQueue(getActivity(),
                new HurlStack(null, getSocketFactory()));

        rq.getCache().clear();
        String url = con.base_url + "/api/employer/create/queries";
        StringRequest smr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            Log.d("save data",response);

                            progressDialog.dismiss();


                            JSONObject js=new JSONObject(response);

                            String status= String.valueOf(js.getBoolean("status"));
                            String message=js.getString("message");



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

                            }else {

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Error")
                                        .setMessage(message)
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


        SpinnerModal bankSel = (SpinnerModal) sp_query_type.getSelectedItem();
        SpinnerModal citySel = (SpinnerModal) sp_sub_type.getSelectedItem();






        HashMap<String, String> params = new HashMap<>();
        params.put("query_type_id", bankSel.getSelKey());
        params.put("comments", edt_comment.getText().toString());
        params.put("query_sub_type_id", citySel.getSelKey());
        params.put("document", selBankPath_cheque);



        smr.setParams(params);
        rq.add(smr);
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



}
