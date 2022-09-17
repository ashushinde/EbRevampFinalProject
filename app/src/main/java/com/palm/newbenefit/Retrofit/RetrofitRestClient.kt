package com.palm.tatarewamp.retrofit

import android.content.Context
import android.util.Log
import com.palm.newbenefit.BuildConfig
import com.palm.newbenefit.R

import com.palm.tatarewamp.SslData.NullHostNameVerifier
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import javax.security.cert.CertificateException


object RetrofitRestClient {

    // const val baseUrl: String = "https://lifekaplan-eb.com"
    const val baseUrl: String = "http://eb.fynity.in/api/"



    fun generateService(context: Context): ApiInterface {

        val client = OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE

        }).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .client(generateSecureOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)

    }

    private fun generateSecureOkHttpClient(context: Context): OkHttpClient? {

        var cf: CertificateFactory? = null

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })
        cf = CertificateFactory.getInstance("X.509")
        val caInput = context.resources.openRawResource(R.raw.cert)
        val ca: Certificate
        try {
            ca = cf!!.generateCertificate(caInput)
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

        val sslContext = SSLContext.getInstance("TLSv1.2")
//        sslContext.init(null, tmf.trustManagers, null)
        sslContext.init(null, trustAllCerts, null)
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)


//         HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
//            SSLContext context = null;
//            context = SSLContext.getInstance("TLS");
//
//            context.init(null, tmf.getTrustManagers(), null);
//            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());


//            return context.socketFactory


        val httpClientBuilder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })

            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)

       // httpClientBuilder.hostnameVerifier { _, _ -> true }
        return httpClientBuilder.build()


        return null
    }



}

