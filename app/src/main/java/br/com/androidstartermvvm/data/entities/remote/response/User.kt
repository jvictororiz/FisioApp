package br.com.androidstartermvvm.data.entities.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

data class User(
    var id: Long?,
     var name: String
)