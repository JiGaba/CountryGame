package com.example.countrygame.database

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.countrygame.api.CountryInfoNetwork
import com.example.countrygame.api.mapToDTOList
import com.example.countrygame.domain.CountryData
import com.example.countrygame.domain.CountryInfo

@Entity(tableName = "country_data")
data class CountryDataDTO (
    @PrimaryKey
    val country: String,
    val region: String
)

fun List<CountryDataDTO>.mapToDomain() : CountryInfo{
    val countryList = mutableListOf<CountryData>()

    this.forEach { c ->
        countryList.add(CountryData(c.country, c.region))
    }

    return CountryInfo(
        status = "DB",
        countries = countryList
    )
}
