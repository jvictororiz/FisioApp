package br.com.fisioapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.Objetivo
import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.ui.fragment.login.RegisterUserObjetivosFragment
import br.com.fisioapp.viewModel.RegisterTreinoClientViewModel
import kotlinx.android.synthetic.main.activity_admin_sessions_by_user.*
import kotlinx.android.synthetic.main.activity_admin_sessions_by_user.view_pager
import kotlinx.android.synthetic.main.item_sessao.*
import kotlinx.android.synthetic.main.user_register_objetivo_fragment.*

class AdminSessionsByUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

    private val treinoViewModel: RegisterTreinoClientViewModel by lazy {
        ViewModelProvider(this).get(RegisterTreinoClientViewModel::class.java)
    }
    private var diagnosticoPagerAdapter: ObjetivePagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_sessions_by_user)

        intent?.extras?.getParcelable<User>(EXTRA_USER)?.let {
            treinoViewModel.selectUser(it)
        }
        gradient_chart.chartValues = arrayOf(
            10f, 30f, 25f, 32f, 13f, 5f, 18f, 36f, 20f, 30f, 28f, 27f, 29f
        )

        treinoViewModel.findObjetive()
        configViews()
        setup()


    }

    private fun setup() {

        treinoViewModel.userSelected.observe(this, Observer {

        })
        treinoViewModel.successObjeties.observe(this, Observer {
            view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    val objetive = it[position]
                    val notes: Array<Float> = Array(objetive.sessao?.size?:0){
                        0F
                    }
                    objetive.sessao?.forEachIndexed { index, sessao ->
                        notes[index] = sessao.nota.toFloat()*20f
                    }
                    gradient_chart.chartValues = notes
                    gradient_chart.refresh()

                }
            })

            val listFragments: ArrayList<ObjetivoPageFragment> = ArrayList()
            it.forEach { objetivo ->
                listFragments.add(ObjetivoPageFragment.newInstance(objetivo) {
                    treinoViewModel.saveObjetive()
                })
            }
            refreshList(listFragments)
        })

        treinoViewModel.emptyObjetivo.observe(this, Observer { })

        treinoViewModel.error.observe(this, Observer {

        })

    }

    private fun refreshList(listFragments: ArrayList<ObjetivoPageFragment>) {
        diagnosticoPagerAdapter = ObjetivePagerAdapter(supportFragmentManager, listFragments)
        view_pager.adapter = diagnosticoPagerAdapter
    }

    private fun configViews() {
        view_pager.setPadding(55, 0, 55, 0)
        view_pager.pageMargin = -20
        view_pager.clipToPadding = false
        view_pager.isSaveFromParentEnabled = false

        view_pager.addOnAdapterChangeListener { viewPager, oldAdapter, newAdapter ->

        }

    }

    inner class ObjetivePagerAdapter(fm: FragmentManager, var fragmentsDiagnostios: ArrayList<ObjetivoPageFragment>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return fragmentsDiagnostios[position]
        }

        override fun getCount() = fragmentsDiagnostios.size

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }

    }

    class ObjetivoPageFragment(var objetivo: Objetivo, var actionSaveEdition: (objetive: Objetivo) -> Unit) : Fragment() {
        private lateinit var clickEdit: () -> Unit

        companion object {
            fun newInstance(objetivo: Objetivo, actionSaveEdition: (objetive: Objetivo) -> Unit) =
                ObjetivoPageFragment(objetivo = objetivo, actionSaveEdition = actionSaveEdition)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.item_sessao, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            tv_objective.text = objetivo.conduta
            tv_objective.text = objetivo.description
            tv_objective.isEnabled = false

            clickEdit = {
                tv_edit.text = getString(R.string.save)
                edt_conduta.isEnabled = true
                edt_conduta.isFocusable = true
                tv_edit.setOnClickListener {
                    tv_edit.text = getString(R.string.edit_conduta)
                    edt_conduta.isFocusable = false
                    edt_conduta.isEnabled = false
                    objetivo.conduta = edt_conduta.text.toString()
                    actionSaveEdition.invoke(objetivo)
                    clickEdit.invoke()
                }
            }
            tv_edit.setOnClickListener {
                clickEdit.invoke()
            }
        }
    }
}
