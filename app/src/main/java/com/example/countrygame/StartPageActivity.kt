package com.example.countrygame

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.countrygame.databinding.ActivityStartPageBinding
import com.example.countrygame.domain.Regions

class StartPageActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityStartPageBinding
    private lateinit var viewModel: StartPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityStartPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(StartPageViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.showDialog.observe(this, Observer { d ->
            // Zde provedete akce při změně hodnoty proměnné
            if(d) showAlertDialog()
        })

        viewModel.showGame.observe(this, Observer { g ->
            // Zde provedete akce při změně hodnoty proměnné
            if(g){
                var index = 0
                val continentList: MutableList<String> = mutableListOf()

                // získání kontinentů pro nastavení hry
                viewModel.checkBoxList.forEach{l ->
                    if(l.get()){
                        continentList.add(Regions.getRegionList[index])
                    }
                    index++
                }

                val stringArray: Array<String?> = continentList.toTypedArray()
                val intent = Intent(this, CountryGameActivity::class.java)
                intent.putExtra("setupData", stringArray)
                startActivity(intent)
                viewModel.setShowGame(false)
            }
        })
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Upozornění")
        alertDialogBuilder.setMessage("Pro pokračování musíte vybrat dva a více kontinentů.")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            // Kód, který se provede po stisknutí tlačítka OK
            viewModel.setShowDialog(false)
            dialog.dismiss() // Zavře dialog
        }

        // Volání metody pro zobrazení alertu
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}