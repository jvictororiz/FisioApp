package br.com.androidstartermvvm.ui.activity

import android.os.Bundle
import br.com.androidstartermvvm.R
import br.com.androidstartermvvm.ui.fragment.HomeFragment
import br.com.bb.oewallet.ui.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        replace(HomeFragment.newInstance())
    }

}
