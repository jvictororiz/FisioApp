package br.com.fisioapp.viewModel

import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.request.AuthenticationRequest
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.entities.remote.response.StatusUser
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent
import br.com.fisioapp.util.ext.status

class LoginViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    val toAdmin = SingleLiveEvent<Unit>()
    val toUser = SingleLiveEvent<Unit>()
    val toLogin = SingleLiveEvent<Unit>()

    fun doLogin(user: String, password: String) = launchWithLoad {
        if (user.isEmpty() || user.length < 5) {
            error.value = context.getString(R.string.user_invalid)
            return@launchWithLoad
        } else if (password.isEmpty() || password.length < 5) {
            error.value = context.getString(R.string.password_invalid)
            return@launchWithLoad
        }

        val result = userRepository.doLogin(
            AuthenticationRequest(
                username = user,
                password = password
            )
        )
        if (result.isSuccessful()) {
            val response = result.data
            saveLocalData(response)
            if(response == null){
                toLogin.call()
            }else{
                when(response.status()){
                    StatusUser.ADMIN-> toAdmin.call()
                    StatusUser.CLIENT-> toAdmin.call()
                }
            }

        } else {
            error.value = result.errorMessage()
        }
    }

    private fun saveLocalData(data: LoginResponse?) = launch {
        data?.let { userRepository.saveUser(it) }
    }
}