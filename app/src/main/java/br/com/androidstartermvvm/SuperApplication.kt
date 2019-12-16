package br.com.androidstartermvvm

import android.app.Application
import android.content.Context

class SuperApplication : Application() {
    companion object{
        lateinit var context: SuperApplication
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}