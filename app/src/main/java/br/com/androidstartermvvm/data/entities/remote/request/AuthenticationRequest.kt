package br.com.androidstartermvvm.data.entities.remote.request

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

data class AuthenticationRequest(
    var username: String?,
     var password: String
)