package com.example.countrygame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrygame.domain.CountryInfo
import kotlinx.coroutines.launch

class CountryGameViewModel(private val repository: Repository)
    : ViewModel() {
    private val _startInfo = "Hru zahájíte tlačítnem další..."
    private var _start = MutableLiveData<Boolean>()
    private var _guestionNumber = 0
    val start: LiveData<Boolean> get() = _start
    private val _countryInfoValue = MutableLiveData<CountryInfo>()
    val countryInfoValue: LiveData<CountryInfo> = _countryInfoValue
    private val _actualCountry = MutableLiveData<String>()
    val actualCountry: LiveData<String> = _actualCountry
    fun setActualCountry(value: String) {
        _actualCountry.value = value
    }
    fun setStart(value: Boolean) {
        _start.value = value
    }
    fun getSubjectInfo(regions: List<String>, limit: String, pretty: Boolean){
        viewModelScope.launch {
            val result = repository.getCountryInfo(regions, limit, pretty)
            _countryInfoValue.postValue(result)

            _actualCountry.value = _startInfo
        }
    }

    fun checkButtonClick(){
        if(_guestionNumber == 0){
            _guestionNumber++
            _start.value = true
        }
        //Log.v("Teststs", "sdsdd")
        //_actualCountry.value = _countryInfoValue.value?.countries?.get(0)?.country
    }

    fun radioClick(index: Int){


        //Log.v("Teststs88888888", index.toString())
        //_actualCountry.value = _countryInfoValue.value?.countries?.get(0)?.country
    }
}