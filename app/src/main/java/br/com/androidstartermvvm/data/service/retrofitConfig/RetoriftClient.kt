package br.com.androidstartermvvm.data.service.retrofitConfig

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import java.util.concurrent.TimeUnit

open class RetrofitClient {
    companion object {
    private val interceptors: List<Interceptor> = listOf<Interceptor>(BackendInterceptor())


    val retrofitReference = OkHttpClient.Builder().apply {
        hostnameVerifier { _, _ -> true }
        addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        interceptors.forEach {
            addInterceptor(it)
        }
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    }.build()


        private const val CONNECT_TIMEOUT: Long = 30L
        private const val READ_TIMEOUT: Long = 30L
    }
}