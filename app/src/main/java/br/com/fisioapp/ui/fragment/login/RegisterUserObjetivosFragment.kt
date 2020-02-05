package br.com.fisioapp.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.*
import br.com.fisioapp.ui.base.BaseLoginFragment
import br.com.fisioapp.util.ext.hideLoad
import br.com.fisioapp.util.ext.showLoad
import br.com.fisioapp.viewModel.RegisterClientViewModel
import kotlinx.android.synthetic.main.activity_register_client.*
import kotlinx.android.synthetic.main.item_objetivo.*
import kotlinx.android.synthetic.main.user_register_objetivo_fragment.*
import java.util.*
import kotlin.collections.ArrayList


class RegisterUserObjetivosFragment(override val fragmentTag: String) : BaseLoginFragment() {
    companion object {
        const val EXTRA_USER_DATA = "EXTRA_USER_DATA"
        fun newInstance(user: User) =
            RegisterUserObjetivosFragment("RegisterUserSintomasFragment").apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_USER_DATA, user)
                }

            }


    }

    private val clientViewModel: RegisterClientViewModel by lazy {
        ViewModelProvider(requireActivity()).get(RegisterClientViewModel::class.java)
    }

    private var diagnosticoPagerAdapter: DiagnosticoPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_register_objetivo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.btn_next?.hideLoad()
        arguments?.getParcelable<UserClient>(EXTRA_USER_DATA)?.let {
            clientViewModel.prepareToEdit(it)
        }

        setupListeners()
        subscribe()
    }

    private fun setupListeners() {
        activity?.btn_back?.visibility = View.VISIBLE
        activity?.btn_next?.setOnClickListener {
            clientViewModel.editUser()
        }
        btn_new_objetive.setOnClickListener {
            diagnosticoPagerAdapter?.addItem(Objetivo("", "", Date()))
            diagnosticoPagerAdapter?.notifyDataSetChanged()
            view_pager.currentItem = (diagnosticoPagerAdapter?.count)?.minus(1) ?: 0
        }
    }

    private fun subscribe() {
        clientViewModel.oldDataUser.observe(this, Observer {
            it?.let { userClient ->
                view_pager.clipToPadding = false
                view_pager.isSaveFromParentEnabled = false;
                view_pager.setPadding(55, 0, 55, 0)
                view_pager.pageMargin = -20

                val listFragments: ArrayList<ObjetivoPageFragment> = ArrayList()
                userClient.objetivos?.forEach { objetivo ->
                    listFragments.add(ObjetivoPageFragment.newInstance(objetivo) { fragment ->
                        diagnosticoPagerAdapter?.removeItem(fragment)
                    })
                }
                refreshList(listFragments)
                activity?.btn_next?.setOnClickListener {
                    clientViewModel.editUser()
                }
            }
        })


        clientViewModel.refreshData.observe(this, Observer {
            it?.let { userClient ->
                clientViewModel.updateDataInUser(userClient.apply {
                    diagnosticosClinico.clear()
                    diagnosticoPagerAdapter?.fragmentsDiagnostios?.forEach { _ ->
                        //                        val cidCode = tv_code_cid.text.toString()
//                        val cid = edt_cid.text.toString()
//                        diagnosticosClinico.add(Pair(DiagnosticoClinico(cidCode, cid), edt_fisio.text.toString()))
                    }
                })
            }
        })

        clientViewModel.loading.observe(this, Observer {
            if (it) activity?.btn_next?.showLoad() else activity?.btn_next?.hideLoad()
        })
    }

    private fun refreshList(listFragments: ArrayList<ObjetivoPageFragment>) {
        diagnosticoPagerAdapter = DiagnosticoPagerAdapter(childFragmentManager, listFragments)
        view_pager.adapter = diagnosticoPagerAdapter
        view_pager.currentItem = diagnosticoPagerAdapter?.count?.minus(1) ?: 0
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.btn_next?.dispose()
    }

    inner class DiagnosticoPagerAdapter(fm: FragmentManager, var fragmentsDiagnostios: ArrayList<ObjetivoPageFragment>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        fun addItem(item: Objetivo) {
            fragmentsDiagnostios.add(ObjetivoPageFragment.newInstance(item) {
                removeItem(it)
            })
            notifyDataSetChanged()
        }

        fun removeItem(fragment: ObjetivoPageFragment) {
            fragmentsDiagnostios.removeAt(fragmentsDiagnostios.indexOf(fragment))
            refreshList(fragmentsDiagnostios)
        }

        override fun getItem(position: Int): Fragment {
            return fragmentsDiagnostios[position]
        }

        override fun getCount() = fragmentsDiagnostios.size

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }

    }

    class ObjetivoPageFragment(var objetivo: Objetivo, var actionClose: (diagnostico: ObjetivoPageFragment) -> Unit) : Fragment() {
        companion object {
            fun newInstance(objetivo: Objetivo, actionClose: (diagnostico: ObjetivoPageFragment) -> Unit) =
                ObjetivoPageFragment(objetivo = objetivo, actionClose = actionClose)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.item_objetivo, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            edt_conduta.setText(objetivo.conduta)
            edt_objetive.setText(objetivo.description)
            ic_close.setOnClickListener {
                actionClose.invoke(this)
            }
        }
    }

}
