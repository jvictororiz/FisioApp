package br.com.fisioapp.ui.fragment.login

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
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

    private val diagnosticoPagerAdapter by lazy { DiagnosticoPagerAdapter(childFragmentManager) }

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
            diagnosticoPagerAdapter.addItem(Pair(DiagnosticoClinico("", ""), ""))
            diagnosticoPagerAdapter.notifyDataSetChanged()
            view_pager.currentItem = diagnosticoPagerAdapter.count - 1
        }
    }

    private fun subscribe() {
        clientViewModel.oldDataUser.observe(this, Observer {
            it?.let { userClient ->
                view_pager.clipToPadding = false
                view_pager.setPadding(55, 0, 55, 0)
                view_pager.pageMargin = -20
                diagnosticoPagerAdapter.addList(userClient.diagnosticosClinico)
                diagnosticoPagerAdapter.notifyDataSetChanged()
                view_pager.adapter = diagnosticoPagerAdapter
                view_pager.currentItem = diagnosticoPagerAdapter.count - 1
                activity?.btn_next?.setOnClickListener {
                    clientViewModel.editUser()
                }

                view_pager.addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                        if (diagnosticoPagerAdapter.count - 1 == position) {
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
                    //                    diagnosticosClinico.
                })
            }
        })

        clientViewModel.loading.observe(this, Observer {
            if (it) activity?.btn_next?.showLoad() else activity?.btn_next?.hideLoad()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.btn_next?.dispose()
    }

    inner class DiagnosticoPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        lateinit var fragmentsDiagnostios: ArrayList<Pair<DiagnosticoClinico, String>>

        fun addItem(item: Pair<DiagnosticoClinico, String>) {
            fragmentsDiagnostios.add(item)
            notifyDataSetChanged()
        }

        fun addList(fragmentsDiagnostios: ArrayList<Pair<DiagnosticoClinico, String>>) {
            this.fragmentsDiagnostios = fragmentsDiagnostios
        }

        override fun getItem(position: Int): Fragment {
            return DiagnosticoClinicoPageFragment.newInstance(fragmentsDiagnostios?.get(position))
        }

        override fun getCount() = fragmentsDiagnostios.size

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }

    }

    class DiagnosticoClinicoPageFragment(var diagnostico: Pair<DiagnosticoClinico, String>) : Fragment() {
        companion object {
            fun newInstance(diagnosticos: Pair<DiagnosticoClinico, String>) =
                DiagnosticoClinicoPageFragment(diagnostico = diagnosticos)
        }

        private val clientViewModel: RegisterClientViewModel by lazy {
            ViewModelProvider(requireActivity()).get(RegisterClientViewModel::class.java)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_diagnostico, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            edt_cid.setText(diagnostico.first.message)
            edt_fisio.setText(diagnostico.second)

            btn_search.setOnClickListener {
                clientViewModel.findCid(edt_cid.text.toString())
            }
            setup()
        }

        private fun setup() {
            clientViewModel.cidSuccess.observe(this, Observer {
                diagnostico.first.code = it.code
                diagnostico.first.message = it.message
                tv_code_cid.text = it.code
                tv_code_cid.visibility = View.VISIBLE
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_check_white)?.toBitmap()?.let { it1 -> btn_search.doneLoadingAnimation(R.color.green, it1) }
                edt_cid.setText(it.message)
                edt_cid.error = null
            })

            clientViewModel.cidLoad.observe(this, Observer {
                if (it) btn_search.startAnimation() else btn_search.revertAnimation()
            })


            clientViewModel.cidError.observe(this, Observer {
                btn_search.revertAnimation()
                edt_cid.error = it
            })
        }
    }

}
