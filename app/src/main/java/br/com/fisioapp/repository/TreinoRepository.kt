package br.com.fisioapp.repository

import br.com.fisioapp.data.entities.remote.request.AuthenticationRequest
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.service.UserService
import br.com.fisioapp.data.service.retrofitConfig.ServiceBuilder
import br.com.bb.oewallet.extension.backgroundCall
import br.com.fisioapp.SuperApplication.Companion.database
import br.com.fisioapp.data.service.TreinoService


class TreinoRepository : BaseRepository() {
    private val treinoService: TreinoService by lazy { ServiceBuilder.create(TreinoService::class.java) }

    suspend fun findTreinos() =
        treinoService.findTreinos().backgroundCall(dispatchers.value.io)


}

