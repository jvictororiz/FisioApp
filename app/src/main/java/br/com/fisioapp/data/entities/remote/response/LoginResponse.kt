package br.com.fisioapp.data.entities.remote.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.google.gson.annotations.JsonAdapter
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "UserTable")
data class LoginResponse(
    @ColumnInfo var token: String?,
    @PrimaryKey var username: String
)

data class TokenObject(
    val exp: Int,
    val iat: Int,
    val name: String,
    val status: StatusUser
)



@Parcelize
enum class StatusUser :Parcelable {
    ADMIN,
    CLIENT;


    companion object {
        fun fromCode(number: Int): StatusUser {
            return values().associateBy(StatusUser::ordinal).getValue(number)
        }
    }

}