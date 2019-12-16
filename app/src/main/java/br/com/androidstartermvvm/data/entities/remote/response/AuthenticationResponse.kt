package br.com.androidstartermvvm.data.entities.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "UserTable")
data class AuthenticationResponse(
    @ColumnInfo var token: String?,
    @PrimaryKey var username: String
)