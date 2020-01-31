package br.com.fisioapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fisioapp.R
import br.com.fisioapp.ui.adapter.ClientAdapter
import br.com.fisioapp.ui.adapter.TreinoAdapter
import br.com.fisioapp.ui.base.BaseActivity
import br.com.fisioapp.viewModel.AdminHomeViewModel
import kotlinx.android.synthetic.main.activity_admin_home.*
import kotlinx.android.synthetic.main.toolbar_home.*


class AdminHomeActivity : BaseActivity() {
    private val clientAdapter by lazy { ClientAdapter() }

    private val viewModel: AdminHomeViewModel by lazy {
        ViewModelProvider(this).get(AdminHomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        setUpToolbar(getString(R.string.title_home_admin)) {
            viewModel.findDataUser()
        }
        subscribe()
        setupListenres()
    }

    private fun setupListenres() {
        rv_clients.adapter = clientAdapter
//        btn_refresh_clients.setOnClickListener { viewModel.findClients() }

        btn_add_client.setOnClickListener {
            startActivityAnim(Intent(this, RegisterClientActivity::class.java))
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return true
            }
        })
    }

    private fun subscribe() {
        viewModel.datasUser.observe(this, Observer {
            LoginActivity.startModeEdit(this, it)
        })
        viewModel.nameClient.observe(this, Observer {
            toolbar_subtitulo.text = it

            tv_title.text = getString(R.string.apresentation, it)
        })

        viewModel.loadClients.observe(this, Observer {
            if (it) {
//                empty_clients.visibility = View.GONE
                load_clients.visibility = View.VISIBLE
            } else {
                load_clients.visibility = View.GONE
            }

        })


        viewModel.clients.observe(this, Observer {
            clientAdapter.submitList(it.toMutableList())

        })
        viewModel.totalItensClients.observe(this, Observer {
            label_clients_total.text = getString(R.string.total_itens, it.toString())
        })

        viewModel.errorClients.observe(this, Observer {
//            empty_clients.visibility = View.VISIBLE
//            tv_error_client.text = it
        })

        viewModel.toLogin.observe(this, Observer {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityClearOthers(intent)
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.exit()
    }
}
