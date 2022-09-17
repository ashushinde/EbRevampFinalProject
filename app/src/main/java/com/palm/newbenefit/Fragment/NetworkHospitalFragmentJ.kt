package com.palm.newbenefit.Fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.error.AuthFailureError
import com.android.volley.request.SimpleMultiPartRequest
import com.android.volley.request.StringRequest
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import com.palm.newbenefit.Activity.MainActivity
import com.palm.newbenefit.Adapter.MapAdapter
import com.palm.newbenefit.ApiConfig.Constants
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener
import com.palm.newbenefit.ApiConfig.RecyclerTouchListener.ClickListener
import com.palm.newbenefit.Module.MapAddress
import com.palm.newbenefit.Module.SpinnerModal
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
class NetworkHospitalFragmentJ : Fragment() {
    var select_type: Spinner? = null
    var state: Spinner? = null
    var city: Spinner? = null
    var con: Constants? = null

    var mobileNumber: String? = null
    var token: String? = null
    var user_id: String? = null
    var course_history_recycle: RecyclerView? = null
    var bank_city_value = ""
    var bank_branch_value = ""
    var bank_name_value = ""
    var manufacturerSpinnerModal: ArrayList<SpinnerModal>? = null
    var modelSpinnerModal: ArrayList<SpinnerModal>? = null
    var versionSpinnerModal: ArrayList<SpinnerModal>? = null
    var rtoSpinnerModal: ArrayList<SpinnerModal>? = null
    var additionalCoverSpinnerModal: ArrayList<SpinnerModal>? = null
    var noClaimBonusSpinnerModal: ArrayList<SpinnerModal>? = null
    var previousInsurerSpinnerModal: ArrayList<SpinnerModal>? = null
    var policy_no: String? = null
    var rtoList: ArrayList<String>? = null
    var policy_sub_type_name: String? = null
    var bank_citySpin: Spinner? = null
    var bank_nameSpin: Spinner? = null
    var bank_city: ArrayList<String>? = null
    var bank_cityList: ArrayList<SpinnerModal>? = null
    var bank_cityAdapter: ArrayAdapter<SpinnerModal>? = null
    var bank_nameList: ArrayList<SpinnerModal>? = null
    var bank_nameAdapter: ArrayAdapter<SpinnerModal>? = null
    var bank_name: ArrayList<String>? = null
    var bank_branchSpin: Spinner? = null
    var bank_branch: ArrayList<String>? = null
    var bank_branchList: ArrayList<SpinnerModal>? = null
    var bank_branchAdapter: ArrayAdapter<SpinnerModal>? = null
    var ob: MutableList<MapAddress>? = null
    var adapter: MapAdapter? = null
    var recyclerView: RecyclerView? = null
    var no_image: ImageView? = null


    var ManufacturerAdapter: ArrayAdapter<SpinnerModal>? = null
    var ModelAdapter: ArrayAdapter<SpinnerModal>? = null
    var VersionAdapter: ArrayAdapter<SpinnerModal>? = null
    var RtoAdapter: ArrayAdapter<SpinnerModal>? = null
    var AdditionalCoverAdapter: ArrayAdapter<SpinnerModal>? = null
    var noClaimBonusAdapter: ArrayAdapter<SpinnerModal>? = null
    var previousInsurerAdapter: ArrayAdapter<SpinnerModal>? = null
    var policy: TextView? = null
    var policyd: TextView? = null
    private val queue: RequestQueue? = null
    var rtoNumberTextView: AutoCompleteTextView? = null
    var rtoNumberTextView_search: AutoCompleteTextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =
            inflater.inflate(R.layout.fragment_network_hospital, container, false)
        setHasOptionsMenu(true)
        (activity as MainActivity?)!!.setTitle("Network Hospital")
        con = Constants()

