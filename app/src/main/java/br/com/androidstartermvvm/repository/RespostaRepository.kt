package br.com.androidstartermvvm.repository

import br.com.androidstartermvvm.data.service.RespostaService
import br.com.androidstartermvvm.data.service.retrofitConfig.ServiceBuilder
import br.com.bb.oewallet.extension.backgroundCall
import br.com.bb.oewallet.extension.ifOffline


class RespostaRepository : BaseRepository() {
    private val userDao = database.value.productDao()
    private val respostaService: RespostaService =
        ServiceBuilder.create(RespostaService::class.java)

    suspend fun listarResposta() =
        respostaService.listarResposta().ifOffline {
            userDao.getAll()
        }

    suspend fun detailResposta(id: Int) =
        respostaService.detailResposta(id).backgroundCall(dispatchers.value.io)

}

