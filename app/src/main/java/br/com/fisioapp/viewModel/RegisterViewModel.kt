package br.com.fisioapp.viewModel

import br.com.fisioapp.data.entities.remote.response.UserResponse
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent

class RegisterViewModel : BaseViewModel() {
    val userData = SingleLiveEvent<UserResponse>()

    fun prepareToEdit(userResponse: UserResponse){
        userData.value = userResponse

    }
    fun prepareToRegister(){

    }

}