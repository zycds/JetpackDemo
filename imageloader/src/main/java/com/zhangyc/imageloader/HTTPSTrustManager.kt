package com.zhangyc.imageloader

import android.annotation.SuppressLint
import android.util.Log
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

class HTTPSTrustManager {

    companion object {
        private var trustManagers: Array<TrustManager>? = null
        private val mAcceptedIssuers = emptyArray<X509Certificate>()
        fun allowAllSSL() {
            Log.d(ImageLoader.tag, "allowAllSSL")
            if (trustManagers == null) trustManagers = arrayOf(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return mAcceptedIssuers
                }

            })
            try {
                val context = SSLContext.getInstance("TLS")
                context?.init(null, trustManagers, SecureRandom())
                HttpsURLConnection.setDefaultSSLSocketFactory(context?.socketFactory)
                HttpsURLConnection.setDefaultHostnameVerifier { hostname, session ->
                    Log.d(ImageLoader.tag, "hostname :  $hostname,  $session")
                    true
                }
            } catch (e: Exception) {
                Log.e(ImageLoader.tag, "TSL : ${e.printStackTrace()}")
            }
        }
    }

}