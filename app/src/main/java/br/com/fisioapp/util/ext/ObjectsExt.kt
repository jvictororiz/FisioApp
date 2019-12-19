package br.com.fisioapp.util.ext

import android.util.Base64
import br.com.fisioapp.data.entities.remote.response.LoginResponse
import br.com.fisioapp.data.entities.remote.response.StatusUser
import br.com.fisioapp.data.entities.remote.response.TokenObject
import com.google.gson.Gson
import java.math.BigDecimal
import java.nio.charset.Charset
import java.text.NumberFormat
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
        val decode = Base64.decode(token?.split(".")?.get(1), Base64.DEFAULT)
            .toString(Charset.defaultCharset())
        val status = Gson().fromJson(decode, TokenObject::class.java).status
        return StatusUser.fromCode(status)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        null
    }
}

fun LoginResponse.name(): String {
    return try {
        val decode = Base64.decode(token?.split(".")?.get(1), Base64.DEFAULT)
            .toString(Charset.defaultCharset())
        Gson().fromJson(decode, TokenObject::class.java).name
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        ""
    }
}