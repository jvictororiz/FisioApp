package br.com.androidstartermvvm.data.entities.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "ProductTable")
data class Product(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "humidity") var name: String
)