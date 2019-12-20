package br.com.fisioapp.viewModel

import br.com.fisioapp.data.entities.remote.response.UserResponse
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent

class RegisterViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    val oldDataUser by lazy { SingleLiveEvent<UserResponse>() }
    val refreshData by lazy { SingleLiveEvent<Unit>() }
    private var newDataUser: UserResponse? = null


    fun prepareToEdit(userResponse: UserResponse) {
        oldDataUser.value = userResponse
    }

    fun saveUser() = launch {
        refreshData.call()
        newDataUser?.let {
            userRepository.saveUser(it)
        }

    }

    fun editUser() = launch {
        refreshData.call()
        newDataUser?.let {
            userRepository.editUser(it)
        }
    }

    fun updateDataInUser(userResponse: UserResponse) {
        newDataUser = userResponse

    }

}