package br.com.androidstartermvvm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import br.com.androidstartermvvm.data.entities.remote.response.Product
import br.com.androidstartermvvm.data.entities.remote.response.User

@Dao
abstract class ProductDao {
    @Query("SELECT * from ProductTable")
    abstract fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(weatherData: Product)

    @Query("DELETE from ProductTable")
    abstract fun deleteAll()
}
