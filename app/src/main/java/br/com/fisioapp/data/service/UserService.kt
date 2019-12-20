package br.com.fisioapp.data.service

import br.com.fisioapp.data.entities.remote.request.AuthenticationRequest
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.entities.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {
    @POST("authentication")
    fun doLogin(@Body request: AuthenticationRequest): Call<LoginResponse>

    @GET("user")
    fun findData(): Call<UserResponse>

    @GET("clients")
    fun findClients(): Call<List<UserResponse>>

    @POST("user")
    fun save(@Body userResponse: UserResponse): Call<UserResponse>


    @PUT("user")
    fun edit(@Body userResponse: UserResponse): Call<UserResponse>
}
