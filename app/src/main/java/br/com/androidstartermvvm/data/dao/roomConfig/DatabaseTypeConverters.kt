package br.com.androidstartermvvm.data.dao.roomConfig

import androidx.room.TypeConverter
import java.util.*

class DatabaseTypeConverters {

    @TypeConverter
    fun dateToLong(date: Date?) = date?.time

    @TypeConverter
    fun longToDate(string: Long) = Date(string)


}