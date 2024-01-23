package com.example.countrygame.model

data class CountryInfo(
    val status: String,
    val statusCode: Int,
    val version: String,
    val access: String,
    val data: Map<String, CountryData>
)

data class CountryData(
    val country: String,
    val region: String
)