package br.com.fisioapp.data.entities.remote.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponse(
     var username: String,
     var name: String
):Parcelable