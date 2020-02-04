package br.com.bb.oewallet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import br.com.fisioapp.R


abstract class BaseFragment : Fragment() {
    abstract val fragmentTag: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun replace(fragment: BaseFragment) {
        val frag = activity?.supportFragmentManager?.findFragmentByTag(fragment.tag) ?: fragment

        activity?.supportFragmentManager?.beginTransaction().also {
            it?.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        }
            ?.replace(R.id.container, frag, fragment.fragmentTag)
            ?.commit()
    }


    fun replaceChild(fragment: BaseFragment) {
        val manager: FragmentManager = childFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        transaction.replace(R.id.container, fragment, fragment.fragmentTag)
        transaction.commit()
    }


}