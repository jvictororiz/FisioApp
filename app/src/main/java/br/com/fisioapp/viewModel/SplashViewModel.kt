package br.com.fisioapp.viewModel

import android.os.Handler
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.request.AuthenticationRequest
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.entities.remote.response.StatusUser
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent
import br.com.fisioapp.util.ext.status

class SplashViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    val toAdmin = SingleLiveEvent<Unit>()
    val toUser = SingleLiveEvent<Unit>()
    val toLogin = SingleLiveEvent<Unit>()

    fun findStatus() = launch {
        val user = userRepository.getUser()
        if(user == null){
            toLogin.call()
        }else{
            when(user.status()){
                StatusUser.ADMIN-> toAdmin.call()
                StatusUser.CLIENT-> toAdmin.call()

            }
        }
    }
}