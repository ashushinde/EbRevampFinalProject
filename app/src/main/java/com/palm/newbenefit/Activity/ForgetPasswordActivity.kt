package com.palm.newbenefit.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.error.AuthFailureError
import com.android.volley.request.SimpleMultiPartRequest
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import com.palm.newbenefit.ApiConfig.Constants
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

class ForgetPasswordActivity : AppCompatActivity() {
    var ff: TextView? = null
    var btn_login: TextView? = null
    var mobNo: EditText? = null
    var con: Constants? = null
    var token: String? = null
    var progressDialog: ProgressDialog? = null
    var prefs: SharedPreferences? = null
    var user_id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        con = Constants()
        ff = findViewById(R.id.ff)
        prefs = getSharedPreferences(
            applicationContext.packageName,
            Context.MODE_PRIVATE
        )

        token = prefs!!.getString("api_token", null)
        btn_login = findViewById(R.id.btn_login)
        mobNo = findViewById(R.id.mobNo)

        ff!!.setOnClickListener(View.OnClickListener {
            val intent =
                Intent(applicationContext, DemoActivty::class.java) //jump to next activity
            startActivity(intent)
            finish()
        })

        ff!!.setOnClickListener {
            val intentRegister = Intent(applicationContext, DemoActivty::class.java)
            startActivity(intentRegister)
        }


        btn_login!!.setOnClickListener(View.OnClickListener {
            if (isNetworkAvailable()) {
                SendData()
            } else {
                AlertDialog.Builder(this@ForgetPasswordActivity)
                    .setTitle("Error?")
                    .setMessage("Please Check Your Internet Connection")
                    .setIcon(android.R.drawable.btn_dialog)
                    .setNegativeButton(android.R.string.ok, null).show()
            }
        })


    }

    fun SendData() {
        var count = 0
        if (mobNo!!.text.toString().trim { it <= ' ' }.length == 0) {
            ++count
            mobNo!!.error = "Enter Email Id"
        } else {
            mobNo!!.error = null
        }
        if (!con!!.isValidEmail(mobNo!!.text.toString().toLowerCase().trim { it <= ' ' })) {
            mobNo!!.error = "Invalid Email"
            ++count
        } else {
            mobNo!!.error = null
        }
        if (count == 0) {
            progressDialog = ProgressDialog.show(
                this@ForgetPasswordActivity, "",
                "Saving. Please wait...", true
            )
            val rq =
                Volley.newRequestQueue(this@ForgetPasswordActivity, HurlStack(null, getSocketFactory()))
            val url = con!!.base_url + "/api/admin/request/forgot/password"
            val smr: SimpleMultiPartRequest =
                object : SimpleMultiPartRequest(
                    Method.POST, url,
                    Response.Listener { response ->
                        try {
                            Log.d("response_forget", response)
                            progressDialog!!.dismiss()
                            val js = JSONObject(response)
                            val status = js.getString("status")
                            if (status.equals("true")) {
                                val message = js.getString("message")
                                val alertDialog =
                                    androidx.appcompat.app.AlertDialog.Builder(this@ForgetPasswordActivity)
                                alertDialog.setTitle("Success")
                                alertDialog.setMessage(message)
                                alertDialog.setCancelable(false)
                                alertDialog.setPositiveButton(
                                    R.string.OK
                                ) { dialogInterface, i ->
                                    if (isNetworkAvailable()) {
                                       logout()
                                    } else {
                                        androidx.appcompat.app.AlertDialog.Builder(
                                            this@ForgetPasswordActivity
                                        )
                                            .setTitle("Error?")
                                            .setMessage("Please Check Your Internet Connection")
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setNegativeButton(
                                                android.R.string.ok,
                                                null
                                            ).show()
                                    }
                                }
                                alertDialog.show()
                            } else {


                                androidx.appcompat.app.AlertDialog.Builder(
                                    this@ForgetPasswordActivity
                                )
                                    .setTitle("Error?")
                                    .setMessage("This Email Address Does Not Exist!")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(
                                        android.R.string.ok,
                                        null
                                    ).show()
                            }
                        } catch (e: Exception) {
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
                        headers["Authorization"] = "Bearer $token"
                        return headers
                    }
                }
            smr.addStringParam("email", mobNo!!.text.toString())
            rq.add(smr)
        }
    }
    fun logout() {


        val intent =
            Intent(this@ForgetPasswordActivity, DemoActivty::class.java)
        startActivity(intent)
        finish()




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

