package br.com.fisioapp.data.entities.remote.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
open class User(
    open var username: String,
    open var name: String,
    open var birthDate: Date,
    open var phoneNumber: String,
    open var urlPhoto: String? = "",
    open var password: String

) : Parcelable

@Parcelize
data class UserAdmin(
    override var username: String,
    override var name: String,
    override var birthDate: Date,
    override var phoneNumber: String,
    override var urlPhoto: String? = "",
    var crefito: String?,
    override var password: String
) : User(username, name, birthDate, phoneNumber, urlPhoto, password)

@Parcelize
data class UserClient(
    override var username: String = "",
    override var password: String = "123",
    override var name: String,
    override var birthDate: Date,
    override var phoneNumber: String,
    override var urlPhoto: String? = "",
    var job: String,
    var diagnosticosClinico: ArrayList<Pair<DiagnosticoClinico, String>> = ArrayList(),
    var objetivos:ArrayList<Objetivo>?= null,
    var fichaTecnica: FichaTecnica? = null
) : User(username, name, birthDate, phoneNumber, urlPhoto, password)


@Parcelize
data class DiagnosticoClinico(
    var code: String?,
    var name: String) : Parcelable

@Parcelize
data class FichaTecnica(var description: String) : Parcelable

@Parcelize
data class Objetivo(
    var conduta: String,
    var description: String,
    var dateInit: Date,
    var dateFinal: Date?=null,
    var sessao: ArrayList<Sessao>? = null
) : Parcelable

@Parcelize
data class Sessao(
    var nota: Int,
    var date: Date,
    var observacao: String
) : Parcelable
