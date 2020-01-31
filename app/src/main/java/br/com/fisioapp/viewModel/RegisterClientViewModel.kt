package br.com.fisioapp.viewModel

import br.com.fisioapp.data.entities.remote.response.DiagnosticoClinico
import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.data.entities.remote.response.UserClient
import br.com.fisioapp.repository.TreinoRepository
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent
import java.util.*

class RegisterClientViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    private val treinoRepository: TreinoRepository by lazy { TreinoRepository() }
    val oldDataUser by lazy { SingleLiveEvent<UserClient>() }
    val refreshData by lazy { SingleLiveEvent<UserClient?>() }
    val success by lazy { SingleLiveEvent<UserClient>() }
    val cidSuccess by lazy { SingleLiveEvent<DiagnosticoClinico>() }
    val cidLoad by lazy { SingleLiveEvent<Boolean>() }
    val cidError by lazy { SingleLiveEvent<String>() }
    private var newDataUser: UserClient? = null


    fun prepareToEdit(user: UserClient) {
        if (user.diagnosticosClinico.isEmpty()) {
            user.diagnosticosClinico.add(Pair(DiagnosticoClinico("", ""), ""))
        }
        oldDataUser.value = user
    }

    fun saveUser() = launchWithLoad {
        refreshData.postValue(oldDataUser.value)
        success.value = UserClient("", "", "", Date(), "", "", "")
//        newDataUser?.let {
//            val saveResult= userRepository.saveUser(it)
//            if(saveResult.isSuccessful()){
//                success.value = it
//            }else{
//                error.value = saveResult.errorMessage()
//            }
//        }

    }

    fun editUser() = launchWithLoad {
        refreshData.value = (oldDataUser.value)
        newDataUser?.let {
            userRepository.editUser(it)
        }
    }

    fun updateDataInUser(user: UserClient) {
        newDataUser = user
    }

    fun findCid(search: String) = launch {
        cidLoad.value = true
        if (isCid(search)) {
            val result = treinoRepository.findCid(search)
            if (result.isSuccessful()) {
                if (result.data != null && result.data.name.isNotEmpty()) {
                    cidSuccess.value = DiagnosticoClinico(result.data.code, result.data.name)
                } else {
                    cidError.value = "CID Inválido"
                }
            } else {
                cidError.value = result.errorMessage()
            }
        } else {
            cidError.value = "CID Inválido"
        }
        cidLoad.value = false
    }

    private fun isCid(search: String): Boolean {
        return search.length < 6 && search.contains(".")
    }
}
