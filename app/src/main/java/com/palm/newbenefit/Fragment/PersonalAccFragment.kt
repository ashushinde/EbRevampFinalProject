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
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import com.palm.newbenefit.Adapter.FormCenterAdapter
import com.palm.newbenefit.ApiConfig.Constants
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener.ClickListener
import com.palm.newbenefit.Module.FormCenter
import com.kmd.newbenefit.R
import com.palm.tatarewamp.SslData.NullHostNameVerifier
import org.json.JSONException
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
class PersonalAccFragment : Fragment() {
    var ob: MutableList<FormCenter>? = null
    var adapter: FormCenterAdapter? = null
    var recyclerView: RecyclerView? = null
    var con: Constants? = null
    var info_text: ImageView? = null
    var company_id: String? = null
    var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =
            inflater.inflate(R.layout.fragment_group_claim, container, false)
        val prefs = activity!!.getSharedPreferences(
            activity!!.packageName,
            Context.MODE_PRIVATE
        )
        token = prefs.getString("api_token", null)
        info_text = v.findViewById(R.id.info_text_GH)
        con = Constants()
        recyclerView = v.findViewById(R.id.core_recycle)
        if (isNetworkAvailable) {
            ob = ArrayList()
            adapter = FormCenterAdapter(activity, ob)
            recyclerView!!.setAdapter(adapter)
            setCertData()
            //Data();
        } else {
            AlertDialog.Builder(activity!!)
                .setTitle("Error?")
                .setMessage("Please Check Your Internet Connection")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show()
        }
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
                        val train = ob!![position]

/*

                Intent intent = new Intent(getActivity(), WebviewActivity.class);    //jump to next activity
                startActivity(intent);
                intent.putExtra("pol_id", train.getPdf_file());


               getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
*/
                    }

                    override fun onLongClick(
                        view: View,
                        position: Int
                    ) {
                    }
                })
        )
        return v
    }

    fun setCertData() {
        info_text!!.visibility = View.GONE
        recyclerView!!.visibility = View.VISIBLE
        val url = con!!.base_url + "/api/employee/get/employee-documnet-list?user_type_name=employee"
        val rq = Volley.newRequestQueue(
            activity,
            HurlStack(null, getSocketFactory())
        )

        rq.cache.clear()
        val mStringRequest: StringRequest = object : StringRequest(
            Method.GET,
            url,
            Response.Listener { response ->
                try {
                    val js = JSONObject(response)
                    Log.d("personal", response)
                    val jsonObj = js.getJSONArray("data")
                    for (i in 0 until jsonObj.length()) {
                        val explrObject = jsonObj.getJSONObject(i)
                        if (explrObject.getString("policy_name")
                                .equals("Personal Accidant", ignoreCase = true)
                        ) {
                            ob!!.add(
                                FormCenter(
                                    explrObject.getString("document_name"),
                                    explrObject.getString("document_path")
                                )
                            )
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                    if (ob!!.isEmpty()) {
                        info_text!!.visibility = View.VISIBLE
                        recyclerView!!.visibility = View.GONE
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Log.e("onErrorResponse", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> =
                    HashMap()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        rq.add(mStringRequest)
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
     open fun getSocketFactory(): SSLSocketFactory? {
        var cf: CertificateFactory? = null
        try {
            cf = CertificateFactory.getInstance("X.509")
            val caInput = resources.openRawResource(R.raw.cert)
            val ca: Certificate
            try {
                ca = cf.generateCertificate(caInput)
                Log.e("CERT", "ca=" + (ca as X509Certificate).subjectDN)
            } finally {
                caInput.close()
            }
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)
            HttpsURLConnection.setDefaultHostnameVerifier(NullHostNameVerifier())
            val context = SSLContext.getInstance("TLSv1.2")
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