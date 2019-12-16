package br.com.androidstartermvvm.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.androidstartermvvm.R
import br.com.androidstartermvvm.viewModel.LoginViewModel
import br.com.androidstartermvvm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by lazy {
       LoginViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

       setupListeners()
        subscribe()
    }

    private fun setupListeners() {
        btn_login.setOnClickListener {
            viewModel.doLogin(edt_username.text.toString(), edt_password.text.toString())
        }
    }

    private fun subscribe() {
        viewModel.error.observe(this, Observer {

        })

        viewModel.responseLogin.observe(this, Observer {

        })
    }

}
