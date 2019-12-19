package br.com.fisioapp.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.bb.oewallet.ui.BaseFragment

import br.com.fisioapp.R
import br.com.fisioapp.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.user_register_fragment.*

class RegisterUserFragment(override val fragmentTag: String) : BaseLoginFragment() {
    companion object {
        fun newInstance() = RegisterUserFragment("RegisterUserFragment")
    }

    val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
        subscribe()
    }

    private fun setupListeners() {

    }

    private fun subscribe() {
    }

    override fun onDestroy() {
        super.onDestroy()
        btn_register.dispose()
    }


}
