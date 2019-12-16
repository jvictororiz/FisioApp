package br.com.androidstartermvvm.repository

import br.com.androidstartermvvm.data.service.UserService
import br.com.androidstartermvvm.data.service.retrofitConfig.ServiceBuilder
import br.com.bb.oewallet.extension.backgroundCall
import br.com.bb.oewallet.extension.ifOffline


class UserRepository : BaseRepository() {
    private val userDao = database.value.productDao()
    private val userService: UserService = ServiceBuilder.create(UserService::class.java)

    suspend fun doLogin(login: String, password: String) =
        userService.doLogin(login, password).backgroundCall(dispatchers.value.io)


    suspend fun findAllProducts(id: Int) =
        userService.findProducts(id).ifOffline {
            userDao.getAll()
        }

}

