package com.example.countrygame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrygame.domain.CountryInfo
import kotlinx.coroutines.launch

class CountryGameViewModel(private val repository: Repository)
    : ViewModel() {

    private val _countryInfoValue = MutableLiveData<CountryInfo>()
    val countryInfoValue: LiveData<CountryInfo> = _countryInfoValue
    fun getSubjectInfo(regions: List<String>, limit: String, pretty: Boolean){
        viewModelScope.launch {
            val result = repository.getCountryInfo(regions, limit, pretty)
            _countryInfoValue.postValue(result)
        }
    }
}