package com.palm.newbenefit.Fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.error.AuthFailureError
import com.android.volley.request.StringRequest
import com.android.volley.toolbox.Volley
import com.palm.newbenefit.Adapter.NomineeEnrollAdapter
import com.palm.newbenefit.ApiConfig.Constants
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener.ClickListener
import com.palm.newbenefit.Module.NomineeEnroll
import com.palm.newbenefit.R
import com.palm.tatarewamp.SslData.NullHostNameVerifier
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

/**
 * A simple [Fragment] subclass.
 */
class NomineeEnrollFragment : Fragment() {
    var gg = 0
    var con: Constants? = null

    var mobileNumber: String? = null
    var token: String? = null
    var policy_id: String? = null
    var user_id: String? = null
    var ob: MutableList<NomineeEnroll>? = null
    var adapter: NomineeEnrollAdapter? = null
    var recyclerView: RecyclerView? = null
    var info_text: ImageView? = null
    var emp_id: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =
            inflater.inflate(R.layout.fragment_nominee_enroll, container, false)
        con = Constants()
        val prefs = activity!!.getSharedPreferences(
            activity!!.packageName,
            Context.MODE_PRIVATE
        )
        token = prefs.getString("api_token", null)
        policy_id = prefs.getString("policy_id", null)
        recyclerView = v.findViewById(R.id.hos_claim_recycle)
        info_text = v.findViewById(R.id.info_text)

        //  setBankDet();
        //Data();
        val numberOfColumns = 1
        recyclerView!!.setLayoutManager(GridLayoutManager(activity, numberOfColumns))
        recyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                activity,
                recyclerView,
                object : ClickListener {
                    override fun onClick(
                        view: View,
                        position: Int
                    ) {
                    }

                    override fun onLongClick(
                        view: View,
                        position: Int
                    ) {
                    }
                })
        )
        val manager =
            GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        recyclerView!!.setLayoutManager(manager)
        return v
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && activity != null) {
            if (isNetworkAvailable) {


                //  getActivity();
                ob = ArrayList()
                adapter = NomineeEnrollAdapter(activity, ob, emp_id)
                recyclerView!!.adapter = adapter
                setBankDet()
            } else {
                AlertDialog.Builder(activity!!)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show()
            }
        }
    }

    private fun setBankDet() {
        val rq =
            Volley.newRequestQueue(activity)
        ob = ArrayList()
        adapter = NomineeEnrollAdapter(activity, ob, emp_id)
        recyclerView!!.adapter = adapter
        val url = con!!.base_url + "/api/employee/get/nominee"
        val smr: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    try {
                        info_text!!.visibility = View.GONE
                        recyclerView!!.visibility = View.VISIBLE
                        val js = JSONObject(response)
                        Log.d("response", response)

                        val status = js.getString("status")
if(status.equals("false")){
    recyclerView!!.setVisibility(View.GONE)
    info_text!!.setVisibility(View.VISIBLE)
}else {
    recyclerView!!.setVisibility(View.VISIBLE)
    info_text!!.setVisibility(View.GONE)
    val jsonObj = js.getJSONArray("data")


    for (i in 0 until jsonObj.length()) {
        val jo_area = jsonObj.getJSONObject(i)
        val fr_name = jo_area.getString("nominee_relation")
        val nominee_id = jo_area.getString("id")
        val emp_id = jo_area.getString("emp_id")
        val nominee_fname =
            jo_area.getString("nominee_fname")
        val nominee_lname =
            jo_area.getString("nominee_lname")
        val fr_id = jo_area.getString("nominee_relation")
        val guardian_relation =
            jo_area.getString("guardian_relation")
        val guardian_fname =
            jo_area.getString("guardian_fname")
        val guardian_lname =
            jo_area.getString("guardian_lname")
        val guardian_dob = jo_area.getString("guardian_dob")
        val share_percentile = jo_area.getString("share_per")
        val nominee_dob = jo_area.getString("nominee_dob")
        val confirmed_flag = "confirmed_flag"
        val confirmed_date = "confirmed_date"
        val status = jo_area.getString("is_confirm")
        val created_at = "created_at"
        ob!!.add(
            NomineeEnroll(
                fr_name,
                nominee_id,
                emp_id,
                nominee_fname,
                nominee_lname,
                fr_id,
                guardian_relation,
                guardian_fname,
                guardian_lname,
                guardian_dob,
                share_percentile,
                nominee_dob,
                confirmed_flag,
                confirmed_date,
                status,
                created_at
            )
        )
    }
    adapter!!.notifyDataSetChanged()
}
                    } catch (e: Exception) {
                    }
                }, Response.ErrorListener { error -> Log.e("onErrorResponse", error.toString()) }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers: MutableMap<String, String> =
                        HashMap()
                    headers["Authorization"] = "Bearer $token"
                    return headers
                }
            }



        val params =
            HashMap<String, String>()
        params["policy_id"] = policy_id!!
        Log.e(
            "policy_id",
            policy_id!!
        )
        smr.setParams(params)
        rq.add(smr)
    }

    override fun onResume() {
        super.onResume()
        if (!userVisibleHint) {
            return
        }
        if (isNetworkAvailable) {
            setBankDet()
        } else {
            AlertDialog.Builder(activity!!)
                .setTitle("Error?")
                .setMessage("Please Check Your Internet Connection")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show()
        }
    }

    private val isNetworkAvailable: Boolean
        private get() {
            val connectivityManager =
                activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    /* HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            SSLContext context = null;
            context = SSLContext.getInstance("TLS");

            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
*/
    private val socketFactory: SSLSocketFactory?
        private get() {
            var cf: CertificateFactory? = null
            try {
                cf = CertificateFactory.getInstance("X.509")
                val caInput = resources.openRawResource(R.raw.cert)
                val ca: Certificate
                try {
                    ca = cf.generateCertificate(caInput)
                    Log.e(
                        "CERT",
                        "ca=" + (ca as X509Certificate).subjectDN
                    )
                } finally {
                    caInput.close()
                }
                val keyStoreType = KeyStore.getDefaultType()
                val keyStore = KeyStore.getInstance(keyStoreType)
                keyStore.load(null, null)
                keyStore.setCertificateEntry("ca", ca)
                val tmfAlgorithm =
                    TrustManagerFactory.getDefaultAlgorithm()
                val tmf =
                    TrustManagerFactory.getInstance(tmfAlgorithm)
                tmf.init(keyStore)
                HttpsURLConnection.setDefaultHostnameVerifier(NullHostNameVerifier())
                val context = SSLContext.getInstance("TLS")
                context.init(null, tmf.trustManagers, null)
                HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)


                /* HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            SSLContext context = null;
            context = SSLContext.getInstance("TLS");

            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
*/
                return context.socketFactory
            } catch (e: CertificateException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyStoreException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }
            return null
        }
}