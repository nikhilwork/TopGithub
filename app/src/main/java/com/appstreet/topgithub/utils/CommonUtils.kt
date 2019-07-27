package com.appstreet.topgithub.utils

import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object {

        val DISPLAY: SimpleDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        val DISPLAY_SHORT: SimpleDateFormat = SimpleDateFormat("EEE", Locale.getDefault())

        fun getCurrentDay(): String {
            try {
                val date = Date()
                return DISPLAY_SHORT.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun epocSecondsToDisplayDateTimeString(epocSeconds: Long): String {
            val d = epocSecondsToDate(epocSeconds)
            return dateToDayDateString(d, false)
        }

        fun epocSecondsToDate(epocSeconds: Long): Date {
            val c = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            c.timeInMillis = epocSeconds * 1000
            return c.time
        }

        fun dateToDayDateString(
            date: Date,
            useShortFormat: Boolean
        ): String {
            return if (useShortFormat) {
                DISPLAY_SHORT.format(date).toUpperCase()
            } else {
                DISPLAY.format(date).toUpperCase()
            }
        }

    }

}