package br.com.fisioapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fisioapp.R
import br.com.fisioapp.ui.base.BaseActivity
import br.com.fisioapp.viewModel.LoginViewModel
import br.com.fisioapp.viewModel.SplashViewModel

class SplashActivity : BaseActivity() {
    private val viewModel: SplashViewModel by lazy { ViewModelProvider(this).get(SplashViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.findStatus()
        subscribe()
    }

    private fun subscribe() {
        viewModel.toLogin.observe(this, Observer {
            startActivityAnim(Intent(this, LoginActivity::class.java))
            finish()
        })
        viewModel.toUser.observe(this, Observer {
            startActivityAnim(Intent(this, UserHomeActivity::class.java))
            finish()

        })
        viewModel.toAdmin.observe(this, Observer {
            startActivityAnim(Intent(this, AdminHomeActivity::class.java))
            finish()

        })
    }
}