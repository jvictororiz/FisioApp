package br.com.androidstartermvvm.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.androidstartermvvm.R
import br.com.androidstartermvvm.viewModel.HomeViewModel
import br.com.bb.oewallet.ui.BaseFragment

class HomeFragment(override val fragmentTag: String) : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment("HomeFragment")
    }

    private val viewModel: HomeViewModel = HomeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.listResposta()
        viewModel.resposta.observe(this, Observer {
        })

        viewModel.respostaDetalhe.observe(this, Observer {

        })

    }

}
