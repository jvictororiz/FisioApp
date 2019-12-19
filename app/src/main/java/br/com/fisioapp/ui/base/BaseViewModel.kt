package br.com.fisioapp.ui.base

import androidx.annotation.IntegerRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fisioapp.SuperApplication
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val loading = SingleLiveEvent<Boolean>()
    val error = SingleLiveEvent<String>()
    val context = SuperApplication.context

    fun launch(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

    fun launchWithLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            loading.value = true
            block()
            loading.value = false
        }

    }

    fun getString(idString: Int): String = SuperApplication.context.getString(idString)


}
