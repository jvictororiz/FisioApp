package br.com.fisioapp.viewModel

import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent
import br.com.fisioapp.util.ext.name
import java.util.*

class AdminHomeViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }

    val nameClient = SingleLiveEvent<String>()
    val toLogin = SingleLiveEvent<Any?>()
    val datasUser = SingleLiveEvent<User>()
    val clients = SingleLiveEvent<List<User>>()
    val loadClients = SingleLiveEvent<Boolean>()
    val errorClients = SingleLiveEvent<String>()
    val totalItensClients = SingleLiveEvent<Int>()

    init {
        nameClient.value = userRepository.persistenceFindUser()?.name()
        findClients()
    }

    private fun findClients() = launch {
        clients.value =
            listOf(
                User("jvictororiz", "joao victor", Date(), "61 998125199", null, "fdafdas"),
                User("jvictororiz", "joao victor", Date(), "61 998125199", null, "fdafdas"),
                User("jvictororiz", "joao victor", Date(), "61 998125199", null, "fdafdas"),
                User("jvictororiz", "joao victor", Date(), "61 998125199", null, "fdafdas")
            )

//        loadClients.value = true
//        val resultClients = userRepository.findClients()
//        if (resultClients.isSuccessful()) {
//            if (resultClients.data?.isEmpty() == true) {
//                errorClients.value = getString(R.string.empty_clients)
//                return@launch
//            }
//            totalItensClients.value = resultClients.data?.size
//            clients.value = resultClients.data
//        } else {
//            errorClients.value = resultClients.errorMessage()
//        }
//        loadClients.value = false
    }

    fun exit() {
        userRepository.persistenceDeleteUser()
        toLogin.call()
    }


    fun findDataUser() = launch {
        val dataUser = userRepository.findData()
        if (dataUser.isSuccessful()) {
            datasUser.value = dataUser.data
        } else {

        }
    }
}