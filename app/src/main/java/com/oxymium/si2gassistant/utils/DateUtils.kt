package com.oxymium.si2gassistant.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateUtils {
    companion object {
        @JvmStatic
        fun convertMillisToDate(millis: Long?): String {
            val calendar = Calendar.getInstance()
            with(calendar) {
                timeInMillis = millis ?: 0L // set the calendar to the time in millis
            }

            // Get year/month/day values of said calendar afterwards
            val year = calendar.get(Calendar.YEAR)

            val day = calendar.get(Calendar.DAY_OF_MONTH).let {
                when (it) {
                    in 0..9 -> "0$it"
                    else -> "$it"
                }
            }

            val month = calendar.get(Calendar.MONTH).let {
                when (it) {
                    0 -> "jan"// first month is index 0
                    1 -> "feb"
                    2 -> "mar"
                    3 -> "apr"
                    4 -> "may"
                    5 -> "jun"
                    6 -> "jul"
                    7 -> "aug"
                    8 -> "sep"
                    9 -> "oct"
                    10 -> "nov"
                    11 -> "dec"
                    else -> "err"
                }
            }

            return "$day $month $year"

        }

        @JvmStatic
        fun convertMillisToTime(millis: Long?, mode: TimeMode): String {
            val calendar =  Calendar.getInstance()
            with(calendar) {
                timeInMillis = millis ?: 0L
            }

            // Get time
            val dateFormat: SimpleDateFormat = when (mode) {
                TimeMode.MINUTES -> SimpleDateFormat("HH:mm", Locale.getDefault())
                TimeMode.SECONDS -> SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            }

            return dateFormat.format(calendar.time)
        }
    }
}

enum class TimeMode {
    MINUTES,
    SECONDS
}