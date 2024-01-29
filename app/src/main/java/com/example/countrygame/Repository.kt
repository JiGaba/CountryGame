package com.example.countrygame

import android.util.Log
import com.example.countrygame.api.CountryApiService
import com.example.countrygame.api.CountryInfoNetwork
import com.example.countrygame.api.mapToDTOList
import com.example.countrygame.api.mapToDomain
import com.example.countrygame.database.MyRoomDatabase
import com.example.countrygame.database.mapToDomain
import com.example.countrygame.domain.CountryInfo
import com.example.countrygame.domain.Regions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repository(
    private val apiService: CountryApiService,
    private val database: MyRoomDatabase
    ) {
    suspend fun getCountryInfo(
        regions: List<String>, limit: String, pretty: Boolean) : CountryInfo? {

        // check number of elements in DB
        val dataCount: Int
        withContext(Dispatchers.IO) {
            dataCount = database.countryDataDao.getDataCount()
        }

        // load data from REST API and cache them to DB
        // load only if not exists in database
        if(dataCount != Regions.STATES_COUNT)
            refreshCountries(regions, limit, pretty)

        // return object from database to ViewModel
        return database.countryDataDao.getAllCountryData().mapToDomain()
    }

    suspend fun refreshCountries(regions: List<String>, limit: String, pretty: Boolean) {
        try {
            // call REST API service to response
            // load all country
            val completeResponse = loadAllCountry(limit, pretty)
            val countryInfoNetwork: CountryInfoNetwork? = completeResponse?.body()

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

    suspend fun loadAllCountry(limit: String, pretty: Boolean) : Response<CountryInfoNetwork>?{
        val regions = Regions.getRegionList
        var completeResponse : Response<CountryInfoNetwork>? = null
        var index = 0

        regions.forEach{region ->
            var apiResponse = apiService.getCountryInfo(region, limit, pretty)

            if(apiResponse.isSuccessful()){
                if(index == 0){
                    index++
                    completeResponse = apiResponse
                }else{
                    val tempNetwork: CountryInfoNetwork? = apiResponse.body()
                    val tempComplete : CountryInfoNetwork? = completeResponse?.body()

                    if(tempNetwork != null && tempComplete != null)
                        completeResponse?.body()?.data = tempComplete.data + tempNetwork.data
                }
            }
        }

        return  completeResponse
    }
}