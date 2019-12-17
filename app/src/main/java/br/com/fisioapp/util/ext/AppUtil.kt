package br.com.fisioapp.util.ext

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
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