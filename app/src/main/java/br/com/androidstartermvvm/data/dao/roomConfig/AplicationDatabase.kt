package br.com.androidstartermvvm.data.dao.roomConfig

import androidx.room.*
import br.com.androidstartermvvm.data.dao.ProductDao
import br.com.androidstartermvvm.data.dao.UserDao
import br.com.androidstartermvvm.data.dao.roomConfig.AplicationDatabase.Companion.VERSION
import br.com.androidstartermvvm.data.entities.remote.response.AuthenticationResponse
import br.com.androidstartermvvm.data.entities.remote.response.Product

@Database(
    entities = [
        AuthenticationResponse::class,
        Product::class
    ],
    version = VERSION
)
@TypeConverters(
    DatabaseTypeConverters::class
)
abstract class AplicationDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {

        const val NAME = "database.db"

        const val VERSION = 2

    }

}