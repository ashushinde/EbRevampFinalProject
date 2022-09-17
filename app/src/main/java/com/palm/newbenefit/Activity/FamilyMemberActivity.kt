package com.palm.newbenefit.Activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.error.AuthFailureError
import com.android.volley.request.StringRequest
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import com.palm.newbenefit.Adapter.FamilyAdapter
import com.palm.newbenefit.ApiConfig.AnalyticsApplication
import com.palm.newbenefit.ApiConfig.Constants
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener.ClickListener
import com.palm.newbenefit.Module.FamilyData
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

class FamilyMemberActivity : AppCompatActivity() {
    var con: Constants? = null
    var context: Context? = null
    var mobileNumber: String? = null
    var token: String? = null
    var user_id: String? = null
    var course_history_recycle: RecyclerView? = null

    var mSearcha: SearchView? = null
    var ll_main_data: LinearLayout? = null
    var allempnotdatafound: RelativeLayout? = null
    var ob: List<FamilyData>? = null
    var adapter: FamilyAdapter? = null
    var recyclerView: RecyclerView? = null
    var tv_data_not_found: TextView? = null
    var add: LinearLayout? = null
    var settingtoolbar: Toolbar? = null
    var emp_id: String? = null
    var info_text: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family_member)
        con = Constants()
//        AnalyticsApplication.getInstance().trackEvent(
//            "Family Member List Page",
//            "Family Member List Page Displaying",
//            "Family Member List Page Displaying"
//        )



        recyclerView = findViewById(R.id.ongoing_recycle)
        add = findViewById(R.id.add)
        info_text = findViewById(R.id.info_text)
        info_text!!.setOnClickListener(View.OnClickListener { finish() })


        con = Constants()
        context = applicationContext


        val prefs = context!!.getSharedPreferences(
            context!!.getPackageName(),
            Context.MODE_PRIVATE
        )

        token = prefs.getString("api_token", null)


        // Data();
        //setCertData();


        // Data();
        //setCertData();
        add!!.setOnClickListener(View.OnClickListener {
            val intent =
                Intent(this@FamilyMemberActivity, AddFamilyDataActivity::class.java)
            this@FamilyMemberActivity.startActivity(intent)
        })


        val numberOfColumnsa = 1
        recyclerView!!.setLayoutManager(GridLayoutManager(this, numberOfColumnsa))

        recyclerView!!.addOnItemTouchListener(
            RecyclerTouchListener(
                this,
                recyclerView,
                object : ClickListener {
                    override fun onClick(view: View, position: Int) {}
                    override fun onLongClick(view: View, position: Int) {}
                })
        )


        val managera =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView!!.setLayoutManager(managera)


    }


    override fun onResume() {
        super.onResume()
        if (isNetworkAvailable()) {
            setCertData()
        } else {
            AlertDialog.Builder(this@FamilyMemberActivity)
                .setTitle("Error?")
                .setMessage("Please Check Your Internet Connection")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, AddFamilyDataActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> {
            }
        }
        return false
    }


    fun setCertData() {
        ob = ArrayList()
        adapter = FamilyAdapter(this, ob)
        recyclerView!!.adapter = adapter
        val url = con!!.base_url + "/api/employee/get/employee-family"
//        val mRequestQueue =
//            Volley.newRequestQueue(this@FamilyMemberActivity, HurlStack(null, getSocketFactory()))
//        mRequestQueue.cache.clear()
        val mRequestQueue = Volley.newRequestQueue(
            this@FamilyMemberActivity,
            HurlStack(null, getSocketFactory())
        )
        mRequestQueue.cache.clear()
        val mStringRequest: StringRequest = object : StringRequest(
            Method.GET,
            url,
            Response.Listener { response ->
                try {
                    val js = JSONObject(response)
                    Log.d("response", response)
                    val jsonObj = js.getJSONArray("data")
                    adapter!!.notifyDataSetChanged()
                    for (i in 0 until jsonObj.length()) {
                        val explrObject = jsonObj.getJSONObject(i)
                        (ob as ArrayList<FamilyData>).add(
                            FamilyData(
                                explrObject.getString("id"),
                                explrObject.getString("first_name"),
                                explrObject.getString("last_name"),
                                "none",
                                "none",
                                "none",
                                explrObject.getString("relation_name"),
                                "none",
                                "none",
                                "none",
                                "none",
                                "none",
                                "none",
                                "none"
                            )
                        )
                    }
                } catch (e: Exception) {
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
        mRequestQueue.add(mStringRequest)
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun getSocketFactory(): SSLSocketFactory? {
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


