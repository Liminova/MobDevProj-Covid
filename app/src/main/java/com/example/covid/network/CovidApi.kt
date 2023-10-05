package com.example.covid.network

import com.example.covid.dataclasses.CountryData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://covid.delnegend.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

// Defines how the data is retrieved from the endpoint
// Functions to call in our code to get the data
// A class but the body is unnecessary -> use interface
interface CovidApiService {
    @GET("/countries")
    suspend fun getCountries(): Map<String, String>

    @GET("/countries/{countryId}")
    suspend fun getCountryData(@Path("countryId") countryId: String): CountryData
}

// This makes sure that we only have one object of this class in our app
// because calling retrofit.create is resource intensive
object CovidApi {
    val retrofitService: CovidApiService by lazy {
        retrofit.create(CovidApiService::class.java)
    }
}