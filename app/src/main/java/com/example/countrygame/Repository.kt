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
import retrofit2.Response

class Repository(
    private val apiService: CountryApiService,
    private val database: MyRoomDatabase
    ) {
    suspend fun getCountryInfo(
        regions: List<String>, limit: String, pretty: Boolean) : CountryInfo? {
        /*val response = apiService.getCountryInfo(region, limit, pretty)

        if(response.isSuccessful){
            val countryInfoNetwork = response.body()
            return  countryInfoNetwork?.mapToDomain()
        }else{
            return  null
        }*/
        // get data from REST API and cache them to DB
        refreshCountries(regions, limit, pretty)

        // return FLOW of SubjectInfoDomain from database to ViewModel
        return database.countryDataDao.getAllCountryData().mapToDomain()
            //.map { it.mapToDomain() }
    }

    suspend fun refreshCountries(regions: List<String>, limit: String, pretty: Boolean) {
        try {
            // call REST API service to response
            val completeResponse = loadAllCountry(regions, limit, pretty)
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

    suspend fun loadAllCountry(regions: List<String>, limit: String, pretty: Boolean) : Response<CountryInfoNetwork>?{

        var completeResponse : Response<CountryInfoNetwork>? = null
        var index = 0

        regions.forEach{region ->
            var apiResponse = apiService.getCountryInfo(region, limit, pretty)
            Log.v("delka pred ",completeResponse?.body()?.data?.count().toString())
            if(apiResponse.isSuccessful()){
                if(index == 0){
                    index++
                    completeResponse = apiResponse
                }else{
                    val tempNetwork: CountryInfoNetwork? = apiResponse.body()
                    val tempComplete : CountryInfoNetwork? = completeResponse?.body()
                    Log.v("t1 ",tempNetwork?.data?.count().toString())
                    Log.v("t2 ",tempComplete?.data?.count().toString())
                    if(tempNetwork != null && tempComplete != null)
                        completeResponse?.body()?.data = tempComplete.data + tempNetwork.data
                }
            }

            Log.v("delka ",completeResponse?.body()?.data?.count().toString())
        }

        return  completeResponse
    }
}