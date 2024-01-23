package com.example.countrygame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CountryViewModelFactory(private val repository: Repository)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CountryGameViewModel::class.java)){
            return  CountryGameViewModel(repository) as T
        }
        throw IllegalArgumentException("Wrong ViewModel class")
    }
}