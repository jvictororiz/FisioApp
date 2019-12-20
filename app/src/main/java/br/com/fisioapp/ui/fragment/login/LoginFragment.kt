package br.com.fisioapp.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import br.com.fisioapp.R
import br.com.fisioapp.ui.activity.AdminHomeActivity
import br.com.fisioapp.ui.activity.ClientHomeActivity
import br.com.fisioapp.ui.activity.LoginAndRegisterActivity
import br.com.fisioapp.ui.base.BaseActivity
import br.com.fisioapp.util.ext.hideLoad
import br.com.fisioapp.util.ext.showLoad
import br.com.fisioapp.util.ext.successLoad
import br.com.fisioapp.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment(override val fragmentTag: String) : BaseLoginFragment() {

    companion object {
        fun newInstance() = LoginFragment("LoginFragment")
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
        subscribe()
    }

    private fun setupListeners() {
        btn_login.setOnClickListener {
            viewModel.doLogin(edt_username.text.toString(), edt_password.text.toString())
        }

        tv_login.setOnClickListener {
            (activity as LoginAndRegisterActivity).selectTypeFragment(LoginAndRegisterActivity.MODE_REGISTER)
        }
    }

    private fun subscribe() {
        viewModel.error.observe( viewLifecycleOwner, Observer {
            tv_error.text = it
        })

        viewModel.toClient.observe(viewLifecycleOwner, Observer {
            btn_login.successLoad()
            (activity as BaseActivity).startActivityAnim(Intent(activity, ClientHomeActivity::class.java))
            activity?.finish()

        })
        viewModel.toAdmin.observe(viewLifecycleOwner, Observer {
            btn_login.successLoad()
            (activity as BaseActivity).startActivityAnim(Intent(activity, AdminHomeActivity::class.java))
            activity?.finish()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) btn_login.showLoad() else btn_login.hideLoad()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        btn_login?.dispose()
    }

}
