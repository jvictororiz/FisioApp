package br.com.androidstartermvvm.data.service

import br.com.androidstartermvvm.data.entities.remote.response.Product
import br.com.androidstartermvvm.data.entities.remote.response.User
import retrofit2.Call
import retrofit2.http.POST

interface UserService {
    @POST("")
    fun doLogin(login: String, password: String): Call<User>


    @POST("")
    fun findProducts(idUser: Int): Call<List<Product>>
}
