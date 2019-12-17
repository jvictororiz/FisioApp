package br.com.fisioapp.repository

import androidx.room.Room
import br.com.fisioapp.SuperApplication
import br.com.fisioapp.data.dao.roomConfig.AplicationDatabase
import br.com.fisioapp.util.AppDispatchers

abstract class BaseRepository {
    protected val dispatchers = lazy {  AppDispatchers()}

}