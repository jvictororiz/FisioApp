package br.com.androidstartermvvm.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Classe que contém os dispatchers padrões a serem utilizados pelo app.
 */
class AppDispatchers(
        /**
         * Dispatcher para execuções em primeiro plano.
         */
        val main: CoroutineDispatcher = Dispatchers.Main,
        /**
         * Dispatcher para execuções de operações mais demoradas (ex: tratamento de imagem,
         * manipulação pesada de dados, etc).
         */
        val computation: CoroutineDispatcher = Dispatchers.Default,
        /**
         * Dispatcher para execuções de I/O.
         */
        val io: CoroutineDispatcher = Dispatchers.IO
)
