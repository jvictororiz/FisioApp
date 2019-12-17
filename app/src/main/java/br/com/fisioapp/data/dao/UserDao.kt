package br.com.fisioapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import br.com.fisioapp.data.entities.remote.response.LoginResponse

@Dao
interface UserDao {
    @Query("SELECT * from UserTable")
    fun find(): LoginResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherData: LoginResponse)

    @Query("DELETE from UserTable")
    fun delete()
}
