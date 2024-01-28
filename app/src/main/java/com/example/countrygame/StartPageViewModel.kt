package com.example.countrygame

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartPageViewModel : ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    // Veřejná LiveData pro přístup z aktivity
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    // Metoda pro změnu hodnoty proměnné
    fun setShowDialog(newValue: Boolean) {
        _showDialog.value = newValue
    }

    val checkBoxList: List<ObservableBoolean> = List(8) {
        ObservableBoolean(false)
    }
    fun onCheckBoxClicked(pos: Int) {
        checkBoxList[pos-1].set(!checkBoxList[pos-1].get())
    }
    fun onButtonStartClick() {
        var checkBoxCheckedCount = 0
        checkBoxList.forEach{ c ->
            if(c.get()) checkBoxCheckedCount++
        }

        if(checkBoxCheckedCount <= 1)
            _showDialog.value = true
            //Log.v("count: ", checkBoxCheckedCount.toString())
        Log.d("MyViewModel ", checkBoxList[0].get().toString())
        Log.d("MyViewModel JA ", checkBoxList[1].get().toString())

    }
}