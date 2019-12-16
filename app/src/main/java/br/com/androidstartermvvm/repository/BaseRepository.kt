package br.com.androidstartermvvm.repository

import androidx.room.Room
import br.com.androidstartermvvm.SuperApplication
import br.com.androidstartermvvm.data.dao.roomConfig.AplicationDatabase
import br.com.androidstartermvvm.util.AppDispatchers

abstract class BaseRepository {
    protected val database = lazy {
        Room.databaseBuilder(SuperApplication.context, AplicationDatabase::class.java, AplicationDatabase.NAME).build()
    }
    protected val dispatchers = lazy {  AppDispatchers()}

}