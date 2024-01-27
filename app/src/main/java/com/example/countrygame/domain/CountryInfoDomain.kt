package com.example.countrygame.domain

data class CountryInfo(
    val status: String,
    val statusCode: Int,
    val version: String,
    val access: String,
    val countries: List<CountryData>
)

data class CountryData(
    val country: String,
    val region: String
)