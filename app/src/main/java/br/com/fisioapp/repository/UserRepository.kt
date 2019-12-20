package br.com.fisioapp.repository

import br.com.fisioapp.data.entities.remote.request.AuthenticationRequest
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.service.UserService
import br.com.fisioapp.data.service.retrofitConfig.ServiceBuilder
import br.com.bb.oewallet.extension.backgroundCall
import br.com.fisioapp.SuperApplication.Companion.database
import br.com.fisioapp.data.entities.remote.response.UserResponse


class UserRepository : BaseRepository() {
    private val userDao = database?.userDao()
    private val userService: UserService by lazy { ServiceBuilder.create(UserService::class.java) }

    suspend fun doLogin(authenticationRequest: AuthenticationRequest) =
        userService.doLogin(authenticationRequest).backgroundCall(dispatchers.value.io)

    suspend fun saveUser(user: UserResponse) =
        userService.save(user).backgroundCall(dispatchers.value.io)

    suspend fun editUser(user: UserResponse) =
        userService.edit(user).backgroundCall(dispatchers.value.io)

    suspend fun findData() =
        userService.findData().backgroundCall(dispatchers.value.io)


    suspend fun findClients() =
        userService.findClients().backgroundCall(dispatchers.value.io)


    fun persistenceSaveUser(loginResponse: LoginResponse) =
        userDao?.insert(loginResponse)

    fun persistenceFindUserr() = userDao?.find()
    fun persistenceDeleteUser() = userDao?.delete()
}

