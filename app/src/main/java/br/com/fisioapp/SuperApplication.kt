package br.com.fisioapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import br.com.fisioapp.data.dao.roomConfig.AplicationDatabase

class SuperApplication : Application() {
    companion object {
        lateinit var context: SuperApplication
        var database: AplicationDatabase? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        database =
            Room.databaseBuilder(this, AplicationDatabase::class.java, AplicationDatabase.NAME)
                .allowMainThreadQueries().build()
    }
}