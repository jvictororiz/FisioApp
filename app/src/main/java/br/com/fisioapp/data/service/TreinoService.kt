package br.com.fisioapp.data.service

import br.com.fisioapp.data.entities.remote.response.DiagnosticoClinico
import br.com.fisioapp.data.entities.remote.response.Objetivo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TreinoService {

    @GET("product")
    fun findTreinos(): Call<List<Objetivo>>

    @GET("findCid/{id}")
    fun findCid(@Path("id") id: String): Call<DiagnosticoClinico>
}
