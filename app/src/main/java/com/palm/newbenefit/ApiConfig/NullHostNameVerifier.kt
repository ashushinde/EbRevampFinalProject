package com.palm.tatarewamp.SslData

import com.palm.newbenefit.ApiConfig.Constants
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

    open class NullHostNameVerifier : HostnameVerifier {

        val con = Constants()

        override fun verify(hostname: String?, session: SSLSession?): Boolean {


            return if (hostname == con.domain) {

                true
            } else {

                false
            }
        }


    }








