package com.example.covid.dataclasses

import com.squareup.moshi.Json

data class CountryData(
    @Json(name = "CountryName") val countryName: String,
    @Json(name = "WHORegion") val whoRegion: String,
    @Json(name = "Reports") val reports: List<Report>
)