        recyclerView = v.findViewById(R.id.network_cycle)
        no_image= v.findViewById(R.id.no_image)
        rtoNumberTextView = v.findViewById(R.id.rtoNumberBike)
        rtoNumberTextView_search = v.findViewById(R.id.rtoNumberBike_search)
        val prefs = context!!.getSharedPreferences(
            context!!.packageName,
            Context.MODE_PRIVATE
        )
        token = prefs.getString("api_token", null)
        policy = v.findViewById(R.id.policy)
        policyd = v.findViewById(R.id.policyd)
        bank_citySpin = v.findViewById(R.id.bank_citySpin)
        bank_branchSpin = v.findViewById(R.id.bank_branchSpin)
        if (isNetworkAvailable) {
            bankName
            //getRtoList();
            // Data();
        } else {
            AlertDialog.Builder(activity!!)
                .setTitle("Error?")
                .setMessage("Please Check Your Internet Connection")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show()
        }
        val fieldValidatorTextWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (filterLongEnough()) {
                    setCertDataByName()
                }
            }

            private fun filterLongEnough(): Boolean {
                return rtoNumberTextView!!.getText().toString().trim { it <= ' ' }.length > 0
            }
        }
        if (isNetworkAvailable) {
            rtoNumberTextView!!.addTextChangedListener(fieldValidatorTextWatcher)
        } else {
            AlertDialog.Builder(activity!!)
                .setTitle("Error?")
                .setMessage("Please Check Your Internet Connection")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show()
        }
        bankCity
        val fieldValidatorTextWatchera: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (filterLongEnough()) {
                    setCertDataByName_search()
                }
            }

            private fun filterLongEnough(): Boolean {
                return rtoNumberTextView_search!!.getText().toString().trim { it <= ' ' }.length > 5
            }
        }
        if (isNetworkAvailable) {
            rtoNumberTextView_search!!.addTextChangedListener(fieldValidatorTextWatchera)
            bankCity
        } else {
            AlertDialog.Builder(activity!!)
                .setTitle("Error?")
                .setMessage("Please Check Your Internet Connection")
                .setIcon(android.R.drawable.btn_dialog)
                .setNegativeButton(android.R.string.ok, null).show()
        }
        bank_citySpin!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                val bank_city_modal = adapterView.selectedItem as SpinnerModal
                if (isNetworkAvailable) {
                    getBankbranch(bank_city_modal.selKey)
                } else {
                    AlertDialog.Builder(activity!!)
                        .setTitle("Error?")
                        .setMessage("Please Check Your Internet Connection")
                        .setIcon(android.R.drawable.btn_dialog)
                        .setNegativeButton(android.R.string.ok, null).show()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        bank_branchSpin!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                val bank_branch_modal = adapterView.selectedItem as SpinnerModal
                if (bank_branch_modal.selValue != "") {
                    if (isNetworkAvailable) {
                        ob = ArrayList()
                        adapter =
                            MapAdapter(activity, ob!!)
                        recyclerView!!.setAdapter(adapter)
                        setCertData()
                    } else {
                        AlertDialog.Builder(activity!!)
                            .setTitle("Error?")
                            .setMessage("Please Check Your Internet Connection")
                            .setIcon(android.R.drawable.btn_dialog)
                            .setNegativeButton(android.R.string.ok, null).show()
                    }


                    //adress();
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
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
            GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        recyclerView!!.setLayoutManager(manager)
        return v
    }


    fun setCertData() {
        val rq =
            Volley.newRequestQueue(activity, HurlStack(null, socketFactory))
        val url = con!!.base_url + "/api/admin/get/networkhospital/details"
        rq.cache.clear()
        val smr: SimpleMultiPartRequest =
            object : SimpleMultiPartRequest(
                Method.POST, url,
                Response.Listener { response ->
                    try {
                        val js = JSONObject(response)
                        Log.d("my hospital", response)


                        val jsonObja = js.getString("status")

                        if(jsonObja.equals("false")){
                            recyclerView!!.visibility = View.GONE
                            no_image!!.visibility = View.VISIBLE

                        }else {
                            val jsonObj = js.getJSONArray("data")

                            recyclerView!!.visibility = View.VISIBLE
                            no_image!!.visibility = View.GONE





                            for (i in 0 until jsonObj.length()) {
                                val explrObject = jsonObj.getJSONObject(i)
                                ob!!.add(
                                    MapAddress(
                                        explrObject.getString("id"),
                                        explrObject.getString("policy_id"),
                                        "",
                                        "",
                                        "",
                                        explrObject.getString("hospital_name"),
                                        explrObject.getString("address1"),
                                        explrObject.getString("address2"),
                                        explrObject.getString("city_name"),
                                        explrObject.getString("state_name"),
                                        explrObject.getString("phone_no"),
                                        explrObject.getString("pin_code"),
                                        "",
                                        "",
                                        explrObject.getString("email"),
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""
                                    )
                                )
                            }
                        }
                        adapter!!.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
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
        val bank_city_modal = bank_branchSpin!!.selectedItem as SpinnerModal
        smr.addStringParam("city_name", bank_city_modal.selValue)
        smr.addStringParam("policy_id", policyd!!.text.toString())
        Log.d("city_names", bank_city_modal.selValue)
        Log.d("policy_id", policyd!!.text.toString())
        rq.add(smr)
    }

    // policy_no=jsonObj.getString("policy_no");
    private val bankName: Unit
        private get() {
            val url = con!!.base_url + "/api/employee/policies"
            val mRequestQueue =
                Volley.newRequestQueue(activity, HurlStack(null, socketFactory))
            mRequestQueue.cache.clear()
            val mStringRequest: StringRequest = object : StringRequest(
                Method.GET,
                url,
                Response.Listener { response ->
                    try {
                        val js = JSONObject(response)
                        Log.d("mypolicy", response)

                        val jsonObj = js.getJSONArray("data")
                        for (i in 0 until jsonObj.length()) {
                            val explrObject = jsonObj.getJSONObject(i)
                            if (explrObject.getString("name")
                                    .equals("Group Mediclaim", ignoreCase = true)
                            ) {
                                policyd!!.text = explrObject.getInt("id").toString()
                                policy!!.text = explrObject.getString("name")
                            }

                            // policy_no=jsonObj.getString("policy_no");
                        }
                    } catch (e: Exception) {
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

    val bankCity: Unit
        get() {
            val url = con!!.base_url + "/api/admin/get/networkhospital/state"
            val mRequestQueue =
                Volley.newRequestQueue(activity, HurlStack(null, socketFactory))
            mRequestQueue.cache.clear()
            val smr: SimpleMultiPartRequest =
                object : SimpleMultiPartRequest(
                    Method.POST, url,
                    Response.Listener { response: String? ->
                        try {
                        val js = JSONObject(response)
                        Log.d("mystate", response.toString())
                        val jsonArr = js.getJSONArray("data")
                        bank_city = ArrayList()
                        bank_cityList = ArrayList()
                        bank_cityList!!.add(SpinnerModal("", "Select State"))
                        bank_city!!.add("")
                        for (i in 0 until jsonArr.length()) {
                            val jsonObj = jsonArr.getJSONObject(i)
                            bank_cityList!!.add(
                                SpinnerModal(
                                    jsonObj.getString("state_name"),
                                    jsonObj.getString("state_name")
                                )
                            )
                            bank_city!!.add(jsonObj.getString("state_name"))
                        }
                        bank_cityAdapter = ArrayAdapter(
                            context!!,
                            R.layout.spinner_item,
                            bank_cityList!!
                        )
                        bank_cityAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        bank_citySpin!!.adapter = bank_cityAdapter
                    } catch (e: Exception) {
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

            smr.addStringParam("policy_id",policyd!!.text.toString())
            mRequestQueue.add(smr)
        }

    fun getBankbranch(bank_city: String?) {
        val bank_name_modal = bank_citySpin!!.selectedItem as SpinnerModal
        val rq =
            Volley.newRequestQueue(activity, HurlStack(null, socketFactory))
        val url = con!!.base_url + "/api/admin/get/networkhospital/city"
        rq.cache.clear()
        val smr: SimpleMultiPartRequest =
            object : SimpleMultiPartRequest(
                Method.POST, url,
                Response.Listener { response: String? ->
                    try {
                        Log.d("city", response.toString())
                        val js = JSONObject(response)
                        Log.d("response", response.toString())
                        val jsonArr = js.getJSONArray("data")
                        bank_branch = ArrayList()
                        bank_branchList = ArrayList()
                        bank_branchList!!.add(SpinnerModal("", "Select City"))
                        bank_branch!!.add("")
                        for (i in 0 until jsonArr.length()) {
                            val jsonObj = jsonArr.getJSONObject(i)
                            bank_branchList!!.add(
                                SpinnerModal(
                                    jsonObj.getString("CITY_NAME"),
                                    jsonObj.getString("CITY_NAME")
                                )
                            )
                            bank_branch!!.add(jsonObj.getString("CITY_NAME"))
                        }
                        bank_branchAdapter = ArrayAdapter(
                            context!!,
                            R.layout.spinner_item,
                            bank_branchList!!
                        )
                        bank_branchAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        bank_branchSpin!!.adapter = bank_branchAdapter
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
        smr.addStringParam("state_name", bank_name_modal.selKey)
        smr.addStringParam("policy_id",policyd!!.text.toString())


        /* smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/rq.add(smr)
    }

    fun setCertDataByName() {
        val rq =
            Volley.newRequestQueue(activity, HurlStack(null, socketFactory))
        val url = con!!.base_url + "/api/admin/get/networkhospital/name"
        rq.cache.clear()
        val smr: SimpleMultiPartRequest =
            object : SimpleMultiPartRequest(
                Method.POST, url,
                Response.Listener { response ->
                    ob = ArrayList()
                    adapter = MapAdapter(activity, ob!!)
                    recyclerView!!.adapter = adapter
                    try {
                        Log.d("response_data", response)
                        val imm =
                            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                        val js = JSONObject(response)
                        Log.d("response", response)

                        val jsonObja = js.getString("status")

                        if(jsonObja.equals("false")){
                            recyclerView!!.visibility = View.GONE
                            no_image!!.visibility = View.VISIBLE

                        }else {
                            val jsonObj = js.getJSONArray("data")

                            recyclerView!!.visibility = View.VISIBLE
                            no_image!!.visibility = View.GONE

                            for (i in 0 until jsonObj.length()) {
                                val explrObject = jsonObj.getJSONObject(i)
                                ob!!.add(
                                    MapAddress(
                                        explrObject.getString("id"),
                                        explrObject.getString("policy_id"),
                                        "",
                                        "",
                                        "",
                                        explrObject.getString("hospital_name"),
                                        explrObject.getString("address1"),
                                        explrObject.getString("address2"),
                                        explrObject.getString("city_name"),
                                        explrObject.getString("state_name"),
                                        explrObject.getString("phone_no"),
                                        explrObject.getString("pin_code"),
                                        "",
                                        "",
                                        explrObject.getString("email"),
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""
                                    )
                                )
                            }
                        }
                        adapter!!.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
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
        smr.addStringParam("hospital_name", rtoNumberTextView!!.text.toString())
        smr.addStringParam("policy_id", policyd!!.text.toString())

        /*smr.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(smr);*/rq.add(smr)
    }

    fun setCertDataByName_search() {
        val rq =
            Volley.newRequestQueue(activity, HurlStack(null, socketFactory))
        val url = con!!.base_url + "/api/admin/get/statcity/pincode"
        rq.cache.clear()
        val smr: SimpleMultiPartRequest =
            object : SimpleMultiPartRequest(
                Method.POST, url,
                Response.Listener { response ->
                    ob = ArrayList()
                    adapter = MapAdapter(activity, ob!!)
                    recyclerView!!.adapter = adapter
                    try {
                        Log.d("response_data", response)
                        val imm =
                            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                        val js = JSONObject(response)
                        Log.d("response", response)


                        val jsonObja = js.getString("status")

                        if(jsonObja.equals("false")){
                            recyclerView!!.visibility = View.GONE
                            no_image!!.visibility = View.VISIBLE

                        }else {
                            val jsonObj = js.getJSONArray("data")

                            recyclerView!!.visibility = View.VISIBLE
                            no_image!!.visibility = View.GONE



                            for (i in 0 until jsonObj.length()) {
                                val explrObject = jsonObj.getJSONObject(i)
                                ob!!.add(
                                    MapAddress(
                                        explrObject.getString("id"),
                                        explrObject.getString("policy_id"),
                                        "",
                                        "",
                                        "",
                                        explrObject.getString("hospital_name"),
                                        explrObject.getString("address1"),
                                        explrObject.getString("address2"),
                                        explrObject.getString("city_name"),
                                        explrObject.getString("state_name"),
                                        explrObject.getString("phone_no"),
                                        explrObject.getString("pin_code"),
                                        "",
                                        "",
                                        explrObject.getString("email"),
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""
                                    )
                                )
                            }
                        }
                        adapter!!.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
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
        smr.addStringParam("policy_id", policyd!!.text.toString())
        smr.addStringParam("pin_code", rtoNumberTextView_search!!.text.toString())
        rq.add(smr)
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