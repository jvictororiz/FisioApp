package br.com.androidstartermvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val loading = SingleLiveEvent<Boolean>()
    val error = SingleLiveEvent<String>()

    fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    fun launchWithLoad(block: suspend () -> Unit) {

        viewModelScope.launch {
            loading.value = true
            block()
            loading.value = false
        }

    }


}
