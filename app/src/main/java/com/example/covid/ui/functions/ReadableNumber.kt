package com.example.covid.ui.functions

fun ReadableNumber(
    number: Number = 123456789,
    delimiter: String = ",",
): String {
    val numberString = number.toString()
    val numberStringReversed = numberString.reversed()
    val numberStringReversedWithCommas =
        numberStringReversed.chunked(3).joinToString(delimiter)
    return numberStringReversedWithCommas.reversed()
}