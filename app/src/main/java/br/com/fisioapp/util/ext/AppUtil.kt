package br.com.fisioapp.util.ext

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fisioapp.SuperApplication


class AppUtil {
    companion object {
        fun isNetworkConnected(): Boolean {
            val cm =
                SuperApplication.context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
            return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
        }
    }
}