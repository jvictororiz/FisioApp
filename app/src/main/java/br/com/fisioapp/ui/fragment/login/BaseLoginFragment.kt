package br.com.fisioapp.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.bb.oewallet.ui.BaseFragment

import br.com.fisioapp.R
import br.com.fisioapp.ui.activity.AdminHomeActivity
import br.com.fisioapp.ui.activity.ClientHomeActivity
import br.com.fisioapp.ui.activity.LoginAndRegisterActivity
import br.com.fisioapp.util.ext.hideLoad
import br.com.fisioapp.util.ext.showLoad
import br.com.fisioapp.util.ext.successLoad
import br.com.fisioapp.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.login_and_register_activity.*

abstract  class BaseLoginFragment : BaseFragment() {

}
