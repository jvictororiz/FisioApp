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
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.DiagnosticoClinico
import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.data.entities.remote.response.UserClient
import br.com.fisioapp.ui.base.BaseLoginFragment
import br.com.fisioapp.util.ext.hideLoad
import br.com.fisioapp.util.ext.showLoad
import br.com.fisioapp.viewModel.RegisterClientViewModel
import kotlinx.android.synthetic.main.activity_register_client.*
import kotlinx.android.synthetic.main.fragment_diagnostico.*
import kotlinx.android.synthetic.main.user_register_sintomas_fragment.*


class RegisterUserSintomasFragment(override val fragmentTag: String) : BaseLoginFragment() {
    companion object {
        const val EXTRA_USER_DATA = "EXTRA_USER_DATA"
        fun newInstance(user: User) =
            RegisterUserSintomasFragment("RegisterUserSintomasFragment").apply {
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
        return inflater.inflate(R.layout.user_register_sintomas_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        btn_new.setOnClickListener {
            diagnosticoPagerAdapter?.addItem(Pair(DiagnosticoClinico("", ""), ""))
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

                val listFragments: ArrayList<DiagnosticoClinicoPageFragment> = ArrayList()
                userClient.diagnosticosClinico.forEach { diagnostico ->
                    listFragments.add(DiagnosticoClinicoPageFragment.newInstance(diagnostico, 0) { fragment ->
                        diagnosticoPagerAdapter?.removeItem(fragment)
                    })
                }
                refreshList(listFragments)
                activity?.btn_next?.setOnClickListener {
                    clientViewModel.editUser()
                }

                view_pager.addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                        if (diagnosticoPagerAdapter?.count?.minus(1) ?: 0 == position) {
                            btn_new.animate().alpha(1F).duration = 150L
                        } else {
                            btn_new.animate().alpha(0F).duration = 150L
                        }
                    }

                    override fun onPageSelected(position: Int) {}
                    override fun onPageScrollStateChanged(state: Int) {}
                })
            }
        })


        clientViewModel.refreshData.observe(this, Observer {
            it?.let { userClient ->
                clientViewModel.updateDataInUser(userClient.apply {
                    diagnosticosClinico.clear()
                    diagnosticoPagerAdapter?.fragmentsDiagnostios?.forEach { _ ->
                        val cidCode = tv_code_cid.text.toString()
                        val cid = edt_cid.text.toString()
                        diagnosticosClinico.add(Pair(DiagnosticoClinico(cidCode, cid), edt_fisio.text.toString()))
                    }
                })
            }
        })

        clientViewModel.loading.observe(this, Observer {
            if (it) activity?.btn_next?.showLoad() else activity?.btn_next?.hideLoad()
        })

        clientViewModel.nextSuccess.observe(this, Observer {
            activity?.btn_next?.hideLoad()
            replace(RegisterUserObjetivosFragment.newInstance(it))
        })
    }

    private fun refreshList(listFragments: ArrayList<DiagnosticoClinicoPageFragment>) {
        diagnosticoPagerAdapter = DiagnosticoPagerAdapter(childFragmentManager, listFragments)
        view_pager.adapter = diagnosticoPagerAdapter
        view_pager.currentItem = diagnosticoPagerAdapter?.count?.minus(1) ?: 0
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.btn_next?.dispose()
    }

    inner class DiagnosticoPagerAdapter(fm: FragmentManager, var fragmentsDiagnostios: ArrayList<DiagnosticoClinicoPageFragment>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        fun addItem(item: Pair<DiagnosticoClinico, String>) {
            fragmentsDiagnostios.add(DiagnosticoClinicoPageFragment.newInstance(item, 0) {
                removeItem(it)
            })
            notifyDataSetChanged()
        }

        fun removeItem(fragment: DiagnosticoClinicoPageFragment) {
            fragmentsDiagnostios.removeAt(fragmentsDiagnostios.indexOf(fragment))
            refreshList(fragmentsDiagnostios)
        }

        override fun getItem(position: Int): Fragment {
            return  fragmentsDiagnostios[position]
        }

        override fun getCount() = fragmentsDiagnostios.size

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }

    }

    class DiagnosticoClinicoPageFragment(var diagnostico: Pair<DiagnosticoClinico, String>, val position: Int, var actionClose: (diagnostico: DiagnosticoClinicoPageFragment) -> Unit) : Fragment() {
        companion object {
            fun newInstance(diagnosticos: Pair<DiagnosticoClinico, String>, position: Int, actionClose: (diagnostico: DiagnosticoClinicoPageFragment) -> Unit) =
                DiagnosticoClinicoPageFragment(diagnostico = diagnosticos, position = position, actionClose = actionClose)
        }

        private val clientViewModel: RegisterClientViewModel by lazy {
            ViewModelProvider(requireActivity()).get(RegisterClientViewModel::class.java)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_diagnostico, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            edt_cid.setText(diagnostico.first.name)
            edt_fisio.setText(diagnostico.second)

            btn_search.setOnClickListener {
                clientViewModel.findCid(edt_cid.text.toString())
            }
            ic_close.setOnClickListener {
                actionClose.invoke(this)
            }
            setup()
        }

        private fun setup() {
            clientViewModel.cidSuccess.observe(this, Observer {
                diagnostico.first.code = it.code
                diagnostico.first.name = it.name
                tv_code_cid.text = it.code
                tv_code_cid.visibility = View.VISIBLE
                edt_cid.setText(it.name)
                edt_cid.error = null
            })

            clientViewModel.cidLoad.observe(this, Observer {
                btn_search.isEnabled = !it
            })


            clientViewModel.cidError.observe(this, Observer {
                btn_search.isEnabled = true
                edt_cid.error = it
            })
        }
    }

}
