package br.com.fisioapp.data.service.retrofitConfig

import br.com.fisioapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class ServiceBuilder {
    companion object {
        private const val API_URL: String = BuildConfig.SERVER_URL
        private const val CONNECT_TIMEOUT: Long = 15L
        private const val READ_TIMEOUT: Long = 15L

        private val converterFactories: Array<Converter.Factory>? = null
        private val retrofit by lazy<Retrofit> {
            retrofitBuilder().build()
        }

        private val retrofitClient: OkHttpClient by lazy {
            OkHttpClient.Builder().apply {
                hostnameVerifier { _, _ -> true }
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })

                listOf<Interceptor>(BackendInterceptor()).forEach {
                    addInterceptor(it)
                }
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            }.build()
        }

        private fun retrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .client(retrofitClient)
                .also { builder ->
                    converterFactories?.forEach {
                        builder.addConverterFactory(it)
                    }
                }
        }

        fun <T> create(java: Class<T>): T {
            return retrofit.create(java)
        }
    }
}
