package br.com.fisioapp.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.ui.base.BaseActivity
import br.com.fisioapp.ui.fragment.login.RegisterUserPersonalDataFragment
import br.com.fisioapp.viewModel.RegisterClientViewModel
import kotlinx.android.synthetic.main.activity_register_client.*

class RegisterClientActivity : BaseActivity() {

    val clientViewModel: RegisterClientViewModel by lazy {
        ViewModelProvider(this).get(RegisterClientViewModel::class.java)
    }

    private val registerFragment by lazy {
        RegisterUserPersonalDataFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_client)

        intent?.extras?.getParcelable<User>(LoginActivity.EXTRA_USER_EDIT)?.let {
            replace(RegisterUserPersonalDataFragment.newInstance(it))
            return
        }
        replace(registerFragment, false)
        subscribe()
    }

    private fun subscribe() {
        clientViewModel.error.observe(this, Observer {
            tv_error.text = it
        })
    }
}
