package com.example.countrygame

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartPageViewModel : ViewModel() {
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
            // dialog

        Log.v("count: ", checkBoxCheckedCount.toString())
        Log.d("MyViewModel ", checkBoxList[0].get().toString())
        Log.d("MyViewModel JA ", checkBoxList[1].get().toString())

    }
}