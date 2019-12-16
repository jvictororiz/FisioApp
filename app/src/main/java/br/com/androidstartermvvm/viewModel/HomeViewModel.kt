package br.com.androidstartermvvm.viewModel

import br.com.androidstartermvvm.data.entities.remote.response.Product
import br.com.androidstartermvvm.repository.RespostaRepository
import br.com.androidstartermvvm.ui.base.BaseViewModel
import br.com.androidstartermvvm.ui.base.SingleLiveEvent
import java.util.*

class HomeViewModel : BaseViewModel() {
    private val respostaRepository: RespostaRepository = RespostaRepository()
    val actionListProduct = SingleLiveEvent<List<Product>>()
    val snackbarMessage = SingleLiveEvent<String>()

    fun listResposta() = launchWithLoad {
//        val result = respostaRepository.listarResposta()
//        if (result.isSuccessful()) {
//            resposta.value = result.data
//        } else {
//            snackbarMessage.value = result.throwable?.message
//        }
    }

    fun detailResposta(atributo: Objects?) = launchWithLoad {
//        val result = atributo?.id?.let { respostaRepository.detailResposta(it) }
//        if (result != null && result.isSuccessful()) {
//            respostaDetalhe.value = result.data
//        } else {
//            snackbarMessage.value = result?.throwable?.message
//        }
    }

}
