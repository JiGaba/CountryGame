package com.example.countrygame.domain

data class CountryInfo(
    val status: String,
    val countries: List<CountryData>
)

data class CountryData(
    val country: String,
    val region: String
)