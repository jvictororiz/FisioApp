package br.com.fisioapp.data.entities.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

data class TreinoResponse(
    val name:String
)

data class CidResponse(
    val code:String,
    val name:String
)