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
    private var _lock = MutableLiveData<Boolean>()
    private var _response = MutableLiveData<Boolean>()
    private var _doDefault = MutableLiveData<Boolean>()
    private var _score = MutableLiveData<String>()
    private var _backToSetting = MutableLiveData<Boolean>()
    private var _guestionNumber = 0
    public var responseNumber = 0
    val start: LiveData<Boolean> get() = _start
    val lock: LiveData<Boolean> get() = _lock
    val doDefault: LiveData<Boolean> get() = _doDefault
    val response: LiveData<Boolean> get() = _response
    val backToSetting: LiveData<Boolean> get() = _backToSetting
    val score: LiveData<String> get() = _score
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
    fun setResponse(value: Boolean) {
        _response.value = value
    }
    fun setLock(value: Boolean) {
        _lock.value = value
    }
    fun setDoDefault(value: Boolean) {
        _doDefault.value = value
    }
    fun setScore(value: String) {
        _score.value = value
    }
    fun setBackToSetting(value: Boolean) {
        _backToSetting.value = value
    }
    fun getSubjectInfo(regions: List<String>, limit: String, pretty: Boolean){
        viewModelScope.launch {
            val result = repository.getCountryInfo(regions, limit, pretty)
            _countryInfoValue.postValue(result)

            _score.value = "Score: 0"
            _actualCountry.value = _startInfo
            _lock.value = true
        }
    }

    fun checkButtonClick(){
        if(_guestionNumber == 0){
            _guestionNumber++
            _start.value = true
            _lock.value = false
        }else{
            _guestionNumber++
            _doDefault.value = true
        }
    }

    fun radioClick(index: Int){
        if(_start.value == true){
            responseNumber = index
            _response.value = true
            _lock.value = true
        }
    }

    fun backButtonClick(){
       _backToSetting.value = true
    }
}