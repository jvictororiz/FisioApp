package br.com.androidstartermvvm.data.service.retrofitConfig

import br.com.androidstartermvvm.data.entities.remote.response.ErroHttp
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class BackendInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader(
            HEADER_AUTHORIZATION, ""
        )

        val response = chain.proceed(builder.build())

        if (!response.isSuccessful) {
            val json = response.body()?.source()?.buffer()?.readString(Charsets.UTF_8)
            try {
                val error = Gson().fromJson(json, ErroHttp::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return response
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
    }

}
