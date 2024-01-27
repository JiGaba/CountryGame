package com.example.countrygame

import com.example.countrygame.api.CountryApiService
import com.example.countrygame.api.CountryInfoNetwork
import com.example.countrygame.api.mapToDomain
import com.example.countrygame.domain.CountryInfo

class Repository(private val apiService: CountryApiService) {
    suspend fun getCountryInfo(
        region: String, limit: String, pretty: Boolean) : CountryInfo? {
        val response = apiService.getCountryInfo(region, limit, pretty)

        if(response.isSuccessful){
            val countryInfoNetwork = response.body()
            return  countryInfoNetwork?.mapToDomain()
        }else{
            return  null
        }
    }
}