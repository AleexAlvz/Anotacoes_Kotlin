package br.com.alexalves.anotacoes_kotlin.database

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {

    @TypeConverter
    fun toCalendar(millis: Long): Calendar{
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return calendar
    }

    @TypeConverter
    fun toMillis(calendar: Calendar): Long{
        return calendar.timeInMillis
    }

}
