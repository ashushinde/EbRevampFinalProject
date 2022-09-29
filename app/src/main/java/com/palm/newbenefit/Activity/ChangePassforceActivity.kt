package com.palm.newbenefit.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.error.AuthFailureError
import com.android.volley.request.SimpleMultiPartRequest
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import com.palm.newbenefit.Adapter.ErrorListAdapter
import com.palm.newbenefit.ApiConfig.Constants
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener
import com.palm.newbenefit.Module.SpinnerModal
import com.kmd.newbenefit.R
import com.palm.tatarewamp.SslData.NullHostNameVerifier
import org.json.JSONArray
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

class ChangePassforceActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null
    var user_id: String? = null
    var changePasswordBtn: TextView? = null
    var currentPasswordText: TextView? = null
    var confirmPasswordText: TextView? = null
    var currentPasswordEditText: EditText? = null
    var newPasswordEditText: EditText? = null
    var confirmPasswordEditText: EditText? = null
    var currentPasswordPrefs: String? = null
    var newPassword:kotlin.String? = null
    var confirmPassword:kotlin.String? = null
    var currentPassword:kotlin.String? = null
    var token:kotlin.String? = null
    var a:kotlin.String? = null
    var preferences: SharedPreferences? = null
    var errorCode = 0
    var con: Constants? = null
    var info_text: ImageView? = null
    var img_btn_show_hide_password: ImageView? = null
    var img_btn_show_hide_password_confrim_current:android.widget.ImageView? = null
    var img_btn_show_hide_password_confrim:android.widget.ImageView? = null
    var str_CheckTrueFalse: String? = null
    var str_CheckTrueFalse_confrim: String? = null
    var str_CheckTrueFalse_current: String? = null
    var status: String? = null

    var policy: TextView? = null
    var checkeda = false
    var checkedb = false
    var checkedc = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_passforce)
        con = Constants()

        changePasswordBtn = findViewById(R.id.changePasswordBtn)
        currentPasswordText = findViewById(R.id.currentPasswordText)
        confirmPasswordText = findViewById(R.id.confirmPasswordText)
         policy= findViewById(R.id.policy)
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText)

        val newPasswordEditText = findViewById(R.id.newPasswordEditText) as EditText
        val confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText) as EditText
        val currentPasswordEditText = findViewById(R.id.currentPasswordEditText) as EditText


        info_text = findViewById(R.id.info_text)
        preferences = getSharedPreferences(
            applicationContext.packageName,
            Context.MODE_PRIVATE
        )
        //currentPasswordPrefs = preferences.getString("userPassword", null);
        //currentPasswordPrefs = preferences.getString("userPassword", null);
        token = preferences!!.getString("api_token", null)



        info_text!!.setOnClickListener(View.OnClickListener { finish() })






        val img_btn_show_hide_password = findViewById(R.id.img_btn_show_hide_password) as ImageView

        val img_btn_show_hide_password_confrim = findViewById(R.id.img_btn_show_hide_password_confrim) as ImageView

        val img_btn_show_hide_password_confrim_current = findViewById(R.id.img_btn_show_hide_password_confrim_current) as ImageView

        newPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        img_btn_show_hide_password.setImageResource(R.drawable.ic_visibility_off_black_24dp)


        confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        img_btn_show_hide_password_confrim.setImageResource(R.drawable.ic_visibility_off_black_24dp)

        currentPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        img_btn_show_hide_password_confrim_current.setImageResource(R.drawable.ic_visibility_off_black_24dp)


        img_btn_show_hide_password.setOnClickListener {

            if (checkeda.equals(false)) {
                checkeda = true

                newPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                img_btn_show_hide_password.setImageResource(R.drawable.ic_eye)
            } else {
                checkeda = false

                newPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                img_btn_show_hide_password.setImageResource(R.drawable.ic_visibility_off_black_24dp)

            }
        }


        policy!!.setOnClickListener {

            val intent = Intent(
                applicationContext,
                PassowrdPolicyActivity::class.java
            )
            startActivity(intent)

        }



        img_btn_show_hide_password_confrim.setOnClickListener {

            if (checkedb.equals(false)) {
                checkedb = true

                confirmPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                img_btn_show_hide_password_confrim.setImageResource(R.drawable.ic_eye)
            } else {
                checkedb = false

                confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                img_btn_show_hide_password_confrim.setImageResource(R.drawable.ic_visibility_off_black_24dp)

            }
        }

        img_btn_show_hide_password_confrim_current.setOnClickListener {

            if (checkedc.equals(false)) {
                checkedc = true

                currentPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                img_btn_show_hide_password_confrim.setImageResource(R.drawable.ic_eye)
            } else {
                checkedc = false

                currentPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                img_btn_show_hide_password_confrim_current.setImageResource(R.drawable.ic_visibility_off_black_24dp)

            }
        }












        val confirmPasswordTextWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                newPassword = newPasswordEditText.getText().toString().trim { it <= ' ' }
                confirmPassword = confirmPasswordEditText.getText().toString().trim { it <= ' ' }
                errorCode = if (confirmPassword != newPassword) {
                    confirmPasswordText!!.setVisibility(View.VISIBLE)
                    confirmPasswordText!!.setText("Password Do Not Match")
                    1
                } else {
                    confirmPasswordText!!.setVisibility(View.GONE)
                    0
                }
            }
        }

        confirmPasswordEditText.addTextChangedListener(confirmPasswordTextWatcher)


        changePasswordBtn!!.setOnClickListener(View.OnClickListener { view: View? ->
            var count = 0
            if (currentPasswordEditText.getText().toString().isEmpty()) {
                currentPasswordEditText.setError("Required")
                ++count
            } else {
                currentPasswordEditText.setError(null)
            }
            if (newPasswordEditText.getText().toString().isEmpty()) {
                newPasswordEditText.setError("Required")
                ++count
            } else {
                newPasswordEditText.setError(null)
            }
            if (confirmPasswordEditText.getText().toString().isEmpty()) {
                confirmPasswordEditText.setError("Required")
                ++count
            } else {
                confirmPasswordEditText.setError(null)
            }
            if (count == 0) {
                if (isNetworkAvailable()) {
                    progressDialog = ProgressDialog.show(
                        this@ChangePassforceActivity, "",
                        "Saving. Please wait...", true
                    )
                    /*  val rq = Volley.newRequestQueue(
                        this@ChangePassforceActivity,
                        HurlStack(null)
                    )*/

                    val rq = Volley.newRequestQueue(
                        this@ChangePassforceActivity,
                        HurlStack(null, getSocketFactory())
                    )
                    val url = con!!.base_url + "/api/admin/reset/password"

                    val request: SimpleMultiPartRequest = object : SimpleMultiPartRequest(
                        Method.POST,
                        url,
                        Response.Listener { response ->
                            Log.d("change", response)
                            progressDialog!!.dismiss()
                            try {
                                val js = JSONObject(response)
                                val status = js.getString("status")
                                if (status.equals("true", ignoreCase = true)) {
                                    val message = js.getString("message")
                                    val alertDialog =
                                        androidx.appcompat.app.AlertDialog.Builder(this@ChangePassforceActivity)
                                    alertDialog.setTitle("Success")
                                        .setIcon(R.drawable.checkmark)
                                    alertDialog.setMessage("Password Changed Successfully. Please Re-login into the account.")
                                    alertDialog.setCancelable(false)
                                    alertDialog.setPositiveButton(
                                        R.string.OK
                                    ) { dialogInterface, i ->
                                        if (isNetworkAvailable()) {
                                            logout()
                                        } else {
                                            androidx.appcompat.app.AlertDialog.Builder(
                                                this@ChangePassforceActivity
                                            )
                                                .setTitle("Error")
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

                                    val `object` = js.getJSONObject("errors")
                                    val arraydata = `object`.getJSONArray("new_password")

                                    ErrorDailog(arraydata);


                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                androidx.appcompat.app.AlertDialog.Builder(
                                    this@ChangePassforceActivity
                                )
                                    .setTitle("Error")
                                    .setMessage("Old Password Not Match Please Try Again.")
                                    .setIcon(android.R.drawable.btn_dialog)
                                    .setNegativeButton(
                                        android.R.string.ok,
                                        null
                                    ).show()
                            }
                        },
                        Response.ErrorListener { error ->
                            progressDialog!!.dismiss()
                            Log.e(
                                "onErrorResponse",
                                error.toString()

                            )
                        }) {
                        @Throws(AuthFailureError::class)
                        override fun getHeaders(): Map<String, String> {
                            val headers: MutableMap<String, String> =
                                HashMap()
                            // Basic Authentication
                            //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);
                            headers["Authorization"] = "Bearer $token"
                            return headers
                        }
                    }

                    request.addStringParam(
                        "new_password",
                        newPasswordEditText.text.toString().trim { it <= ' ' }
                    )
                    request.addStringParam(
                        "old_password",
                        currentPasswordEditText.text.toString().trim { it <= ' ' }
                    )

                    request.addStringParam(
                        "reenter_password",
                        currentPasswordEditText.text.toString().trim { it <= ' ' }
                    )

                    rq.add(request)

                } else {
                    AlertDialog.Builder(this@ChangePassforceActivity)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show()
                }
            } else {
                Toast.makeText(
                    this@ChangePassforceActivity,
                    "Please Check the above fields.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun ErrorDailog(arraydata: JSONArray) {
        val myDialog: Dialog
        myDialog = Dialog(this@ChangePassforceActivity)
        myDialog.setContentView(R.layout.error_list_layout)
        con = Constants()
        val error_recycle: RecyclerView = myDialog.findViewById(R.id.error_recycle)
        val back = myDialog.findViewById<ImageView>(R.id.back)
        val ok = myDialog.findViewById<TextView>(R.id.ok)
        ok.setOnClickListener { myDialog.dismiss() }
        back.setOnClickListener { myDialog.dismiss() }
        var ob: MutableList<SpinnerModal?>? = null
        var adapter: ErrorListAdapter? = null
        ob = ArrayList<SpinnerModal?>()
        adapter = ErrorListAdapter(this@ChangePassforceActivity, ob)
        error_recycle.adapter = adapter
        val numberOfColumns = 1
        error_recycle.layoutManager =
            GridLayoutManager(this@ChangePassforceActivity, numberOfColumns)
        error_recycle.addOnItemTouchListener(
            RecyclerTouchListener(
                this@ChangePassforceActivity,
                error_recycle,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View?, position: Int) {}
                    override fun onLongClick(view: View?, position: Int) {}
                })
        )
        val manager =
            GridLayoutManager(this@ChangePassforceActivity, 1, GridLayoutManager.VERTICAL, false)
        error_recycle.layoutManager = manager
        try {

            for (i in 0 until arraydata.length()) {
                ob.add(SpinnerModal(arraydata[i].toString()))
            }
            adapter.notifyDataSetChanged()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        myDialog.setCancelable(false)
        myDialog.setCanceledOnTouchOutside(false)
        myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(myDialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        myDialog.window!!.attributes = layoutParams
        myDialog.show()
    }


    fun logout() {

        val prefs = getSharedPreferences(
            applicationContext.packageName,
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.clear().apply()
        editor.apply()
        val intent =
            Intent(this@ChangePassforceActivity, DemoActivty::class.java)
        startActivity(intent)




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




    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        moveTaskToBack(false)
    }
}


