package br.com.androidstartermvvm.ui.base

import androidx.appcompat.app.AppCompatActivity
import br.com.androidstartermvvm.R
import br.com.bb.oewallet.ui.BaseFragment

abstract class BaseActivity : AppCompatActivity() {
    fun replace(fragment: BaseFragment, addToBackstack: Boolean? = false) {
        val fg = supportFragmentManager.findFragmentByTag(fragment.fragmentTag) ?: fragment
        supportFragmentManager.beginTransaction()
            .also {
                if (addToBackstack == true) {
                    it.addToBackStack(fragment.fragmentTag)
                }

            }
            .replace(R.id.container, fg, fragment.fragmentTag)
            .commit()
    }

}
