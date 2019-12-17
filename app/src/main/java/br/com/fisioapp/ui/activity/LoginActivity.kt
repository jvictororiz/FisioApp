package br.com.fisioapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.*
import br.com.fisioapp.R
import br.com.fisioapp.viewModel.LoginViewModel
import br.com.fisioapp.ui.base.BaseActivity
import br.com.fisioapp.util.ext.hideLoad
import br.com.fisioapp.util.ext.successLoad
import br.com.fisioapp.util.ext.showLoad
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

        viewModel.toLogin.observe(this, Observer {
            btn_login.successLoad()
            startActivityAnim(Intent(this, LoginActivity::class.java))
            finish()
        })
        viewModel.toUser.observe(this, Observer {
            btn_login.successLoad()
            startActivityAnim(Intent(this, UserHomeActivity::class.java))
            finish()

        })
        viewModel.toAdmin.observe(this, Observer {
            btn_login.successLoad()
            startActivityAnim(Intent(this, AdminHomeActivity::class.java))
            finish()
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
