package com.example.countrygame

import com.example.countrygame.api.CountryApiService
import com.example.countrygame.model.CountryInfo

class Repository(private val apiService: CountryApiService) {
    suspend fun getCountryInfo(
        region: String, limit: String, pretty: Boolean) : CountryInfo? {
        val response = apiService.getCountryInfo(region, limit, pretty)

        if(response.isSuccessful){
            return  response.body()
        }else{
            return  null
        }
    }
}