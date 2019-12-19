package br.com.fisioapp.data.service

import br.com.fisioapp.data.entities.remote.request.AuthenticationRequest
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.entities.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("authentication")
    fun doLogin(@Body request: AuthenticationRequest): Call<LoginResponse>

    @GET("clients")
    fun findClients(): Call<List<UserResponse>>
}
