package br.com.fisioapp.repository

import br.com.fisioapp.data.entities.remote.request.AuthenticationRequest
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.service.UserService
import br.com.fisioapp.data.service.retrofitConfig.ServiceBuilder
import br.com.bb.oewallet.extension.backgroundCall
import br.com.fisioapp.SuperApplication.Companion.database


class UserRepository : BaseRepository() {
    private val userDao = database?.userDao()
    private val userService: UserService by lazy { ServiceBuilder.create(UserService::class.java) }

    suspend fun doLogin(authenticationRequest: AuthenticationRequest) =
        userService.doLogin(authenticationRequest).backgroundCall(dispatchers.value.io)

    suspend fun findClients() =
        userService.findClients().backgroundCall(dispatchers.value.io)


    fun saveUser(loginResponse: LoginResponse) =
        userDao?.insert(loginResponse)


    fun getUser() = userDao?.find()
}

