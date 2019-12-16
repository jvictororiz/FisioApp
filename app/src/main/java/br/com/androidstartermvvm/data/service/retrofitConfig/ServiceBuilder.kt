package br.com.androidstartermvvm.data.service.retrofitConfig

import br.com.androidstartermvvm.BuildConfig
import okhttp3.Interceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ServiceBuilder {
    companion object {
        private val converterFactories: Array<Converter.Factory>? = null
        private val retrofit by lazy<Retrofit> {
            retrofitBuilder()
            .build() }
        private const val apiUrl: String = BuildConfig.SERVER_URL
        private fun retrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiUrl)
                .client(RetrofitClient.retrofitReference)
                .also { builder ->
                    converterFactories?.forEach {
                        builder.addConverterFactory(it)
                    }
                }
        }

        fun<T> create(java: Class<T>): T {
            return retrofit.create(java)
        }
    }
}
