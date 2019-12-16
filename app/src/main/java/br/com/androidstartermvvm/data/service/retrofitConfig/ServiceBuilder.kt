package br.com.androidstartermvvm.data.service.retrofitConfig

import br.com.androidstartermvvm.data.service.RespostaService
import okhttp3.Interceptor
import retrofit2.Converter
import retrofit2.Retrofit

open class ServiceBuilder {
    companion object {
        private val interceptors: List<Interceptor> = listOf<Interceptor>(BackendInterceptor())
        private val converterFactories: Array<Converter.Factory>? = null
        private val retrofit by lazy<Retrofit> { retrofitBuilder()
            .build() }
        private const val apiUrl: String = "fdsa"
        private fun retrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(apiUrl)
                .client(RetrofitClient.retrofitReference)
                .also { builder ->
                    interceptors.forEach {
                        RetrofitClient.retrofitReference.interceptors().add(it)
                    }
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
