package br.com.fisioapp.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.UserResponse
import br.com.fisioapp.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.user_register_fragment.*

class RegisterUserFragment(override val fragmentTag: String) : BaseLoginFragment() {
    companion object {
        const val EXTRA_USER_DATA = "EXTRA_USER_DATA"
        fun newInstance(userResponse: UserResponse) =
            RegisterUserFragment("RegisterUserFragment").apply {
                bundleOf(
                    Pair(EXTRA_USER_DATA, userResponse)
                )
            }

        fun newInstance() =  RegisterUserFragment("RegisterUserFragment")
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

        arguments?.getParcelable<UserResponse>(EXTRA_USER_DATA)?.let {
            viewModel.prepareToEdit(it)
        }

        setupListeners()
        subscribe()
    }

    private fun setupListeners() {
        btn_register.setOnClickListener {
            viewModel.saveUser()
        }
    }

    private fun subscribe() {
        viewModel.oldDataUser.observe(viewLifecycleOwner, Observer {
            edt_name.setText(it.name)
        })

        viewModel.oldDataUser.observe(viewLifecycleOwner, Observer {

            edt_username.isEnabled = false
            btn_register.setOnClickListener {
                viewModel.editUser()
            }
        })

        viewModel.refreshData.observe(viewLifecycleOwner, Observer {
            viewModel.updateDataInUser(
                UserResponse(
                    username =edt_username.toString(),
                    name = edt_name.text.toString()
                )
            )
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        btn_register?.dispose()
    }

}
