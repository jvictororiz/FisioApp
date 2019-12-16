package br.com.androidstartermvvm.data.service

import br.com.androidstartermvvm.data.entities.local.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RespostaService {
    @POST("")
    fun listarResposta(): Call<List<User>>


    @POST("")
    fun detailResposta(@Body idReposta:Int): Call<Resposta>
}
