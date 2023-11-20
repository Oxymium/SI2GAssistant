package com.oxymium.si2gassistant.ui.theme

import java.util.Calendar

class DateUtils {
    companion object {
        @JvmStatic
        fun convertMillisToDate(millis: Long): String {
            val calendar = Calendar.getInstance()
            with(calendar) {
                timeInMillis = millis // set the calendar to the time in millis
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
                    0 -> "Jan"// first month is index 0
                    1 -> "Feb"
                    2 -> "Mar"
                    3 -> "Apr"
                    4 -> "May"
                    5 -> "Jun"
                    6 -> "Jul"
                    7 -> "Aug"
                    8 -> "Sep"
                    9 -> "Oct"
                    10 -> "Nov"
                    11 -> "Dec"
                    else -> "Err"
                }
            }

            return "$day $month $year"

        }
    }
}