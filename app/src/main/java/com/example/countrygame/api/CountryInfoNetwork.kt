package com.example.countrygame.api

data class CountryInfoNetwork(
    val status: String,
    val statusCode: Int,
    val version: String,
    val access: String,
    val data: Map<String, CountryDataNetwork>
)

data class CountryDataNetwork(
    val country: String,
    val region: String
)