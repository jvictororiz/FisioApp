package br.com.bb.oewallet.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.androidstartermvvm.R

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
