package br.com.bb.oewallet.extension

import java.math.BigDecimal
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
    }catch (ex:Exception){
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
    }catch (ex:java.lang.Exception){
        this?.replace("R$", "")?.replace(".", "")?.replace(",", ".")?.toDouble()?.toBigDecimal()
    }
}