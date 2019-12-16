package br.com.androidstartermvvm.repository

import br.com.androidstartermvvm.data.entities.remote.request.AuthenticationRequest
import br.com.androidstartermvvm.data.entities.remote.response.AuthenticationResponse
import br.com.androidstartermvvm.data.service.UserService
import br.com.androidstartermvvm.data.service.retrofitConfig.ServiceBuilder
import br.com.bb.oewallet.extension.backgroundCall
import br.com.bb.oewallet.extension.ifOffline


class UserRepository : BaseRepository() {
    private val userDao = database.value.userDao()
    private val userService: UserService = ServiceBuilder.create(UserService::class.java)

    suspend fun doLogin(authenticationRequest: AuthenticationRequest) =
        userService.doLogin(authenticationRequest).backgroundCall(dispatchers.value.io)


    fun saveUser(authenticationResponse: AuthenticationResponse) =
        userDao.insert(authenticationResponse)


    fun getUser() = userDao.find()
}

