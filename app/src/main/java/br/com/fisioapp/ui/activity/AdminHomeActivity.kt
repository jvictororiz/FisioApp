package br.com.fisioapp.ui.activity

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

class AdminHomeActivity : BaseActivity() {

    private val treinoAdapter = TreinoAdapter()
    private val clientAdapter = ClientAdapter()

    private val viewModel: AdminHomeViewModel by lazy {
        ViewModelProvider(this).get(AdminHomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        setUpToolbar(getString(R.string.title_home_admin))
        subscribe()
        setupListenres()
    }

    private fun setupListenres() {
        btn_refresh_clients.setOnClickListener { viewModel.findClients() }
        btn_refresh_treinos.setOnClickListener { viewModel.findTreinos() }

        btn_add_client.setOnClickListener {

        }
        btn_add_treino.setOnClickListener {

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
        viewModel.nameClient.observe(this, Observer {
            tv_title.text = getString(R.string.apresentation)
        })

        viewModel.loadClients.observe(this, Observer {
            if (it) {
                empty_clients.visibility = View.GONE
                load_clients.visibility = View.VISIBLE
            } else {
                load_clients.visibility = View.GONE
            }

        })

        viewModel.loadTreinos.observe(this, Observer {
            if (it) {
                empty_treinos.visibility = View.GONE
                load_treinos.visibility = View.VISIBLE
            } else {
                load_treinos.visibility = View.GONE
            }
        })

        viewModel.clients.observe(this, Observer {
            clientAdapter.submitList(it.toMutableList())

        })
        viewModel.treinos.observe(this, Observer {
            treinoAdapter.submitList(it.toMutableList())
        })

        viewModel.totalItensTreinos.observe(this, Observer {
            label_treinos_total.text = getString(R.string.total_itens, it.toString())
        })

        viewModel.totalItensClients.observe(this, Observer {
            label_clients_total.text = getString(R.string.total_itens, it.toString())
        })

        viewModel.errorClients.observe(this, Observer {
            empty_clients.visibility = View.VISIBLE
            tv_error_client.text = it
        })
        viewModel.errorTreinos.observe(this, Observer {
            empty_treinos.visibility = View.VISIBLE
            tv_error_treino.text = it
        })
    }
}
