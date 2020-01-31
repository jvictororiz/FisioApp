package br.com.fisioapp.data.service.retrofitConfig

import br.com.fisioapp.repository.UserRepository
import okhttp3.Interceptor
import okhttp3.Response

class BackendInterceptor : Interceptor {
    private val userRepository: UserRepository by lazy {  UserRepository()}

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        userRepository.persistenceFindUser()?.token?.let {
            request.addHeader(HEADER_AUTHORIZATION, it)
        }

        val response = chain.proceed(request.build())
        val json = response.peekBody(Long.MAX_VALUE).string()

//        if (!response.isSuccessful || !result.status.success) {
//            try {
//                when (result.status.code) {
//                    401 -> {
//                        val intent = Intent(BaseActivity.ACTION_ERROR_UNAUTHORIZED)
//                        LocalBroadcastManager.getInstance(SuperApplication.context).sendBroadcast(intent)
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
        return response
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
    }

}
