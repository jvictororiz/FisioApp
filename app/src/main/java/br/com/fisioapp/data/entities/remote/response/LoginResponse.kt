package br.com.fisioapp.data.entities.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "UserTable")
data class LoginResponse(
    @ColumnInfo var token: String?,
    @PrimaryKey var username: String
)

data class TokenObject(
    val status:Int,
    val  username: String
)

enum class StatusUser(val code: Int) {
    ADMIN(0),
    CLIENT(1);

    companion object{
        fun fromCode(number: Int): StatusUser {
           return  values().associateBy(StatusUser::ordinal).getValue(number)
        }
    }

}