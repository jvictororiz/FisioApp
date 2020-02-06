package br.com.fisioapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.Objetivo
import br.com.fisioapp.data.entities.remote.response.Sessao
import br.com.fisioapp.data.entities.remote.response.User
import br.com.fisioapp.ui.sheetDialog.SessionsBottomDialog
import br.com.fisioapp.viewModel.RegisterTreinoClientViewModel
import kotlinx.android.synthetic.main.activity_admin_sessions_by_user.*
import kotlinx.android.synthetic.main.activity_admin_sessions_by_user.view_pager
import kotlinx.android.synthetic.main.item_register_sessao.*

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

        treinoViewModel.findObjetive()
        configViews()
        setup()


    }

    private fun setup() {


        treinoViewModel.userSelected.observe(this, Observer {
            tv_apresentation_user.text = getString(R.string.register_session, it.name)
        })

        treinoViewModel.successObjeties.observe(this, Observer {

            setupChart(it[0])
            view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    setupChart(it[position])
                    gradient_chart.refresh()

                }
            })

            val listFragments: ArrayList<ObjetivoPageFragment> = ArrayList()
            it.forEach { objetivo ->
                listFragments.add(ObjetivoPageFragment.newInstance(
                    objetivo = objetivo,
                    actionSaveEdition = {
                        Toast.makeText(this, "Salvar aqui", Toast.LENGTH_SHORT).show()

                    }, actionSeeSessions = { sessoes ->
                        SessionsBottomDialog(this, sessoes).show()
                    })
                )
            }
            refreshList(listFragments)
        })

        treinoViewModel.emptyObjetivo.observe(this, Observer { })

        treinoViewModel.error.observe(this, Observer {

        })

    }

    private fun setupChart(objetive: Objetivo) {
        if (objetive.sessao?.size ?: 0 > 2) {
            val notes: Array<Float> = Array(objetive.sessao?.size ?: 0) {
                0F
            }
            objetive.sessao?.forEachIndexed { index, sessao ->
                notes[index] = (5 - sessao.nota).toFloat() * 20f
            }
            gradient_chart.chartValues = notes
        } else {
            gradient_chart.visibility = View.INVISIBLE
        }

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

    class ObjetivoPageFragment(var objetivo: Objetivo, var actionSaveEdition: (objetive: Objetivo) -> Unit, var actionSeeSessions: (sessions: List<Sessao>) -> Unit) : Fragment() {
        private lateinit var clickEdit: () -> Unit

        companion object {
            fun newInstance(objetivo: Objetivo, actionSaveEdition: (objetive: Objetivo) -> Unit, actionSeeSessions: (sessions: List<Sessao>) -> Unit) =
                ObjetivoPageFragment(objetivo = objetivo, actionSaveEdition = actionSaveEdition, actionSeeSessions = actionSeeSessions)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.item_register_sessao, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            tv_objective.text = objetivo.conduta
            tv_objective.text = objetivo.description
            tv_objective.isEnabled = false

            clickEdit = {
                tv_edit.text = getString(R.string.save)
                edt_conduta.isEnabled = true
                edt_conduta.isFocusableInTouchMode = true
                tv_edit.setOnClickListener {
                    tv_edit.text = getString(R.string.edit_conduta)
                    edt_conduta.isFocusableInTouchMode = false
                    edt_conduta.isEnabled = false
                    objetivo.conduta = edt_conduta.text.toString()
                    actionSaveEdition.invoke(objetivo)
                    tv_edit.setOnClickListener { clickEdit.invoke() }
                }
            }
            tv_edit.setOnClickListener {
                clickEdit.invoke()
            }

            tv_see_sessions.setOnClickListener { objetivo.sessao?.let { it1 -> actionSeeSessions.invoke(it1) } }
        }
    }
}