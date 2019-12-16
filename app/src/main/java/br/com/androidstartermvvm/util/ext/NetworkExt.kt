package br.com.bb.oewallet.extension

import br.com.androidstartermvvm.data.entities.local.Result
import br.com.androidstartermvvm.util.AppDispatchers
import br.com.androidstartermvvm.util.ext.AppUtil
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
            val response = this@backgroundCall.execute()
            if (response.isSuccessful) {
                Result.success(response.body(), response.code())
            } else {
                Result.error(response.headers().get("ERROR"))
            }
        } catch (e: Exception) {
            if (e is ConnectException || e is java.net.UnknownHostException) {
                Result.error<T?>(ConnectException("Seu dispositivo está sem internet."))
            } else {
                Result.error(e)
            }
        }
    }
}

suspend fun <T> Call<T>.ifOffline(listenerOffline: () -> T?): Result<T?> {
    if (AppUtil.isNetworkConnected()) {
        return withContext(context = AppDispatchers().io) {
            val response = this@ifOffline.execute()
            if (response.isSuccessful) {
                Result.success(response.body(), response.code())
            } else {
                Result.error(response.headers().get("ERROR"))
            }
        }
    } else {
        return withContext(context = AppDispatchers().computation) {
            val response = listenerOffline()
            Result.success(response, 0)
        }
    }
}

