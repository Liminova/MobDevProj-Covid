package com.example.covid.dataclasses

import com.squareup.moshi.Json

data class Report(
    @Json(name = "Date") val date: String,
    @Json(name = "NewCases") val newCases: Int,
    @Json(name = "NewDeaths") val newDeaths: Int,
    @Json(name = "CumulativeCases") val cumulativeCases: Int,
    @Json(name = "CumulativeDeaths") val cumulativeDeaths: Int
)