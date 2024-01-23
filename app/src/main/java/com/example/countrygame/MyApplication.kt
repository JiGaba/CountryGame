package com.example.countrygame

import android.app.Application
import com.example.countrygame.api.CountryApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    val apiService: CountryApiService by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.first.org/data/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(CountryApiService::class.java)
    }

    val repository: Repository by lazy{
        Repository(apiService)
    }

    override fun onCreate() {
        super.onCreate()
    }
}