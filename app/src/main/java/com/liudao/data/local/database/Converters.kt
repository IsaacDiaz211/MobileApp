package com.liudao.data.local.database

import androidx.room.TypeConverter
import java.time.LocalDate

object Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? = date?.toEpochDay()

    @TypeConverter
    fun toLocalDate(epochDay: Long?): LocalDate? = epochDay?.let { LocalDate.ofEpochDay(it) }
}