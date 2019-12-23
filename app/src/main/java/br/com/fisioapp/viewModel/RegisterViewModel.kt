package br.com.fisioapp.viewModel

import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.data.entities.remote.response.UserClient
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent

class RegisterViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    val oldDataUser by lazy { SingleLiveEvent<UserClient>() }
    val refreshData by lazy { SingleLiveEvent<Unit>() }
    val success by lazy { SingleLiveEvent<Unit>() }
    private var newDataUser: UserClient? = null


    fun prepareToEdit(user: UserClient) {
        oldDataUser.value = user
    }

    fun saveUser() = launchWithLoad {
        refreshData.call()
        newDataUser?.let {
            val saveResult= userRepository.saveUser(it)
            if(saveResult.isSuccessful()){
                success.call()
            }else{
                error.value = saveResult.errorMessage()
            }
        }

    }

    fun editUser() = launchWithLoad {
        refreshData.call()
        newDataUser?.let {
            userRepository.editUser(it)
        }
    }

    fun updateDataInUser(user: UserClient) {
        newDataUser = user
    }
}