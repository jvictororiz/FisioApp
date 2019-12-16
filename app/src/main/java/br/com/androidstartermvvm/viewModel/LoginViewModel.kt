package br.com.androidstartermvvm.viewModel

import br.com.androidstartermvvm.data.entities.remote.request.AuthenticationRequest
import br.com.androidstartermvvm.data.entities.remote.response.AuthenticationResponse
import br.com.androidstartermvvm.repository.UserRepository
import br.com.androidstartermvvm.ui.base.BaseViewModel
import br.com.androidstartermvvm.ui.base.SingleLiveEvent

class LoginViewModel : BaseViewModel() {
    private val userRepository: UserRepository = UserRepository()
    val responseLogin = SingleLiveEvent<Unit>()

    fun doLogin(user: String, password: String) = launchWithLoad {
        val result = userRepository.doLogin(AuthenticationRequest(
            username = user,
            password = password
        ))
        if (result.isSuccessful()) {
            val data = result.data?.response
            saveLocalData(data)

        } else {
            error.value = result.throwable?.message
        }
    }

    private fun saveLocalData(data: AuthenticationResponse?) = launch {
        data?.let {
            userRepository.saveUser(it)
        }
    }

}
