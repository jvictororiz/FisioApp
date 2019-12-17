package br.com.fisioapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fisioapp.R
import br.com.fisioapp.ui.base.BaseActivity
import br.com.fisioapp.viewModel.SplashViewModel

class SplashActivity : BaseActivity() {
    private val viewModel: SplashViewModel by lazy { ViewModelProvider(this).get(SplashViewModel::class.java) }
    private val TIME_TO_END_SPLASH = 700L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        subscribe()
        viewModel.findStatus()
            Handler().postDelayed({
        }, TIME_TO_END_SPLASH)
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
