package com.matthew.carvalhodagenais.gamecubecollector.helpers

import android.util.Log
import java.lang.Exception
import java.util.*

class DateHelper {
    companion object {
        fun createCalendar(year: Int, month: Int, day: Int) {

        }

        fun createCalendar(dateString: String) {

        }

        fun createDate(cal: Calendar) {

        }

        /**
         * Creates a Date from a date string in format DD/MM/YEAR
         *
         * @param ddmmyearString
         * @return Date?
         */
        fun createDate(ddmmyearString: String): Date? {
            var date: Date?
            try {
                val array = ddmmyearString.split('/').toTypedArray()
                val cal = Calendar.getInstance()
                cal.set(array[0].toInt(), array[1].toInt(), array[2].toInt())
                date = cal.time
            } catch(e: Exception) {
                Log.e("EXCEPTION", "Could not convert $ddmmyearString to Date.\n${e.message.toString()}")
                date = null
            }
            return date
        }

        /**
         * Creates a formatted date string from a Date
         *
         * @param date Date
         * @return String
         */
        fun createDateString(date: Date?): String? {
            val str: String?
            if (date != null) {
                val cal = Calendar.getInstance()
                cal.time = Date(date.time)
                str = "${cal.get(Calendar.DAY_OF_MONTH)}/" +
                        "${cal.get(Calendar.MONTH)}/" +
                        "${cal.get(Calendar.YEAR)}"
            } else {
                str = null
            }
            return str
        }
    }
}