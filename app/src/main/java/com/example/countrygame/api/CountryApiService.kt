package com.example.countrygame.api

import com.example.countrygame.domain.CountryInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApiService {
    companion object{
        const val COUNTRY_INFO_ENDPOINT = "countries"
    }

    @GET(COUNTRY_INFO_ENDPOINT)
    suspend fun getCountryInfo(
        @Query("region") region: String,
        @Query("limit") limit: String,
        @Query("pretty") pretty: Boolean
    ) : Response<CountryInfo>
}