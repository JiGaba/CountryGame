package com.example.countrygame.api

import com.example.countrygame.database.CountryDataDTO
import com.example.countrygame.domain.CountryData
import com.example.countrygame.domain.CountryInfo

data class CountryInfoNetwork(
    val status: String,
    val statusCode: Int,
    val version: String,
    val access: String,
    var data: Map<String, CountryDataNetwork>
)

data class CountryDataNetwork(
    val country: String,
    val region: String
)

fun CountryInfoNetwork.mapToDomain(): CountryInfo {
    val countryList = mutableListOf<CountryData>()

    this.data.forEach { (countryCode, countryData) ->
        countryList.add(CountryData(countryData.country, countryData.region))
    }

    return CountryInfo(
        status = this.status,
        statusCode = this.statusCode,
        version = this.version,
        access = this.access,
        countries = countryList
    )
}

fun CountryInfoNetwork.mapToDTOList(): List<CountryDataDTO> {
    val countryList = mutableListOf<CountryDataDTO>()

    this.data.forEach { (countryCode, countryData) ->
        countryList.add(CountryDataDTO(countryData.country, countryData.region))
    }

    return countryList
}