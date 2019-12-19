package br.com.fisioapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.UserResponse
import br.com.fisioapp.ui.base.BaseActivity
import br.com.fisioapp.ui.fragment.login.LoginFragment
import br.com.fisioapp.ui.fragment.login.RegisterUserFragment
class LoginAndRegisterActivity : BaseActivity() {

    private val loginFragment by lazy {
        LoginFragment.newInstance()
    }
    private val registerFragment by lazy {
        RegisterUserFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_and_register_activity)
        selectTypeFragment(intent?.extras?.getInt(EXTRAS_TYPE_SCREEN)?: MODE_LOGIN)
    }

    fun selectTypeFragment(type: Int) {
        when (type) {
            MODE_LOGIN -> {
                replace(loginFragment, false)
            }

            MODE_REGISTER -> {
                replace(registerFragment, true)
            }
            MODE_EDIT_PROFILE -> {
                intent?.extras?.getParcelable<UserResponse>(EXTRA_USER_EDIT)?.let {
                    replace(registerFragment, true)
                    registerFragment.apply { viewModel.prepareToEdit(it) }
                }
            }
        }
    }

    companion object {
        const val EXTRAS_TYPE_SCREEN = "EXTRAS_TYPE_SCREEN"
        const val EXTRA_USER_EDIT = "EXTRA_USER_EDIT"
        const val MODE_LOGIN = 0
        const val MODE_REGISTER = 1
        const val MODE_EDIT_PROFILE = 2

        fun startModeEdit(context: Context, userResponse: UserResponse){
            val intent = Intent(context, LoginAndRegisterActivity::class.java).apply {
                putExtra(EXTRA_USER_EDIT, userResponse)
                putExtra(EXTRAS_TYPE_SCREEN, EXTRAS_TYPE_SCREEN)
            }
            (context as BaseActivity).startActivityAnim(intent)
        }
    }

}
