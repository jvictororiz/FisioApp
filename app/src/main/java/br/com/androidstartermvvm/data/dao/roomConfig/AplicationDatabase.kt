package br.com.androidstartermvvm.data.dao.roomConfig

import androidx.room.*
import br.com.androidstartermvvm.data.dao.ProductDao
import br.com.androidstartermvvm.data.dao.roomConfig.AplicationDatabase.Companion.VERSION
import br.com.androidstartermvvm.data.entities.remote.response.Product

@Database(
    entities = [
        Product::class
    ],
    version = VERSION
)
@TypeConverters(
    DatabaseTypeConverters::class
)
abstract class AplicationDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {

        const val NAME = "database.db"

        const val VERSION = 1

    }

}