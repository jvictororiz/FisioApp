package br.com.androidstartermvvm.data.service

import br.com.androidstartermvvm.data.entities.remote.request.AuthenticationRequest
import br.com.androidstartermvvm.data.entities.remote.response.AuthenticationResponse
import br.com.androidstartermvvm.data.entities.remote.response.BaseResponse
import br.com.androidstartermvvm.data.entities.remote.response.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RespostaService {
    @POST("")
    fun listarResposta(): Call<List<User>>


    @POST("")
    fun detailResposta(@Body idReposta:AuthenticationRequest): Call<BaseResponse<AuthenticationResponse>>
}
