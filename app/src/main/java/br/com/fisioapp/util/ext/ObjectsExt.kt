package br.com.fisioapp.util.ext

import android.text.format.DateUtils
import android.util.Base64
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.entities.remote.response.StatusUser
import br.com.fisioapp.data.entities.remote.response.TokenObject
import br.com.fisioapp.data.entities.remote.response.UserClient
import com.google.gson.Gson
import java.math.BigDecimal
import java.nio.charset.Charset
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/* Extensões para os primitivos */

private val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

private const val BLANK = ""

fun Double?.toMoney(): String? = this?.let(numberFormat::format)

fun BigDecimal?.toMoney(displaySymbol: Boolean = true): String? {
    try {
        val formatted = numberFormat.format(this)
        if (!displaySymbol) {
            // Retira o símbolo
            return formatted.replace(numberFormat.currency.symbol, BLANK).trim()
        }
        return formatted
    } catch (ex: Exception) {
        ex.printStackTrace()
        return ""
    }
}

fun String?.removeSpaces() = this?.replace(" ", "")

fun String?.containsIgnoreCase(text: String): Boolean? {
    return this?.toLowerCase()?.contains(text.toLowerCase())
}

fun String?.toBigDECIMAL(): BigDecimal? {
    return try {
        return this?.toBigDecimal()
    } catch (ex: java.lang.Exception) {
        this?.replace("R$", "")?.replace(".", "")?.replace(",", ".")?.toDouble()?.toBigDecimal()
    }
}

fun LoginResponse.status(): StatusUser? {
    return try {
        val decode = Base64.decode(token?.split(".")?.get(1), Base64.DEFAULT).toString(Charset.defaultCharset())
        return (Gson().fromJson(decode, TokenObject::class.java).status)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        null
    }
}


fun UserClient.stepOneValid(): Boolean {
    return this.name.isNotEmpty() && this.job.isNotEmpty() && this.phoneNumber.isNotEmpty() && this.username.isNotEmpty()
}


fun UserClient.stepThreeValid(): Boolean {
    return this.fichaTecnica != null
}

fun LoginResponse.name(): String {
    return try {
        val decode = Base64.decode(token?.split(".")?.get(1), Base64.DEFAULT).toString(Charset.defaultCharset())
        (Gson().fromJson(decode, TokenObject::class.java).name)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        ""
    }
}

fun LoginResponse.expiredAuthorization(): Boolean {
    return try {
        val decode = Base64.decode(token?.split(".")?.get(1), Base64.DEFAULT).toString(Charset.defaultCharset())
        val token = Gson().fromJson(decode, TokenObject::class.java)
        System.currentTimeMillis() > Date(token.exp.toLong()).time
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        true
    }
}

fun Int.isPar(): Boolean {
    return this % 2 == 0
}
