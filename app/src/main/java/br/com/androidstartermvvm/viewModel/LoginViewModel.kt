package br.com.androidstartermvvm.viewModel

import br.com.androidstartermvvm.data.entities.remote.response.Product
import br.com.androidstartermvvm.repository.UserRepository
import br.com.androidstartermvvm.ui.base.BaseViewModel
import br.com.androidstartermvvm.ui.base.SingleLiveEvent

class LoginViewModel : BaseViewModel() {
    private val respostaRepository: UserRepository = UserRepository()
    val actionListProduct = SingleLiveEvent<List<Product>>()

    fun doLogin(user: String, password: String) = launchWithLoad {
        val result = respostaRepository.doLogin(user, password)
        if (result.isSuccessful()) {
            val data = result.data
            findAllProducts(data?.id)
        } else {
            error.value = result.throwable?.message
        }
    }

    fun findAllProducts(idUser:Long?) = launchWithLoad {
        val result = idUser?.toInt()?.let { respostaRepository.findAllProducts(it) }
        if (result != null && result.isSuccessful()) {
            actionListProduct.value = result.data
        } else {
            error.value = result?.throwable?.message
        }
    }

}
