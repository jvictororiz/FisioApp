package br.com.androidstartermvvm.ui.activity

import android.os.Bundle
import androidx.lifecycle.*
import br.com.androidstartermvvm.R
import br.com.androidstartermvvm.viewModel.LoginViewModel
import br.com.androidstartermvvm.ui.base.BaseActivity
import br.com.androidstartermvvm.util.ext.hideLoad
import br.com.androidstartermvvm.util.ext.successLoad
import br.com.androidstartermvvm.util.ext.showLoad
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

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
            tv_error.text = it
        })

        viewModel.responseLogin.observe(this, Observer {
            btn_login.successLoad()
        })

        viewModel.loading.observe(this, Observer {
            if (it) btn_login.showLoad() else btn_login.hideLoad()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        btn_login.dispose()
    }

}
