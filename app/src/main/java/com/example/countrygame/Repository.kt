package com.example.countrygame

import android.util.Log
import com.example.countrygame.api.CountryApiService
import com.example.countrygame.api.CountryInfoNetwork
import com.example.countrygame.api.mapToDTOList
import com.example.countrygame.api.mapToDomain
import com.example.countrygame.database.MyRoomDatabase
import com.example.countrygame.database.mapToDomain
import com.example.countrygame.domain.CountryInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val apiService: CountryApiService,
    private val database: MyRoomDatabase
    ) {
    suspend fun getCountryInfo(
        region: String, limit: String, pretty: Boolean) : CountryInfo? {
        /*val response = apiService.getCountryInfo(region, limit, pretty)

        if(response.isSuccessful){
            val countryInfoNetwork = response.body()
            return  countryInfoNetwork?.mapToDomain()
        }else{
            return  null
        }*/
        // get data from REST API and cache them to DB
        refreshCountries(region, limit, pretty)

        // return FLOW of SubjectInfoDomain from database to ViewModel
        return database.countryDataDao.getAllCountryData().mapToDomain()
            //.map { it.mapToDomain() }
    }

    suspend fun refreshCountries(region: String, limit: String, pretty: Boolean) {
        try {
            // call REST API service to response
            val apiResponse = apiService.getCountryInfo(region, limit, pretty)
            // parse SubjectInfoNetwork from response body
            val countryInfoNetwork: CountryInfoNetwork? = apiResponse.body()

            if(countryInfoNetwork != null) {
                // convert network model from REST API to DB entity
                val countryDataDTOList = countryInfoNetwork.mapToDTOList()

                // save to Room database
                withContext(Dispatchers.IO) {
                    database.countryDataDao.insertAll(countryDataDTOList)
                }
            } else {
                // no data found on REST API
                Log.v("MYAPP", "Not found on REST API")
            }
        } catch (e: Exception) {
            // Handle PI call errors
            Log.e("MYAPP", "Error refreshing subjects " + e.localizedMessage)
        }
    }
}