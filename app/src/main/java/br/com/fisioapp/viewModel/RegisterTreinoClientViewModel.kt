package br.com.fisioapp.viewModel

import br.com.fisioapp.data.entities.remote.response.*
import br.com.fisioapp.repository.TreinoRepository
import br.com.fisioapp.repository.UserRepository
import br.com.fisioapp.ui.base.BaseViewModel
import br.com.fisioapp.ui.base.SingleLiveEvent
import java.util.*
import kotlin.collections.ArrayList

class RegisterTreinoClientViewModel : BaseViewModel() {
    private val userRepository: UserRepository by lazy { UserRepository() }
    private val treinoRepository: TreinoRepository by lazy { TreinoRepository() }

    val userSelected by lazy { SingleLiveEvent<User>() }
    val successObjeties by lazy { SingleLiveEvent<List<Objetivo>>() }
    val emptyObjetivo by lazy { SingleLiveEvent<Boolean>() }

    fun findObjetive() = launch {

        successObjeties.value = listOf(
            Objetivo("Minha conduta número 1" ,"Descrição da conduta aqui bla bla bla", Date(), Date(), listOf(Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(4, Date(), "teste"),Sessao(2, Date(), "teste"),Sessao(3, Date(), "teste"))),
            Objetivo("Minha conduta número 1" ,"Descrição da conduta aqui bla bla bla", Date(), Date(), listOf(Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(5, Date(), "teste"),Sessao(2, Date(), "teste"))),
            Objetivo("Minha conduta número 1" ,"Descrição da conduta aqui bla bla bla", Date(), Date(), listOf(Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(4, Date(), "teste"),Sessao(2, Date(), "teste"),Sessao(3, Date(), "teste"))),
            Objetivo("Minha conduta número 1" ,"Descrição da conduta aqui bla bla bla", Date(), Date(), listOf(Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(5, Date(), "teste"),Sessao(4, Date(), "teste"),Sessao(3, Date(), "teste"))),
            Objetivo("Minha conduta número 1" ,"Descrição da conduta aqui bla bla bla", Date(), Date(), listOf(Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(1, Date(), "teste"),Sessao(4, Date(), "teste"),Sessao(2, Date(), "teste"),Sessao(3, Date(), "teste")))

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

}
