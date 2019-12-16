package br.com.androidstartermvvm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import br.com.androidstartermvvm.data.entities.remote.response.AuthenticationResponse
import br.com.androidstartermvvm.data.entities.remote.response.Product
import br.com.androidstartermvvm.data.entities.remote.response.User

@Dao
abstract class UserDao {
    @Query("SELECT * from UserTable")
    abstract fun find(): AuthenticationResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(weatherData: AuthenticationResponse)

    @Query("DELETE from UserTable")
    abstract fun delete()
}
