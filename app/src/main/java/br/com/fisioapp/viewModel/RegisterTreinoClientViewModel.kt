package br.com.fisioapp.viewModel

import br.com.fisioapp.data.entities.remote.response.*
import br.com.fisioapp.repository.TreinoRepository
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent
import java.util.*

class RegisterTreinoClientViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    private val treinoRepository: TreinoRepository by lazy { TreinoRepository() }

    val userSelected by lazy { SingleLiveEvent<User>() }
    val objetiveSelected by lazy { SingleLiveEvent<Objetivo>() }
    val successObjeties by lazy { SingleLiveEvent<List<Objetivo>>() }
    val emptyObjetivo by lazy { SingleLiveEvent<Boolean>() }

    fun findObjetive() = launch {
        successObjeties.value = listOf(
            Objetivo("Minha conduta número 1", "Descrição da conduta aqui bla bla bla", Date(), Date(), arrayListOf(Sessao(5, Date(), "teste"), Sessao(5, Date(), "teste"), Sessao(5, Date(), "teste"), Sessao(5, Date(), "teste"), Sessao(5, Date(), "teste"), Sessao(5, Date(), "teste"))),
            Objetivo("Minha conduta número 1", "Descrição da conduta aqui bla bla bla", Date(), Date(), arrayListOf(Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(5, Date(), "teste"), Sessao(2, Date(), "teste"))),
            Objetivo("Minha conduta número 1", "Descrição da conduta aqui bla bla bla", Date(), Date(), arrayListOf(Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(4, Date(), "teste"), Sessao(2, Date(), "teste"), Sessao(3, Date(), "teste"))),
            Objetivo("Minha conduta número 1", "Descrição da conduta aqui bla bla bla", Date(), Date(), arrayListOf(Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(5, Date(), "teste"), Sessao(4, Date(), "teste"), Sessao(3, Date(), "teste"))),
            Objetivo("Minha conduta número 1", "Descrição da conduta aqui bla bla bla", Date(), Date(), arrayListOf(Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(1, Date(), "teste"), Sessao(4, Date(), "teste"), Sessao(2, Date(), "teste"), Sessao(3, Date(), "teste")))

        )

//        val result = treinoRepository.findTreinos()
//        if (result.isSuccessful()) {
//            emptyObjetivo.value = result.data?.isEmpty()
//            successObjeties.value = result.data
//        } else {
//            error.value = result.errorMessage()
//        }

    }

    fun saveObjetive() = launch {

    }

    fun selectUser(it: User) {
        userSelected.value = it
    }

    fun setObjetiveSelected(objetivo: Objetivo) {
        objetiveSelected.value = objetivo
    }

    fun registerSession(it: Sessao) {
        val value = objetiveSelected.value
        value?.sessao?.add(it)
        //TODO:CHAMAR SERVIÇO DE SALVAR
    }

}
