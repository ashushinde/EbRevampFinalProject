package com.palm.newbenefit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.palm.newbenefit.ApiConfig.Constants;
import com.palm.newbenefit.R;
import com.palm.tatarewamp.SslData.NullHostNameVerifier;

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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ViewOverAllClaimActivity extends AppCompatActivity {
TextView claim_id ,claim_status;

TextView bill_amount,claim_aprove_amount,claim_increed_amount,chrge_settle_amount
        ,post_hospital_amount,pre_hos_amount,room_rent_charges,consultation_charges,med_charge,invest_charge
        ,dom_charge,icu_amount,icu_rel_amount,nurse_amount,health_checkup_amount,maternity_amount
        ,daycare_amount,organ_don_amount,ancilary_service_amount,dental_amount,patient_amount
        ,pa_amount,ci_amount,tds_amount,service_tax,copayment_amount,claim_exe_amount,disallow_amount,settle_amount
        ,policy_type,policy_no,ins_company_name,corporate_name,policy_date,policy_end_date
        ,employe_name,emp_mob_no,emp_email_id,claim_aprove_date,claim_settle_date
        ,def_reason,reject_reason,def_raised_date,def_clo_date,def_first_remind,
        patient_name,relation_emp,claim_aliment,tpa_name,claim_type,source,claim_reg_date,claim_amount,hos_name
        ,hos_add,hos_city,hos_state,hos_pincode,hos_date,dis_date,room_category,maternity,doc_upload;

    Constants con = null;
    Context context;
    String mobileNumber = null;
    String token = null;
    String user_id = null;
    String claimid,claim_amnt;
    TextView deduct_amount,reported_amount,
            def_second_remind,balance_sum,
            claim_typeipd,exp_clos_date,deduct_reason,claim_sub_status;
  TextView resolvedeficiency;
ImageView info_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = new Constants();


        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        token = prefs.getString("api_token", null);
        setContentView(R.layout.activity_view_over_all_claim);
        med_charge=findViewById(R.id.med_charge);
        consultation_charges=findViewById(R.id.consultation_charges);
        room_rent_charges=findViewById(R.id.room_rent_charges);
        pre_hos_amount=findViewById(R.id.pre_hos_amount);
        post_hospital_amount=findViewById(R.id.post_hospital_amount);
        chrge_settle_amount=findViewById(R.id.chrge_settle_amount);
        claim_increed_amount=findViewById(R.id.claim_increed_amount);
        def_second_remind=findViewById(R.id.def_second_remind);
        claim_aprove_amount=findViewById(R.id.claim_aprove_amount);
        bill_amount=findViewById(R.id.bill_amount);
        deduct_reason=findViewById(R.id.deduct_reason);
        claim_sub_status=findViewById(R.id.claim_sub_status);
        deduct_amount=findViewById(R.id.deduct_amount);
        reported_amount=findViewById(R.id.reported_amount);
        balance_sum=findViewById(R.id.balance_sum);
        info_text=findViewById(R.id.info_text);
        resolvedeficiency=findViewById(R.id.resolvedeficiency);
        maternity_amount=findViewById(R.id.maternity_amount);
        health_checkup_amount=findViewById(R.id.health_checkup_amount);
        nurse_amount=findViewById(R.id.nurse_amount);
        icu_rel_amount=findViewById(R.id.icu_rel_amount);
        icu_amount=findViewById(R.id.icu_amount);
        dom_charge=findViewById(R.id.dom_charge);
        invest_charge=findViewById(R.id.invest_charge);
        exp_clos_date=findViewById(R.id.exp_clos_date);
        claim_typeipd=findViewById(R.id.claim_typeipd);
        claim_aprove_date=findViewById(R.id.claim_aprove_date);
        emp_email_id=findViewById(R.id.emp_email_id);
        emp_mob_no=findViewById(R.id.emp_mob_no);
        employe_name=findViewById(R.id.employe_name);
        policy_end_date=findViewById(R.id.policy_end_date);
        policy_date=findViewById(R.id.policy_date);
        corporate_name=findViewById(R.id.corporate_name);
        ins_company_name=findViewById(R.id.ins_company_name);
        policy_no=findViewById(R.id.policy_no);
        policy_type=findViewById(R.id.policy_type);
        settle_amount=findViewById(R.id.settle_amount);
        disallow_amount=findViewById(R.id.disallow_amount);
        claim_exe_amount=findViewById(R.id.claim_exe_amount);
        copayment_amount=findViewById(R.id.copayment_amount);
        service_tax=findViewById(R.id.service_tax);
        tds_amount=findViewById(R.id.tds_amount);
        ci_amount=findViewById(R.id.ci_amount);
        pa_amount=findViewById(R.id.pa_amount);
        patient_amount=findViewById(R.id.patient_amount);
        dental_amount=findViewById(R.id.dental_amount);
        ancilary_service_amount=findViewById(R.id.ancilary_service_amount);
        organ_don_amount=findViewById(R.id.organ_don_amount);
        daycare_amount=findViewById(R.id.daycare_amount);

        claim_id=findViewById(R.id.claim_id);
        claim_status=findViewById(R.id.claim_status);
        doc_upload=findViewById(R.id.doc_upload);
        maternity=findViewById(R.id.maternity);
        room_category=findViewById(R.id.room_category);
        dis_date=findViewById(R.id.dis_date);
        hos_date=findViewById(R.id.hos_date);
        hos_pincode=findViewById(R.id.hos_pincode);
        hos_state=findViewById(R.id.hos_state);
        hos_city=findViewById(R.id.hos_city);
        hos_add=findViewById(R.id.hos_add);
        hos_name=findViewById(R.id.hos_name);
        claim_amount=findViewById(R.id.claim_amount);
        claim_reg_date=findViewById(R.id.claim_reg_date);
        source=findViewById(R.id.source);
        claim_type=findViewById(R.id.claim_type);
        tpa_name=findViewById(R.id.tpa_name);
        claim_aliment=findViewById(R.id.claim_aliment);
        relation_emp=findViewById(R.id.relation_emp);
        patient_name=findViewById(R.id.patient_name);
        def_first_remind=findViewById(R.id.def_first_remind);
        def_clo_date=findViewById(R.id.def_clo_date);
        reject_reason=findViewById(R.id.reject_reason);
        def_raised_date=findViewById(R.id.def_raised_date);
        def_reason=findViewById(R.id.def_reason);
        claim_settle_date=findViewById(R.id.claim_settle_date);
        Intent intent = getIntent();



        claimid = intent.getStringExtra("claim_id");
        claim_amnt = intent.getStringExtra("claim_amnt");


        info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        resolvedeficiency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewOverAllClaimActivity.this, UploadDeficiencyActivity.class);

                intent.putExtra("claim_id", claimid);
                intent.putExtra("claim_amnt","");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });



        doc_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewOverAllClaimActivity.this,
                        ViewDataActivtyDoc.class);

                intent.putExtra("claim_id", claimid);
                intent.putExtra("claim_amnt","");
                //  intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });




        GetEmployeeId();


    }


    void GetEmployeeId(){
        String url = con.base_url+"/api/admin/get-claim-data";


       RequestQueue mRequestQueue = Volley.newRequestQueue(ViewOverAllClaimActivity.this,
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
                            String employee_mail = explrObject.getString("employee_mail");
                            String employee_mobile_number = explrObject.getString("employee_mobile_number");

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


                            String deficiency_second_reminder = explrObject.getString("deficiency_second_reminder");

                            String report_amount = explrObject.getString("reported_amount");
                            String dudecutamount = explrObject.getString("deduction_amount");
                            String deduction_reason = explrObject.getString("deduction_reason");
                            String sub = explrObject.getString("claim_sub_status");
                          String documents = explrObject.getString("documents");
                            String balance_suminsured = explrObject.getString("balance_suminsured");
                            String deficiency_expected_closure_date = explrObject.getString("deficiency_expected_closure_date");

                            String claim_type_ipd_opd =
                                    explrObject.getString("claim_type_ipd_opd");
                            if (deficiency_expected_closure_date == "null" || deficiency_expected_closure_date.isEmpty()) {

                                claim_typeipd.setText("-");

                            }else {
                                claim_typeipd.setText(claim_type_ipd_opd);
                            }



                            if (deficiency_expected_closure_date == "null" || deficiency_expected_closure_date.isEmpty()) {

                                exp_clos_date.setText("-");

                            }else {
                                exp_clos_date.setText(deficiency_expected_closure_date);
                            }
                            if (deficiency_second_reminder == "null" || deficiency_second_reminder.isEmpty()) {

                                def_second_remind.setText("-");

                            }else {
                                def_second_remind.setText(deficiency_second_reminder);
                            }

                            if (balance_suminsured == "null" || balance_suminsured.isEmpty()) {

                                balance_sum.setText("-");

                            }else {
                                balance_sum.setText(balance_suminsured);
                            }


                            if (sub == "null" || report_amount.isEmpty()||
                                    sub.equalsIgnoreCase("null")
                                    ||sub.equalsIgnoreCase("0")) {

                                source.setText("Online");

                            }else {
                                source.setText("Offline");
                            }

                            if (sub == "null" || report_amount.isEmpty()||
                                    sub.equalsIgnoreCase("null")
                                    ||sub.equalsIgnoreCase("0")) {



                            }else {
                                claim_sub_status.setText(sub);
                            }

                            if (report_amount == "null" || report_amount.isEmpty()||
                                    report_amount.equalsIgnoreCase("null")
                        ||report_amount.equalsIgnoreCase("0")) {



                            }else {
                                reported_amount.setText(report_amount);
                            }


                            if (deduction_reason == "null" || deduction_reason.isEmpty()||
                                    deduction_reason.equalsIgnoreCase("null")
                                    ||deduction_reason.equalsIgnoreCase("0")) {

                                deduct_reason.setText("-");

                            }else {
                                deduct_reason.setText(deduction_reason);
                            }



                            if (dudecutamount == "null" || dudecutamount.isEmpty()||
                                    dudecutamount.equalsIgnoreCase("null")
                                    ||dudecutamount.equalsIgnoreCase("0")) {



                            }else {
                                deduct_amount.setText(dudecutamount);
                            }

//                        if (tpa_claim_id == "null" || tpa_claim_id.isEmpty()) {
//
//
//
//                        }else {
//                            claim_id.setText(tpa_claim_id);
//                        }


                        if (employee_name == "null" || employee_name.isEmpty()) {



                        }else {
                            employe_name.setText(employee_name);
                        }
                        if (employee_mail == "null" || employee_mail.isEmpty()) {



                        }else {
                            emp_email_id.setText(employee_mail);
                        }
                        if (employee_mobile_number == "null" || employee_mobile_number.isEmpty()) {



                        }else {
                            emp_mob_no.setText(employee_mobile_number);
                        }

                            if (claim_settled_amount == "null" || claim_settled_amount.isEmpty()) {



                            }else {
                                chrge_settle_amount.setText(claim_settled_amount);
                            }



                        if (member_name == "null" || member_name.isEmpty()) {



                            }else {
                            patient_name.setText(member_name);
                        }


                        if (member_relation == "null" || member_relation.isEmpty()) {



                        }else {
                            relation_emp.setText(member_relation);
                        }
                        if (ailment == "null" || ailment.isEmpty()) {



                        }else {
                            claim_aliment.setText(ailment);
                        }
                        if (policy_number == "null" || policy_number.isEmpty()) {



                        }else {
                            policy_no.setText(policy_number);
                        }


                        if (policy_types == "null" || policy_types.isEmpty()) {



                        }else {
                            policy_type.setText(policy_types);
                        }


                        if (policy_start_dates == "null" || policy_start_dates.isEmpty()) {



                        }else {
                            policy_date.setText(policy_start_dates);
                        }

                        if (policy_end_dates== "null" || policy_end_dates.isEmpty()) {



                        }else {
                            policy_end_date.setText(policy_end_dates);
                        }

                        if (claim_amnt== "null" || claim_amnt.isEmpty()) {



                        }else {
                            claim_exe_amount.setText(claim_amnt);
                        }


//                        if (member_start_date == "null" || member_start_date.isEmpty()) {
//
//                            star.setText(member_start_date);
//
//                        }


                        if (company_name == "null" || company_name.isEmpty()) {



                        }else {
                            corporate_name.setText(company_name);
                        }


//                        if (sum_insured == "null" || sum_insured.isEmpty()) {
//
//                            emp_mob_no.setText(sum_insured);
//
//                        }

                        if (claim_received_data_tpa == "null" || claim_received_data_tpa.isEmpty()) {



                        }else {
                            claim_reg_date.setText(claim_received_data_tpa);
                        }

                        if (hospitalization_date == "null" || hospitalization_date.isEmpty()) {



                        }else {
                            hos_date.setText(hospitalization_date);
                        }

                        if (discharge_date == "null" || discharge_date.isEmpty()) {



                        }else {
                            dis_date.setText(discharge_date);
                        }


                        if (claim_types == "null" || claim_types.isEmpty()) {



                        }else {
                            claim_type.setText(claim_types);
                        }


                        if (hospital_name == "null" || hospital_name.isEmpty()) {



                        }else {
                            hos_name.setText(hospital_name);
                        }


                        if (hospital_add == "null" || hospital_add.isEmpty()) {



                        }else {
                            hos_add.setText(hospital_add);
                        }
                        if (hospital_city == "null" || hospital_city.isEmpty()) {


                        }else {
                            hos_city.setText(hospital_city);

                        }
                        if (hospital_state == "null" || hospital_state.isEmpty()) {



                        }else {
                            hos_state.setText(hospital_state);
                        }
                        if (hospital_pincode == "null" || hospital_pincode.isEmpty()) {


                        }else {
                            hos_pincode.setText(hospital_pincode);

                        }
                        if (claimed_amount == "null" || claimed_amount.isEmpty()) {



                        }else {
                            claim_amount.setText(claimed_amount);
                        }


                        if (billed_amount == "null" || billed_amount.isEmpty()) {



                        }else {
                            bill_amount.setText(billed_amount);
                        }


//                        if (claimed_amount == "null" || claimed_amount.isEmpty()) {
//
//                            emp_mob_no.setText(claimed_amount);
//
//                        }

                        if (claim_approved_amount == "null" || claim_approved_amount.isEmpty()) {



                        }else {
                            claim_aprove_amount.setText(claim_approved_amount);
                        }
                        if (claim_settled_amount == "null" || claim_settled_amount.isEmpty()) {



                        }else {
                            settle_amount.setText(claim_settled_amount);
                        }
                        if (claim_incurred_amount == "null" || claim_incurred_amount.isEmpty()) {



                        }else {
                            claim_increed_amount.setText(claim_incurred_amount);
                        }
                        if (claim_statuss == "null" || claim_statuss.isEmpty()) {


                        }else {
                            claim_status.setText(claim_statuss);

                        }
                        if (claim_approval_date == "null" || claim_approval_date.isEmpty()) {



                        }else {
                            claim_aprove_date.setText(claim_approval_date);
                        }
                        if (claim_settled_date == "null" || claim_settled_date.isEmpty()) {



                        }else {
                            claim_settle_date.setText(claim_settled_date);
                        }
                        if (pre_hospital_amount == "null" || pre_hospital_amount.isEmpty()) {



                        }else {
                            pre_hos_amount.setText(pre_hospital_amount);
                        }
                        if (post_hospital_amounts == "null" || post_hospital_amounts.isEmpty()) {



                        }else {
                            post_hospital_amount.setText(post_hospital_amounts);
                        }
                        if (deficiency_reason == "null" || deficiency_reason.isEmpty()) {


                        }else {
                            def_reason.setText(deficiency_reason);

                        }
                        if (reject_season == "null" || reject_season.isEmpty()) {



                        }else {
                            reject_reason.setText(reject_season);
                        }
                        if (tpa_names == "null" || tpa_names.isEmpty()) {


                        }else {
                            tpa_name.setText(tpa_names);

                        }
//                        if (tpa_licence_no == "null" || tpa_licence_no.isEmpty()) {
//
//                            emp_mob_no.setText(tpa_licence_no);
//
//                        }
                        if (room_rent_amounts == "null" || room_rent_amounts.isEmpty()) {


                        }else {
                            room_rent_charges.setText(room_rent_amounts);

                        }
                        if (consultation_chargess == "null" || consultation_chargess.isEmpty()) {



                        }else {
                            consultation_charges.setText(consultation_chargess);
                        }
                        if (medicine_charges == "null" || medicine_charges.isEmpty()) {



                        }else {
                            med_charge.setText(medicine_charges);
                        }
                        if (investigation_charges == "null" || investigation_charges.isEmpty()) {



                        }else {
                            invest_charge.setText(investigation_charges);
                        }
                        if (domiciliary_hospital_amount == "null" || domiciliary_hospital_amount.isEmpty()) {



                        }else {
                            dom_charge.setText(domiciliary_hospital_amount);
                        }
                        if (maternity_amounts == "null" || maternity_amounts.isEmpty()) {



                        }else {
                            maternity_amount.setText(maternity_amounts);
                        }
//                        if (maternity == "null" || maternity.isEmpty()) {
//
//                            emp_mob_no.setText(maternity);
//
//                        }
                        if (daycare_amounts == "null" || daycare_amounts.isEmpty()) {



                        }else {
                            daycare_amount.setText(daycare_amounts);
                        }
                        if (organ_donor_amount == "null" || organ_donor_amount.isEmpty()) {



                        }else {
                            organ_don_amount.setText(organ_donor_amount);
                        }
                        if (ancillary_service_amount == "null" || ancillary_service_amount.isEmpty()) {



                        }else {
                            ancilary_service_amount.setText(ancillary_service_amount);
                        }
                        if (dental_amounts == "null" || dental_amounts.isEmpty()) {



                        }else {
                            dental_amount.setText(dental_amounts);
                        }
                        if (out_patient_amounts == "null" || out_patient_amounts.isEmpty()) {



                        }else {
                            patient_amount.setText(out_patient_amounts);
                        }
                        if (pa_amounts == "null" || pa_amounts.isEmpty()) {



                        }else {
                            pa_amount.setText(pa_amounts);
                        }
                        if (ci_amounts == "null" || ci_amounts.isEmpty()) {



                        }else {
                            ci_amount.setText(ci_amounts);
                        }
                        if (health_checkup_amounts == "null" || health_checkup_amounts.isEmpty()) {



                        }else {
                            health_checkup_amount.setText(health_checkup_amounts);
                        }

                        if (ci_buffer_amounts == "null" || ci_buffer_amounts.isEmpty()) {



                        }else {
                            tds_amount.setText(ci_buffer_amounts);
                        }

                        if (disallowed_amount == "null" || disallowed_amount.isEmpty()) {



                        }else {
                            disallow_amount.setText(disallowed_amount);
                        }
                        if (deficiency_raised_date == "null" || deficiency_raised_date.isEmpty()) {



                        }else {
                            def_raised_date.setText(deficiency_raised_date);
                        }
                        if (deficiency_first_reminder == "null" || deficiency_first_reminder.isEmpty()) {



                        }else {
                            def_first_remind.setText(deficiency_first_reminder);
                        }

                        if (deficiency_closure_date == "null" || deficiency_closure_date.isEmpty()) {


                        }else {

                            def_clo_date.setText(deficiency_closure_date);
                        }

                        if (insurance_company_names == "null" || insurance_company_names.isEmpty()) {


                        }else {

                            ins_company_name.setText(insurance_company_names);
                        }
//                        if (balance_sum_insured == "null" || balance_sum_insured.isEmpty()) {
//
//                            sum.setText(balance_sum_insured);
//
//                        }

                        if (room_rent_chargess == "null" || room_rent_chargess.isEmpty()) {



                        }else {
                            room_rent_charges.setText(room_rent_chargess);
                        }

                        if (claim_registered_tpa == "null" || claim_registered_tpa.isEmpty()) {



                        }else {
                            claim_reg_date.setText(claim_registered_tpa);
                        }
//                        if (provider_types == "null" || provider_types.isEmpty()) {
//
//                            provider_type.setText(provider_types);
//
//                        }

//                        if (rohini_code == "null" || rohini_code.isEmpty()) {
//
//                            emp_mob_no.setText(rohini_code);
//
//                        }

                        if (tds_amounts == "null" || tds_amounts.isEmpty()) {


                        }else {
                            tds_amount.setText(tds_amounts);

                        }

                        if (service_tax == "null" || service_tax.isEmpty()) {



                        }else {
                            ancilary_service_amount.setText(service_tax);
                        }

                        if (copayment_amounts == "null" || copayment_amounts.isEmpty()) {



                        }else {
                            copayment_amount.setText(copayment_amounts);
                        }
                      /*  if (insurance_claim_ids == "null" || insurance_claim_ids.isEmpty()) {



                        }else {
                            claim_id.setText(insurance_claim_ids);
                        }
*/
                        if (room_rent_chargess == "null" || room_rent_chargess.isEmpty()) {



                        }else {
                            room_rent_charges.setText(room_rent_chargess);
                        }

                        if (icu_related_amount == "null" || icu_related_amount.isEmpty()) {



                        }else {
                            icu_rel_amount.setText(icu_related_amount);
                        }
                        if (nursing_amount == "null" || nursing_amount.isEmpty()) {



                        }else {
                            nurse_amount.setText(nursing_amount);
                        }
                        if (disallowance_reason_1 == "null" || disallowance_reason_1.isEmpty()) {



                        }else {
                            disallow_amount.setText(disallowance_reason_1);
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