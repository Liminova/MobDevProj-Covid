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
}
