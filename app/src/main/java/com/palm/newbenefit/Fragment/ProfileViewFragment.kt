package com.palm.newbenefit.Fragment


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.error.AuthFailureError
import com.android.volley.request.StringRequest
import com.android.volley.toolbox.Volley
import com.palm.newbenefit.Activity.*
import com.palm.newbenefit.ApiConfig.Constants
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
class ProfileViewFragment : Fragment() {
    var persnol_detail: LinearLayout? = null
    var weather_report: LinearLayout? = null

        var bank_detail:LinearLayout? = null
    var family_detail:LinearLayout? = null
    var change_password:LinearLayout? = null
    var logoutt: ImageView? = null
    var con: Constants? = null
    //var context: Context? = null
    var user_id: String? = null

    var emp_id: String? = null
    var current_access_right: String? = null
    var full_name: String? = null
    var email: String? = null
    var emp_last_name: String? = null
    var desc_id: String? = null


    var user_profile_name: TextView? = null
    var userImage: ImageView? = null
    var email_id: TextView? = null
    var mobile: TextView? = null
    var last_login: TextView? = null

    var charact: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        val v =inflater.inflate(R.layout.profile_demo, container, false)
        (activity as MainActivity?)?.setTitle("")
        con = Constants()
        //context = activity
        val prefs = context!!.getSharedPreferences(
            context!!.packageName,
            Context.MODE_PRIVATE
        )

        emp_id = prefs.getString("api_token", null)
//        current_access_right = prefs.getString("current_access_right", null)
//        email = prefs.getString("email_id", null)
//        full_name = prefs.getString("full_name", null)
//        emp_last_name = prefs.getString("emp_last_name", null)
//        desc_id = prefs.getString("mobile", null)


        user_profile_name = v.findViewById(R.id.email)
        email_id = v.findViewById(R.id.fullName)
        mobile = v.findViewById(R.id.mobile)
        charact = v.findViewById(R.id.charact)
        last_login = v.findViewById(R.id.last_login)
        userImage = v.findViewById(R.id.userImage)
//        user_profile_name!!.text = full_name
//        email_id!!.text = email
//        mobile!!.text = desc_id
//
//
//        val firstCharacter = full_name!![0]
//        charact!!.text = firstCharacter.toString()


        weather_report = v.findViewById(R.id.weather_report)
        persnol_detail = v.findViewById(R.id.personal_detail)
        bank_detail = v.findViewById(R.id.bank_detail)
        family_detail = v.findViewById(R.id.family_detail)
        change_password = v.findViewById(R.id.changePassword)

      setProfileDet();
//        val details = """
//            VERSION.RELEASE : ${Build.VERSION.RELEASE}
//            VERSION.INCREMENTAL : ${Build.VERSION.INCREMENTAL}
//            VERSION.SDK.NUMBER : ${Build.VERSION.SDK_INT}
//            BOARD : ${Build.BOARD}
//            BOOTLOADER : ${Build.BOOTLOADER}
//            BRAND : ${Build.BRAND}
//            CPU_ABI : ${Build.CPU_ABI}
//            CPU_ABI2 : ${Build.CPU_ABI2}
//            DISPLAY : ${Build.DISPLAY}
//            FINGERPRINT : ${Build.FINGERPRINT}
//            HARDWARE : ${Build.HARDWARE}
//            HOST : ${Build.HOST}
//            ID : ${Build.ID}
//            MANUFACTURER : ${Build.MANUFACTURER}
//            MODEL : ${Build.MODEL}
//            PRODUCT : ${Build.PRODUCT}
//            SERIAL : ${Build.SERIAL}
//            TAGS : ${Build.TAGS}
//            TIME : ${Build.TIME}
//            TYPE : ${Build.TYPE}
//            UNKNOWN : ${Build.UNKNOWN}
//            USER : ${Build.USER}
//            """.trimIndent()
//
//
//
//        Log.d("all_data", details)


        persnol_detail!!.setOnClickListener {
            val intent =
                Intent(activity, MyPersonalActivity::class.java) //jump to next activity
            startActivity(intent)
        }

        bank_detail!!.setOnClickListener(View.OnClickListener {
            val intent =
                Intent(activity, BankDetailExample::class.java) //jump to next activity
            startActivity(intent)
        })

        family_detail!!.setOnClickListener(View.OnClickListener {
            val intent =
                Intent(activity, FamilyMemberActivity::class.java) //jump to next activity
            startActivity(intent)
        })

        change_password!!.setOnClickListener { view: View? ->
            val intent =
                Intent(activity, ChangePassforceActivity::class.java) //jump to next activity
            startActivity(intent)
        }



        return v
    }

    fun setProfileDet() {
        val url = con!!.base_url + "/api/admin/user"
//        val mRequestQueue =
//            Volley.newRequestQueue(activity!!, HurlStack(null, getSocketFactory()))
//        mRequestQueue.cache.clear()


        val mRequestQueue =
                Volley.newRequestQueue(activity!!)
        mRequestQueue.cache.clear()
        val mStringRequest: StringRequest = object : StringRequest(
            Method.GET,
            url,
            Response.Listener { response ->
                try {
                    val js = JSONObject(response)
                    Log.d("mydata", response)
                    val jsonObj = js.getJSONArray("data")
                    for (i in 0 until jsonObj.length()) {
                        val explrObject = jsonObj.getJSONObject(0)
                        val first_name_input = explrObject.getString("name")
                        val email_input = explrObject.getString("email")
                        val gendera = explrObject.getString("gender")
                        Log.d("gendera", gendera)
                        if (first_name_input !== "null" && !first_name_input.isEmpty()) {
                            user_profile_name!!.setText(first_name_input)
                        }
                        if (email_input !== "null" && !email_input.isEmpty()) {
                            email_id!!.setText(email_input)
                        }
                        if (gendera.equals("Female", ignoreCase = true)) {
                            userImage!!.setBackgroundResource(R.drawable.user_female)
                        } else {
                            userImage!!.setBackgroundResource(R.drawable.user_male)
                        }
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
                headers["Authorization"] = "Bearer $emp_id"
                return headers
            }
        }
        mRequestQueue.add(mStringRequest)

    }






    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
