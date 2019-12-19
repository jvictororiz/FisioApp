package br.com.fisioapp.data.service

import br.com.fisioapp.data.entities.remote.response.TreinoResponse
import retrofit2.Call
import retrofit2.http.GET

interface TreinoService {

    @GET("product")
    fun findTreinos(): Call<List<TreinoResponse>>
}
