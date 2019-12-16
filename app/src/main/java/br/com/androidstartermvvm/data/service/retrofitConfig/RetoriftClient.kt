package br.com.androidstartermvvm.data.service.retrofitConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

open class RetrofitClient : OkHttpClient() {
    companion object {
        private const val CONNECT_TIMEOUT: Long = 30L
        private const val READ_TIMEOUT: Long = 30L


        val retrofitReference: OkHttpClient = Builder().apply {
            hostnameVerifier { _, _ -> true }
            addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)

            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        }.build()

    }
}