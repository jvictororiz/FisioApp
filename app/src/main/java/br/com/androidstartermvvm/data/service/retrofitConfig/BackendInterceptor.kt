package br.com.androidstartermvvm.data.service.retrofitConfig

import br.com.androidstartermvvm.data.entities.remote.response.BaseResponse
import br.com.androidstartermvvm.repository.UserRepository
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

class BackendInterceptor : Interceptor {
    private val userRepository: UserRepository = UserRepository()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        userRepository.getUser().token?.let {
            builder.addHeader(HEADER_AUTHORIZATION, it)
        }

        val response = chain.proceed(builder.build())
        val json = response.body()?.source()?.buffer()?.readString(Charsets.UTF_8)
        val result = Gson().fromJson(json, BaseResponse::class.java)
        if (!response.isSuccessful || !result.status.success) {
            try {
                when (result.status.code) {
                    401 -> {
                    }
                }
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
