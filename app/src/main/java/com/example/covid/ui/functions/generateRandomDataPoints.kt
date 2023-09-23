package com.example.covid.ui.functions

import com.patrykandpatrick.vico.core.entry.FloatEntry

fun generateRandomDataPoints(): List<FloatEntry> {
    val dataPoints = arrayListOf<FloatEntry>()
    var xPos = 0f
    for (i in 1..100) {
        val randomYFloat = (1..1000).random().toFloat()
        dataPoints.add(FloatEntry(x = xPos, y = randomYFloat))
        xPos += 1f
    }
    return dataPoints
}