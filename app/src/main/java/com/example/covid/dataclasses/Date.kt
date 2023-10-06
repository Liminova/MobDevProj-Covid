package com.example.covid.dataclasses

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

class Date(
    private val year: Int = 0, private val month: Int = 0, private val day: Int = 0
) {
    fun epochDateToString(epochDate: Long): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = epochDate * 1000
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(calendar.time)
    }

    fun intToEpochDate(): Long {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.set(year, month - 1, day, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis / 1000
    }
}