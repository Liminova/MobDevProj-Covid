package com.example.covid.dataclasses

data class Date(
    val year: Int,
    val month: Int,
    val day: Int
) {
    override fun toString(): String {
        return "$year-$month-$day"
    }

    fun toFloat(): Float {
        return (year * 10000 + month * 100 + day).toFloat()
    }

    fun toInt(): Int {
        return (year * 10000 + month * 100 + day)
    }

    fun toEpochDay(): Long {
        val daysInMonth = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> 28
            else -> 0
        }
        return (year * 365 + month * daysInMonth + day).toLong()
    }
}
