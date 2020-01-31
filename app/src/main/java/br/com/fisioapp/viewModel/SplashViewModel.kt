package br.com.fisioapp.viewModel

import br.com.fisioapp.data.entities.remote.response.StatusUser
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent
import br.com.fisioapp.util.ext.expiredAuthorization
import br.com.fisioapp.util.ext.status

class SplashViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    val toAdmin = SingleLiveEvent<Unit>()
    val toUser = SingleLiveEvent<Unit>()
    val toLogin = SingleLiveEvent<Unit>()

    fun findStatus() = launch {
        val user = userRepository.persistenceFindUser()
        if(user == null || !user.expiredAuthorization()){
            toLogin.call()
        }else{
            when(user.status()){
                StatusUser.ADMIN-> toAdmin.call()
                StatusUser.CLIENT-> toUser.call()
            }
        }
    }
}