package com.palm.newbenefit.Adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.error.AuthFailureError
import com.android.volley.request.StringRequest
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import com.palm.newbenefit.Activity.MapsActivity
import com.palm.newbenefit.ApiConfig.Constants
import com.palm.newbenefit.Module.MapAddress
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

class MapAdapter(
    context: Context?,
    train: List<MapAddress>
) : RecyclerView.Adapter<MapAdapter.ViewHolder>() {
    private val mTrain: List<MapAddress>
    private var context: Context? = null
    var con: Constants
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val trainingView =
            inflater.inflate(R.layout.hospital_list, parent, false)

        // Return a new holder instance
        return ViewHolder(context, trainingView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        // Get the data model based on position
        val train = mTrain[position]
        con = Constants()

        // Set item views based on your views and data model
        val add = viewHolder.address
        val mob = viewHolder.contactno
        val email = viewHolder.emailid
        val name = viewHolder.name



        if(train.aDDRESS2.equals("null")){
            add.text = train.aDDRESS1 + ","+train.cITY_NAME+","+train.sTATE_NAME+","+train.pIN_CODE
        }else{
            add.text = train.aDDRESS1 + ","+ train.aDDRESS2+ ","+train.cITY_NAME+","+train.sTATE_NAME+","+train.pIN_CODE
        }


        email.text = train.eMAIL
        name.text = train.hOSPITAL_NAME


        if(train.phoneNo.equals("null")){
            mob.text = "-"

        }else{
            mob.text = train.phoneNo

        }


        viewHolder.map.setOnClickListener { v ->
            val progressDialog =
                ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT)
            progressDialog.setMessage("Its loading....")
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog.show()
            object : Thread() {
                override fun run() {
                    try {
                        val train = mTrain[position]
                        val context = v.context


                        val intent = Intent(context,MapsActivity::class.java);
                        var userName = train.cITY_NAME
                        var password = train.sTATE_NAME
                        intent.putExtra("Username", train.aDDRESS1)
                        intent.putExtra("Password", password)
                        context.startActivity(intent);
                    } catch (e: Exception) {
                    }

// dismiss the progress dialog
                    progressDialog.dismiss()
                }
            }.start()
        }
        viewHolder.sms_address.setOnClickListener {
            val rq =
                Volley.newRequestQueue(context, HurlStack(null, socketFactory))
            rq.cache.clear();
            val url = con.base_url + "/api/admin/network/hospital/communicate"
            val smr: StringRequest =
                object : StringRequest(
                    Method.POST,
                    url,
                    Response.Listener { response ->
                        try {
                            val js = JSONObject(response)
                            val status = js.getBoolean("status")
                            val message = js.getString("message")
                            AlertDialog.Builder(context!!)
                                .setTitle("Success")
                                .setMessage(message)
                                .setIcon(R.drawable.checkmark)
                                .setNegativeButton(android.R.string.ok, null).show()
                        } catch (e: Exception) {
                            // progressDialog.dismiss();
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.e(
                            "onErrorResponse",
                            error.toString()
                        )
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers: MutableMap<String, String> =
                            HashMap()
                        val prefs = context!!.getSharedPreferences(
                            context!!.packageName,
                            Context.MODE_PRIVATE
                        )
                        val api_token = prefs.getString("api_token", null)
                        headers["Authorization"] = "Bearer $api_token"
                        return headers
                    }
                }


            val params =
                HashMap<String, String>()

            params["hospital_id"] = train.network_hospital_id.toString()
            params["type"] ="sms"
            smr.setParams(params)
            rq.add(smr)
        }
        viewHolder.email.setOnClickListener {
            val rq =
                Volley.newRequestQueue(context, HurlStack(null, socketFactory))
            rq.cache.clear();
            val url = con.base_url + "/api/admin/network/hospital/communicate"
            val smr: StringRequest =
                object : StringRequest(
                    Method.POST,
                    url,
                    Response.Listener { response ->
                        try {
                            val js = JSONObject(response)
                            val status = js.getBoolean("status")
                            val message = js.getString("message")
                            AlertDialog.Builder(context!!)
                                .setTitle("Success")
                                .setMessage(message)
                                .setIcon(R.drawable.checkmark)
                                .setNegativeButton(android.R.string.ok, null).show()
                        } catch (e: Exception) {
                            // progressDialog.dismiss();
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.e(
                            "onErrorResponse",
                            error.toString()
                        )
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers: MutableMap<String, String> =
                            HashMap()
                        val prefs = context!!.getSharedPreferences(
                            context!!.packageName,
                            Context.MODE_PRIVATE
                        )
                        val api_token = prefs.getString("api_token", null)
                        headers["Authorization"] = "Bearer $api_token"
                        return headers
                    }
                }


            val params =
                HashMap<String, String>()

            params["hospital_id"] = train.network_hospital_id.toString()
            params["type"] ="email"
            smr.setParams(params)
            rq.add(smr)
        }
    }

    fun SendData() {}

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mTrain.size
    }

    inner class ViewHolder(
        context: Context?,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        var address: TextView
        var emailid: TextView
        var contactno: TextView
        var name: TextView
        var map: ImageView
        var sms_address: ImageView
        var email: ImageView

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            name = itemView.findViewById<View>(R.id.name) as TextView
            emailid = itemView.findViewById<View>(R.id.emailid) as TextView
            contactno = itemView.findViewById<View>(R.id.contactno) as TextView
            address = itemView.findViewById<View>(R.id.address) as TextView
            sms_address =
                itemView.findViewById<View>(R.id.sms_address) as ImageView
            email = itemView.findViewById<View>(R.id.email) as ImageView
            map = itemView.findViewById<View>(R.id.map) as ImageView
        }
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
                val caInput = context!!.resources.openRawResource(R.raw.cert)
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

    // Pass in the contact array into the constructor
    init {
        this.context = context
        mTrain = train
        con = Constants()
    }
}