package br.com.fisioapp.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.DiagnosticoClinico
import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.data.entities.remote.response.UserClient
import br.com.fisioapp.ui.base.BaseLoginFragment
import br.com.fisioapp.util.ext.hideLoad
import br.com.fisioapp.util.ext.showLoad
import br.com.fisioapp.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register_client.*
import kotlinx.android.synthetic.main.user_register_sintomas_fragment.*

class RegisterUserSintomasFragment(override val fragmentTag: String) : BaseLoginFragment() {
    companion object {
        const val EXTRA_USER_DATA = "EXTRA_USER_DATA"
        fun newInstance(user: User) =
            RegisterUserSintomasFragment("RegisterUserSintomasFragment").apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_USER_DATA, user) }

            }
    }


    val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_register_sintomas_fragment, container, false)
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
        activity?.btn_back?.visibility = View.VISIBLE
        activity?.btn_next?.setOnClickListener {
            viewModel.editUser()
        }
    }

    private fun subscribe() {
        viewModel.oldDataUser.observe(viewLifecycleOwner, Observer {
            it?.let { userClient ->
                if (userClient.diagnosticosClinico.isEmpty()) {
                    userClient.diagnosticosClinico.add(Pair(DiagnosticoClinico("", ""), ""))
                    userClient.diagnosticosClinico.add(Pair(DiagnosticoClinico("", ""), ""))
                    userClient.diagnosticosClinico.add(Pair(DiagnosticoClinico("", ""), ""))
                    userClient.diagnosticosClinico.add(Pair(DiagnosticoClinico("", ""), ""))
                }
                view_pager.clipToPadding = false
                view_pager.setPadding(40, 0, 40, 0)
                view_pager.pageMargin = 20
                view_pager.adapter =
                    DiagnosticoPagerAdapter(childFragmentManager, userClient.diagnosticosClinico)
                activity?.btn_next?.setOnClickListener {
                    viewModel.editUser()
                }
            }
        })

        viewModel.refreshData.observe(viewLifecycleOwner, Observer {
            it?.let { userClient ->
                viewModel.updateDataInUser(userClient.apply {
                    //                    diagnosticosClinico.
                })
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) activity?.btn_next?.showLoad() else activity?.btn_next?.hideLoad()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.btn_next?.dispose()
    }

    inner class DiagnosticoPagerAdapter(
        fm: FragmentManager,
        private val fragmentsDiagnostios: List<Pair<DiagnosticoClinico, String>>
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return DiagnosticoClinicoFragment.newInstance(fragmentsDiagnostios[position])
        }

        override fun getCount() = fragmentsDiagnostios.size

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }

    }

    class DiagnosticoClinicoFragment(
        val diagnostico: Pair<DiagnosticoClinico, String>
    ) : Fragment() {
        companion object {
            fun newInstance(diagnosticos: Pair<DiagnosticoClinico, String>) =
                DiagnosticoClinicoFragment(diagnostico = diagnosticos)
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_diagnostico, container, false)
        }
    }
}
