package br.com.fisioapp.data.dao.roomConfig

import androidx.room.*
import br.com.fisioapp.data.dao.UserDao
import br.com.fisioapp.data.dao.roomConfig.AplicationDatabase.Companion.VERSION
import br.com.fisioapp.data.entities.remote.response.LoginResponse

@Database(
    entities = [
        LoginResponse::class
    ],
    version = VERSION
)
@TypeConverters(
    DatabaseTypeConverters::class
)
abstract class AplicationDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        const val NAME = "database-db"

        const val VERSION = 3

    }

}