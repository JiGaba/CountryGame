package com.example.countrygame.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.countrygame.domain.CountryData

@Entity(tableName = "country_data")
data class CountryDataDTO (
    @PrimaryKey
    val country: String,
    val region: String
)

fun CountryDataDTO.mapToDomain() : CountryData{
    return CountryData(
        country = this.country,
        region = this.region
    )
}