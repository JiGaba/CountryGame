package com.example.countrygame

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartPageViewModel : ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    private val _showGame = MutableLiveData<Boolean>()
    // Veřejná LiveData pro přístup z aktivity
    val showDialog: LiveData<Boolean> get() = _showDialog
    val showGame: LiveData<Boolean> get() = _showGame
    val checkBoxList: List<ObservableBoolean> = List(8) {
        ObservableBoolean(false)
    }
    // Metoda pro změnu hodnoty proměnné
    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    fun setShowGame(value: Boolean) {
        _showGame.value = value
    }
    fun onCheckBoxClicked(pos: Int) {
        checkBoxList[pos-1].set(!checkBoxList[pos-1].get())
    }
    fun onButtonStartClick() {
        var checkBoxCheckedCount = 0
        checkBoxList.forEach{ c ->
            if(c.get()) checkBoxCheckedCount++
        }

        if(checkBoxCheckedCount <= 1){
            _showDialog.value = true
        }else{
            _showGame.value = true
        }
    }
}