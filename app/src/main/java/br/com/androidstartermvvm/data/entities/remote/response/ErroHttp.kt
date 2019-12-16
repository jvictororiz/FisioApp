package br.com.androidstartermvvm.data.entities.remote.response

import com.google.gson.annotations.SerializedName

/** Classe padrão para todos os erros retornados pelo backend. */
data class ErroHttp(
    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("error")
    val erro: String,
    @SerializedName("message")
    val mensagem: String
)
