package br.com.androidstartermvvm.repository

import br.com.androidstartermvvm.data.service.RespostaService
import br.com.androidstartermvvm.data.service.retrofitConfig.ServiceBuilder
import br.com.bb.oewallet.extension.backgroundCall
import br.com.bb.oewallet.extension.ifOffline


class RespostaRepository : BaseRepository() {
    private val userDao = database.value.productDao()
    private val respostaService: RespostaService =
        ServiceBuilder.create(RespostaService::class.java)


}

