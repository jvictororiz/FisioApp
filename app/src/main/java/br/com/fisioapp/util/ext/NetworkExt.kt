package br.com.bb.oewallet.extension

import br.com.fisioapp.data.entities.local.Result
import br.com.fisioapp.util.AppDispatchers
import br.com.fisioapp.util.ext.AppUtil
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.net.ConnectException

/**
 * Executa uma [retrofit2.Call] dentro de um contexto de uma coroutine.
 *
 * @return [Result] com objeto de resposta da requisição ou uma mensagem de erro.
 */
suspend fun <T> Call<T>.backgroundCall(dispatcher: CoroutineDispatcher): Result<T?> {
    return withContext(context = dispatcher) {
        try {
            val response = execute()
            if (response.isSuccessful) {
                Result.success(response.body(), response.code())
            }
            else {
                val error = Gson().fromJson(response.errorBody()?.string(), ErrorDefault::class.java)
                Result.error(error.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is ConnectException || e is java.net.UnknownHostException) {
                Result.error<T?>(ConnectException("Seu dispositivo está sem internet."))
            } else {
                Result.error(e)
            }
        }
    }
}

suspend fun <T> Call<T>.ifOffline(listenerOffline: () -> T?): Result<T?> {
    return if (AppUtil.isNetworkConnected()) {
        withContext(context = AppDispatchers().io) {
            val response = this@ifOffline.execute()
            if (response.isSuccessful) {
                Result.success(response.body(), response.code())
            } else {
                Result.error(response.headers().get("ERROR"))
            }
        }
    } else {
        withContext(context = AppDispatchers().computation) {
            val response = listenerOffline()
            Result.success(response, 0)
        }
    }
}

data class ErrorDefault  (val message:String)

