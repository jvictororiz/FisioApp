package br.com.fisioapp.viewModel

import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.TreinoResponse
import br.com.fisioapp.data.entities.remote.response.UserResponse
import br.com.fisioapp.repository.TreinoRepository
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent
import br.com.fisioapp.util.ext.name

class AdminHomeViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    private val treinoRepository: TreinoRepository by lazy { TreinoRepository() }


    val nameClient = SingleLiveEvent<String>()

    val treinos = SingleLiveEvent<List<TreinoResponse>>()
    val loadTreinos = SingleLiveEvent<Boolean>()
    val errorTreinos = SingleLiveEvent<String>()
    val totalItensTreinos = SingleLiveEvent<Int>()

    val clients = SingleLiveEvent<List<UserResponse>>()
    val loadClients = SingleLiveEvent<Boolean>()
    val errorClients = SingleLiveEvent<String>()
    val totalItensClients = SingleLiveEvent<Int>()

    init {
        nameClient.value = userRepository.getUser()?.name()
        findClients()
        findTreinos()
    }

    fun findClients() = launch {
        loadClients.value = true
        val resultClients = userRepository.findClients()
        if (resultClients.isSuccessful()) {
            if (resultClients.data?.isEmpty() == true) {
                errorClients.value = getString(R.string.empty_clients)
                return@launch
            }
            totalItensClients.value = resultClients.data?.size
            clients.value = resultClients.data
        } else {
            errorClients.value = resultClients.errorMessage()
        }
        loadClients.value = false
    }

    fun findTreinos() = launch {
        loadTreinos.value = true
        val resultTreinos = treinoRepository.findTreinos()
        if (resultTreinos.isSuccessful()) {
            if (resultTreinos.data?.isEmpty() == true) {
                errorTreinos.value = getString(R.string.empty_treinos)
                return@launch
            }
            totalItensTreinos.value = resultTreinos.data?.size
            treinos.value = resultTreinos.data
        } else {
            errorTreinos.value = resultTreinos.errorMessage()
        }
        loadTreinos.value = false

    }
}