package br.com.bb.oewallet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.androidstartermvvm.R

abstract class BaseFragment : Fragment() {
    abstract val fragmentTag: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun replace(fragment: BaseFragment) {
        val frag = activity?.supportFragmentManager?.findFragmentByTag(fragment.tag) ?: fragment

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, frag, fragment.fragmentTag)
            ?.commit()
    }

}