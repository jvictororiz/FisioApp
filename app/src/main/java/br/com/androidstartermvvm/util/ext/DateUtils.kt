package br.com.androidstartermvvm.util.ext


import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/** Locale Brasil */
val LOCALE_PT_BR = Locale("pt", "BR")

fun String.toDate(pattern: String): Date? {
    val format = SimpleDateFormat(pattern, LOCALE_PT_BR)
    try {
        return format.parse(this)
    } catch (e: ParseException) {
        return null
    }
}


fun Date.toString(pattern: String): String? {
    return SimpleDateFormat(pattern, LOCALE_PT_BR).format(this)
}

