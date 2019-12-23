package br.com.fisioapp.ui.fragment.login

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.bb.oewallet.ui.BaseFragment

import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.data.entities.remote.response.UserClient
import br.com.fisioapp.ui.base.BaseLoginFragment
import br.com.fisioapp.util.ext.hideLoad
import br.com.fisioapp.util.ext.showLoad
import br.com.fisioapp.util.ext.toDate
import br.com.fisioapp.util.ext.toString
import br.com.fisioapp.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register_client.*
import kotlinx.android.synthetic.main.user_register_fragment.*

class RegisterUserPersonalDataFragment(override val fragmentTag: String) : BaseLoginFragment() {
    companion object {
        const val EXTRA_USER_DATA = "EXTRA_USER_DATA"
        fun newInstance(user: User) =
            RegisterUserPersonalDataFragment("RegisterUserFragment").apply {
                bundleOf(
                    Pair(EXTRA_USER_DATA, user)
                )
            }

        fun newInstance() = RegisterUserPersonalDataFragment("RegisterUserFragment")
    }


    val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.getParcelable<UserClient>(EXTRA_USER_DATA)?.let {
            viewModel.prepareToEdit(it)
        }

        setupListeners()
        subscribe()
    }

    private fun setupListeners() {
        btn_back.isEnabled = false
        btn_next.setOnClickListener {
            viewModel.saveUser()
        }
    }

    private fun subscribe() {
        viewModel.oldDataUser.observe(viewLifecycleOwner, Observer {
            edt_username.isEnabled = false
            edt_name.setText(it.name)
            edt_username.setText(it.username)
            edt_birthday.setText(it.birthDate.toString("dd/MM/yyyy"))
            edt_phone_number.setText(it.phoneNumber)
            edt_job.setText(it.job)
            btn_next.setOnClickListener {
                viewModel.editUser()
            }
        })

        viewModel.refreshData.observe(viewLifecycleOwner, Observer {
            viewModel.updateDataInUser(
                UserClient(
                    username = edt_username.toString(),
                    name = edt_name.text.toString(),
                    birthDate = edt_birthday.text.toString().toDate("dd/MM/yyyy"),
                    phoneNumber = edt_phone_number.text.toString(),
                    job = edt_job.text.toString()
                )
            )
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) btn_next.showLoad() else btn_next.hideLoad()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        btn_next?.dispose()
    }
}
